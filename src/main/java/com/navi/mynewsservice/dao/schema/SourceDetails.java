package com.navi.mynewsservice.dao.schema;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name ="newssource")
public class SourceDetails {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Integer id;
    private String name;
    public SourceDetails() {
    }

    public SourceDetails(String name) {
        this.name = name;
    }
}
