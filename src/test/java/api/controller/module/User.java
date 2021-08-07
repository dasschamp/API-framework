package api.controller.module;

import java.io.IOException;
import java.util.Map;

import org.json.JSONObject;
import org.testng.annotations.Test;

import com.framework.base.GenericActions;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class User extends GenericActions {


	public static Response createRole(String accessToken,
    	    String userType,
		    String centerReferenceId,
		    String roleId,
		    String territoryId,
		    String rolename) throws IOException {

	
		RestAssured.baseURI = LoadEnv.getProperty("IDAM");
		RequestSpecification request = RestAssured.given()
				.contentType("application/json; charset=utf-8")
				.header("authorization", "Bearer " + accessToken);
		
		int randomNum=com.framework.base.GenericActions.getRandomNum(10000);
		
		JSONObject requestBody = new JSONObject();
		requestBody.put("UserType", userType);
		requestBody.put("centerReferenceId", centerReferenceId);
		requestBody.put("email", "RestAssure"+rolename+randomNum+"@test.com");
		requestBody.put("firstName",rolename+"Test");
		requestBody.put("homeTelephone", "77429"+randomNum);
		requestBody.put("lastName",rolename+"Test");
		requestBody.put("mobileTelephone","84245"+randomNum);
		requestBody.put("roleId", roleId);
		requestBody.put("territoryId", territoryId);
		requestBody.put("workTelephone", "67554"+randomNum);

		request.body(requestBody.toString());
		response = request.post("user");

		return response;
	}

	public static Response getUserList(String accessToken,Map<String,String> queryParam) throws IOException {

		RestAssured.baseURI = LoadEnv.getProperty("IDAM");
		response = RestAssured.given()
				.contentType("application/json; charset=utf-8")
				.header("authorization", "Bearer " + accessToken)
				.queryParams(queryParam).get("user");

		return response;
	}
	
	public static Response postCreateUser(String accessToken,Map<String,String> json) throws IOException {

		RestAssured.baseURI = LoadEnv.getProperty("IDAM");
		response = RestAssured.given()
				.contentType("application/json; charset=utf-8")
				.header("authorization", "Bearer " + accessToken)
				.body(json).post("user");

		return response;
	}
	
	public static Response putEditUser(String accessToken,Map<String,String> json) throws IOException {

		RestAssured.baseURI = LoadEnv.getProperty("IDAM");
		response = RestAssured.given()
				.contentType("application/json; charset=utf-8")
				.header("authorization", "Bearer " + accessToken)
				.body(json).put("user");

		return response;
	}
	
	public static Response getStudentDataCard(String accessToken,String userId) throws IOException {

		RestAssured.baseURI = LoadEnv.getProperty("IDAM");
		response = RestAssured.given()
				.contentType("application/json")
				.header("authorization", "Bearer " + accessToken)
				.get("user/"+userId);

		return response;
	}
}
