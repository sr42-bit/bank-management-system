package com.corebanking.engine.infrastructure.web.dto.response;

import java.math.BigDecimal;

public class DashboardStatsResponse {

    private long totalCustomers;
    private long totalAccounts;
    private long activeAccounts;
    private long closedAccounts;
    private BigDecimal totalBalance;

    public DashboardStatsResponse(
            long totalCustomers,
            long totalAccounts,
            long activeAccounts,
            long closedAccounts,
            BigDecimal totalBalance) {

        this.totalCustomers = totalCustomers;
        this.totalAccounts = totalAccounts;
        this.activeAccounts = activeAccounts;
        this.closedAccounts = closedAccounts;
        this.totalBalance = totalBalance;
    }

    public long getTotalCustomers() {
        return totalCustomers;
    }

    public long getTotalAccounts() {
        return totalAccounts;
    }

    public long getActiveAccounts() {
        return activeAccounts;
    }

    public long getClosedAccounts() {
        return closedAccounts;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }
}