package api.controller.idam;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.framework.base.GenericActions;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ResetPassword extends GenericActions {
	
public static Response postResetPassword(String access_Token,String username,String password) throws IOException{
		Map<String,String> json = new HashMap<>();
		json.put("UserName", username);
		json.put("NewPassword", password);
		RestAssured.baseURI = LoadEnv.getProperty("IDAM");
		response = given().header("authorization", "Bearer " + access_Token)
				.contentType("application/json")
				.when().body(json)
				.post("user/resetpassword");
		
		return response;
		
	}

}
