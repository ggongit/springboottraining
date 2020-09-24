package lib.service.api.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lib.book.repository.BookRepository;
import lib.book.repository.BookServerInfoRepository;
import lib.dto.BookDTO;
import lib.model.Book;
import lib.model.BookServerInfo;
import lib.server.rest.pojo.BookListResponse;
import lib.server.rest.pojo.SingleBookResponse;
import lib.server.wrapper.BookWrapperService;
import lib.server.wrapper.exceptions.ServerException;
import lib.service.api.BookService;
import lib.service.api.exception.BookException;
import lib.service.mapper.BookEntityMapper;

@Service("bookServiceInProd")
public class BookServiceImpl implements BookService {
	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BookServerInfoRepository bookServerInfoRepo;
	
	@Autowired
	private BookWrapperService bookServerService;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public BookDTO addBook(Long iSBN, String title, String author, String publisher) throws BookException {
		if (getBookByIsbn(iSBN) != null) {
			throw new BookException("Book with given ISBN already exists: " + iSBN);
		}

		Book book = bookRepository.save(new Book(iSBN, title, author, publisher));
		return convertBookToBookDTO(book);
	}

	@Override
	public BookDTO getBookByIsbn(Long iSBN) {
		return convertBookToBookDTO(bookRepository.getBookByIsbn(iSBN));
	}

	@Override
	public List<BookDTO> getBooksByTitle(String title) {
		return bookRepository.getBooksByTitle(title).stream().map(book -> convertBookToBookDTO(book))
				.collect(Collectors.toList());
	}

	@Override
	public List<BookDTO> getBooksByAuthor(String author) {
		return bookRepository.getBooksByAuthor(author).stream().map(book -> convertBookToBookDTO(book))
				.collect(Collectors.toList());
	}

	@Override
	public List<BookDTO> getBooksByPublisher(String publisher) {
		return bookRepository.getBooksByPublisher(publisher).stream().map(book -> convertBookToBookDTO(book))
				.collect(Collectors.toList());
	}

	@Override
	public long getAllBookCounts() {
		return bookRepository.count();
	}

	@Override
	public List<BookDTO> getAllBooks() {
		Iterable<Book> bookIterable = bookRepository.findAll();
		return StreamSupport.stream(bookIterable.spliterator(), false).map(book -> convertBookToBookDTO(book))
				.collect(Collectors.toList());
	}

	@Override
	public boolean deleteBookByIsbn(Long isbn) {
		int result = bookRepository.deleteBookByIsbn(isbn);
		return result > 0;
	}

	@Override
	public List<BookDTO> getBooksByTitleContains(String keyword) {
		return bookRepository.getBooksByTitleContains(keyword).stream().map(book -> convertBookToBookDTO(book))
				.collect(Collectors.toList());
	}

	@Override
	public void deleteAllRecords() {
		bookRepository.deleteAll();
	}

	private BookDTO convertBookToBookDTO(Book book) {
		
		if(book == null) {
			return null;
		}
		
		BookDTO bookDTO = BookEntityMapper.INSTANCE.bookToBookDTO(book);
		BookServerInfo bookServerInfo = bookServerInfoRepo.findByBook_idIn(book.getId());
		bookDTO.setRepoId(bookServerInfo != null ? bookServerInfo.getRepoId() : "NA");
		return bookDTO;
	}
	
	private Book convertBookDTOToBook(BookDTO book) {
		return BookEntityMapper.INSTANCE.bookDTOToBook(book);
	}

	@Override
	public List<BookDTO> getBooksByAuthorList(List<String> authors) {
//		return bookRepository.findAllByAuthor(authors)
//				.stream()
//				.map(book->convertBookToBookDTO(book))
//				.collect(Collectors.toList());
		List<BookDTO> bookList = new ArrayList<>();
		authors.stream().forEach(auth -> bookList.addAll(getBooksByAuthor(auth)));
		return bookList;
	}

	@Override
	public void saveBookInServer(BookDTO bookDTO) throws ServerException, BookException {
		if(bookDTO == null)
		{
			throw new BookException("Given book is NULL");
		}
		BookServerInfo bookInfo = bookServerInfoRepo.findByBook_idIn(bookDTO.getId());
		
		if(bookInfo != null)
		{
			throw new ServerException("Book already saved in server");
		}
		
		SingleBookResponse bookResponse = bookServerService.saveBook(bookDTO.getIsbn(), bookDTO.getTitle(),
				bookDTO.getAuthor(), bookDTO.getPublisher());
		
		// Update repo id mapping in local db
		saveBookServerInfo(bookDTO.getId(), bookResponse.getRepoId());
	}

	@Override
	public void login(String url, String username, String password) throws ServerException {
		bookServerService.login(url, username, password);
	}

	@Override
	public void logout() throws ServerException {
		bookServerService.logout();
	}

	@Override
	public List<BookDTO> getAllBooksFromServer() throws ServerException {
		List<BookDTO> books = new ArrayList<BookDTO>();
		BookListResponse bookListResponse = bookServerService.getAllBooks();
		bookListResponse.getBookResponseList().forEach(singleRes->{
			BookDTO book = new BookDTO(singleRes.getIsbn(), singleRes.getTitle(), singleRes.getAuthor(), singleRes.getPublisher());
			book.setRepoId(singleRes.getRepoId());
			books.add(book);
		});
		return books;
	}

	@Override
	public BookDTO downloadBookFromServer(Long isbn) throws ServerException, BookException {
		
		if (getBookByIsbn(isbn) != null) {
			throw new BookException("Book with given ISBN already exists locally: " + isbn);
		}
		
		BookListResponse bookListResponse = bookServerService.fetchBookFromServerByIsbn(isbn);
		
		if(bookListResponse.getBookResponseList().size() == 0)
		{
			throw new BookException("Book with given ISBN not found in server: " + isbn);
		}
		
		SingleBookResponse bookResponse = bookListResponse.getBookResponseList().get(0);
		
		// Add book into local db
		BookDTO book = addBook(isbn, bookResponse.getTitle(), bookResponse.getAuthor(), bookResponse.getPublisher());
		book.setRepoId(bookResponse.getRepoId());
		saveBookServerInfo(book.getId(), book.getRepoId());
		return book;
	}
	
	private void saveBookServerInfo(int bookLocalId, String bookRepoId)
	{
		//Book book = convertBookDTOToBook(bookDTO);
		Book book  = entityManager.find(Book.class, bookLocalId);
		BookServerInfo bookServerInfo = new BookServerInfo(book, bookRepoId); 
		bookServerInfoRepo.save(bookServerInfo);
	}
}
