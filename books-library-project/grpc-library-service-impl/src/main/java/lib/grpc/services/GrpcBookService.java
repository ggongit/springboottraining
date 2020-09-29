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
import lib.grpc.services.auto.BookLibraryProtos.GenericResponse.ResponseType;
import lib.grpc.services.auto.BookLibraryProtos.IsbnRequest;
import lib.grpc.services.auto.BookLibraryProtos.MultipleAuthorsRequest;
import lib.grpc.services.auto.BookLibraryProtos.SingleBookResponse;
import lib.grpc.services.auto.BookLibraryProtos.TitleRequest;
import lib.grpc.services.auto.BookLibraryProtos.UserLoginRequest;
import lib.grpc.services.auto.BookServiceGrpc.BookServiceImplBase;
import lib.server.wrapper.exceptions.ServerException;
import lib.service.api.BookService;
import lib.service.api.exception.BookException;

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
		
		try {
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
					.setPublisher(book.getPublisher())
					.setRepoId(book.getRepoId())
					.setPreviewPath(getBookPreviewPath(book));
			});
			
			if(bookOptional.isPresent() == false)
			{
				messageBuilder.setType(ResponseType.ERROR).setMessage("Failed to add book");
			}
		} 
		catch (BookException e) {
			messageBuilder.setType(ResponseType.ERROR).setMessage(e.getMessage());
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
					.setPublisher(bookDto.getPublisher())
					.setRepoId(bookDto.getRepoId())
					.setPreviewPath(getBookPreviewPath(bookDto));
			bookListResponseBuilder.addBookList(bookDetailBuilder);
		});
		
		bookListResponseBuilder.setGenericResponse(getGenericResponseBuilder(
				ResponseType.SUCCESS,
				bookListResponseBuilder.getBookListCount() + " books retrieved successfully"));
		
		responseObserver.onNext(bookListResponseBuilder.build());
		responseObserver.onCompleted();
	}
	
	@Override
	public void deleteBookByIsbn(IsbnRequest request, StreamObserver<GenericResponse> responseObserver) 
	{
		long isbn = request.getIsbn();
		boolean isSuccess = bookService.deleteBookByIsbn(isbn);
		
		GenericResponse.Builder builder;
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
	
	@Override
	public void login(UserLoginRequest request, StreamObserver<GenericResponse> responseObserver) 
	{
		GenericResponse.Builder builder;
		
		try {
			bookService.login(request.getServerUrl(), request.getUserId(), request.getPassword());
			builder = getGenericResponseBuilder(ResponseType.SUCCESS, "User logged-in successful");
		} catch (ServerException e) {
			builder = getGenericResponseBuilder(ResponseType.ERROR, e.getMessage());
		}
		responseObserver.onNext(builder.build());
		responseObserver.onCompleted();
	}
	
	@Override
	public void logout(EmptyRequest request, StreamObserver<GenericResponse> responseObserver) {
		
		GenericResponse.Builder builder;
		try {
			bookService.logout();
			builder = getGenericResponseBuilder(ResponseType.SUCCESS, "User logged out successful");
		} catch (ServerException e) {
			builder = getGenericResponseBuilder(ResponseType.ERROR, e.getMessage());
		}
		responseObserver.onNext(builder.build());
		responseObserver.onCompleted();
	}
	
	@Override
	public void saveBookToServer(IsbnRequest request, StreamObserver<GenericResponse> responseObserver) 
	{
		long isbn = request.getIsbn();
		BookDTO bookDTO = bookService.getBookByIsbn(isbn);
		
		GenericResponse.Builder builder;
		if(bookDTO == null)
		{
			builder = getGenericResponseBuilder(ResponseType.WARNING, "No book found with given isbn: " + isbn);
		}
		else
		{
			try {
				bookService.saveBookInServer(bookDTO);
				builder = getGenericResponseBuilder(ResponseType.SUCCESS, "Book saved in server successfully");
			} catch (ServerException | BookException e) {
				builder = getGenericResponseBuilder(ResponseType.ERROR, e.getMessage());
			}
		}
		
		responseObserver.onNext(builder.build());
		responseObserver.onCompleted();
	}
	
	@Override
	public void downloadBookFromServer(IsbnRequest request, StreamObserver<SingleBookResponse> responseObserver) {
	
		GenericResponse.Builder genericResponse;
		BookDetails.Builder bookDetailsBuilder = BookDetails.newBuilder();
		SingleBookResponse.Builder finalResponseBuilder = SingleBookResponse.newBuilder();
		try {
			BookDTO bookDto = bookService.downloadBookFromServer(request.getIsbn());
			bookDetailsBuilder
					.setIsbn(bookDto.getIsbn())
					.setTitle(bookDto.getTitle())
					.setAuthor(bookDto.getAuthor())
					.setPublisher(bookDto.getPublisher())
					.setRepoId(bookDto.getRepoId())
					.setPreviewPath(getBookPreviewPath(bookDto));
			genericResponse = getGenericResponseBuilder(ResponseType.SUCCESS, "Book is downloaded successfully from server");
		} catch (ServerException | BookException e) {
			genericResponse = getGenericResponseBuilder(ResponseType.ERROR, e.getMessage());
		}
		finalResponseBuilder.setGenericResponse(genericResponse).setBookDetails(bookDetailsBuilder);
		responseObserver.onNext(finalResponseBuilder.build());
		responseObserver.onCompleted();
	}
	
	@Override
	public void fetchAllBooksFromServer(EmptyRequest request, StreamObserver<BookListResponse> responseObserver) {
		BookListResponse.Builder responseBuilder;
		try {
			List<BookDTO> bookList = bookService.getAllBooksFromServer();
			responseBuilder = getBookListResponseBuilder(bookList);

		} catch (ServerException e) {
			responseBuilder = BookListResponse.newBuilder()
					.setGenericResponse(getGenericResponseBuilder(ResponseType.ERROR, e.getMessage()));
		}
		responseObserver.onNext(responseBuilder.build());
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
					.setPublisher(bookDto.getPublisher())
					.setRepoId(bookDto.getRepoId())
					.setPreviewPath(getBookPreviewPath(bookDto));
			
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
	
	private String getBookPreviewPath(BookDTO book)
	{
		return book != null && !book.getRepoId().equals("NA") ? "publishedbook.png" : "unpublishedbook.jpg"; 
	}
	
}
