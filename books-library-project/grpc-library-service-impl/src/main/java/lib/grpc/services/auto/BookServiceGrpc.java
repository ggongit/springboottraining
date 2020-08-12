package lib.grpc.services.auto;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: book.proto")
public final class BookServiceGrpc {

  private BookServiceGrpc() {}

  public static final String SERVICE_NAME = "lib.grpc.services.auto.BookService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<lib.grpc.services.auto.BookLibraryProtos.BookRequest,
      lib.grpc.services.auto.BookLibraryProtos.SingleBookResponse> getAddBookMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "addBook",
      requestType = lib.grpc.services.auto.BookLibraryProtos.BookRequest.class,
      responseType = lib.grpc.services.auto.BookLibraryProtos.SingleBookResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<lib.grpc.services.auto.BookLibraryProtos.BookRequest,
      lib.grpc.services.auto.BookLibraryProtos.SingleBookResponse> getAddBookMethod() {
    io.grpc.MethodDescriptor<lib.grpc.services.auto.BookLibraryProtos.BookRequest, lib.grpc.services.auto.BookLibraryProtos.SingleBookResponse> getAddBookMethod;
    if ((getAddBookMethod = BookServiceGrpc.getAddBookMethod) == null) {
      synchronized (BookServiceGrpc.class) {
        if ((getAddBookMethod = BookServiceGrpc.getAddBookMethod) == null) {
          BookServiceGrpc.getAddBookMethod = getAddBookMethod = 
              io.grpc.MethodDescriptor.<lib.grpc.services.auto.BookLibraryProtos.BookRequest, lib.grpc.services.auto.BookLibraryProtos.SingleBookResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "lib.grpc.services.auto.BookService", "addBook"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  lib.grpc.services.auto.BookLibraryProtos.BookRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  lib.grpc.services.auto.BookLibraryProtos.SingleBookResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new BookServiceMethodDescriptorSupplier("addBook"))
                  .build();
          }
        }
     }
     return getAddBookMethod;
  }

  private static volatile io.grpc.MethodDescriptor<lib.grpc.services.auto.BookLibraryProtos.TitleRequest,
      lib.grpc.services.auto.BookLibraryProtos.BookListResponse> getGetBooksByTitleMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getBooksByTitle",
      requestType = lib.grpc.services.auto.BookLibraryProtos.TitleRequest.class,
      responseType = lib.grpc.services.auto.BookLibraryProtos.BookListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<lib.grpc.services.auto.BookLibraryProtos.TitleRequest,
      lib.grpc.services.auto.BookLibraryProtos.BookListResponse> getGetBooksByTitleMethod() {
    io.grpc.MethodDescriptor<lib.grpc.services.auto.BookLibraryProtos.TitleRequest, lib.grpc.services.auto.BookLibraryProtos.BookListResponse> getGetBooksByTitleMethod;
    if ((getGetBooksByTitleMethod = BookServiceGrpc.getGetBooksByTitleMethod) == null) {
      synchronized (BookServiceGrpc.class) {
        if ((getGetBooksByTitleMethod = BookServiceGrpc.getGetBooksByTitleMethod) == null) {
          BookServiceGrpc.getGetBooksByTitleMethod = getGetBooksByTitleMethod = 
              io.grpc.MethodDescriptor.<lib.grpc.services.auto.BookLibraryProtos.TitleRequest, lib.grpc.services.auto.BookLibraryProtos.BookListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "lib.grpc.services.auto.BookService", "getBooksByTitle"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  lib.grpc.services.auto.BookLibraryProtos.TitleRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  lib.grpc.services.auto.BookLibraryProtos.BookListResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new BookServiceMethodDescriptorSupplier("getBooksByTitle"))
                  .build();
          }
        }
     }
     return getGetBooksByTitleMethod;
  }

  private static volatile io.grpc.MethodDescriptor<lib.grpc.services.auto.BookLibraryProtos.EmptyRequest,
      lib.grpc.services.auto.BookLibraryProtos.CountResponse> getGetBookCountsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getBookCounts",
      requestType = lib.grpc.services.auto.BookLibraryProtos.EmptyRequest.class,
      responseType = lib.grpc.services.auto.BookLibraryProtos.CountResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<lib.grpc.services.auto.BookLibraryProtos.EmptyRequest,
      lib.grpc.services.auto.BookLibraryProtos.CountResponse> getGetBookCountsMethod() {
    io.grpc.MethodDescriptor<lib.grpc.services.auto.BookLibraryProtos.EmptyRequest, lib.grpc.services.auto.BookLibraryProtos.CountResponse> getGetBookCountsMethod;
    if ((getGetBookCountsMethod = BookServiceGrpc.getGetBookCountsMethod) == null) {
      synchronized (BookServiceGrpc.class) {
        if ((getGetBookCountsMethod = BookServiceGrpc.getGetBookCountsMethod) == null) {
          BookServiceGrpc.getGetBookCountsMethod = getGetBookCountsMethod = 
              io.grpc.MethodDescriptor.<lib.grpc.services.auto.BookLibraryProtos.EmptyRequest, lib.grpc.services.auto.BookLibraryProtos.CountResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "lib.grpc.services.auto.BookService", "getBookCounts"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  lib.grpc.services.auto.BookLibraryProtos.EmptyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  lib.grpc.services.auto.BookLibraryProtos.CountResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new BookServiceMethodDescriptorSupplier("getBookCounts"))
                  .build();
          }
        }
     }
     return getGetBookCountsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<lib.grpc.services.auto.BookLibraryProtos.EmptyRequest,
      lib.grpc.services.auto.BookLibraryProtos.BookListResponse> getGetAllBooksMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getAllBooks",
      requestType = lib.grpc.services.auto.BookLibraryProtos.EmptyRequest.class,
      responseType = lib.grpc.services.auto.BookLibraryProtos.BookListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<lib.grpc.services.auto.BookLibraryProtos.EmptyRequest,
      lib.grpc.services.auto.BookLibraryProtos.BookListResponse> getGetAllBooksMethod() {
    io.grpc.MethodDescriptor<lib.grpc.services.auto.BookLibraryProtos.EmptyRequest, lib.grpc.services.auto.BookLibraryProtos.BookListResponse> getGetAllBooksMethod;
    if ((getGetAllBooksMethod = BookServiceGrpc.getGetAllBooksMethod) == null) {
      synchronized (BookServiceGrpc.class) {
        if ((getGetAllBooksMethod = BookServiceGrpc.getGetAllBooksMethod) == null) {
          BookServiceGrpc.getGetAllBooksMethod = getGetAllBooksMethod = 
              io.grpc.MethodDescriptor.<lib.grpc.services.auto.BookLibraryProtos.EmptyRequest, lib.grpc.services.auto.BookLibraryProtos.BookListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "lib.grpc.services.auto.BookService", "getAllBooks"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  lib.grpc.services.auto.BookLibraryProtos.EmptyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  lib.grpc.services.auto.BookLibraryProtos.BookListResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new BookServiceMethodDescriptorSupplier("getAllBooks"))
                  .build();
          }
        }
     }
     return getGetAllBooksMethod;
  }

  private static volatile io.grpc.MethodDescriptor<lib.grpc.services.auto.BookLibraryProtos.IsbnRequest,
      lib.grpc.services.auto.BookLibraryProtos.GenericResponse> getDeleteBookByIsbnMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "deleteBookByIsbn",
      requestType = lib.grpc.services.auto.BookLibraryProtos.IsbnRequest.class,
      responseType = lib.grpc.services.auto.BookLibraryProtos.GenericResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<lib.grpc.services.auto.BookLibraryProtos.IsbnRequest,
      lib.grpc.services.auto.BookLibraryProtos.GenericResponse> getDeleteBookByIsbnMethod() {
    io.grpc.MethodDescriptor<lib.grpc.services.auto.BookLibraryProtos.IsbnRequest, lib.grpc.services.auto.BookLibraryProtos.GenericResponse> getDeleteBookByIsbnMethod;
    if ((getDeleteBookByIsbnMethod = BookServiceGrpc.getDeleteBookByIsbnMethod) == null) {
      synchronized (BookServiceGrpc.class) {
        if ((getDeleteBookByIsbnMethod = BookServiceGrpc.getDeleteBookByIsbnMethod) == null) {
          BookServiceGrpc.getDeleteBookByIsbnMethod = getDeleteBookByIsbnMethod = 
              io.grpc.MethodDescriptor.<lib.grpc.services.auto.BookLibraryProtos.IsbnRequest, lib.grpc.services.auto.BookLibraryProtos.GenericResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "lib.grpc.services.auto.BookService", "deleteBookByIsbn"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  lib.grpc.services.auto.BookLibraryProtos.IsbnRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  lib.grpc.services.auto.BookLibraryProtos.GenericResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new BookServiceMethodDescriptorSupplier("deleteBookByIsbn"))
                  .build();
          }
        }
     }
     return getDeleteBookByIsbnMethod;
  }

  private static volatile io.grpc.MethodDescriptor<lib.grpc.services.auto.BookLibraryProtos.MultipleAuthorsRequest,
      lib.grpc.services.auto.BookLibraryProtos.BookListResponse> getGetBooksByAuthorListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getBooksByAuthorList",
      requestType = lib.grpc.services.auto.BookLibraryProtos.MultipleAuthorsRequest.class,
      responseType = lib.grpc.services.auto.BookLibraryProtos.BookListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<lib.grpc.services.auto.BookLibraryProtos.MultipleAuthorsRequest,
      lib.grpc.services.auto.BookLibraryProtos.BookListResponse> getGetBooksByAuthorListMethod() {
    io.grpc.MethodDescriptor<lib.grpc.services.auto.BookLibraryProtos.MultipleAuthorsRequest, lib.grpc.services.auto.BookLibraryProtos.BookListResponse> getGetBooksByAuthorListMethod;
    if ((getGetBooksByAuthorListMethod = BookServiceGrpc.getGetBooksByAuthorListMethod) == null) {
      synchronized (BookServiceGrpc.class) {
        if ((getGetBooksByAuthorListMethod = BookServiceGrpc.getGetBooksByAuthorListMethod) == null) {
          BookServiceGrpc.getGetBooksByAuthorListMethod = getGetBooksByAuthorListMethod = 
              io.grpc.MethodDescriptor.<lib.grpc.services.auto.BookLibraryProtos.MultipleAuthorsRequest, lib.grpc.services.auto.BookLibraryProtos.BookListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "lib.grpc.services.auto.BookService", "getBooksByAuthorList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  lib.grpc.services.auto.BookLibraryProtos.MultipleAuthorsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  lib.grpc.services.auto.BookLibraryProtos.BookListResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new BookServiceMethodDescriptorSupplier("getBooksByAuthorList"))
                  .build();
          }
        }
     }
     return getGetBooksByAuthorListMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static BookServiceStub newStub(io.grpc.Channel channel) {
    return new BookServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static BookServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new BookServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static BookServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new BookServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class BookServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void addBook(lib.grpc.services.auto.BookLibraryProtos.BookRequest request,
        io.grpc.stub.StreamObserver<lib.grpc.services.auto.BookLibraryProtos.SingleBookResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getAddBookMethod(), responseObserver);
    }

    /**
     */
    public void getBooksByTitle(lib.grpc.services.auto.BookLibraryProtos.TitleRequest request,
        io.grpc.stub.StreamObserver<lib.grpc.services.auto.BookLibraryProtos.BookListResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetBooksByTitleMethod(), responseObserver);
    }

    /**
     */
    public void getBookCounts(lib.grpc.services.auto.BookLibraryProtos.EmptyRequest request,
        io.grpc.stub.StreamObserver<lib.grpc.services.auto.BookLibraryProtos.CountResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetBookCountsMethod(), responseObserver);
    }

    /**
     */
    public void getAllBooks(lib.grpc.services.auto.BookLibraryProtos.EmptyRequest request,
        io.grpc.stub.StreamObserver<lib.grpc.services.auto.BookLibraryProtos.BookListResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetAllBooksMethod(), responseObserver);
    }

    /**
     */
    public void deleteBookByIsbn(lib.grpc.services.auto.BookLibraryProtos.IsbnRequest request,
        io.grpc.stub.StreamObserver<lib.grpc.services.auto.BookLibraryProtos.GenericResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteBookByIsbnMethod(), responseObserver);
    }

    /**
     */
    public void getBooksByAuthorList(lib.grpc.services.auto.BookLibraryProtos.MultipleAuthorsRequest request,
        io.grpc.stub.StreamObserver<lib.grpc.services.auto.BookLibraryProtos.BookListResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetBooksByAuthorListMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAddBookMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                lib.grpc.services.auto.BookLibraryProtos.BookRequest,
                lib.grpc.services.auto.BookLibraryProtos.SingleBookResponse>(
                  this, METHODID_ADD_BOOK)))
          .addMethod(
            getGetBooksByTitleMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                lib.grpc.services.auto.BookLibraryProtos.TitleRequest,
                lib.grpc.services.auto.BookLibraryProtos.BookListResponse>(
                  this, METHODID_GET_BOOKS_BY_TITLE)))
          .addMethod(
            getGetBookCountsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                lib.grpc.services.auto.BookLibraryProtos.EmptyRequest,
                lib.grpc.services.auto.BookLibraryProtos.CountResponse>(
                  this, METHODID_GET_BOOK_COUNTS)))
          .addMethod(
            getGetAllBooksMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                lib.grpc.services.auto.BookLibraryProtos.EmptyRequest,
                lib.grpc.services.auto.BookLibraryProtos.BookListResponse>(
                  this, METHODID_GET_ALL_BOOKS)))
          .addMethod(
            getDeleteBookByIsbnMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                lib.grpc.services.auto.BookLibraryProtos.IsbnRequest,
                lib.grpc.services.auto.BookLibraryProtos.GenericResponse>(
                  this, METHODID_DELETE_BOOK_BY_ISBN)))
          .addMethod(
            getGetBooksByAuthorListMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                lib.grpc.services.auto.BookLibraryProtos.MultipleAuthorsRequest,
                lib.grpc.services.auto.BookLibraryProtos.BookListResponse>(
                  this, METHODID_GET_BOOKS_BY_AUTHOR_LIST)))
          .build();
    }
  }

  /**
   */
  public static final class BookServiceStub extends io.grpc.stub.AbstractStub<BookServiceStub> {
    private BookServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private BookServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BookServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new BookServiceStub(channel, callOptions);
    }

    /**
     */
    public void addBook(lib.grpc.services.auto.BookLibraryProtos.BookRequest request,
        io.grpc.stub.StreamObserver<lib.grpc.services.auto.BookLibraryProtos.SingleBookResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getAddBookMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getBooksByTitle(lib.grpc.services.auto.BookLibraryProtos.TitleRequest request,
        io.grpc.stub.StreamObserver<lib.grpc.services.auto.BookLibraryProtos.BookListResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetBooksByTitleMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getBookCounts(lib.grpc.services.auto.BookLibraryProtos.EmptyRequest request,
        io.grpc.stub.StreamObserver<lib.grpc.services.auto.BookLibraryProtos.CountResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetBookCountsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAllBooks(lib.grpc.services.auto.BookLibraryProtos.EmptyRequest request,
        io.grpc.stub.StreamObserver<lib.grpc.services.auto.BookLibraryProtos.BookListResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetAllBooksMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteBookByIsbn(lib.grpc.services.auto.BookLibraryProtos.IsbnRequest request,
        io.grpc.stub.StreamObserver<lib.grpc.services.auto.BookLibraryProtos.GenericResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteBookByIsbnMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getBooksByAuthorList(lib.grpc.services.auto.BookLibraryProtos.MultipleAuthorsRequest request,
        io.grpc.stub.StreamObserver<lib.grpc.services.auto.BookLibraryProtos.BookListResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetBooksByAuthorListMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class BookServiceBlockingStub extends io.grpc.stub.AbstractStub<BookServiceBlockingStub> {
    private BookServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private BookServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BookServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new BookServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public lib.grpc.services.auto.BookLibraryProtos.SingleBookResponse addBook(lib.grpc.services.auto.BookLibraryProtos.BookRequest request) {
      return blockingUnaryCall(
          getChannel(), getAddBookMethod(), getCallOptions(), request);
    }

    /**
     */
    public lib.grpc.services.auto.BookLibraryProtos.BookListResponse getBooksByTitle(lib.grpc.services.auto.BookLibraryProtos.TitleRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetBooksByTitleMethod(), getCallOptions(), request);
    }

    /**
     */
    public lib.grpc.services.auto.BookLibraryProtos.CountResponse getBookCounts(lib.grpc.services.auto.BookLibraryProtos.EmptyRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetBookCountsMethod(), getCallOptions(), request);
    }

    /**
     */
    public lib.grpc.services.auto.BookLibraryProtos.BookListResponse getAllBooks(lib.grpc.services.auto.BookLibraryProtos.EmptyRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetAllBooksMethod(), getCallOptions(), request);
    }

    /**
     */
    public lib.grpc.services.auto.BookLibraryProtos.GenericResponse deleteBookByIsbn(lib.grpc.services.auto.BookLibraryProtos.IsbnRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeleteBookByIsbnMethod(), getCallOptions(), request);
    }

    /**
     */
    public lib.grpc.services.auto.BookLibraryProtos.BookListResponse getBooksByAuthorList(lib.grpc.services.auto.BookLibraryProtos.MultipleAuthorsRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetBooksByAuthorListMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class BookServiceFutureStub extends io.grpc.stub.AbstractStub<BookServiceFutureStub> {
    private BookServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private BookServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BookServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new BookServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<lib.grpc.services.auto.BookLibraryProtos.SingleBookResponse> addBook(
        lib.grpc.services.auto.BookLibraryProtos.BookRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getAddBookMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<lib.grpc.services.auto.BookLibraryProtos.BookListResponse> getBooksByTitle(
        lib.grpc.services.auto.BookLibraryProtos.TitleRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetBooksByTitleMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<lib.grpc.services.auto.BookLibraryProtos.CountResponse> getBookCounts(
        lib.grpc.services.auto.BookLibraryProtos.EmptyRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetBookCountsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<lib.grpc.services.auto.BookLibraryProtos.BookListResponse> getAllBooks(
        lib.grpc.services.auto.BookLibraryProtos.EmptyRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetAllBooksMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<lib.grpc.services.auto.BookLibraryProtos.GenericResponse> deleteBookByIsbn(
        lib.grpc.services.auto.BookLibraryProtos.IsbnRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteBookByIsbnMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<lib.grpc.services.auto.BookLibraryProtos.BookListResponse> getBooksByAuthorList(
        lib.grpc.services.auto.BookLibraryProtos.MultipleAuthorsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetBooksByAuthorListMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ADD_BOOK = 0;
  private static final int METHODID_GET_BOOKS_BY_TITLE = 1;
  private static final int METHODID_GET_BOOK_COUNTS = 2;
  private static final int METHODID_GET_ALL_BOOKS = 3;
  private static final int METHODID_DELETE_BOOK_BY_ISBN = 4;
  private static final int METHODID_GET_BOOKS_BY_AUTHOR_LIST = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final BookServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(BookServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ADD_BOOK:
          serviceImpl.addBook((lib.grpc.services.auto.BookLibraryProtos.BookRequest) request,
              (io.grpc.stub.StreamObserver<lib.grpc.services.auto.BookLibraryProtos.SingleBookResponse>) responseObserver);
          break;
        case METHODID_GET_BOOKS_BY_TITLE:
          serviceImpl.getBooksByTitle((lib.grpc.services.auto.BookLibraryProtos.TitleRequest) request,
              (io.grpc.stub.StreamObserver<lib.grpc.services.auto.BookLibraryProtos.BookListResponse>) responseObserver);
          break;
        case METHODID_GET_BOOK_COUNTS:
          serviceImpl.getBookCounts((lib.grpc.services.auto.BookLibraryProtos.EmptyRequest) request,
              (io.grpc.stub.StreamObserver<lib.grpc.services.auto.BookLibraryProtos.CountResponse>) responseObserver);
          break;
        case METHODID_GET_ALL_BOOKS:
          serviceImpl.getAllBooks((lib.grpc.services.auto.BookLibraryProtos.EmptyRequest) request,
              (io.grpc.stub.StreamObserver<lib.grpc.services.auto.BookLibraryProtos.BookListResponse>) responseObserver);
          break;
        case METHODID_DELETE_BOOK_BY_ISBN:
          serviceImpl.deleteBookByIsbn((lib.grpc.services.auto.BookLibraryProtos.IsbnRequest) request,
              (io.grpc.stub.StreamObserver<lib.grpc.services.auto.BookLibraryProtos.GenericResponse>) responseObserver);
          break;
        case METHODID_GET_BOOKS_BY_AUTHOR_LIST:
          serviceImpl.getBooksByAuthorList((lib.grpc.services.auto.BookLibraryProtos.MultipleAuthorsRequest) request,
              (io.grpc.stub.StreamObserver<lib.grpc.services.auto.BookLibraryProtos.BookListResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class BookServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    BookServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return lib.grpc.services.auto.BookLibraryProtos.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("BookService");
    }
  }

  private static final class BookServiceFileDescriptorSupplier
      extends BookServiceBaseDescriptorSupplier {
    BookServiceFileDescriptorSupplier() {}
  }

  private static final class BookServiceMethodDescriptorSupplier
      extends BookServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    BookServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (BookServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new BookServiceFileDescriptorSupplier())
              .addMethod(getAddBookMethod())
              .addMethod(getGetBooksByTitleMethod())
              .addMethod(getGetBookCountsMethod())
              .addMethod(getGetAllBooksMethod())
              .addMethod(getDeleteBookByIsbnMethod())
              .addMethod(getGetBooksByAuthorListMethod())
              .build();
        }
      }
    }
    return result;
  }
}
