package lib.grpc.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.grpc.stub.StreamObserver;
import lib.grpc.services.auto.BookLibraryProtos.SingleBookResponse;
import lib.grpc.services.auto.BookLibraryProtos.GenericResponse;
import lib.grpc.services.auto.BookLibraryProtos.GenericResponse.ResponseType;

@Aspect
@Component
public class ServiceAdvices 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceAdvices.class);
	private static final String GRPC_SERVICE_REQUEST_START_PREFIX = "==> GRPC service request recieved: ";
	private static final String GRPC_SERVICE_REQUEST_END_PREFIX = "==> GRPC service request ended: ";
	private static final String GRPC_SERVICE_REQUEST_FAILED_PREFIX = "==> GRPC service request failed: ";
	private static final String GRPC_SERVICE_REQUEST_SUCCESS_PREFIX = "==> GRPC service request successfull: ";
	
	
	@Before("serviceMethods() && args(request,..)")
	public void validateServiceInputs(
			JoinPoint jp,
			com.google.protobuf.GeneratedMessageV3 request) 
	{
		LOGGER.info(GRPC_SERVICE_REQUEST_START_PREFIX 
				+ request.getDescriptorForType().getFullName() 
				+ " >>> "
				+ request 
				+ " >>> Requester: " 
				+ jp.toString());
	}
	
	// Alternate way of above method
//	@Before("bean(*Book*) && args(request,..)")
//	public void validateBookServiceInputs(JoinPoint jp, com.google.protobuf.GeneratedMessageV3 request) 
//	{
//		System.out.println(request);
//		System.out.println(jp.toString());
//		System.out.println(jp.getTarget());
//	}
	
	@After("allMethodsInGrpcBookService() && methodsByArgs(request, responseObserver)")
	public void finallyAdvice(
			com.google.protobuf.GeneratedMessageV3 request,
			StreamObserver<com.google.protobuf.GeneratedMessageV3> responseObserver)
	{
		LOGGER.info(GRPC_SERVICE_REQUEST_END_PREFIX + " >>> " + request);
	}
	
	@AfterThrowing(pointcut="allMethodsInGrpcBookService() && methodsByArgs(request, responseObserver)", throwing="exceptionThrown")
	public void exceptionAdvice(
			com.google.protobuf.GeneratedMessageV3 request,
			StreamObserver<com.google.protobuf.GeneratedMessageV3> responseObserver,
			Exception exceptionThrown)
	{
		LOGGER.error(GRPC_SERVICE_REQUEST_FAILED_PREFIX + exceptionThrown.getMessage());
		
		GenericResponse genResp = GenericResponse.newBuilder().setType(ResponseType.ERROR).setMessage(exceptionThrown.getMessage()).build();
		responseObserver.onNext(genResp);
		responseObserver.onCompleted();
	}
	
	@AfterReturning(pointcut="allMethodsInGrpcBookService() && methodsByArgs(request, responseObserver)", returning="")
	public void successAdvice(
			com.google.protobuf.GeneratedMessageV3 request,
			StreamObserver<com.google.protobuf.GeneratedMessageV3> responseObserver)
	{
		LOGGER.info(GRPC_SERVICE_REQUEST_SUCCESS_PREFIX + " >>> " + request);
	}
	
//	@Around("serviceMethods() && args(request,..)")
//	public void aroundAdvice(ProceedingJoinPoint pjp, com.google.protobuf.GeneratedMessageV3 request)
//	{
//		// Code before executing the target method
//		LOGGER.info(GRPC_SERVICE_REQUEST_START_PREFIX + request.getDescriptorForType().getFullName() + " >>> " + request);
//		
//		try 
//		{
//			pjp.proceed();
//		}
//		catch (Throwable e) 
//		{
//			LOGGER.error(e.getMessage());
//			return;
//		}
//		
//		// Code after executing the target method
//		LOGGER.info(GRPC_SERVICE_REQUEST_END_PREFIX + request);
//		
//	}

	@Pointcut("args(request, responseObserver)")
	public void methodsByArgs(com.google.protobuf.GeneratedMessageV3 request, StreamObserver<com.google.protobuf.GeneratedMessageV3> responseObserver){}
	
	@Pointcut("execution(public * lib.grpc.services.GrpcBookService.*(..))")
	public void serviceMethods() {}
	
	@Pointcut("within(lib.grpc.services.GrpcBookService)")
	public void allMethodsInGrpcBookService() {}
	
	
	// Tips:
	// 1. args(java.io.Serializable) and execution(* *(java.io.Serializable)) are not same. The args version 
	// matches if the argument passed at runtime is Serializable, the execution version matches if the 
	// method signature declares a single parameter of type Serializable.


	
}
