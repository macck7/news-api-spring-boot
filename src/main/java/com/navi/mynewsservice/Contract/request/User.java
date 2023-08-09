package com.navi.mynewsservice.Contract.request;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private String email;
    private String country;
    private String category;
    private List<String> sources;

    public User(String email, String country, String category, List<String> sources) {
        this.email = email;
        this.country = country;
        this.category = category;
        this.sources = sources;
    }
}
