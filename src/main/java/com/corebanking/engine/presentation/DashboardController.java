package com.corebanking.engine.presentation;

import com.corebanking.engine.application.service.DashboardService;
import com.corebanking.engine.infrastructure.web.dto.response.DashboardStatsResponse;
import com.corebanking.engine.infrastructure.web.dto.response.GrowthResponse;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    // ---------------------------
    // KPI Cards
    // ---------------------------
    @GetMapping("/stats")
    public DashboardStatsResponse getStats() {
        return dashboardService.getStats();
    }

    // ---------------------------
    // Account Growth by Year
    // ---------------------------
    @GetMapping("/accounts-growth")
    public List<GrowthResponse> accountGrowth(
            @RequestParam int year
    ) {
        return dashboardService.getAccountGrowth(year);
    }

    // ---------------------------
    // Customer Growth by Year
    // ---------------------------
    @GetMapping("/customers-growth")
    public List<GrowthResponse> customerGrowth(
            @RequestParam int year
    ) {
        return dashboardService.getCustomerGrowth(year);
    }
}
