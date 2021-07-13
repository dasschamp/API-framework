package wse.idam.testcases;

import java.io.IOException;
import java.util.ArrayList;
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


public class TC08_CreateUserInCenter extends GenericActions{
		
		@Test(description="Verify user creation in provided center")
		public void verifyUserCreationInCenter() throws IOException  {
			Map<String, String> jsonRequest = new HashMap<>();
			Map<String, String> userinfo = UserInfo.getUserInfo(token);
			String centerRefID = userinfo.get("CenterRefID");
			jsonRequest.put("centerReferenceId", centerRefID);
			jsonRequest.put("territoryId", userinfo.get("TerritoryId"));
			jsonRequest.put("UserType","3");;
			String random = String.valueOf(getRandomNum(10000));
			jsonRequest.put("firstName","APIautomation"+random);
			jsonRequest.put("lastName","test");
			jsonRequest.put("email", "Restassure"+random+"@test.com");
			jsonRequest.put("homeTelephone","1234567");
			jsonRequest.put("mobileTelephone","1234567");
			jsonRequest.put("workTelephone","1234567");
			
			Map<String,String> queryParam = new HashMap<>();
			queryParam.put("isActive", "true");
			Response userRole = Roles.getUserRole(token,centerRefID,queryParam);		
			List<List<Map<String,String>>> list = userRole.getBody().jsonPath().get("roles");
			String roleId = list.get(2).get(10).get("id");
			jsonRequest.put("roleId",roleId);
		
			Response userResponse = User.postCreateUser(token,jsonRequest);
			verifyStatusCode(userResponse,201);
			verifyResponseField(convertResponseToMap(userResponse),"userId");
			verifyResponseField(convertResponseToMap(userResponse),"username");
		
		}
	
		@Test(description="Verify user creation in provided center with invalid field values")
	  public void verifyUserCreationInCenterInvalid() throws IOException { 
		  	Map<String, String> jsonRequest = new HashMap<>();	
			Map<String, String> userinfo = UserInfo.getUserInfo(token);
			String centerRefID = userinfo.get("CenterRefID");
			jsonRequest.put("centerReferenceId", centerRefID);
			jsonRequest.put("territoryId", "");
			jsonRequest.put("UserType","3");
			jsonRequest.put("firstName","");
			jsonRequest.put("lastName","");
			jsonRequest.put("email", "");
			
			Map<String,String> queryParam = new HashMap<>();
			queryParam.put("isActive", "true");
			Response userRole = Roles.getUserRole(token,centerRefID,queryParam);		
			List<List<Map<String,String>>> list = userRole.getBody().jsonPath().get("roles");
			String roleId = list.get(2).get(10).get("id");
			jsonRequest.put("roleId",roleId);
			Response userResponse = User.postCreateUser(token,jsonRequest);
			verifyStatusCode(userResponse,400);
			Set<String> validation = new HashSet<>();
			validation.add("TerritoryId");
			validation.add("firstName");
			validation.add("lastName");
			validation.add("email");
			verifyResponseAllFieldsUsingContainsText(userResponse,validation);
			
	  }

}
