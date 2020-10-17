package com.clarisight.task.demo.input;

import com.clarisight.task.demo.enums.QuerySortBy;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class QueryDataInput {

    private QuerySortBy sortBy = QuerySortBy.COUNT;

    private List<String> filterByQuery;

    private BigDecimal count = new BigDecimal("50");

    @NonNull
    private Date fromDate;

    @NonNull
    private Date toDate;
}
