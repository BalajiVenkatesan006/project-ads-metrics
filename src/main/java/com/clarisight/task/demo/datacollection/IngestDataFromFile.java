package com.clarisight.task.demo.datacollection;


import com.clarisight.task.demo.ClariSightConstants;
import com.clarisight.task.demo.ClariSightUtils;
import com.clarisight.task.demo.model.QueryData;
import com.clarisight.task.demo.service.QueryDataService;
import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.clarisight.task.demo.ClariSightConstants.*;


@Slf4j
@Component
public class IngestDataFromFile {

    Map<Integer,String> headerCellNumberHMap;

    @Autowired
    QueryDataService queryDataService;

    public void readDataFromFileAndIngestToDataBase(InputStream inputStream){
        CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
        log.debug("readDataFromFileAndIngestToDataBase");
        headerCellNumberHMap = new HashMap<>();
        try{
            List<String[]> csvData = reader.readAll();
            for(int index =0; index<csvData.size();index++) {
                if(index == 0){
                    processHeader(csvData.get(index));
                }
                else{
                    processRows(csvData.get(index),index);
                }
            }
            log.debug("processing done");
        }
        catch (Exception e){
            log.error("Exception {} message {} stackTrace {}",e,e.getMessage(),e.getStackTrace());
        }
    }

    private int processHeader(String header[]){
        log.debug("Processing Header");
       for(int index =0 ;index <header.length;index++){
           headerCellNumberHMap.put(index,header[index]);
        }
        log.debug("The header size is "+headerCellNumberHMap.size());
        return headerCellNumberHMap.size();
    }


    private void writeInvalidRowToFile(String invalidDataColumns, List<String> dataRows,int rowNum){
        log.debug("Invalid Data is there {} & data row size {} rowNum {}",invalidDataColumns,dataRows.size(),rowNum);
        log.debug("Invalid Data in the row "+rowNum);
        for(int index = 0; index < dataRows.size(); index++){
           log.debug(dataRows.get(index)+" ");
        }
    }

    private   void processRows(String data[],int rowNum){
        ArrayList<String> rowData = new ArrayList<>();
        boolean addToTheDB = true;
        String invalidDataColumns = "Invalid";
        QueryData queryData = new QueryData();
        for(int index =0; index<data.length;index++) {
            String valueOftheCell = data[index];
            rowData.add(valueOftheCell);
            if(index >= headerCellNumberHMap.size()){
                continue;
            }
            if(valueOftheCell.length() <= 0 || valueOftheCell.trim().isEmpty()){
                continue;
            }
            switch (headerCellNumberHMap.get(index).trim().toLowerCase().replace(" ","")) {
                case COLUMN_DATE:
                    Date date = null;
                    try {
                        SimpleDateFormat formatter1 = new SimpleDateFormat(ClariSightConstants.DATE_PATTERN);
                        date = formatter1.parse(valueOftheCell);
                        if (date != null) {
                            queryData.setDate(date);
                        }
                    } catch (Exception e) {
                        addToTheDB = false;
                        invalidDataColumns += " " + headerCellNumberHMap.get(index);
                    }
                    break;
                case COLUMN_QUERY:
                    queryData.setQuery(valueOftheCell);
                    break;
                case COLUMN_COUNT:
                    BigDecimal count = null;
                    try {
                        count = new BigDecimal(valueOftheCell);
                        if (count != null) {
                            queryData.setCount(count);
                        }
                    } catch (Exception e) {
                        addToTheDB = false;
                        invalidDataColumns += " " + headerCellNumberHMap.get(index);
                    }
                    break;
            }
        }
        if(addToTheDB){
           saveQueryData(queryData);
        }
        else{
            writeInvalidRowToFile(invalidDataColumns,rowData,rowNum);
        }
    }
    private void saveQueryData(QueryData queryData){
        String hashKey = ClariSightUtils.generateHash(queryData.getQuery()+""+queryData.getDate());
        QueryData q = queryDataService.findByhashKey(hashKey);
        if(q != null){
            log.debug("Duplicate data");
        }
        else{
            queryData.setHashKey(hashKey);
            queryDataService.save(queryData);
        }
    }
}
