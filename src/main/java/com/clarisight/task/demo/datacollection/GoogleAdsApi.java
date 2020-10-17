package com.clarisight.task.demo.datacollection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URL;

@Component
public class GoogleAdsApi implements ThirdPartyDataProvider {

    @Value("${google.data.url}")
    private static String url = "https://clarisights-users.s3.eu-central-1.amazonaws.com/assignment/sample+data.csv";

    @Autowired
    IngestDataFromFile ingestDataFromFile;

    /*
    Instead of hitting a third party API to get data file, we mock it with a static URL.
     */
    @Override
    public void getDataFile() {
        try {
            InputStream input = new URL(url).openStream();
            ingestDataFromFile.readDataFromFileAndIngestToDataBase(input);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
