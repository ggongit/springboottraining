package lib.book.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lib.model.Book;

// Query creation from method name
// https://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html#repositories.query-methods.query-creation

@Repository
public interface BookRepository extends CrudRepository<Book, Integer>
{
	public Book getBookByIsbn(Long isbn);
	public List<Book> getBooksByTitle(String title);
	public List<Book> getBooksByAuthor(String author);
	public List<Book> getBooksByPublisher(String publisher);
	public List<Book> getBooksByTitleContains(String keyword);
	
	// @TODO - this does not work!!
	public List<Book> findAllByAuthor(List<String> authors);
	
	// Why @Transactional? Check this out - https://stackoverflow.com/questions/39827054/spring-jpa-repository-transactionality
	@Transactional
	public int deleteBookByIsbn(Long isbn);
}
