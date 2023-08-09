package com.navi.mynewsservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsResponse {

    private List<Article> articles;
    public NewsResponse() {
        articles = new ArrayList<>();
    }


}
