#Project ads-metrics


#Code structure

Spring boot jpa starter project
- uses in-memory h2 database
- opencsv for csv file processing

#Build

mvn clean install

#Run

./mvnw spring-boot:run


Sample request:
EndPoint
http://localhost:8083/query/data

Request Body: (JSON)
{
    "sortBy":"COUNT",
    "filterByQuery":["best web hosting","cheap web hosting"],
    "count" : 1,
    "fromDate": "2020-06-01",
    "toDate" : "2020-06-30"
}

fromDate and toDate parameters are mandatory rest are all optional fields.
sortBy can have values such as ("QUERY", "COUNT") Default value is COUNT.
count Default value is 50.


How to build and run the project?

 1)  After downloading the project navigate to the folder
 2)  Execute mvn clean install in terminal
 3)  A target folder will be generated where you can find a .jar file
 4)  Run the jar file using java -jar fileName.jar
 5)  The service will be up in port 8083.
