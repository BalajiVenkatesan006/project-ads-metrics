package com.clarisight.task.demo.repository;

import com.clarisight.task.demo.model.QueryData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface QueryDataRepository extends JpaRepository<QueryData,Long> {

    List<QueryData> findByDateGreaterThanEqualAndDateLessThanEqual(Date from, Date to);

    QueryData findByHashKey(String hashKey);
}
