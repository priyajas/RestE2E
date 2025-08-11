package base;

import io.restassured.RestAssured;
import  io.restassured.RestAssured.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {

        protected static RequestSpecification requestSpec;
        protected static Properties prop;

        @BeforeClass(alwaysRun = true)
        public void setUp() {
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


