package com.navi.mynewsservice.entity;

import lombok.Data;

import java.util.List;

@Data
public class EmailRequest {
    private String to;
    private String subject;
    private String body;
    private List<String> newsList;

    public EmailRequest() {
    }
}


