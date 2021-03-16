package zw.co.malvern.utils;

import zw.co.malvern.api.book.create.BookRequest;
import zw.co.malvern.domain.Book;
import zw.co.malvern.domain.Category;
import zw.co.malvern.utils.request.CategoryRequest;

import java.math.BigDecimal;

public class TestData {
    public static int localPort = 9101;

    public static BookRequest newBookRequest() {
        final BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle("Java SE 8");
        bookRequest.setDescription("Java Certification book");
        bookRequest.setPrice(49.91);
        bookRequest.setCategoryRequest(newCategoryRequest());
        return bookRequest;
    }

    private static CategoryRequest newCategoryRequest() {
        final CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setTitle("educational");
        return categoryRequest;
    }

    public static Book savedBook() {
        final Book book = new Book();
        final Category category = new Category();
        category.setTitle("educational");
        book.setPrice(new BigDecimal(49.91));
        book.setCategoryId(1L);
        book.setDescription("Java Certification book");
        book.setTitle("Java SE 8");
        return book;
    }

    public static Category bookCategory() {
        final Category category = new Category();
        category.setId(1L);
        category.setTitle("educational");
        return category;
    }
}
