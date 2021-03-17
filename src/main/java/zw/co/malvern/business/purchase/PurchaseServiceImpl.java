package zw.co.malvern.business.purchase;

import zw.co.malvern.api.purchase.CardDto;
import zw.co.malvern.api.purchase.PurchaseRequest;
import zw.co.malvern.api.purchase.PurchaseResponse;
import zw.co.malvern.integration.Card;
import zw.co.malvern.integration.TransactionIntegrationService;
import zw.co.malvern.integration.TransactionRequest;
import zw.co.malvern.integration.TransactionResponse;

import static zw.co.malvern.utils.dateUtils.DateTimeCustomFormatter.formatStringToDate;

public class PurchaseServiceImpl implements PurchaseService {
    private final TransactionIntegrationService transactionIntegrationService;

    public PurchaseServiceImpl(TransactionIntegrationService transactionIntegrationService) {
        this.transactionIntegrationService = transactionIntegrationService;
    }

    @Override
    public PurchaseResponse purchaseBook(PurchaseRequest purchaseRequest) {
        final TransactionResponse transactionResponse = transactionIntegrationService
                .purchaseBook(convertPurchaseRequest(purchaseRequest));
        return buildResponse(transactionResponse);
    }

    private PurchaseResponse buildResponse(TransactionResponse transactionResponse) {
        final PurchaseResponse response = new PurchaseResponse();
        response.setDebitReference(transactionResponse.getDebitReference());
        response.setReference(transactionResponse.getReference());
        response.setResponseCode(transactionResponse.getResponseCode());
        response.setResponseDescription(transactionResponse.getResponseDescription());
        response.setUpdated(transactionResponse.getUpdated());
        return response;
    }

    private TransactionRequest convertPurchaseRequest(PurchaseRequest purchaseRequest) {
        final TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setType(purchaseRequest.getType());
        transactionRequest.setReference(purchaseRequest.getReference());
        transactionRequest.setNarration(purchaseRequest.getNarration());
        transactionRequest.setExtendedType(purchaseRequest.getExtendedType());
        transactionRequest.setCard(convertCardDto(purchaseRequest.getCardDto()));
        transactionRequest.setAdditionalData(purchaseRequest.getAdditionalData());
        transactionRequest.setAmount(purchaseRequest.getAmount());
        transactionRequest.setCreated(formatStringToDate(purchaseRequest.getCreated()));
        return transactionRequest;
    }

    private Card convertCardDto(CardDto cardDto) {
        final Card card = new Card();
        card.setExpiry(cardDto.getExpiry());
        card.setId(cardDto.getId());
        return card;
    }
}
