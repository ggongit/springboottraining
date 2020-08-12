package lib.grpc.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import io.grpc.stub.StreamObserver;
import lib.dto.BookDTO;
import lib.grpc.services.auto.BookLibraryProtos.BookDetails;
import lib.grpc.services.auto.BookLibraryProtos.BookListResponse;
import lib.grpc.services.auto.BookLibraryProtos.BookRequest;
import lib.grpc.services.auto.BookLibraryProtos.CountResponse;
import lib.grpc.services.auto.BookLibraryProtos.EmptyRequest;
import lib.grpc.services.auto.BookLibraryProtos.GenericResponse;
import lib.grpc.services.auto.BookLibraryProtos.IsbnRequest;
import lib.grpc.services.auto.BookLibraryProtos.MultipleAuthorsRequest;
import lib.grpc.services.auto.BookLibraryProtos.GenericResponse.Builder;
import lib.grpc.services.auto.BookLibraryProtos.GenericResponse.ResponseType;
import lib.grpc.services.auto.BookLibraryProtos.SingleBookResponse;
import lib.grpc.services.auto.BookLibraryProtos.TitleRequest;
import lib.grpc.services.auto.BookServiceGrpc.BookServiceImplBase;
import lib.service.api.BookService;

@GRpcService
public class GrpcBookService extends BookServiceImplBase
{
	@Autowired
	@Qualifier("bookServiceInProd")
	private BookService bookService;

	@Override
	public void addBook(BookRequest request, StreamObserver<SingleBookResponse> responseObserver) 
	{
		BookDetails requestedBookDetails = request.getBookDetails();
		
		BookDTO addedBook = null;
		GenericResponse.Builder messageBuilder = GenericResponse.newBuilder();
		BookDetails.Builder bookDetailsBuilder = BookDetails.newBuilder();
		SingleBookResponse.Builder finalResponseBuilder = SingleBookResponse.newBuilder();
		
		addedBook = bookService.addBook(
				requestedBookDetails.getIsbn(),
				requestedBookDetails.getTitle(),
				requestedBookDetails.getAuthor(),
				requestedBookDetails.getPublisher());
		
		Optional<BookDTO> bookOptional = Optional.ofNullable(addedBook);
		
		bookOptional.ifPresent(book->{
			
			messageBuilder.setType(ResponseType.SUCCESS).setMessage("Given book is added successfully");
			
			bookDetailsBuilder
				.setIsbn(book.getIsbn())
				.setTitle(book.getTitle())
				.setAuthor(book.getAuthor())
				.setPublisher(book.getPublisher());
		});
		
		if(bookOptional.isPresent() == false)
		{
			messageBuilder.setType(ResponseType.ERROR).setMessage("Failed to add book");
		}
		
		SingleBookResponse bookResponse = finalResponseBuilder
				.setBookDetails(bookDetailsBuilder)
				.setGenericResponse(messageBuilder)
				.build();
		
		responseObserver.onNext(bookResponse);
		responseObserver.onCompleted();
	}
	
	@Override
	public void getBooksByTitle(TitleRequest request, StreamObserver<BookListResponse> responseObserver) 
	{
		String title = request.getTitle();
		List<BookDTO> bookList = bookService.getBooksByTitle(title);
		responseObserver.onNext(getBookListResponseBuilder(bookList).build());
		responseObserver.onCompleted();
	}
	
	@Override
	public void getBookCounts(EmptyRequest request, StreamObserver<CountResponse> responseObserver) 
	{
		long bookCounts = bookService.getAllBookCounts();
		
		CountResponse countResponse = CountResponse.newBuilder()
				.setCounts((int)bookCounts)
				.setGenericResponse(getGenericResponseBuilder(ResponseType.SUCCESS, "Books count successfully retrieved"))
				.build();
		
		responseObserver.onNext(countResponse);
		responseObserver.onCompleted();
	}

	@Override
	public void getAllBooks(EmptyRequest request, StreamObserver<BookListResponse> responseObserver) 
	{
		List<BookDTO> bookList = bookService.getAllBooks();
		BookListResponse.Builder bookListResponseBuilder = BookListResponse.newBuilder();
		
		bookList.forEach(bookDto->{
			
			BookDetails.Builder bookDetailBuilder = BookDetails.newBuilder()
					.setIsbn(bookDto.getIsbn())
					.setTitle(bookDto.getTitle())
					.setAuthor(bookDto.getAuthor())
					.setPublisher(bookDto.getPublisher());
			bookListResponseBuilder.addBookList(bookDetailBuilder);
		});
		
		bookListResponseBuilder.setGenericResponse(getGenericResponseBuilder(ResponseType.SUCCESS, "Books retrieved successfully"));
		responseObserver.onNext(bookListResponseBuilder.build());
		responseObserver.onCompleted();
	}
	
	@Override
	public void deleteBookByIsbn(IsbnRequest request, StreamObserver<GenericResponse> responseObserver) 
	{
		long isbn = request.getIsbn();
		boolean isSuccess = bookService.deleteBookByIsbn(isbn);
		
		Builder builder;
		if(isSuccess)
		{
			builder = getGenericResponseBuilder(ResponseType.SUCCESS, "Book deleted successfully");
		}
		else
		{
			builder = getGenericResponseBuilder(ResponseType.WARNING, "Book could not be deleted");
		}
		
		responseObserver.onNext(builder.build());
		responseObserver.onCompleted();
	}
	
	@Override
	public void getBooksByAuthorList(MultipleAuthorsRequest request, StreamObserver<BookListResponse> responseObserver) 
	{
		List<String> authors = request.getAuthorRequestList().stream().map(singleBook->singleBook.getAuthor()).collect(Collectors.toList());
		List<BookDTO> bookList = bookService.getBooksByAuthorList(authors);
		responseObserver.onNext(getBookListResponseBuilder(bookList).build());
		responseObserver.onCompleted();
	}
	
	private GenericResponse.Builder getGenericResponseBuilder(ResponseType type, String message)
	{
		return GenericResponse.newBuilder().setType(type).setMessage(message);
	}
	
	private BookListResponse.Builder getBookListResponseBuilder(List<BookDTO> bookList)
	{
		BookListResponse.Builder bookListResBuilder = BookListResponse.newBuilder();
		
		bookList.forEach(bookDto->{
			
			BookDetails.Builder bookDetailsBuilder =  BookDetails.newBuilder()
					.setIsbn(bookDto.getIsbn())
					.setTitle(bookDto.getTitle())
					.setAuthor(bookDto.getAuthor())
					.setPublisher(bookDto.getPublisher());
			
			bookListResBuilder.addBookList(bookDetailsBuilder);
		});
		
		if(bookListResBuilder.getBookListCount() > 0)
		{
			bookListResBuilder.setGenericResponse(
					getGenericResponseBuilder(
							ResponseType.SUCCESS,
							bookListResBuilder.getBookListCount() + " books successfully retrieved"));
		}
		else
		{
			bookListResBuilder.setGenericResponse(getGenericResponseBuilder(ResponseType.WARNING, "No book found"));
		}
		
		return bookListResBuilder;
	}
	
}
