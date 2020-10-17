package com.clarisight.task.demo.datacollection;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DailyJob {

    @Autowired
    GoogleAdsApi googleAdsApi;

    //This Job runs at 12:00 am every day to fetch the google Ad API data
 //   @Scheduled(cron = "0 0 0 * * *")
    @Scheduled(fixedDelay = 86400000)
    public void getDataFromGoogleAdsApi(){
        log.debug("running a daily Job to fetch google Ad's API");
        googleAdsApi.getDataFile();
    }
}
