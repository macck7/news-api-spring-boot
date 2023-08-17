package com.navi.mynewsservice.model.schema;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String title;

    @ManyToOne
    @JoinColumn(name = "newsdata_id")
    private NewsData newsData;

    public News() {
    }

    public News(String title) {
        this.title = title;
    }
}
