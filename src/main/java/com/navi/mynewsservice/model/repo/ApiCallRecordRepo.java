package com.navi.mynewsservice.model.repo;

import com.navi.mynewsservice.model.schema.ApiCallRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ApiCallRecordRepo extends JpaRepository<ApiCallRecord, Long> {

        @Query("SELECT a.endpoint, COUNT(a), AVG(a.timeTaken) FROM ApiCallRecord a GROUP BY a.endpoint")
        List<Object[]> findDistinctRequestsCountAndAvgTime();


        @Query(value = "SELECT endpoint, COUNT(*) AS number_calls FROM ApiCallRecord GROUP BY endpoint")
       List<Object[]> findDistinctRequestTypesWithCallCountAndCost();

}



