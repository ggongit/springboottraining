package lib.service.api.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lib.book.repository.BookRepository;
import lib.dto.BookDTO;
import lib.model.Book;
import lib.service.api.BookService;
import lib.service.mapper.BookEntityMapper;

@Service("bookServiceInProd")
public class BookServiceImpl implements BookService
{
	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public BookDTO addBook(Long iSBN, String title, String author, String publisher)
	{
		Book book = bookRepository.save(new Book(iSBN, title, author, publisher));
		return convertBookToBookDTO(book);
	}

	@Override
	public BookDTO getBookByIsbn(Long iSBN)
	{
		return convertBookToBookDTO(bookRepository.getBookByIsbn(iSBN));
	}

	@Override
	public List<BookDTO> getBooksByTitle(String title)
	{
		return bookRepository.getBooksByTitle(title)
				.stream()
				.map(book->convertBookToBookDTO(book))
				.collect(Collectors.toList());
	}

	@Override
	public List<BookDTO> getBooksByAuthor(String author)
	{
		return bookRepository.getBooksByAuthor(author)
				.stream()
				.map(book->convertBookToBookDTO(book))
				.collect(Collectors.toList());
	}

	@Override
	public List<BookDTO> getBooksByPublisher(String publisher)
	{
		return bookRepository.getBooksByPublisher(publisher)
				.stream()
				.map(book->convertBookToBookDTO(book))
				.collect(Collectors.toList());
	}

	@Override
	public long getAllBookCounts() 
	{
		return bookRepository.count();
	}

	@Override
	public List<BookDTO> getAllBooks() 
	{
		Iterable<Book> bookIterable = bookRepository.findAll();
		return StreamSupport.stream(bookIterable.spliterator(), false)
				.map(book->convertBookToBookDTO(book))
				.collect(Collectors.toList());
	}

	@Override
	public boolean deleteBookByIsbn(Long isbn) 
	{
		int result = bookRepository.deleteBookByIsbn(isbn);
		return result > 0;
	}

	@Override
	public List<BookDTO> getBooksByTitleContains(String keyword) 
	{
		return bookRepository.getBooksByTitleContains(keyword)
				.stream()
				.map(book->convertBookToBookDTO(book))
				.collect(Collectors.toList());
	}

	@Override
	public void deleteAllRecords() 
	{
		bookRepository.deleteAll();
	}

	private BookDTO convertBookToBookDTO(Book book)
	{
		return BookEntityMapper.INSTANCE.bookToBookDTO(book);
	}

	@Override
	public List<BookDTO> getBooksByAuthorList(List<String> authors) 
	{
//		return bookRepository.findAllByAuthor(authors)
//				.stream()
//				.map(book->convertBookToBookDTO(book))
//				.collect(Collectors.toList());
		List<BookDTO> bookList = new ArrayList<>();
		authors.stream().forEach(auth->bookList.addAll(getBooksByAuthor(auth)));
		return bookList;
	}
}
