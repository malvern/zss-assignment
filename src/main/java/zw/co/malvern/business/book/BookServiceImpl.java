package zw.co.malvern.business.book;

import zw.co.malvern.api.book.create.BookRequest;
import zw.co.malvern.domain.Book;
import zw.co.malvern.domain.Category;
import zw.co.malvern.repository.BookRepository;
import zw.co.malvern.repository.CategoryRepository;
import zw.co.malvern.utils.exceptions.BookException;
import zw.co.malvern.utils.request.CategoryRequest;
import zw.co.malvern.utils.response.BasicResponse;

import java.math.BigDecimal;
import java.util.Optional;


public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public BookServiceImpl(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public BasicResponse createNewBook(BookRequest bookRequest) {
        validateBookRequest(bookRequest);
        final Category category = getBookCategory(bookRequest);
        final Book savedBook = bookRepository.save(convertBookRequest(bookRequest, category.getId()));
        return buildResponse(savedBook);
    }

    private Category getBookCategory(BookRequest bookRequest) {
        final Optional<Category> category = categoryRepository
                .findByTitle(bookRequest.getCategoryRequest().getTitle());
        if (!category.isPresent())
            throw new BookException("Book Category is not defined.Please define book category first");
        return category.get();
    }

    private BasicResponse buildResponse(Book savedBook) {
        final BasicResponse response = new BasicResponse();
        response.setNarrative("New Book with title " + savedBook.getTitle() + " has been successfully created");
        response.setSuccess(true);
        return response;
    }

    private void validateBookRequest(BookRequest bookRequest) {
        validateRequestStringField(bookRequest.getTitle(), "title");
        validateRequestStringField(bookRequest.getDescription(), "description");
        validateRequestDoubleField(bookRequest.getPrice(), "price");
        validateRequestCategoryField(bookRequest.getCategoryRequest(), "category");
    }


    private void validateRequestStringField(String request, String fieldName) {
        if (request == null || request.isEmpty())
            throw new BookException("Book " + fieldName + " cannot be empty");
    }

    private void validateRequestCategoryField(CategoryRequest category, String fieldName) {
        if (category == null)
            throw new BookException("Book " + fieldName + " cannot be empty");
    }

    private void validateRequestDoubleField(Double request, String fieldName) {
        if (request == null)
            throw new BookException("Book " + fieldName + " cannot be empty");
    }


    private Book convertBookRequest(BookRequest bookRequest, Long categoryId) {
        final Book book = new Book();
        book.setTitle(bookRequest.getTitle());
        book.setDescription(bookRequest.getDescription());
        book.setPrice(BigDecimal.valueOf(bookRequest.getPrice()));
        book.setCategoryId(categoryId);
        return book;
    }
}
