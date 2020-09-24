package lib.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import lib.dto.BookDTO;
import lib.model.Book;

/* Mapstruct references:
 * https://medium.com/@tushar.sharma118/mapstruct-an-exploratory-example-51047d97352d
 * https://mapstruct.org/
 * https://mapstruct.org/documentation/stable/reference/html/
 * 
*/
 
@Mapper //(componentModel="spring")
public interface BookEntityMapper
{
	BookEntityMapper INSTANCE = Mappers.getMapper(BookEntityMapper.class);
	
	//@Mapping(source = "id", ignore = true)
	//@BeanMapping(ignoreByDefault = true, ignoreUnmappedSourceProperties = {"id"})
	BookDTO bookToBookDTO(Book book);
	Book bookDTOToBook(BookDTO book);
}
