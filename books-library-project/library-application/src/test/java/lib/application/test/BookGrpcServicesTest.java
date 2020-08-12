package lib.application.test;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.grpc.stub.StreamObserver;
import lib.dto.BookDTO;
import lib.grpc.services.GrpcBookService;
import lib.grpc.services.auto.BookLibraryProtos.BookDetails;
import lib.grpc.services.auto.BookLibraryProtos.BookListResponse;
import lib.grpc.services.auto.BookLibraryProtos.BookRequest;
import lib.grpc.services.auto.BookLibraryProtos.CountResponse;
import lib.grpc.services.auto.BookLibraryProtos.EmptyRequest;
import lib.grpc.services.auto.BookLibraryProtos.GenericResponse;
import lib.grpc.services.auto.BookLibraryProtos.GenericResponse.ResponseType;
import lib.grpc.services.auto.BookLibraryProtos.IsbnRequest;
import lib.grpc.services.auto.BookLibraryProtos.MultipleAuthorsRequest;
import lib.grpc.services.auto.BookLibraryProtos.SingleAuthorRequest;
import lib.grpc.services.auto.BookLibraryProtos.SingleBookResponse;
import lib.grpc.services.auto.BookLibraryProtos.TitleRequest;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookGrpcServicesTest 
{
	//@MockBean
	@Autowired
	private GrpcBookService grpcBookService;
	
	private static long[] BOOK_ISBN = {1617292540L, 618260307L};
	private static String[] BOOK_TITLE = {"Spring Boot in Action", "The Hobbit"};
	private static String[] BOOK_AUTHOR = {"Craig Walls", "J. R. R. Tolkien"};
	private static String[] BOOK_PUBLISHER = {"Manning Publications", "Houghton Mifflin USA"};

	private <T> T verifyResponse(StreamObserver<T> responseObserver, Class<T> myClass)
	{
		Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
		ArgumentCaptor<T> captor = ArgumentCaptor.forClass(myClass);
		Mockito.verify(responseObserver, Mockito.times(1)).onNext(captor.capture());
		return captor.getValue();
	}
	
	private void verifySuccessResponse(GenericResponse response)
	{
		Assert.assertEquals(response.getType(), ResponseType.SUCCESS);
	}
	
	private void verifyBookDetails(BookDetails response)
	{
		boolean isFound = false;
		for(int i = 0; i < BOOK_ISBN.length; i++)
		{
			if(response.getIsbn() == BOOK_ISBN[i])
			{
				isFound = true;
				Assert.assertEquals(response.getTitle(), BOOK_TITLE[i]);
				Assert.assertEquals(response.getAuthor(), BOOK_AUTHOR[i]);
				Assert.assertEquals(response.getPublisher(), BOOK_PUBLISHER[i]);
				break;
			}
		}
		
		Assert.assertTrue(isFound);
	}
	
	private StreamObserver<SingleBookResponse> addBook(long isbn, String title, String author, String publisher)
	
	{
		BookRequest request = BookRequest.newBuilder()
                .setBookRequestId(1)
                .setBookDetails(BookDetails.newBuilder()
                		.setIsbn(isbn)
                		.setTitle(title)
                		.setAuthor(author)
                		.setPublisher(publisher))
                .build();
		
		StreamObserver<SingleBookResponse> responseObserver = Mockito.mock(StreamObserver.class);
		
		grpcBookService.addBook(request, responseObserver);
		
		return responseObserver;
	}
	
	private StreamObserver<SingleBookResponse> addBook()
	
	{
		return addBook(BOOK_ISBN[0], BOOK_TITLE[0], BOOK_AUTHOR[0], BOOK_PUBLISHER[0]);
	}
	
	private StreamObserver<SingleBookResponse> addAnotherBook()
	
	{
		return addBook(BOOK_ISBN[1], BOOK_TITLE[1], BOOK_AUTHOR[1], BOOK_PUBLISHER[1]);
	}
	
	@Test
	public void shouldAddFirstBookSuccessfully()
	{
		
		StreamObserver<SingleBookResponse> responseObserver = addBook();
		
//		Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
//		ArgumentCaptor<SingleBookResponse> captor = ArgumentCaptor.forClass(SingleBookResponse.class);
//		Mockito.verify(responseObserver, Mockito.times(1)).onNext(captor.capture());
//		SingleBookResponse response = captor.getValue();
		
		SingleBookResponse response = verifyResponse(responseObserver, SingleBookResponse.class);
		
		verifySuccessResponse(response.getGenericResponse());
		verifyBookDetails(response.getBookDetails());
	}
	
	@Test
	public void shouldAddSecondBookSuccessfully()
	{
		// Add first book
		addBook();
		
		// Add second one
		StreamObserver<SingleBookResponse> responseObserver = addBook(618260307L, "The Hobbit", "J. R. R. Tolkien", "Houghton Mifflin USA");
		
		SingleBookResponse response = verifyResponse(responseObserver, SingleBookResponse.class);
		
		verifySuccessResponse(response.getGenericResponse());
		Assert.assertEquals(response.getBookDetails().getIsbn(), 618260307L);
		Assert.assertEquals(response.getBookDetails().getTitle(), "The Hobbit");
		Assert.assertEquals(response.getBookDetails().getAuthor(), "J. R. R. Tolkien");
		Assert.assertEquals(response.getBookDetails().getPublisher(), "Houghton Mifflin USA");
	}
	
	@Test
	public void shouldRetrieveBookByTitleSuccessfully()
	{
		addBook();
		
		TitleRequest request = TitleRequest.newBuilder().setTitle(BOOK_TITLE[0]).build();
		StreamObserver<BookListResponse> responseObserver = Mockito.mock(StreamObserver.class);
		grpcBookService.getBooksByTitle(request, responseObserver);
		
		BookListResponse response = verifyResponse(responseObserver, BookListResponse.class);
		
		verifySuccessResponse(response.getGenericResponse());
		Assert.assertTrue(response.getBookListCount() == 1);
		Assert.assertNotNull(response.getBookList(0));
		verifyBookDetails(response.getBookList(0));
	}
	
	@Test
	public void shouldRetrieveAllBookCountsSuccessfully()
	{
		addBook();
		addAnotherBook();
		
		StreamObserver<CountResponse> responseObserver = Mockito.mock(StreamObserver.class);
		grpcBookService.getBookCounts(EmptyRequest.newBuilder().build(), responseObserver);
		
		CountResponse response = verifyResponse(responseObserver, CountResponse.class);
		verifySuccessResponse(response.getGenericResponse());
		Assert.assertTrue(response.getCounts() == 2);
	}
	
	@Test
	public void shouldRetrieveAllBooksSuccessfully()
	{
		addBook();
		addAnotherBook();
		
		StreamObserver<BookListResponse> responseObserver = Mockito.mock(StreamObserver.class);
		grpcBookService.getAllBooks(EmptyRequest.newBuilder().build(), responseObserver);
		
		BookListResponse response = verifyResponse(responseObserver, BookListResponse.class);
		verifySuccessResponse(response.getGenericResponse());
		Assert.assertTrue(response.getBookListCount() == 2);
		response.getBookListList().forEach(bookDetail->{
		
			Assert.assertNotNull(bookDetail);
			verifyBookDetails(bookDetail);
		});

	}
	
	@Test
	public void shouldDeleteBookByIsbnSuccessfully()
	{
		addBook();
		addAnotherBook();
		
		IsbnRequest request = IsbnRequest.newBuilder().setIsbn(BOOK_ISBN[0]).build();
		StreamObserver<GenericResponse> responseObserver = Mockito.mock(StreamObserver.class);
		
		grpcBookService.deleteBookByIsbn(request, responseObserver);
		GenericResponse response = verifyResponse(responseObserver, GenericResponse.class);
		verifySuccessResponse(response);
		

		StreamObserver<CountResponse> bookCountsResponseObserver = Mockito.mock(StreamObserver.class);
		grpcBookService.getBookCounts(EmptyRequest.newBuilder().build(), bookCountsResponseObserver);
		CountResponse countResponse = verifyResponse(bookCountsResponseObserver, CountResponse.class);
		Assert.assertTrue(countResponse.getCounts() == 1);
		
		TitleRequest titleRequest = TitleRequest.newBuilder().setTitle(BOOK_TITLE[0]).build();
		StreamObserver<BookListResponse> titleResponseObserver = Mockito.mock(StreamObserver.class);
		grpcBookService.getBooksByTitle(titleRequest, titleResponseObserver);
		BookListResponse bookByTitleResponse = verifyResponse(titleResponseObserver, BookListResponse.class);
		Assert.assertTrue(bookByTitleResponse.getBookListCount() == 0);
	}
	
	@Test
	public void shouldRetrieveAllBooksByAuthorsSuccessfully()
	{
		addBook();
		addAnotherBook();
		
		MultipleAuthorsRequest request = MultipleAuthorsRequest.newBuilder()
				.addAuthorRequest(SingleAuthorRequest.newBuilder().setAuthor(BOOK_AUTHOR[0]))
				.addAuthorRequest(SingleAuthorRequest.newBuilder().setAuthor(BOOK_AUTHOR[1]))
				.build();
		
		StreamObserver<BookListResponse> responseObserver = Mockito.mock(StreamObserver.class);
		grpcBookService.getBooksByAuthorList(request, responseObserver);
		
		BookListResponse response = verifyResponse(responseObserver, BookListResponse.class);
		verifySuccessResponse(response.getGenericResponse());
		Assert.assertTrue(response.getBookListCount() == 2);
		response.getBookListList().forEach(bookDetail->{
		
			Assert.assertNotNull(bookDetail);
			verifyBookDetails(bookDetail);
		});
	}
}
