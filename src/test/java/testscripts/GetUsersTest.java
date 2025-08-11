package testscripts;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetUsersTest extends BaseTest {

    @Test
    public void getUsersTest() {
        Response res =
                given()
                        .queryParam("page", 2)
                        .when()
                        .get("/users")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        Assert.assertTrue(res.jsonPath().getList("data").size() > 0, "Users list is empty");
    }
}
