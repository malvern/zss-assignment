package zw.co.malvern.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;
import zw.co.malvern.business.create.book.BookService;
import zw.co.malvern.business.create.book.BookServiceImpl;
import zw.co.malvern.business.create.category.CategoryService;
import zw.co.malvern.business.create.category.CategoryServiceImpl;
import zw.co.malvern.business.purchase.PurchaseService;
import zw.co.malvern.business.purchase.PurchaseServiceImpl;
import zw.co.malvern.business.retrieve.RetrieveBookService;
import zw.co.malvern.business.retrieve.RetrieveBookServiceImpl;
import zw.co.malvern.domain.DomainMarkerInterface;
import zw.co.malvern.integration.TransactionIntegrationService;
import zw.co.malvern.integration.TransactionIntegrationServiceImpl;
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

    @Bean
    public CategoryService categoryService(final CategoryRepository categoryRepository) {
        return new CategoryServiceImpl(categoryRepository);
    }

    @Bean
    public RetrieveBookService retrieveBookService(final BookRepository bookRepository,
                                                   final CategoryRepository categoryRepository) {
        return new RetrieveBookServiceImpl(bookRepository, categoryRepository);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public TransactionIntegrationService transactionIntegrationService(final RestTemplate restTemplate) {
        return new TransactionIntegrationServiceImpl(restTemplate);
    }

    @Bean
    public PurchaseService purchaseService(final TransactionIntegrationService transactionIntegrationService) {
        return new PurchaseServiceImpl(transactionIntegrationService);
    }
}
