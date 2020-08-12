package lib.rest.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lib.grpc.services.auto.BookLibraryProtos.BookDetails;
import lib.grpc.services.client.GrpcClient;

@RestController
//@RequestMapping("/test")
public class BookController 
{
	@Autowired
	private GrpcClient grpcClient;
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

//	@GetMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
//	public List<BookDetails> getBookByTitle(@RequestParam(value = "title") String title) // http://localhost:9123/books?title=The%20Hobbit
//	{
//		return grpcClient.getBookByTitle(title);
//	}
	
//	@GetMapping(value = "/books/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
//	public BookDetails getBookByTitle(@PathVariable String title) // http://localhost:9123/books/The Hobbit 
//	{
//		return grpcClient.getBookByTitle(title).get(0);
//	}
	
	@GetMapping(value = "/books/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<BookDetails> getBooksByTitle(@PathVariable String title) // http://localhost:9123/books/The Hobbit 
	{
		List<BookDetails> list = grpcClient.getBookByTitle(title);
		System.out.println(list);
		return list;
	}
	
	@GetMapping("/greeting")
	public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return counter.incrementAndGet() + ". " + String.format(template, name);
	}
}
