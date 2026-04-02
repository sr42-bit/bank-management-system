package com.corebanking.engine.application.service.dashboard;

import com.corebanking.engine.infrastructure.persistence.jpa.repository.SpringAccountJpaRepository;
import com.corebanking.engine.infrastructure.persistence.jpa.repository.SpringCustomerJpaRepository;
import com.corebanking.engine.infrastructure.web.dto.response.DashboardStatsResponse;
import com.corebanking.engine.infrastructure.web.dto.response.GrowthResponse;
import org.springframework.stereotype.Service;
import com.corebanking.engine.infrastructure.persistence.jpa.entity.AccountJpaEntity;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DashboardService {

    private final SpringCustomerJpaRepository customerRepository;
    private final SpringAccountJpaRepository accountRepository;

    public DashboardService(
            SpringCustomerJpaRepository customerRepository,
            SpringAccountJpaRepository accountRepository) {

        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    // KPI Stats
    public DashboardStatsResponse getStats() {

        long totalCustomers = customerRepository.count();
        long totalAccounts = accountRepository.count();
        long activeAccounts = accountRepository.countByStatus("ACTIVE");
        long closedAccounts = accountRepository.countByStatus("CLOSED");

        BigDecimal totalBalance = accountRepository.sumTotalBalance();

        if (totalBalance == null) {
            totalBalance = BigDecimal.ZERO;
        }

        return new DashboardStatsResponse(
                totalCustomers,
                totalAccounts,
                activeAccounts,
                closedAccounts,
                totalBalance
        );
    }

    // Account growth graph
    public List<GrowthResponse> getAccountGrowth(int year) {

        List<Object[]> results = accountRepository.getAccountGrowth();

        return results.stream()
                .map(r -> new GrowthResponse(
                        monthName((Integer) r[0]),
                        (Long) r[1]
                ))
                .toList();
    }

    // Customer growth graph
    public List<GrowthResponse> getCustomerGrowth(int year) {

        List<Object[]> results = customerRepository.getCustomerGrowth();

        return results.stream()
                .map(r -> new GrowthResponse(
                        monthName((Integer) r[0]),
                        (Long) r[1]
                ))
                .toList();
    }

    // Convert month number to name
    private String monthName(int month) {

        String[] months = {
                "Jan","Feb","Mar","Apr","May","Jun",
                "Jul","Aug","Sep","Oct","Nov","Dec"
        };

        return months[month - 1];
    }

    // Get all accounts (for Accounts page)
    public List<AccountJpaEntity> getAllAccounts() {
        return accountRepository.findAll();
    }
    
    
    // Get single account
    @SuppressWarnings("null")
    public AccountJpaEntity getAccountById(String accountId) {
    
        return accountRepository
                .findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }
}