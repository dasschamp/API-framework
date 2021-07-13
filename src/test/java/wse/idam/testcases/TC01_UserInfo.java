package wse.idam.testcases;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.framework.base.GenericActions;

import api.controller.idam.TokenCreation;
import api.controller.idam.UserInfo;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class TC01_UserInfo extends GenericActions{
				
		@Test(description="Verify if user is able to get the details")
		public void verifyUserInfoView() throws IOException  {	
			Response fullResponse = UserInfo.getUserInfoDetails(token);
			Map<String, String> response = fullResponse.jsonPath().getMap("");
			verifyStatusCode(fullResponse,200);
			Set<String> validation = new HashSet<>();
			validation.add("userId");
			validation.add("ssdsRefId");
			validation.add("login");
			validation.add("userTypes");
			validation.add("firstName");
			validation.add("lastName");
			validation.add("fullName");
			validation.add("birthDate");
			validation.add("gender");
			validation.add("email");
			validation.add("centerId");
			validation.add("territoryId");
			validation.add("centerRefID");
			validation.add("centerName");
			validation.add("countryRefID");
			validation.add("catalog");
			verifyResponseAllFields(response, validation);

		}
		@Test(description="Verify if user is able to get the details with invalid values")
			public void verifyUserInfoViewNegative() throws IOException  {	
				Response fullResponse = UserInfo.getUserInfoDetails(token+"12");
				verifyStatusCode(fullResponse,401);
				
		}

}
