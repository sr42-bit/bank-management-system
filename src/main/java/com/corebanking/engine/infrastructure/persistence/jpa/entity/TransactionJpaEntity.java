package com.corebanking.engine.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.corebanking.engine.domain.model.enums.TransactionStatus;
import com.corebanking.engine.domain.model.enums.TransactionType;

@Entity
@Table(name = "transactions")
public class TransactionJpaEntity {

    @Id
    private String transactionId;

    private String fromAccountId;

    private String toAccountId;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionType type;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public TransactionJpaEntity() {}

    // GETTERS
    public String getTransactionId() { return transactionId; }
    public String getFromAccountId() { return fromAccountId; }
    public String getToAccountId() { return toAccountId; }
    public BigDecimal getAmount() { return amount; }
    public TransactionType getType() { return type; }
    public TransactionStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // SETTERS
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public void setFromAccountId(String fromAccountId) { this.fromAccountId = fromAccountId; }
    public void setToAccountId(String toAccountId) { this.toAccountId = toAccountId; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setType(TransactionType type) { this.type = type; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setStatus(TransactionStatus status) { this.status = status; }
}