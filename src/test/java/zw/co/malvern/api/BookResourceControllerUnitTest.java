package zw.co.malvern.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import zw.co.malvern.api.book.create.BookRequest;
import zw.co.malvern.api.book.create.BookResource;
import zw.co.malvern.business.book.BookService;
import zw.co.malvern.utils.exceptions.BookException;
import zw.co.malvern.utils.response.BasicResponse;
import zw.co.malvern.utils.response.ErrorResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zw.co.malvern.utils.TestData.localPort;
import static zw.co.malvern.utils.TestData.newBookRequest;

@WebMvcTest(controllers = {BookResource.class})
@ActiveProfiles("dev")
class BookResourceControllerUnitTest {
    @MockBean
    private BookService bookService;
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("creating new book")
    void givenNewRequest_whenCreatingNewBook_shouldReturnSuccessResponse() throws Exception {
        final String url = "http://localhost:" + localPort + "/api/book/create";
        final BookRequest newBookRequest = newBookRequest();
        final BasicResponse basicResponse = new BasicResponse();
        basicResponse.setSuccess(true);
        basicResponse.setNarrative("New Book with title " + newBookRequest.getTitle() + " has been successfully created");
        given(bookService.createNewBook(any(BookRequest.class))).willReturn(basicResponse);
        createNewBookRequest(url, newBookRequest, basicResponse);
        verify(bookService, times(1)).createNewBook(any(BookRequest.class));
    }


    @Test
    @DisplayName("attempting to create new book with invalid parameters")
    void givenInvalidRequest_whenCreatingNewBook_shouldReturnFailedResponse() throws Exception {
        final String url = "http://localhost:" + localPort + "/api/book/create";
        final BookRequest newBookRequest = newBookRequest();
        newBookRequest.setTitle(null);
        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setSuccess(false);
        errorResponse.setError("Error due to user request");
        errorResponse.setNarrative("Book title cannot be empty");
        given(bookService.createNewBook(any(BookRequest.class)))
                .willThrow(new BookException("Book title cannot be empty"));
        createNewBookRequest(url, newBookRequest, errorResponse, 400);
        verify(bookService, times(1)).createNewBook(any(BookRequest.class));
    }


    @Test
    @DisplayName("when database service is not available")
    void givenInvalidRequest_whenCreatingNewBook_shouldReturnFailedResponseAfterThrowingInternalError() throws Exception {
        final String url = "http://localhost:" + localPort + "/api/book/create";
        final BookRequest newBookRequest = newBookRequest();
        newBookRequest.setTitle(null);
        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setSuccess(false);
        errorResponse.setNarrative("Error with error code XXXIX occurred.See backend manual for reference");
        errorResponse.setError("Internal Error Occurred");
        given(bookService.createNewBook(any(BookRequest.class)))
                .willThrow(new RuntimeException("Failed to connect to the database"));
        createNewBookRequest(url, newBookRequest, errorResponse, 500);
        verify(bookService, times(1)).createNewBook(any(BookRequest.class));
    }


    private void createNewBookRequest(String url, BookRequest newBookRequest, BasicResponse basicResponse) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(newBookRequest)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("narrative").value(basicResponse.getNarrative()))
                .andExpect(jsonPath("success").isBoolean())
                .andExpect(jsonPath("success").value(basicResponse.isSuccess()))
                .andReturn();
    }

    private void createNewBookRequest(String url, BookRequest newBookRequest, ErrorResponse errorResponse,
                                      int statusCode) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(newBookRequest)))
                .andExpect(status().is(statusCode))
                .andExpect(jsonPath("narrative").value(errorResponse.getNarrative()))
                .andExpect(jsonPath("error").value(errorResponse.getError()))
                .andExpect(jsonPath("success").isBoolean())
                .andExpect(jsonPath("success").value(errorResponse.isSuccess()))
                .andReturn();
    }
}
