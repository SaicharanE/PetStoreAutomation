package api.test;

import api.endpoints.UserEndpoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DDTests {


    @Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
    public void testPostuser(String UserID, String UserName, String FirstName, String LastName, String Email, String Password, String Phone)
    {

        User userPayload = new User();

        userPayload.setId(Integer.parseInt(UserID));
        userPayload.setUsername(UserName);
        userPayload.setFirstName(FirstName);
        userPayload.setLastName(LastName);
        userPayload.setEmail(Email);
        userPayload.setPassword(Password);
        userPayload.setPhone(Phone);

        Response res = UserEndpoints.createUser(userPayload);
        Assert.assertEquals(res.getStatusCode(), 200);
    }

    @Test(priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
    public void testReadUserByName(String UserName) {
        Response res = UserEndpoints.readUser(UserName);
        res.then().log().all();
        Assert.assertEquals(res.getStatusCode(), 200);
    }

    @Test(priority = 3, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
    public void testDeleteUserbyName(String UserName)
    {
        Response res = UserEndpoints.deleteUser(UserName);
    }
}
