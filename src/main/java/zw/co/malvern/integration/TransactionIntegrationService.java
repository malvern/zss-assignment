package zw.co.malvern.integration;

public interface TransactionIntegrationService {

    TransactionResponse purchaseBook(TransactionRequest transactionRequest);
}
