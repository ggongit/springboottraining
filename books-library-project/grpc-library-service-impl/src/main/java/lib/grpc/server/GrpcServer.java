package lib.grpc.server;

import java.io.IOException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lib.grpc.services.GrpcBookService;
import lib.service.api.BookService;

//@SpringBootApplication
//@EntityScan(basePackages = {"lib.model"})
//@ComponentScan(basePackages = {"lib.service.api.impl", "lib.service.api", "lib.grpc.services"})
//@EnableJpaRepositories(basePackages = {"lib.book.repository"})
//@PropertySource("classpath:application.properties")
public class GrpcServer
{

	public static void main(String[] args) throws IOException, InterruptedException
	{
		//SpringApplication.run(GrpcServer.class, args);
//		ApplicationContext context = new AnnotationConfigApplicationContext(GrpcServer.class);
//		BookService bookService = context.getBean("bookServiceInProd", BookService.class);
		
	
		startServer();
	}
	
	public static void startServer() throws IOException, InterruptedException
	{	
		Server server = ServerBuilder.forPort(7070).addService(new GrpcBookService()).build();
		server.start();
		System.out.println("server started at "+ server.getPort());
		server.awaitTermination();
	}
}
