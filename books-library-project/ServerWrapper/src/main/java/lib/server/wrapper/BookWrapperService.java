package lib.server.wrapper;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lib.server.rest.client.NXServerServices;
import lib.server.rest.pojo.BookListResponse;
import lib.server.rest.pojo.SingleBookResponse;
import lib.server.wrapper.exceptions.ServerException;

@Service
public class BookWrapperService 
{
	@Autowired
	private NXServerServices restClient;
	
	private String URL_SEPARATOR = "/";
	
	public void login(String url, String username, String password) throws ServerException
	{
		this.restClient.initClient(url, username, password);
	}
	
	public void logout() throws ServerException
	{
		this.restClient.logout();
	}
	
	public SingleBookResponse saveBook(Long isbn, String title, String author, String publisher) throws ServerException
	{
		String addBookURL = this.restClient.getUrl() + "/api/v1/book/" + isbn + URL_SEPARATOR + title + URL_SEPARATOR + author + URL_SEPARATOR + publisher;
		try {
			return restClient.postCall(addBookURL, "", SingleBookResponse.class);
		} catch (IOException e) {
			throw new ServerException(e);
		}
	}
	
	public BookListResponse getAllBooks() throws ServerException
	{
		String url = this.restClient.getUrl() + "/api/v1/book/all";
		try {
			return restClient.getCall(url, BookListResponse.class);
		} catch (IOException e) {
			throw new ServerException(e);
		}
	}
	
	public BookListResponse fetchBookFromServerByIsbn(Long isbn) throws ServerException
	{
		String url = this.restClient.getUrl() + "/api/v1/book/byIsbn/" + isbn;
		try {
			return restClient.getCall(url, BookListResponse.class);
		} catch (IOException e) {
			throw new ServerException(e);
		}
	}
}
