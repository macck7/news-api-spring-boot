package com.navi.mynewsservice.dao.repo;

import com.navi.mynewsservice.dao.schema.ApiCallRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiCallRecordRepo extends JpaRepository<ApiCallRecord, Long> {

}

