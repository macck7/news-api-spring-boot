package com.navi.mynewsservice.dao.repo;

import com.navi.mynewsservice.dao.schema.SourceDetails;
import com.navi.mynewsservice.entity.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SourceRepo extends JpaRepository<SourceDetails,Integer> {
    SourceDetails findByName(String name);
}
