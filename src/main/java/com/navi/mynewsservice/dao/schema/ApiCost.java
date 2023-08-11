package com.navi.mynewsservice.dao.schema;

import lombok.Data;

@Data
public class ApiCost {
        private String requestType;
        private Long callCount;
        private Double cost;

        public ApiCost(String requestType, Long callCount, Double avgTimeTaken, Double cost) {
            this.requestType = requestType;
            this.callCount = callCount;
            this.cost = cost;
        }

        // Getters and setters...


}
