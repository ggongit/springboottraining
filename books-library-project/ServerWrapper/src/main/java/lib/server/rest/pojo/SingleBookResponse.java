package lib.server.rest.pojo;

import java.io.Serializable;

public class SingleBookResponse implements Serializable
{
	private static final long serialVersionUID = 3315160803586443505L;
	private String repoId;
	private Long isbn;
	private String title;
	private String author;
	private String publisher;
	
	public Long getIsbn() {
		return isbn;
	}
	public void setIsbn(Long isbn) {
		this.isbn = isbn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getRepoId() {
		return repoId;
	}
	public void setRepoId(String id) {
		this.repoId = id;
	}

}
