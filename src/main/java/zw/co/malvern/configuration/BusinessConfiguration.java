package zw.co.malvern.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zw.co.malvern.business.book.BookService;
import zw.co.malvern.business.book.BookServiceImpl;

@Configuration
public class BusinessConfiguration {

    @Bean
    public BookService bookService() {
        return new BookServiceImpl();
    }
}
