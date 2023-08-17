package com.navi.mynewsservice.model.repo;

import com.navi.mynewsservice.model.schema.RequestCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestCountRepository extends JpaRepository<RequestCount,String> {
    RequestCount findByEmail(String email);

    @Query(value = "SELECT email, count FROM requestcount WHERE count = (SELECT MAX(count) FROM requestcount)", nativeQuery = true)
    List<Object[]> findTopUsersWithCounts();
}
