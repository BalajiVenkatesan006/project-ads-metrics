package com.clarisight.task.demo.service.impl;

import com.clarisight.task.demo.model.QueryData;
import com.clarisight.task.demo.repository.QueryDataRepository;
import com.clarisight.task.demo.service.QueryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("queryDataService")
public class QueryDataServiceImpl implements QueryDataService {

    @Autowired
    QueryDataRepository queryDataRepository;

    @Override
    public QueryData save(QueryData data) {
        return queryDataRepository.save(data);
    }

    @Override
    public List<QueryData> getQueryDataInDateRange(Date from, Date to) {
        return queryDataRepository.findByDateGreaterThanEqualAndDateLessThanEqual(from,to);
    }

    @Override
    public QueryData findByhashKey(String hashKey) {
        return queryDataRepository.findByHashKey(hashKey);
    }

    @Override
    public List<QueryData> findAllQueries() {
        return queryDataRepository.findAll();
    }
}
