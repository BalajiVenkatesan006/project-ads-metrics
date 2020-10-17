package com.clarisight.task.demo.apiservice;


import com.clarisight.task.demo.enums.QuerySortBy;
import com.clarisight.task.demo.input.QueryDataInput;
import com.clarisight.task.demo.model.QueryData;
import com.clarisight.task.demo.output.Query;
import com.clarisight.task.demo.output.QueryDataOutput;
import com.clarisight.task.demo.service.QueryDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service("queryDataControllerService")
public class QueryDataControllerService {

    @Autowired
    QueryDataService queryDataService;

    boolean checkFilterQuery(List<String> inputQuery, String query){
        return inputQuery!=null && inputQuery.size() >0 ? inputQuery.contains(query):true;
    }

    public void fetchQueryData(QueryDataInput input, HttpServletResponse httpServletResponse) {
        log.debug("fetchQueryData ");
        QueryDataOutput queryDataOutput = new QueryDataOutput();
        List<Query> queries = new ArrayList<>();
        try{
            List<QueryData> queryDataList = queryDataService.getQueryDataInDateRange(input.getFromDate(),input.getToDate());
            if(queryDataList!=null && queryDataList.size() > 0){
                Map<String, BigDecimal> queryDataListParsed = queryDataList
                        .stream()
                        .filter(x -> checkFilterQuery(input.getFilterByQuery(),x.getQuery()))
                        .collect(Collectors.groupingBy(QueryData::getQuery,Collectors.mapping(QueryData::getCount,Collectors.reducing(BigDecimal.ZERO,BigDecimal::add))));
                BigDecimal lesserCount = BigDecimal.ZERO;
                for(Map.Entry<String,BigDecimal> entry: queryDataListParsed.entrySet()){
                    Query query = null;
                    if(entry.getValue().compareTo(input.getCount()) > 0){
                        query = new Query();
                        query.setCount(entry.getValue());
                        query.setQueryName(entry.getKey());
                    }
                    else{
                        lesserCount = lesserCount.add(entry.getValue());
                    }
                    if(query != null){
                        queries.add(query);
                    }
                }
                if(!lesserCount.equals(BigDecimal.ZERO)){
                    Query query = new Query();
                    query.setQueryName("Query count  <"+input.getCount());
                    query.setCount(lesserCount);
                    queries.add(query);
                }
                if(input.getSortBy()!= null){
                    if(input.getSortBy() == QuerySortBy.COUNT)
                        queries.sort(Comparator.comparing(Query::getCount).reversed());
                    else
                        queries.sort(Comparator.comparing(Query::getQueryName));
                }
                queryDataOutput.setQueries(queries);
            }
            httpServletResponse.setContentType("text/csv");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String currentDateTime = dateFormatter.format(new Date());
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
            httpServletResponse.setHeader(headerKey, headerValue);
            ICsvBeanWriter csvWriter = new CsvBeanWriter(httpServletResponse.getWriter(), CsvPreference.STANDARD_PREFERENCE);
            String[] csvHeader = {"Query Name", "count"};
            String[] nameMapping = {"queryName", "count"};
            csvWriter.writeHeader(csvHeader);
            for (Query q : queries) {
                csvWriter.write(q, nameMapping);
            }
            csvWriter.close();
        }
        catch (Exception e){
            log.error("Exception, "+e.getMessage());
        }
    }
}
