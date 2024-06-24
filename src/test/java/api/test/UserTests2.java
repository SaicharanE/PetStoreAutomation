package api.test;

import api.endpoints.UserEndpoints;
import api.endpoints.UserEndpoints2;
import api.payload.User;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserTests2 {

	Faker faker;
	User userPayload;
	public Logger logger; // for logs

	@BeforeClass
	public void setupData()
	{
		faker=new Faker();
		userPayload=new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());

		logger= LogManager.getLogger(UserTests2.class);

		logger.debug("debugging.....");
	}
	
	@Test(priority=1)
	public void testPostUser()
	{
		logger.info("********** Creating user  ***************");
		Response response= UserEndpoints2.createUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(),200);
		logger.info("**********User is creatged  ***************");
	}
	@Test(priority=2)
	public void readUser()
	{
		logger.info("********** Reading User Info ***************");
		Response response= UserEndpoints2.readUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
		logger.info("**********User info  is displayed ***************");
	}

	@Test(priority=3)
	public void updateUser()
	{
		logger.info("********** Updating User ***************");
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());

		Response response = UserEndpoints2.updateUser(this.userPayload.getUsername(), userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);

		Response Afterresponse= UserEndpoints.readUser(this.userPayload.getUsername());
		Assert.assertEquals(Afterresponse.getStatusCode(),200);

		logger.info("********** User updated ***************");
	}

	@Test(priority=4)
	public void deleteUser()
	{

		logger.info("**********   Deleting User  ***************");
		Response response = UserEndpoints2.deleteUser(this.userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(),200);

		logger.info("********** User deleted ***************");
	}
}
