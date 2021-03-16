package zw.co.malvern.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Book {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String description;
    private BigDecimal price;
    private Long categoryId;
    private LocalDateTime recordCreationDate;

    @PrePersist
    void init() {
        if (recordCreationDate == null)
            recordCreationDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public LocalDateTime getRecordCreationDate() {
        return recordCreationDate;
    }

}
