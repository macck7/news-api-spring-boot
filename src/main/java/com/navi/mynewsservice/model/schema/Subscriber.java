package com.navi.mynewsservice.model.schema;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "subscriber")
@Entity
public class Subscriber {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    int id;
    String email;

    public Subscriber() {
    }
}
