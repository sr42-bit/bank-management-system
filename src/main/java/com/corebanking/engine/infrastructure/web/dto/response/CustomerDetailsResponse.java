package com.corebanking.engine.infrastructure.web.dto.response;

import java.util.List;

public record CustomerDetailsResponse(

        String customerId,
        String name,
        String email,
        String phone,
        String gender,
        String dob,
        String status,
        String kycStatus,

        List<AccountSummaryResponse> accounts

) {}

