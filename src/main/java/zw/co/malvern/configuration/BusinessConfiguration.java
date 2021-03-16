package zw.co.malvern.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import zw.co.malvern.business.book.BookService;
import zw.co.malvern.business.book.BookServiceImpl;
import zw.co.malvern.domain.DomainMarkerInterface;
import zw.co.malvern.repository.BookRepository;
import zw.co.malvern.repository.CategoryRepository;
import zw.co.malvern.repository.RepositoryMarkerInterface;

@Configuration
@EntityScan(basePackageClasses = {DomainMarkerInterface.class})
@EnableJpaRepositories(basePackageClasses = {RepositoryMarkerInterface.class})
public class BusinessConfiguration {

    @Bean
    public BookService bookService(final BookRepository bookRepository, final CategoryRepository categoryRepository) {
        return new BookServiceImpl(bookRepository, categoryRepository);
    }
}
