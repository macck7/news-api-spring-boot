package com.navi.mynewsservice.model.repo;

import com.navi.mynewsservice.model.schema.NewsData;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NewsDataRepository extends JpaRepository<NewsData, Integer> {

    NewsData findByCountryAndCategory(String country, String category);

}
