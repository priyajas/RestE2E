package testscripts;

import base.BaseTest;
import commonMethods.CommonUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import resources.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class GetUsersTest extends BaseTest {
    @Test
    public void getUsers_PositiveTest() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", 2);
        Response res = CommonUtils.doGet(Constants.get_Endpoint, params);
        SoftAssert soft = new SoftAssert();
        JsonPath json = res.jsonPath();
        // Validate multiple headers
        Map<String, String> headers = res.getHeaders().asList().stream()
                .collect(Collectors.toMap(h -> h.getName(), h -> h.getValue()));
        Assert.assertTrue(headers.containsKey("Connection"));
        Assert.assertEquals(headers.get("Connection"), "keep-alive");
        soft.assertEquals(res.getStatusCode(), 200, "Status code mismatch");
        soft.assertEquals(res.getContentType(), "application/json; charset=utf-8", "Content type mismatch");
        soft.assertEquals(json.getInt("page"), 2, "Page number mismatch");
        soft.assertEquals(json.getInt("per_page"), 6, "Per page mismatch");
        soft.assertEquals(json.getInt("total"), 12, "Total mismatch");
        soft.assertEquals(json.getInt("total_pages"), 2, "Total pages mismatch");
        soft.assertAll();  // triggers all assertions at once
        Assert.assertEquals(res.statusCode(), 200, "Status code mismatch");
        Assert.assertTrue(res.jsonPath().getList("data").size() > 0, "Users list is empty");
    }


    @Test
    public void getUsers_MissingParam_NegativeTest() {
        Response res = CommonUtils.doGet(Constants.get_Endpoint, null);

        Assert.assertEquals(res.statusCode(), 401, "Expected 401 or error based on API spec");
        // Some APIs may return default page=1, others may error. Assert accordingly.
    }

    @Test
    public void getUsers_WrongParam_NegativeTest() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", -2); // invalid param

        Response res = CommonUtils.doGet(Constants.get_Endpoint, params);

        Assert.assertEquals(res.statusCode(), 401, "Expected 401 ");
        Assert.assertNull(res.jsonPath().getList("data"), "'data' should not exist for unauthorized call");
    }
}
