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


public class TC09_EditUserInCenter extends GenericActions{
				
		@Test(description="Verify if user is able to edit the details")
		public void verifyUserEdit() throws IOException  {
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
			String userId = convertResponseToMap(userResponse).get("userId");
			verifyResponseField(convertResponseToMap(userResponse),"userId");
			verifyResponseField(convertResponseToMap(userResponse),"username");
			
			jsonRequest.put("email", "AutomationAPI"+random+"@test.com");
			jsonRequest.put("userId", userId);
			Response editResponse = User.putEditUser(token,jsonRequest);
			verifyStatusCode(editResponse,200);
		
		}
	
		@Test(description="Verify if user is able to edit the details with invalid values")
	  public void verifyUserEditInvalid() throws IOException { 
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
			
			jsonRequest.put("email", "");
			jsonRequest.put("firstName"," ");
			jsonRequest.put("lastName"," ");
			Response editResponse = User.putEditUser(token,jsonRequest);
			verifyStatusCode(editResponse,400);
			
	  }

}
