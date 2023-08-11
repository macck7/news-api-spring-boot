package com.navi.mynewsservice.dao.schema;

import lombok.Data;

@Data
public class ApiCallRecordDTO {
    private String requestType;
    private Long callCount;
    private Double avgTimeTaken;

    public ApiCallRecordDTO(String requestType, Long callCount, Double avgTimeTaken) {
        this.requestType = requestType;
        this.callCount = callCount;
        this.avgTimeTaken = avgTimeTaken;
    }

    // Getters and setters...
}
