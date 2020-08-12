package lib.service.mapper;

import javax.annotation.Generated;
import lib.dto.BookDTO;
import lib.model.Book;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-08-08T23:53:11+0530",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_251 (Oracle Corporation)"
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
}
