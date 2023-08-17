package com.navi.mynewsservice.model.schema;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "newsdata")
public class NewsData {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    Integer id;

    String category;
    String country;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "newsData")
    private List<News> newsList;

    public NewsData() {
    }

    public NewsData(String category, String country) {
        this.category = category;
        this.country = country;
    }

}
