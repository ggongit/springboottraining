package lib.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import lib.grpc.server.GrpcServer;
import lib.service.api.BookService;

@SpringBootApplication//(scanBasePackages = "lib")
@EntityScan(basePackages = {"lib.model"})
@ComponentScan(basePackages = {"lib.service.*", "lib.grpc"})
@EnableJpaRepositories(basePackages = {"lib.book.repository"})
@PropertySource("classpath:application.properties")
public class BooksLibraryApp
{
	@Value("${welcome.msg}")
	private String welcomeMsg;

	private static ApplicationContext applicationContext;
	
	private ConfigurationPropertySource a;
	
	public static void main(String[] args)
	{
		applicationContext = SpringApplication.run(BooksLibraryApp.class, args);
//		ApplicationContext context = new AnnotationConfigApplicationContext(BooksLibraryApp.class);
//		System.out.println(context.getBean(BooksLibraryApp.class).welcomeMsg);
//		
//		BookService bookService = context.getBean("bookServiceInProd", BookService.class);
//		
//		GrpcBookService grpcBookService = context.getBean(GrpcBookService.class);
		
		//testPOJOServices();
		//testGrpcServices();
		
	}
	
	private static void testGrpcServices()
	{
		try
		{
			GrpcServer.startServer();
		} 
		catch (IOException | InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	private static void testPOJOServices()
	{
		BookService bookService  = applicationContext.getBean("bookServiceInProd", BookService.class);

		List<String> authors = new ArrayList<>();
		authors.add("J. R. R. Tolkien");
		authors.add("Lynley Dodd");
		
		bookService.getBooksByAuthorList(authors).forEach(System.out::println);
		
		Long isbn = getNewIsbn();
		bookService.addBook(isbn, "The Hobbit", "J. R. R. Tolkien", "Houghton Mifflin USA");
		bookService.addBook(getNewIsbn(), "Slinky Malinki", "Lynley Dodd", "Mallinson Rendel NZ");
		bookService.addBook(getNewIsbn(), "Hairy Maclary from Donaldson's Dairy", "Lynley Dodd", "Mallinson Rendel NZ");
		bookService.addBook(getNewIsbn(), "How to Lie with Statistics", "Darrell Huff", "W. W. Norton USA");
		bookService.addBook(getNewIsbn(), "Mechanical Harry", "Bob Kerr", "Mallinson Rendel NZ");		
		bookService.addBook(getNewIsbn(), "My Cat Likes to Hide in Boxes", "Lynley Dodd", "Mallinson Rendel NZ");
		bookService.addBook(getNewIsbn(), "My Cat Likes to Hide in Boxes", "Eve Sutton", "Mallinson Rendel NZ");
		
		System.out.println(" ");
		System.out.println("=============>>>>>>" + "Total number of books in library: " + bookService.getAllBookCounts());
		System.out.println(" ");
		System.out.println("=============>>>>>>" + "Following are total records of the books in the library:");
		bookService.getAllBooks().forEach(System.out::println);
		
		System.out.println(" ");
		System.out.println("=============>>>>>>" + "Get book by ISBN " + isbn + ": " + bookService.getBookByIsbn(isbn));
		System.out.println(" ");
		
		bookService.deleteBookByIsbn(isbn);
		System.out.println("=============>>>>>>" + "Total number of books in library: " + bookService.getAllBookCounts());
		System.out.println(" ");
		System.out.println("=============>>>>>>" + "Get book by ISBN " + isbn + ": " + bookService.getBookByIsbn(isbn));
		System.out.println(" ");
		System.out.println("=============>>>>>>" + "Get book containing Title \"to\": " + bookService.getBooksByTitleContains("to"));
		
//		bookService.deleteAllRecords();
//		System.out.println(" ");
//		System.out.println("=============>>>>>>" + "Total number of books in library: " + bookService.getAllBookCounts());
//		System.out.println(" ");
	}
	
	private static Long getNewIsbn()
	{
		Random random = new Random();
		random.nextInt(100000);
		return Long.valueOf(random.nextInt(100000));
	}
	
}
