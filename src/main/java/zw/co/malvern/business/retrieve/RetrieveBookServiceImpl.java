package zw.co.malvern.business.retrieve;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import zw.co.malvern.api.retreive.BookDto;
import zw.co.malvern.api.retreive.BooksResponse;
import zw.co.malvern.domain.Book;
import zw.co.malvern.repository.BookRepository;
import zw.co.malvern.repository.CategoryRepository;

import java.util.Collections;
import java.util.stream.Collectors;

public class RetrieveBookServiceImpl implements RetrieveBookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public RetrieveBookServiceImpl(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public BooksResponse viewAllAvailableBooks(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        final Page<Book> availableBooks = bookRepository.findAll(pageable);
        return buildBooksResponse(availableBooks);
    }

    private BooksResponse buildBooksResponse(Page<Book> availableBooks) {
        final BooksResponse response = new BooksResponse();
        if (availableBooks.getContent().isEmpty()) {
            response.setNarrative("No available books");
            response.setBookDto(Collections.emptyList());
            return response;
        }
        response.setSuccess(true);
        response.setNarrative("Available books");
        response.setBookDto(availableBooks.getContent().stream()
                .map(this::getBookDto).collect(Collectors.toList()));
        return response;
    }

    @Override
    public BooksResponse viewByCategory(String category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        final Page<Book> booksByCategory = bookRepository.findBooksByCategory(category, pageable);
        return buildBooksResponse(booksByCategory);
    }

    private BookDto getBookDto(Book book) {
        final BookDto bookDto = new BookDto();
        bookDto.setDescription(book.getDescription());
        bookDto.setPrice(book.getPrice().doubleValue());
        bookDto.setTitle(book.getTitle());
        bookDto.setCategory(categoryRepository.findCategoryById(book.getCategoryId()));  // need refactoring
        return bookDto;
    }
}
