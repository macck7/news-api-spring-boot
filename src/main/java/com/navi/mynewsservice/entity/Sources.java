package com.navi.mynewsservice.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class Sources {
    private List<Source> sources;
    Sources() {
        sources = new ArrayList<>();
    }
}
