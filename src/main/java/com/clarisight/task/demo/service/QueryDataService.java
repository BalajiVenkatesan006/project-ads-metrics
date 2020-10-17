package com.clarisight.task.demo.service;


import com.clarisight.task.demo.model.QueryData;

import java.util.Date;
import java.util.List;

public interface QueryDataService {
    QueryData save(QueryData data);
    List<QueryData> getQueryDataInDateRange(Date from, Date to);
    QueryData findByhashKey(String hashKey);
    List<QueryData> findAllQueries();
}
