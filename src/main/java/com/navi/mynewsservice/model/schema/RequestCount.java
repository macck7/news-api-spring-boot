package com.navi.mynewsservice.model.schema;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "requestcount")
@Data
public class RequestCount {
    @Id
    private String email;
    private int count;


    public RequestCount() {
    }

}

