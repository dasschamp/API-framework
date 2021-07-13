package wse.idam.testcases;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.testng.annotations.Test;

import com.framework.base.GenericActions;

import api.controller.idam.Roles;
import api.controller.idam.TokenCreation;
import api.controller.idam.User;
import api.controller.idam.UserInfo;
import io.restassured.response.Response;


public class TC07_StudentDataCard extends GenericActions{
		
		
		@Test(description="Verify student data card details")
		public void verifyStudentDataCard() throws IOException  {	
			String centerRefId = UserInfo.getUserInfo(token).get("CenterRefID");
			Map<String,String> queryParam = new HashMap<>();
			queryParam.put("roles", "student");
			queryParam.put("centerreferenceid",centerRefId);
			queryParam.put("limit", "1");
			queryParam.put("offset", "0");
			Response fullResponse = User.getUserList(token,queryParam);
			verifyStatusCode(fullResponse,200);
			List<Map<String,String>> list = fullResponse.jsonPath().get("users");
			String userId = list.get(0).get("userId");
			Response studentResponse = User.getStudentDataCard(token,userId);
			verifyStatusCode(studentResponse,200);
			Set<String> validation = new HashSet<>();
			validation.add("userId");
			validation.add("prospectRefId");
			validation.add("firstName");
			validation.add("lastName");
			validation.add("email");
			validation.add("userName");
			validation.add("password");
			validation.add("isSponsorContactInfo");
			validation.add("sponsorContactInfo");
			validation.add("ssdsId");
			validation.add("userType");
			validation.add("roleId");
			validation.add("centerId");
			validation.add("territoryId");
			validation.add("homeTelephone");
			validation.add("mobileTelephone");
			validation.add("workTelephone");
			validation.add("centerReferenceId");
			validation.add("gender");
			validation.add("fax");
			validation.add("userId");
			validation.add("birthDate");
			validation.add("sendMailPreference");
			validation.add("address");
			validation.add("callBetween");
			validation.add("source");
			validation.add("isEmailVerified");
			validation.add("isActive");
			validation.add("userRole");
			validation.add("userTypeName");
			validation.add("userExternalId");
			validation.add("isTerritoryToken");
			validation.add("isDigitalValidationEnabledCenter");
			verifyResponseAllFields(convertResponseToMap(studentResponse), validation);
		}
	
	@Test(description="Verify student data card details with invalid user")
	  public void verifyStudentDataCardInvalid() throws IOException { 
			Response studentResponse = User.getStudentDataCard(token,"186cb4df-8b2c-4949-9bdc-ccc7af8610bz");
			verifyStatusCode(studentResponse,400);
	  }

}
