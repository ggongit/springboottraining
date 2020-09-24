package lib.server.rest.client;

import java.io.IOException;

import org.nuxeo.client.NuxeoClient;
import org.nuxeo.client.spi.NuxeoClientRemoteException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lib.server.wrapper.exceptions.ServerException;
import okhttp3.Response;

@Service
public class NXServerServices {
	private NuxeoClient client = null;
	private String url = null;
	private ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
			false);

	public void initClient(String url, String username, String password) throws ServerException {

		if(username == null || url == null || password == null)
		{
			throw new ServerException("Provided input is invalid");
		}
		
		if (client == null) 
		{
			try {
				client = new NuxeoClient.Builder().url(url).authentication(username, password).schemas("*").connect();
				this.url = url;
			} catch (NuxeoClientRemoteException e) {
				throw new ServerException("Exception while connecting to server (possibly incorrect login details) " + e.getMessage());
			}
			
		}
		else if(getCurrentUser().equals(username))
		{
			throw new ServerException("Given user is already logged-in");
		}
		else
		{
			throw new ServerException("A different user is already logged-in");
		}
	}

	public void logout() throws ServerException {
		validateLogin();
		
		if (client != null) {
			client.disconnect();
			client = null;
		}
	}

	private void validateLogin() throws ServerException {
		if (client == null) {
			throw new ServerException("User not logged in");
		}
	}

	public String getUrl() {
		return url;
	}

	public String getCurrentUser() throws ServerException {
		validateLogin();
		return client.getCurrentUser().getUserName();
	}
	
	public <T> T postCall(String url, Object requestBody, Class<T> response) throws ServerException, IOException {

		validateLogin();

		Response apiResponse = this.client.post(url, this.mapper.writeValueAsString(requestBody));
		if (!apiResponse.isSuccessful()) {
			throw new ServerException(apiResponse.message());
		}

		String restAPIResponse = apiResponse.body().string();
		T responseBody = this.mapper.readValue(restAPIResponse, response);

		return responseBody;
	}
	
	public <T> T getCall(String url, Class<T> response) throws ServerException, IOException {

		validateLogin();

		Response apiResponse = this.client.get(url);
		if (!apiResponse.isSuccessful()) {
			throw new ServerException(apiResponse.message());
		}

		String restAPIResponse = apiResponse.body().string();
		T responseBody = this.mapper.readValue(restAPIResponse, response);

		return responseBody;
	}
}
