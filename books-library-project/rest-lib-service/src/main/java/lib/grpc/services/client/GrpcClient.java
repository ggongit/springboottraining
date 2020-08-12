package lib.grpc.services.client;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.google.protobuf.UnknownFieldSet;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lib.grpc.services.auto.BookLibraryProtos.BookDetails;
import lib.grpc.services.auto.BookLibraryProtos.BookListResponse;
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
	
	public List<BookDetails> getBookByTitle(String title)
	{
		TitleRequest titleReq = TitleRequest.newBuilder().setTitle(title).build();
		BookListResponse response = bookServiceStub.getBooksByTitle(titleReq);
		UnknownFieldSet unk = response.getUnknownFields();
		return response.getBookListList();
	}
}
