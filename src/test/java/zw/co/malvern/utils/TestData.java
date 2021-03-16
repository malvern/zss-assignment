package zw.co.malvern.utils;

import zw.co.malvern.api.book.create.BookRequest;
import zw.co.malvern.utils.request.CategoryRequest;

public class TestData {
    public static int localPort = 9101;

    public static BookRequest newBookRequest() {
        final BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle("Java SE 8");
        bookRequest.setDescription("Java Certification book");
        bookRequest.setPrice(49.90);
        bookRequest.setCategoryRequest(newCategoryRequest());
        return bookRequest;
    }

    private static CategoryRequest newCategoryRequest() {
        final CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setTitle("educational");
        return categoryRequest;
    }
}
