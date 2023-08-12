package com.navi.mynewsservice.Contract.response;

import lombok.Data;

import java.util.Optional;

@Data
public class ApiCallRecordDTO {
    private String request;
    private Optional callCount;
    private Optional avgTimeTaken;

    public ApiCallRecordDTO(String request, Optional callCount, Optional avgTimeTaken) {
        this.request = request;
        this.callCount = callCount;
        this.avgTimeTaken = avgTimeTaken;
    }

    // Getters and setters...
}
