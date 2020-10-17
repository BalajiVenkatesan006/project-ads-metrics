package com.clarisight.task.demo.output;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Query {
    String queryName;
    BigDecimal count;
}
