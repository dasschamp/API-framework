package api.controller.module;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import com.framework.base.GenericActions;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class RolesList extends GenericActions {


	public static Map<String, String> getStaffRoleID(String accessToken)
			throws IOException {

	
		RestAssured.baseURI = LoadEnv.getProperty("IDAM");
		response = given().header("authorization", "Bearer " + accessToken).when()
				.param("isActive", "true").get("roles/");

		JsonPath apiResponse = response.jsonPath();

		Map<String, String> outputMap = new LinkedHashMap<String, String>();

		List<String> userTypeId = apiResponse.get("userTypeId");

		for (int i = 0; i < userTypeId.size(); i++) {
			
			List<Map<String, String>> rolesObject = apiResponse.get("roles[" + i + "]");

			for (int j = 0; j < rolesObject.size(); j++) {

				Map<String, String> temp = rolesObject.get(j);

				outputMap.put(temp.get("description"),i+1+" "+temp.get("id")); //Role,usertype,id
			}

      }
	
		return outputMap;
	}
}
