package lib.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lib.grpc.services.auto.BookLibraryProtos.BookListResponse;
import lib.grpc.services.auto.BookLibraryProtos.GenericResponse;
import lib.grpc.services.auto.BookLibraryProtos.IsbnRequest;
import lib.grpc.services.auto.BookLibraryProtos.SingleBookResponse;
import lib.grpc.services.auto.BookLibraryProtos.UserLoginRequest;
import lib.grpc.services.client.GrpcClient;

@RestController
@RequestMapping("/book")
@CrossOrigin
public class ServerOpsController 
{
	@Autowired
	private GrpcClient grpcClient;
	
	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse login(@RequestBody UserLoginRequest loginRequest)
	{
		return grpcClient.login(loginRequest);
	}
	
	@PostMapping(value = "/save/{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse saveBookInServer(@PathVariable long isbn)
	{
		GenericResponse response = grpcClient.saveBookInServer(isbn);
		return response;
	}
	
	@PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse logout()
	{
		return grpcClient.logout();
	}
	
	@GetMapping(value = {"/allRepo"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public BookListResponse fetchAllBooksFromServer()
	{
		return grpcClient.fetchAllBooksFromServer();
	}
	
	@GetMapping(value = {"/download/{isbn}"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public SingleBookResponse downloadBookFromServer(@PathVariable long isbn)
	{
		return grpcClient.downloadBookFromServer(isbn);
	}
}
