package com.navi.mynewsservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Article {
        private String title;
}
