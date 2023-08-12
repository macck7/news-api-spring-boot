package com.navi.mynewsservice.model.repo;

import com.navi.mynewsservice.model.schema.SourceDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SourceRepo extends JpaRepository<SourceDetails,Integer> {
    SourceDetails findByName(String name);
}
