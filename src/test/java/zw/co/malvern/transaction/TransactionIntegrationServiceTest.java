package zw.co.malvern.transaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import zw.co.malvern.integration.TransactionResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static zw.co.malvern.utils.TestData.transactionRequest;

@SpringBootTest
@ActiveProfiles("dev")
class TransactionIntegrationServiceTest {
    private RestTemplate restTemplate;
    @Value("${payment.url}")
    private String url;
    @Value("${payment.token}")
    private String token;

    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
    }

    @Test
    @DisplayName("send purchase request")
    void givenPaymentRequest_whenPurchasingBook_shouldReturnSuccess() {
        final ResponseEntity<TransactionResponse> response = restTemplate.exchange(url, HttpMethod.POST,
                new HttpEntity<>(transactionRequest(), requestHeaders()), TransactionResponse.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody().getDebitReference()).isNotNull();
        assertThat(response.getBody().getReference()).isEqualTo("1a1d7ef9-03e9-4224-bd9d-4b00a4bbd1cc");
        assertThat(response.getBody().getResponseCode()).isEqualTo("000");
        assertThat(response.getBody().getUpdated()).isNotNull();

    }


    private HttpHeaders requestHeaders() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set("Authorization", token);
        return httpHeaders;
    }
}
