package zw.co.malvern.business.purchase;


import zw.co.malvern.api.purchase.PurchaseRequest;
import zw.co.malvern.api.purchase.PurchaseResponse;

@FunctionalInterface
public interface PurchaseService {

    PurchaseResponse purchaseBook(PurchaseRequest purchaseRequest);
}
