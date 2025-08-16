package commonMethods;

import io.restassured.response.Response;

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
}
