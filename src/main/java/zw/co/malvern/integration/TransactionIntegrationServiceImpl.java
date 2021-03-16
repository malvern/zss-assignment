package zw.co.malvern.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


public class TransactionIntegrationServiceImpl implements TransactionIntegrationService {

    @Value("${payment.url}")
    private String url;
    @Value("${payment.token}")
    private String token;
    private final RestTemplate restTemplate;

    public TransactionIntegrationServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public TransactionResponse purchaseBook(TransactionRequest transactionRequest) {

        final ResponseEntity<TransactionResponse> response = restTemplate.exchange(url, HttpMethod.POST,
                new HttpEntity<>(transactionRequest, requestHeaders()), TransactionResponse.class);
        return response.getBody();
    }

    private HttpHeaders requestHeaders() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set("Authorization", token);
        return httpHeaders;
    }
}
