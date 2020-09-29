package lib.rest.integtest;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.protobuf.util.JsonFormat;

import lib.grpc.services.auto.BookLibraryProtos.BookDetails;
import lib.grpc.services.auto.BookLibraryProtos.BookListResponse;
import lib.grpc.services.auto.BookLibraryProtos.BookRequest;
import lib.grpc.services.auto.BookLibraryProtos.GenericResponse;
import lib.grpc.services.auto.BookLibraryProtos.GenericResponse.ResponseType;
import lib.grpc.services.auto.BookLibraryProtos.SingleBookResponse;
import lib.grpc.services.auto.BookLibraryProtos.UserLoginRequest;
import lib.rest.controller.BookController;
import lib.rest.controller.ServerOpsController;
import lib.rest.main.WebServerStarter;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = WebServerStarter.class)
@WebMvcTest(controllers = { BookController.class, ServerOpsController.class })
public class BookServerOpsTests {
	private static final String SERVER_URL = "http://localhost:8080/nuxeo";
	private static final String LOGIN_ID = "Administrator";
	private static final String LOGIN_PSWD = "Administrator";

	@Autowired
	private MockMvc mockMvc;

	private Long getNewIsbn() {
		Random random = new Random();
		random.nextInt(100000);
		return Long.valueOf(random.nextInt(100000));
	}

	private void login() throws Exception {
		UserLoginRequest.Builder loginReqBuilder = UserLoginRequest.newBuilder().setServerUrl(SERVER_URL)
				.setUserId(LOGIN_ID).setPassword(LOGIN_PSWD);

		String request = JsonFormat.printer().print(loginReqBuilder);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/book/login").accept(MediaType.APPLICATION_JSON)
				.content(request).contentType(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		GenericResponse genericResponse = toGenericResponse(response.getContentAsString());
		Assert.assertTrue(genericResponse.getType() == ResponseType.SUCCESS);
	}

	private void logout() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/book/logout").accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		GenericResponse genericResponse = toGenericResponse(response.getContentAsString());
		Assert.assertTrue(genericResponse.getType() == ResponseType.SUCCESS);

	}

	private BookDetails addBook(Long isbn, String title, String author, String publisher) throws Exception {
		BookDetails.Builder bookDetails = BookDetails.newBuilder().setIsbn(isbn).setTitle(title).setAuthor(author)
				.setPublisher(publisher);
		BookRequest.Builder bookReq = BookRequest.newBuilder().setBookDetails(bookDetails);
		String request = JsonFormat.printer().print(bookReq);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/book/add").accept(MediaType.APPLICATION_JSON)
				.content(request).contentType(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		SingleBookResponse resp = toSingleBookResponse(response.getContentAsString());
		return resp.getBookDetails();
	}

	private GenericResponse toGenericResponse(String jsonString) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.registerTypeAdapter(GenericResponse.class, new GenericResponseAdapter()).create();
		GenericResponse genResp = gson.fromJson(jsonString, GenericResponse.class);
		return genResp;
	}

