package lib.dto;

public class BookDTO 
{
	private Integer id;
	private Long isbn;
	private String title;
	private String author;
	private String publisher;
	
	public BookDTO() {}
	public BookDTO(Long isbn, String title, String author, String publisher)
	{
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
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
	
	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id) 
	{
		this.id = id;
	}
	
	public void setIsbn(Long isbn) 
	{
		this.isbn = isbn;
	}

	public void setTitle(String title) 
	{
		this.title = title;
	}

	public void setAuthor(String author) 
	{
		this.author = author;
	}

	public void setPublisher(String publisher) 
	{
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
