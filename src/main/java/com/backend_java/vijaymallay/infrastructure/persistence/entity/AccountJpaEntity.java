package com.backend_java.vijaymallay.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountJpaEntity {

    @Id
    private String accountId;
    private Long accountNumber;
    private String customerId;
    private String accountType;
    private double balance;
    private String currencyCode;
    private double availableBalance;
    private double pendingBalance;
    private String status;
    private double dailyWithdrawalLimit;
    private double transferLimit;
    private double overdraftLimit;
    private String productId;
    private String branchId;
    private String kycStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long version;

}
