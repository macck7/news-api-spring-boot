package com.navi.mynewsservice.model.repo;

import com.navi.mynewsservice.model.schema.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Integer> {
}
