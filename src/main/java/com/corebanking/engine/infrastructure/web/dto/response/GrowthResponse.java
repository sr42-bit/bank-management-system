package com.corebanking.engine.infrastructure.web.dto.response;

public class GrowthResponse {

    private String month;
    private long count;

    public GrowthResponse(String month, long count) {
        this.month = month;
        this.count = count;
    }

    public String getMonth() {
        return month;
    }

    public long getCount() {
        return count;
    }
}