package lib.service.mapper;

import javax.annotation.Generated;
import lib.dto.BookDTO;
import lib.model.Book;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-09-29T12:59:29+0530",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_252 (AdoptOpenJDK)"
)
public class BookEntityMapperImpl implements BookEntityMapper {

    @Override
    public BookDTO bookToBookDTO(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDTO bookDTO = new BookDTO();

        bookDTO.setId( book.getId() );
        bookDTO.setIsbn( book.getIsbn() );
        bookDTO.setTitle( book.getTitle() );
        bookDTO.setAuthor( book.getAuthor() );
        bookDTO.setPublisher( book.getPublisher() );

        return bookDTO;
    }

    @Override
    public Book bookDTOToBook(BookDTO book) {
        if ( book == null ) {
            return null;
        }

        Book book1 = new Book();

        book1.setId( book.getId() );
        book1.setIsbn( book.getIsbn() );
        book1.setTitle( book.getTitle() );
        book1.setAuthor( book.getAuthor() );
        book1.setPublisher( book.getPublisher() );

        return book1;
    }
}
