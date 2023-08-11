package com.navi.mynewsservice.dao;

import lombok.Data;

public class RequestTypeCallCountDTO {
    private String requestType;
    private Long callCount;

    public RequestTypeCallCountDTO(String requestType, Long callCount) {
        this.requestType = requestType;
        this.callCount = callCount;
    }

    // Getters and setters...
}
