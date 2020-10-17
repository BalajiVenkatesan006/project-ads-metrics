package com.clarisight.task.demo.controller;

import com.clarisight.task.demo.apiservice.QueryDataControllerService;
import com.clarisight.task.demo.input.QueryDataInput;
import com.clarisight.task.demo.output.QueryDataOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping(value = "/query")
public class QueryDataController {

    @Autowired
    QueryDataControllerService queryDataControllerService;

    @RequestMapping(value = "/data",method = RequestMethod.POST)
    public void fetchData(@RequestBody QueryDataInput input, HttpServletResponse httpServletResponse){
         queryDataControllerService.fetchQueryData(input,httpServletResponse);
    }

}
