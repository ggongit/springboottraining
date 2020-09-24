package lib.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class BookServerInfo 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@OneToOne(cascade = {CascadeType.MERGE})
    @JoinColumn (name="fk_book_id",  unique=true)
    //@MapsId
	private Book book;
	
	@Column(nullable = false)
	private String repoId;

	public BookServerInfo ()
	{
		
	}
	
	public BookServerInfo(Book book, String repoId)
	{
		this.book = book;
		this.repoId = repoId;
	}
	
	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getRepoId() {
		return repoId;
	}

	public void setRepoId(String repoId) {
		this.repoId = repoId;
	}
	
}
