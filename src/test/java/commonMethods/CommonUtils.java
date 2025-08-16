package commonMethods;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import resources.Constants;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class CommonUtils {

    // Generic GET request with optional query params
    public static Response doGet(String endpoint, Map<String, Object> queryParams) {
        if (queryParams != null) {
            return given()
                    .queryParams(queryParams)
                    .when()
                    .get(endpoint)
                    .then()
                    .extract()
                    .response();
        } else {
            return given()
                    .when()
                    .get(endpoint)
                    .then()
                    .extract()
                    .response();
        }
    }



    public static Response doPost(String endpoint,Object payload) {
        return given()
                .header("x-api-key", " reqres-free-v1")
                .body(payload)                   // Attach payload
                .when()
                .post(endpoint)                  // POST endpoint
                .then()
                .extract()
                .response();


    }
}