	private SingleBookResponse toSingleBookResponse(String jsonString) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.registerTypeAdapter(SingleBookResponse.class, new SingleBookResponseAdapter()).create();
		SingleBookResponse obj = gson.fromJson(jsonString, SingleBookResponse.class);
		return obj;
	}

	private BookListResponse toBookListResponse(String jsonString) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.registerTypeAdapter(BookListResponse.class, new BookListResponseAdapter()).create();
		BookListResponse obj = gson.fromJson(jsonString, BookListResponse.class);
		return obj;
	}

	@Before
	public void initTest() throws Exception {
		login();
	}

	@After
	public void tearDown() throws Exception {
		logout();
	}

	@Test
	public void saveBookinServerShouldBeSuccessful() throws Exception {
		Long isbn = addBook(getNewIsbn(), "BookTitle1", "BookAuthor1", "BookPublisher1").getIsbn();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/book/save/" + isbn)
				.accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
		GenericResponse genResp = toGenericResponse(response.getContentAsString());
		Assert.assertTrue(genResp.getType() == ResponseType.SUCCESS);
	}

	@Test
	public void fetchAllBooksFromServerShouldBeSuccessful() throws Exception {
		BookDetails localBook = addBook(getNewIsbn(), "BookTitle2", "BookAuthor2", "BookPublisher2");
		Long isbn = localBook.getIsbn();
		RequestBuilder addBookRequestBuilder = MockMvcRequestBuilders.post("/book/save/" + isbn)
				.accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(addBookRequestBuilder).andReturn().getResponse();

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/book/allRepo").accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		BookListResponse bookRes = toBookListResponse(response.getContentAsString());
		Assert.assertTrue(bookRes.getGenericResponse().getType() == ResponseType.SUCCESS);
		Assert.assertTrue(bookRes.getBookListCount() > 1);

		List<BookDetails> filterBook = bookRes.getBookListList().stream()
				.filter(book -> book.getIsbn() == localBook.getIsbn()).collect(Collectors.toList());
		Assert.assertTrue(filterBook.size() == 1);
		Assert.assertFalse(filterBook.get(0).getRepoId().isEmpty());
	}

	@Test
	public void downloadBookFromServerShouldBeSuccessful() throws Exception
	{
		// Add book locally
		BookDetails localBook = addBook(getNewIsbn(), "BookTitle3", "BookAuthor3", "BookPublisher3");
		
		// Save in server
		Long isbn = localBook.getIsbn();
		RequestBuilder addBookRequestBuilder = MockMvcRequestBuilders.post("/book/save/" + isbn)
				.accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(addBookRequestBuilder).andReturn().getResponse();
		
		// Delete it from local
		RequestBuilder deleteBookRequestBuilder = MockMvcRequestBuilders.delete("/book/byIsbn/" + isbn)
				.accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(deleteBookRequestBuilder).andReturn().getResponse();
		
		// Download it
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/book/download/" + isbn)
				.accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
		SingleBookResponse bookResponse = toSingleBookResponse(response.getContentAsString());
		Assert.assertTrue(bookResponse.getGenericResponse().getType() == ResponseType.SUCCESS);
		Assert.assertTrue(bookResponse.getBookDetails().getIsbn() == isbn);
	}
	
	public class SingleBookResponseAdapter extends TypeAdapter<SingleBookResponse> {
		/**
		 * Override the read method to return a {@SingleBookResponse} object from it's
		 * json representation.
		 */
		@Override
		public SingleBookResponse read(JsonReader jsonReader) throws IOException {

			// Create a builder for the SingleBookResponse message
			SingleBookResponse.Builder bookResponseBuilder = SingleBookResponse.newBuilder();

			// Use the JsonFormat class to parse the json string into the builder object
			// The Json string will be parsed fromm the JsonReader object
			JsonFormat.parser().merge(JsonParser.parseReader(jsonReader).toString(), bookResponseBuilder);
			// Return the built Person message
			return bookResponseBuilder.build();
		}

		/**
		 * Override the write method and set the json value of the Person message.
		 */
		@Override
		public void write(JsonWriter jsonWriter, SingleBookResponse person) throws IOException {
			// Call the printer of the JsonFormat class to convert the Person proto message
			// to Json
			jsonWriter.jsonValue(JsonFormat.printer().print(person));
		}
	}

	public class GenericResponseAdapter extends TypeAdapter<GenericResponse> {
		/**
		 * Override the read method to return a {@SingleBookResponse} object from it's
		 * json representation.
		 */
		@Override
		public GenericResponse read(JsonReader jsonReader) throws IOException {

			// Create a builder for the SingleBookResponse message
			GenericResponse.Builder bookResponseBuilder = GenericResponse.newBuilder();

			// Use the JsonFormat class to parse the json string into the builder object
			// The Json string will be parsed fromm the JsonReader object
			JsonFormat.parser().merge(JsonParser.parseReader(jsonReader).toString(), bookResponseBuilder);
			// Return the built Person message
			return bookResponseBuilder.build();
		}

		/**
		 * Override the write method and set the json value of the Person message.
		 */
		@Override
		public void write(JsonWriter jsonWriter, GenericResponse person) throws IOException {
			// Call the printer of the JsonFormat class to convert the Person proto message
			// to Json
			jsonWriter.jsonValue(JsonFormat.printer().print(person));
		}
	}

	public class BookListResponseAdapter extends TypeAdapter<BookListResponse> {
		/**
		 * Override the read method to return a {@SingleBookResponse} object from it's
		 * json representation.
		 */
		@Override
		public BookListResponse read(JsonReader jsonReader) throws IOException {

			// Create a builder for the SingleBookResponse message
			BookListResponse.Builder bookResponseBuilder = BookListResponse.newBuilder();

			// Use the JsonFormat class to parse the json string into the builder object
			// The Json string will be parsed fromm the JsonReader object
			JsonFormat.parser().merge(JsonParser.parseReader(jsonReader).toString(), bookResponseBuilder);
			// Return the built Person message
			return bookResponseBuilder.build();
		}

		/**
		 * Override the write method and set the json value of the Person message.
		 */
		@Override
		public void write(JsonWriter jsonWriter, BookListResponse person) throws IOException {
			// Call the printer of the JsonFormat class to convert the Person proto message
			// to Json
			jsonWriter.jsonValue(JsonFormat.printer().print(person));
		}
	}
}
