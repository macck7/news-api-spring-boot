package com.navi.mynewsservice.Contract.response;


import lombok.Data;

@Data
public class ApiCost {
    String endPoint;
    long amount;

    public ApiCost(){

    }

    public ApiCost(String endPoint, long amount) {
        this.endPoint = endPoint;
        this.amount = amount;
    }
}