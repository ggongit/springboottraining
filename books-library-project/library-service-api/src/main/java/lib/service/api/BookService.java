package lib.service.api;

import java.util.List;

import org.springframework.stereotype.Service;

import lib.dto.BookDTO;


@Service
public interface BookService
{
	public BookDTO addBook(Long isbn, String title, String author, String publisher);
	
	public List<BookDTO> getAllBooks();
	public BookDTO getBookByIsbn(Long isbn);
	public List<BookDTO> getBooksByTitle(String title);
	public List<BookDTO> getBooksByAuthor(String author);
	public List<BookDTO> getBooksByPublisher(String publisher);
	public long getAllBookCounts();
	public List<BookDTO> getBooksByAuthorList(List<String> authors);

	public List<BookDTO> getBooksByTitleContains(String keyword);

	public boolean deleteBookByIsbn(Long isbn);
	public void deleteAllRecords();
}
