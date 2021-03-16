package zw.co.malvern.business;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import zw.co.malvern.api.create.book.BookRequest;
import zw.co.malvern.business.book.BookService;
import zw.co.malvern.business.book.BookServiceImpl;
import zw.co.malvern.domain.Book;
import zw.co.malvern.domain.Category;
import zw.co.malvern.repository.BookRepository;
import zw.co.malvern.repository.CategoryRepository;
import zw.co.malvern.utils.exceptions.BookException;
import zw.co.malvern.utils.response.BasicResponse;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static zw.co.malvern.utils.TestData.*;

class BookServiceUnitTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private CategoryRepository categoryRepository;
    private BookService bookService;
    private ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        bookService = new BookServiceImpl(bookRepository, categoryRepository);
    }

    @Test
    @DisplayName("capture book creation arguments")
    void givenNewBookRequest_whenCreatingNewBook_shouldCreateNewBook() {
        final BookRequest newBookRequest = newBookRequest();
        final Category category = bookCategory();
        given(bookRepository.save(any(Book.class))).willReturn(savedBook());
        given(categoryRepository.findByTitleIgnoreCase(anyString())).willReturn(Optional.of(category));
        final BasicResponse response = bookService.createNewBook(newBookRequest);
        verify(bookRepository).save(bookArgumentCaptor.capture());
        final Book savedBook = bookArgumentCaptor.getValue();
        verify(bookRepository, times(1)).save(any(Book.class));
        verify(categoryRepository, times(1)).findByTitleIgnoreCase(anyString());
        assertNotNull(savedBook);
        assertThat(response).isNotNull();
        assertThat(response.isSuccess()).as("success").isEqualTo(true);
        assertThat(savedBook.getId()).isNull();
        assertThat(savedBook.getRecordCreationDate()).isNull();
        assertThat(savedBook.getCategoryId()).isNotNull();
        assertThat(savedBook.getPrice()).as("book price").isEqualTo(BigDecimal.valueOf(49.91));
        assertThat(savedBook.getDescription()).as("book description").isEqualTo("Java Certification book");
        assertThat(savedBook.getCategoryId()).as("book category").isEqualTo(1L);

    }

    @Test
    @DisplayName("create new book")
    void givenNewBookRequest_whenCreatingNewBook_shouldReturnSuccessResponse() {
        final BookRequest newBookRequest = newBookRequest();
        final Category category = bookCategory();
        given(bookRepository.save(any(Book.class))).willReturn(savedBook());
        given(categoryRepository.findByTitleIgnoreCase(anyString())).willReturn(Optional.of(category));
        final BasicResponse response = bookService.createNewBook(newBookRequest);
        verify(bookRepository, times(1)).save(any(Book.class));
        verify(categoryRepository, times(1)).findByTitleIgnoreCase(anyString());
        assertThat(response).isNotNull();
        assertThat(response.getNarrative()).as("narrative")
                .isEqualTo("New Book with title " + newBookRequest.getTitle() + " has been successfully created");
        assertThat(response.isSuccess()).as("success").isEqualTo(true);
    }

    @Test
    @DisplayName("attempting to create book with invalid title")
    void givenNewBookRequest_whenCreatingNewBookWithInvalidTitle_shouldReturnThrowBookException() {
        final BookRequest newBookRequest = newBookRequest();
        newBookRequest.setTitle(null);
        final String fieldName = "title";
        throwException(newBookRequest, fieldName);
    }

    @Test
    @DisplayName("attempting to create book with invalid description")
    void givenNewBookRequest_whenCreatingNewBookWithInvalidDescription_shouldReturnThrowBookException() {
        final BookRequest newBookRequest = newBookRequest();
        newBookRequest.setDescription(null);
        final String fieldName = "description";
        throwException(newBookRequest, fieldName);
    }

    @Test
    @DisplayName("attempting to create book with invalid price")
    void givenNewBookRequest_whenCreatingNewBookWithInvalidPrice_shouldReturnThrowBookException() {
        final BookRequest newBookRequest = newBookRequest();
        newBookRequest.setPrice(null);
        final String fieldName = "price";
        throwException(newBookRequest, fieldName);
    }

    @Test
    @DisplayName("attempting to create book with invalid description")
    void givenNewBookRequest_whenCreatingNewBookWithInvalidCategory_shouldReturnThrowBookException() {
        final BookRequest newBookRequest = newBookRequest();
        newBookRequest.setCategoryRequest(null);
        final String fieldName = "category";
        throwException(newBookRequest, fieldName);
    }

    @Test
    @DisplayName("attempting to create book with non existence category")
    void givenNewBookRequest_whenCreatingNewBookWithNonExistenceCategory_shouldReturnThrowBookException() {
        final BookRequest newBookRequest = newBookRequest();
        given(categoryRepository.findByTitleIgnoreCase(anyString())).willReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.createNewBook(newBookRequest))
                .isInstanceOf(BookException.class)
                .hasMessage("Book Category is not defined.Please define book category first");
        verify(categoryRepository, times(1)).findByTitleIgnoreCase(anyString());

    }


    private void throwException(BookRequest newBookRequest, String fieldName) {
        assertThatThrownBy(() -> bookService.createNewBook(newBookRequest))
                .isInstanceOf(BookException.class)
                .hasMessage("Book " + fieldName + " cannot be empty");
    }
}
