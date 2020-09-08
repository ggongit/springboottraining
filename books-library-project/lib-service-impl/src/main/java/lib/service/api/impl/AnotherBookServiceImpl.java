package lib.service.api.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lib.dto.BookDTO;
import lib.service.api.BookService;
import lib.service.api.exception.BookException;

@Service
public class AnotherBookServiceImpl implements BookService
{

	@Override
	public BookDTO addBook(Long iSBN, String title, String author, String publisher) throws BookException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookDTO> getAllBooks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BookDTO getBookByIsbn(Long iSBN) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookDTO> getBooksByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookDTO> getBooksByAuthor(String author) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookDTO> getBooksByPublisher(String publisher) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getAllBookCounts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<BookDTO> getBooksByTitleContains(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteBookByIsbn(Long isbn) {
		// TODO Auto-generated method stub
		return false;
		
	}

	@Override
	public void deleteAllRecords() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<BookDTO> getBooksByAuthorList(List<String> authors) {
		// TODO Auto-generated method stub
		return null;
	}

}
