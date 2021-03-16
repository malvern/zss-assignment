package zw.co.malvern.utils;

import zw.co.malvern.api.create.book.BookRequest;
import zw.co.malvern.domain.Book;
import zw.co.malvern.domain.Category;
import zw.co.malvern.integration.Card;
import zw.co.malvern.integration.TransactionRequest;
import zw.co.malvern.utils.request.CategoryRequest;

import java.math.BigDecimal;
import java.util.HashMap;

import static zw.co.malvern.utils.dateUtils.DateTimeCustomFormatter.formatStringToDate;

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

    public static CategoryRequest categoryRequest() {
        final CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setTitle("educational");
        return categoryRequest;
    }

    public static TransactionRequest transactionRequest() {
        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("SampleKey", "This is a sample value");
        final TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(123.33);
        transactionRequest.setAdditionalData(hashMap);
        transactionRequest.setCard(getCard());
        transactionRequest.setExtendedType("NONE");
        transactionRequest.setNarration("Test Narration");
        transactionRequest.setReference("1a1d7ef9-03e9-4224-bd9d-4b00a4bbd1cc");
        transactionRequest.setType("PURCHASE");
        transactionRequest.setCreated(formatStringToDate("2021-03-11T13:19:17.676Z"));

        return transactionRequest;
    }

    private static Card getCard() {
        final Card card = new Card();
        card.setId("1234560000000001");
        card.setExpiry("2020-01-01");
        return card;
    }


}
