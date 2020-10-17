package com.clarisight.task.demo;

import com.clarisight.task.demo.datacollection.IngestDataFromFile;
import com.clarisight.task.demo.model.QueryData;
import com.clarisight.task.demo.service.QueryDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.transaction.Transactional;
import java.io.*;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class QueryDataTest {

    @Autowired
    IngestDataFromFile ingestDataFromFile;

    @Autowired
    QueryDataService queryDataService;

    @Test
    public void testDataProcessing() throws IOException {
        Resource resource = new ClassPathResource("sample.csv");
        InputStream inputStream = new FileInputStream(resource.getFile());
        ingestDataFromFile.readDataFromFileAndIngestToDataBase(inputStream);
        List<QueryData> queryData = queryDataService.findAllQueries();
        assertEquals(queryData.size(),116);
    }
}
