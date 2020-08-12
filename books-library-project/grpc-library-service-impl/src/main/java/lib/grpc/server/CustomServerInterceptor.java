package lib.grpc.server;

import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

import io.grpc.ForwardingServerCallListener.SimpleForwardingServerCallListener;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;

//@GRpcGlobalInterceptor
public class CustomServerInterceptor implements ServerInterceptor 
{

	@Autowired
	private ApplicationContext appContext;
	
	@Override
	public <ReqT, RespT> Listener<ReqT> interceptCall(
			ServerCall<ReqT, RespT> call,
			Metadata headers,
			ServerCallHandler<ReqT, RespT> next) 
	{

		String fullMethodName = call.getMethodDescriptor().getFullMethodName(); // lib.grpc.services.auto.BookService/getBooksByTitle
		String fullyQualifiedServiceName = MethodDescriptor.extractFullServiceName(fullMethodName); // lib.grpc.services.auto.BookService
		String serviceName = call.getMethodDescriptor().getServiceName(); // lib.grpc.services.auto.BookService
		String methodTypeName = call.getMethodDescriptor().getType().name(); // UNARY

		final ServerCall.Listener<ReqT> listener = next.startCall(call, headers);
		AutowireCapableBeanFactory factory = appContext.getAutowireCapableBeanFactory();
		
		//return next.startCall(call, headers);
		return new SimpleForwardingServerCallListener<ReqT>(listener) 
		 {
	         @Override
			public void onMessage(ReqT message)
	        {
	        	 System.out.println("Inside onMessage: " + message.toString());
				 super.onMessage(message); // 2nd
			}

			@Override
			public void onCancel() 
			{
				super.onCancel();
			}

			@Override
			public void onComplete() 
			{
				System.out.println("Inside onComplete");
				super.onComplete(); // Finally after the actual call
			}

			@Override
			public void onReady() 
			{
				System.out.println("Inside onReady");
				super.onReady(); // 1st
			}

			@Override
	         public void onHalfClose() 
	         {
				// In order to catch runtime exception
	            try 
	            {
	            	System.out.println("Inside onHalfClose");
	                super.onHalfClose(); // 3rd
	            }
	            catch (Exception e) 
	            {
	               call.close(Status.INTERNAL.withCause (e).withDescription("My error message: " + e.getMessage()), new Metadata());
	            }
	         }
	      };
	}

}
