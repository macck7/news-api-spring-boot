package com.navi.mynewsservice.dao.repo;

import com.navi.mynewsservice.dao.RequestTypeCallCountDTO;
import com.navi.mynewsservice.dao.schema.ApiCallRecord;
import com.navi.mynewsservice.dao.schema.ApiCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ApiCallRecordRepo extends JpaRepository<ApiCallRecord, Long> {

        @Query("SELECT acr.requestType, COUNT(acr) AS callCount, AVG(acr.timeTaken) AS avgTimeTaken FROM ApiCallRecord acr GROUP BY acr.requestType")
        List<RequestTypeCallCountDTO> findDistinctRequestTypesWithCallCountAndAvgTime();

        @Query("SELECT acr.requestType, " +
                "COUNT(acr) AS callCount, " +
                "AVG(acr.timeTaken) AS avgTimeTaken, " +
                "CASE WHEN acr.requestType LIKE 'GET /source%' THEN COUNT(acr) * 10.50 " +
                "     WHEN acr.requestType LIKE 'GET /id%' THEN COUNT(acr) * 200.0 " +
                "     ELSE 0.0 " +
                "END AS cost " +
                "FROM ApiCallRecord acr " +
                "GROUP BY acr.requestType")
        List<ApiCost> findDistinctRequestTypesWithCallCountAndAvgTimeAndCost();



}



