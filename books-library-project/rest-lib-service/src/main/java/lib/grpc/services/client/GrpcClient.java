package lib.grpc.services.client;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lib.grpc.services.auto.BookLibraryProtos.BookListResponse;
import lib.grpc.services.auto.BookLibraryProtos.BookRequest;
import lib.grpc.services.auto.BookLibraryProtos.CountResponse;
import lib.grpc.services.auto.BookLibraryProtos.EmptyRequest;
import lib.grpc.services.auto.BookLibraryProtos.GenericResponse;
import lib.grpc.services.auto.BookLibraryProtos.IsbnRequest;
import lib.grpc.services.auto.BookLibraryProtos.MultipleAuthorsRequest;
import lib.grpc.services.auto.BookLibraryProtos.SingleAuthorRequest;
import lib.grpc.services.auto.BookLibraryProtos.SingleBookResponse;
import lib.grpc.services.auto.BookLibraryProtos.TitleRequest;
import lib.grpc.services.auto.BookServiceGrpc;
import lib.grpc.services.auto.BookServiceGrpc.BookServiceBlockingStub;

@Component
public class GrpcClient 
{
	private BookServiceBlockingStub bookServiceStub;
	
	@PostConstruct
	public void init()
	{
		ManagedChannel  channel = ManagedChannelBuilder.forAddress("localhost", 7070).usePlaintext().build();
		bookServiceStub = BookServiceGrpc.newBlockingStub(channel);
	}
	
	public BookListResponse getBooksByTitle(String title)
	{
		TitleRequest titleReq = TitleRequest.newBuilder().setTitle(title).build();
		BookListResponse response = bookServiceStub.getBooksByTitle(titleReq);
		return response;
	}
	
	public BookListResponse getBooksByAuthorList(List<String> authorList)
	{
		MultipleAuthorsRequest.Builder requestBuilder = MultipleAuthorsRequest.newBuilder();
		
		authorList.forEach(author->{
			SingleAuthorRequest.Builder authorReq = SingleAuthorRequest.newBuilder();
			authorReq.setAuthor(author);
			requestBuilder.addAuthorRequest(authorReq);
		});
		
		return bookServiceStub.getBooksByAuthorList(requestBuilder.build());
	}
	
	public CountResponse getAllBookCounts()
	{
		return bookServiceStub.getBookCounts(EmptyRequest.newBuilder().build());
	}
	
	public BookListResponse getAllBooks()
	{
		return bookServiceStub.getAllBooks(EmptyRequest.newBuilder().build());
	}
	
	public SingleBookResponse addBook(BookRequest bookRequest)
	{
		try {
			return bookServiceStub.addBook(bookRequest);	
		} catch (Exception e) {
			Throwable th = Optional.ofNullable(e.getCause()).orElse(e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, th.getMessage());
		}
	}
	
	public GenericResponse deleteBookByIsbn(long isbn)
	{
		IsbnRequest.Builder reqBuilder = IsbnRequest.newBuilder().setIsbn(isbn);
		return bookServiceStub.deleteBookByIsbn(reqBuilder.build());
	}
}
