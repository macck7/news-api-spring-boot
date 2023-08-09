package com.navi.mynewsservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsResponse {

    private List<Article> articles;

    public NewsResponse() {
        articles = new ArrayList<>();
    }

    public List<Article> getArticles() {
        return articles;
    }
    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public NewsResponse(List<Article> articles) {
        this.articles = articles;
    }

}
