package com.navi.mynewsservice.dao.schema;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "api_call_records")
@Data
public class ApiCallRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String endpoint;
    private String request;
    private String response;
    private long timeTaken; // Time taken in milliseconds

    // Constructors, getters, setters, etc.

    public ApiCallRecord(String endpoint, String request, String response, long timeTaken) {
        this.endpoint = endpoint;
        this.request = request;
        this.response = response;
        this.timeTaken = timeTaken;
    }
}

