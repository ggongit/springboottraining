package lib.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Book
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable = false, unique=true)
	private Long isbn;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String author;
	@Column(nullable = false)
	private String publisher;
	
//	@OneToOne(mappedBy="book", cascade = {CascadeType.MERGE,CascadeType.REFRESH}, fetch = FetchType.EAGER)
	@OneToOne(mappedBy="book", cascade = {CascadeType.REMOVE})
	private BookServerInfo bookServerInfo;
	
	public Book()
	{
		
	}
	
	public Book(Long isbn, String title, String author, String publisher)
	{
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
	}

	// Needs to 
	public Integer getId() 
	{
		return id;
	}

	public Long getIsbn() 
	{
		return isbn;
	}

	public String getTitle() 
	{
		return title;
	}

	public String getAuthor() 
	{
		return author;
	}

	public String getPublisher() 
	{
		return publisher;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public void setIsbn(Long isbn) {
		this.isbn = isbn;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	@Override
	public String toString() 
	{
		return "Book==> ISBN: " + getIsbn() + 
				"; Title: " + getTitle() + 
				"; Author: " + getAuthor() + 
				"; Publisher: " + getPublisher(); 
	}
	
}
