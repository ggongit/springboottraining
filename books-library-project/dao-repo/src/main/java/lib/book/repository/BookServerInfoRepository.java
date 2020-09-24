package lib.book.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lib.model.BookServerInfo;

@Repository
public interface BookServerInfoRepository extends CrudRepository<BookServerInfo, Integer>{

	BookServerInfo findByBook_idIn(int bookId);
}
