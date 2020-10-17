package com.clarisight.task.demo.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QueryDataOutput {
    List<Query> queries;
    boolean success = true;
    String errorMessage;
}
