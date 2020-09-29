package lib.server.wrapper.integtest;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.Assert;

import lib.server.ServerWrapperApplication;
import lib.server.rest.pojo.BookListResponse;
import lib.server.rest.pojo.SingleBookResponse;
import lib.server.wrapper.BookWrapperService;
import lib.server.wrapper.exceptions.ServerException;

@ContextConfiguration (classes = ServerWrapperApplication.class)
@RunWith(SpringRunner.class)
public class BookServerServiceTests
{
	private static final String SERVER_URL = "http://localhost:8080/nuxeo";
	private static final String LOGIN_ID = "Administrator";
	private static final String LOGIN_PSWD = "Administrator";
	
	@Autowired
	private BookWrapperService bookWrapperService;
	
	private Long getNewIsbn()
	{
		Random random = new Random();
		random.nextInt(100000);
		return Long.valueOf(random.nextInt(100000));
	}
	
	@Before
	public void initTest() throws ServerException
	{
		bookWrapperService.login(SERVER_URL, LOGIN_ID, LOGIN_PSWD);
	}
	
	@After
	public void tearDown() throws ServerException
	{
		bookWrapperService.logout();
	}
	
	@Test
	public void saveBookWithValidInputShouldSaveDataInServerSuccessfully() throws ServerException
	{
		Long isbn = getNewIsbn();
		SingleBookResponse response = bookWrapperService.saveBook(isbn, "BookTitle1", "BookAuthor1", "BookPublisher1");
		Assert.assertEquals(response.getIsbn(), isbn);
		Assert.assertEquals(response.getTitle(), "BookTitle1");
		Assert.assertEquals(response.getAuthor(), "BookAuthor1");
		Assert.assertEquals(response.getPublisher(), "BookPublisher1");
		System.out.println("Repo id: " + response.getRepoId());
		Assert.assertTrue(response.getRepoId() != null && !response.getRepoId().isEmpty());
	}
	
	@Test
	public void getAllBooksShouldSuccessfullyRetrieveBooks() throws ServerException
	{
		Long isbn = getNewIsbn();
		SingleBookResponse saveBookresponse = bookWrapperService.saveBook(isbn, "BookTitle2", "BookAuthor2", "BookPublisher2");
		Assert.assertTrue(saveBookresponse.getRepoId() != null && !saveBookresponse.getRepoId().isEmpty());
		
		BookListResponse allBookResponse = bookWrapperService.getAllBooks();
		Assert.assertEquals(allBookResponse.getStatusCode(), 200);
		Assert.assertTrue(!allBookResponse.getBookResponseList().isEmpty());
		Assert.assertTrue(allBookResponse.getBookResponseList().stream().anyMatch(bookRes->bookRes.getIsbn().equals(isbn)));
	}
	
	@Test
	public void fetchBookFromServerByIsbnShouldSuccessfullyRetrieveBook() throws ServerException
	{
		Long isbn = getNewIsbn();
		SingleBookResponse saveBookresponse = bookWrapperService.saveBook(isbn, "BookTitle3", "BookAuthor3", "BookPublisher3");
		Assert.assertTrue(saveBookresponse.getRepoId() != null && !saveBookresponse.getRepoId().isEmpty());
		
		BookListResponse allBookResponse = bookWrapperService.fetchBookFromServerByIsbn(isbn);
		Assert.assertEquals(allBookResponse.getStatusCode(), 200);
		Assert.assertTrue(allBookResponse.getBookResponseList().size() == 1);
		Assert.assertTrue(allBookResponse.getBookResponseList().stream().anyMatch(bookRes->bookRes.getIsbn().equals(isbn)));
		
		SingleBookResponse singleBookResponse = allBookResponse.getBookResponseList().get(0);
		Assert.assertEquals(singleBookResponse.getIsbn(), isbn);
		Assert.assertEquals(singleBookResponse.getTitle(), "BookTitle3");
		Assert.assertEquals(singleBookResponse.getAuthor(), "BookAuthor3");
		Assert.assertEquals(singleBookResponse.getPublisher(), "BookPublisher3");
	}

}
