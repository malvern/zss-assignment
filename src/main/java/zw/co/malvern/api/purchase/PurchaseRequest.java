package zw.co.malvern.api.purchase;

import java.util.HashMap;

public class PurchaseRequest {
    private String type;
    private String extendedType;
    private Double amount;
    private String created;
    private String reference;
    private String narration;
    private CardDto cardDto;
    private HashMap<String, Object> additionalData;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExtendedType() {
        return extendedType;
    }

    public void setExtendedType(String extendedType) {
        this.extendedType = extendedType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public CardDto getCardDto() {
        return cardDto;
    }

    public void setCardDto(CardDto cardDto) {
        this.cardDto = cardDto;
    }

    public HashMap<String, Object> getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(HashMap<String, Object> additionalData) {
        this.additionalData = additionalData;
    }
}
