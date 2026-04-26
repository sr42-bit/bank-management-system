package com.corebanking.engine.application.port.in.usecase.dashboard;

import java.util.Map;

public interface DashboardUseCase {
    Map<String, Object> getDashboardStats();
}