package zw.co.malvern.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import zw.co.malvern.api.book.create.BookRequest;
import zw.co.malvern.utils.response.BasicResponse;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static zw.co.malvern.utils.TestData.newBookRequest;

@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookResourceIntegrationTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;

    private String url;

    @BeforeEach
    void setUp() {
        url = "http://localhost:" + port + "/api/book/create";
    }

    @Test
    @DisplayName("creating new book")
    void givenBookRequest_whenCreatingNewBook_shouldReturnSuccessResponse() {
        final BookRequest newBookRequest = newBookRequest();
        final BasicResponse expectedResponse = new BasicResponse();
        expectedResponse
                .setNarrative("New Book with title " + newBookRequest.getTitle() + " has been successfully created");
        expectedResponse.setSuccess(true);
        createNewBookRequest(newBookRequest, expectedResponse, 200);
    }

    @Test
    @DisplayName("creating new book with invalid request : missing title")
    void givenInvalidBookRequest_whenCreatingNewBook_shouldReturnFailedResponse() {
        final BookRequest newBookRequest = newBookRequest();
        newBookRequest.setTitle(null);
        final BasicResponse expectedResponse = new BasicResponse();
        expectedResponse.setNarrative("Book title cannot be empty");
        createNewBookRequest(newBookRequest, expectedResponse, 400);
    }

    private void createNewBookRequest(BookRequest newBookRequest,
                                      BasicResponse expectedResponse,
                                      int statusCode) {
        final ResponseEntity<BasicResponse> response = testRestTemplate
                .exchange(url, HttpMethod.POST, new HttpEntity<>(newBookRequest), BasicResponse.class);
        assertThat(response).as("creating new book response").isNotNull();
        assertThat(response.getStatusCode().value()).as("response status")
                .isEqualTo(statusCode);
        assertThat(response.getBody()).as("response body").isNotNull();
        assertThat(response.getBody().getNarrative()).as("response body narrative")
                .isEqualTo(expectedResponse.getNarrative());
        assertThat(response.getBody().isSuccess()).as("response body status check")
                .isEqualTo(expectedResponse.isSuccess());
    }


}
