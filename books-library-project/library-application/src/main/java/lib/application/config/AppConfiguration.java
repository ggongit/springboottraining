package lib.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import lib.service.api.BookService;
import lib.service.api.impl.BookServiceImpl;

//@Configuration
//@ComponentScan(basePackages={"lib.book.repository", "lib.service.api.impl"})
public class AppConfiguration 
{
	//@Bean
	public BookService getService()
	{
		return new BookServiceImpl();
	}
}
