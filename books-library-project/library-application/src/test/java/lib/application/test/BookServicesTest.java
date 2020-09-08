package lib.application.test;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import lib.dto.BookDTO;
import lib.service.api.BookService;
import lib.service.api.exception.BookException;

@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureTestDatabase
@DataJpaTest
public class BookServicesTest 
{
	@Autowired
	@Qualifier("bookServiceInProd")
	private BookService bookService;
	
//	@MockBean
//	BookRepository bR;
//	@Test
//	public void sometest()
//	{
//		List<BookDTO> testBooks = new ArrayList<BookDTO>();
//		testBooks.add(new BookDTO(899L, "Title-1", "Author-1", "Publisher-1"));
//		testBooks.add(new BookDTO(658L, "Title-2", "Author-2", "Publisher-2"));
//		Mockito.when(bR.getBooksByTitle("Spring Boot in Action")).thenReturn(testBooks);
//		
//		BookDTO bookInDb = bookService.addBook(1617292540L, "Spring Boot in Action", "Craig Walls", "Manning Publications");
//		List<BookDTO> recievedBooks = bookService.getBooksByTitle("Spring Boot in Action");
//		recievedBooks.forEach(System.out::println);
//	}
	
//	@Test
//	public void shouldAddFirstBookSuccessfullyUsingDTO()
//	{
//		BookDTO book = bookService.addBookDTO(1617292540L, "Spring Boot in Action", "Craig Walls", "Manning Publications");
//		Assert.assertNotNull(book);
//		Assert.assertEquals(book.getIsbn().longValue(), 1617292540L);
//		Assert.assertEquals(book.getTitle(), "Spring Boot in Action");
//		Assert.assertEquals(book.getAuthor(), "Craig Walls");
//		Assert.assertEquals(book.getPublisher(), "Manning Publications");
//	}
//	
	@Test
	public void shouldAddFirstBookSuccessfully() throws BookException
	{
		BookDTO book = bookService.addBook(1617292540L, "Spring Boot in Action", "Craig Walls", "Manning Publications");
		Assert.assertNotNull(book);
		Assert.assertEquals(book.getIsbn().longValue(), 1617292540L);
		Assert.assertEquals(book.getTitle(), "Spring Boot in Action");
		Assert.assertEquals(book.getAuthor(), "Craig Walls");
		Assert.assertEquals(book.getPublisher(), "Manning Publications");
	}
	
	@Test
	public void shouldAddSecondBookSuccessfully() throws BookException
	{
		BookDTO book1 = bookService.addBook(1617292540L, "Spring Boot in Action", "Craig Walls", "Manning Publications");
		BookDTO book2 =  bookService.addBook(618260307L, "The Hobbit", "J. R. R. Tolkien", "Houghton Mifflin USA");
		Assert.assertNotNull(book2);
		Assert.assertEquals(book2.getIsbn().longValue(), 618260307L);
		Assert.assertEquals(book2.getTitle(), "The Hobbit");
		Assert.assertEquals(book2.getAuthor(), "J. R. R. Tolkien");
		Assert.assertEquals(book2.getPublisher(), "Houghton Mifflin USA");
	}
	
	@Test
	public void shouldRetrieveBookByIsbnSuccessfully() throws BookException
	{
		bookService.addBook(1617292540L, "Spring Boot in Action", "Craig Walls", "Manning Publications");
		Assert.assertNotNull(bookService.getBookByIsbn(1617292540L));
	}
	
	@Test
	public void shouldRetrieveBookByTitleSuccessfully() throws BookException
	{
		bookService.addBook(1617292540L, "Spring Boot in Action", "Craig Walls", "Manning Publications");
		List<BookDTO> booksByTitle = bookService.getBooksByTitle("Spring Boot in Action");
		Assert.assertTrue(booksByTitle.size() == 1);
		Assert.assertNotNull(booksByTitle.get(0));
	}
	
	@Test
	public void shouldRetrieveBookByAuthorSuccessfully() throws BookException
	{
		bookService.addBook(1617292540L, "Spring Boot in Action", "Craig Walls", "Manning Publications");
		List<BookDTO> booksByAuthor = bookService.getBooksByAuthor("Craig Walls");
		Assert.assertTrue(booksByAuthor.size() == 1);
		Assert.assertNotNull(booksByAuthor.get(0));
	}
	
	@Test
	public void shouldRetrieveBookByPublisherSuccessfully() throws BookException
	{
		bookService.addBook(1617292540L, "Spring Boot in Action", "Craig Walls", "Manning Publications");
		List<BookDTO> booksByPublisher = bookService.getBooksByPublisher("Manning Publications");
		Assert.assertTrue(booksByPublisher.size() == 1);
		Assert.assertNotNull(booksByPublisher.get(0));
	}
	
	@Test
	public void shouldRetrieveAllBooksSuccessfully() throws BookException
	{
		bookService.addBook(1617292540L, "Spring Boot in Action", "Craig Walls", "Manning Publications");
		bookService.addBook(618260307L, "The Hobbit", "J. R. R. Tolkien", "Houghton Mifflin USA");
		
		List<BookDTO> allBooks = bookService.getAllBooks();
		Assert.assertTrue(allBooks.size() == 2);
		allBooks.forEach(book->Assert.assertNotNull(book));
	}
	
	@Test
	public void shouldRetrieveAllBookCountsSuccessfully() throws BookException
	{
		bookService.addBook(1617292540L, "Spring Boot in Action", "Craig Walls", "Manning Publications");
		bookService.addBook(618260307L, "The Hobbit", "J. R. R. Tolkien", "Houghton Mifflin USA");
		Assert.assertTrue(bookService.getAllBookCounts() == 2);
	}
	
	@Test
	public void shouldDeleteBookSuccessfully() throws BookException
	{
		bookService.addBook(618260307L, "The Hobbit", "J. R. R. Tolkien", "Houghton Mifflin USA");
		bookService.deleteBookByIsbn(618260307L);
		
		Assert.assertThat(bookService.getBooksByTitle("The Hobbit").size(), CoreMatchers.equalTo(0));
		Assert.assertThat(bookService.getBooksByAuthor("J. R. R. Tolkien").size(), CoreMatchers.equalTo(0));
		Assert.assertThat(bookService.getBooksByPublisher("Houghton Mifflin USA").size(), CoreMatchers.equalTo(0));
		Assert. assertThat(bookService.getBookByIsbn(618260307L), CoreMatchers.nullValue());
		Assert.assertThat(bookService.getAllBookCounts(), CoreMatchers.equalTo(0L));
		Assert.assertThat(bookService.getAllBooks().size(), CoreMatchers.equalTo(0));
	}
	
}
