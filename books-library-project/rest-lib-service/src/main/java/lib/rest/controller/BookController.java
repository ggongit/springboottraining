package lib.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lib.grpc.services.auto.BookLibraryProtos.BookListResponse;
import lib.grpc.services.auto.BookLibraryProtos.BookRequest;
import lib.grpc.services.auto.BookLibraryProtos.CountResponse;
import lib.grpc.services.auto.BookLibraryProtos.GenericResponse;
import lib.grpc.services.auto.BookLibraryProtos.SingleBookResponse;
import lib.grpc.services.client.GrpcClient;

@RestController
@RequestMapping("/book")
public class BookController 
{
	@Autowired
	private GrpcClient grpcClient;

//	@GetMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
//	public List<BookDetails> getBookByTitle(@RequestParam(value = "title") String title) // http://localhost:9123/books?title=The%20Hobbit
//	{
//		return grpcClient.getBookByTitle(title);
//	}
	
//	@GetMapping(value = "/books/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
//	public BookListResponse getBooksByTitle(@PathVariable String title) // http://localhost:9123/books/The Hobbit 
//	{
//		return grpcClient.getBookByTitle(title);
//	}
	
	@GetMapping(value = "/byTitle", produces = MediaType.APPLICATION_JSON_VALUE)
	public BookListResponse getBooksByTitle(@RequestBody String title)
	{
		return grpcClient.getBooksByTitle(title);
	}
	
	@GetMapping(value = "/byAuthors", produces = MediaType.APPLICATION_JSON_VALUE)
	public BookListResponse getBooksByAuthorList(@RequestBody List<String> authorList)
	{
		return grpcClient.getBooksByAuthorList(authorList);
	}
	
	@GetMapping(value = "/counts", produces = MediaType.APPLICATION_JSON_VALUE)
	public CountResponse getAllBookCounts()
	{
		return grpcClient.getAllBookCounts();
	}
	
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public BookListResponse getAllBooks()
	{
		return grpcClient.getAllBooks();
	}
	
	@PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public SingleBookResponse addBook(@RequestBody BookRequest bookRequest)
	{
		return grpcClient.addBook(bookRequest);
	}
	
	@DeleteMapping(value = "/byIsbn", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse deleteBookByIsbn(@RequestBody long isbn)
	{
		return grpcClient.deleteBookByIsbn(isbn);
	}
	
}
