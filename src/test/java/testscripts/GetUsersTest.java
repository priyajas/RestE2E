package testscripts;

import base.BaseTest;
import commonMethods.CommonUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import resources.Constants;

import java.util.HashMap;
import java.util.Map;

public class GetUsersTest extends BaseTest {
    @Test
    public void getUsers_PositiveTest() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", 2);
        Response res = CommonUtils.doGet(Constants.get_Endpoint, params);
        Assert.assertEquals(res.statusCode(), 200, "Status code mismatch");
        Assert.assertTrue(res.jsonPath().getList("data").size() > 0, "Users list is empty");
    }


    @Test
    public void getUsers_MissingParam_NegativeTest() {
        Response res = CommonUtils.doGet(Constants.get_Endpoint, null);

        Assert.assertEquals(res.statusCode(), 200, "Expected 200 or error based on API spec");
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
