package testscripts;

import base.BaseTest;
import commonMethods.CommonUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.UserRequest;
import resources.Constants;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class PostUsersTest extends BaseTest {
    @Test
    public void postUsers_PositiveTestWithStringPayload() {
        String payload = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";
        Response res = CommonUtils.doPost(Constants.post_Endpoint, payload);
        // Extract response fields
        String id = res.jsonPath().getString("id");
        String token = res.jsonPath().getString("token");

        // Validate response fields are not null
        Assert.assertNotNull(id, "ID should not be null");
        Assert.assertNotNull(token, "token should not be null");


    }
    @Test
    public void postUsers_PositiveTestWithPOJO() {
        // Create POJO object
        UserRequest user1 = new UserRequest("eve.holt@reqres.in", "pistol");
        Response res = CommonUtils.doPost(Constants.post_Endpoint, user1);
        // Extract response fields
        String id = res.jsonPath().getString("id");
        String token = res.jsonPath().getString("token");

        // Validate response fields are not null
        Assert.assertNotNull(id, "ID should not be null");
        Assert.assertNotNull(token, "token should not be null");
       //Validate schema
        res.then()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("register_schema.json"));

    }
}
