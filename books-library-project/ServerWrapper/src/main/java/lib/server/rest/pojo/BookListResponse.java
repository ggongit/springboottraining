package lib.server.rest.pojo;

import java.io.Serializable;
import java.util.List;

public class BookListResponse implements Serializable {
	
	private static final long serialVersionUID = -3796300068603868602L;
	private List<SingleBookResponse> bookResponseList;
	private int statusCode;
	private String statusMessage;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public List<SingleBookResponse> getBookResponseList() {
		return bookResponseList;
	}

	public void setBookResponseList(List<SingleBookResponse> bookResponseList) {
		this.bookResponseList = bookResponseList;
	}

}
