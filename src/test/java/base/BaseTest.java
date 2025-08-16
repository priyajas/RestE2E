package base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

import java.io.*;
import java.util.Properties;

public class BaseTest {
    protected static RequestSpecification requestSpec;
    protected static Properties prop;
    protected static ResponseSpecification resSpec;
    // Create file for logging

    @BeforeClass(alwaysRun = true)
    public void setUp() throws FileNotFoundException {
        PrintStream logFile = new PrintStream(new File("api_logs.txt"));
        if (requestSpec == null) {
            loadProperties();
            requestSpec = new RequestSpecBuilder()
                    .setBaseUri(prop.getProperty("baseURI"))
                    .setBasePath(prop.getProperty("basePath"))
                    .setContentType(ContentType.JSON)
                    .addHeader("Accept", "application/json")
                    .log(LogDetail.ALL) // Logs Request details
                    .build();

            RestAssured.requestSpecification = requestSpec;
            resSpec = new ResponseSpecBuilder()
                    .expectContentType("application/json; charset=utf-8")
                    .build();
            RestAssured.responseSpecification = resSpec;
        }
    }

    private void loadProperties() {
        try {
            prop = new Properties();
            FileInputStream fis = new FileInputStream("src/test/java/resources/config.properties");
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not load config.properties file");
        }
    }
}


