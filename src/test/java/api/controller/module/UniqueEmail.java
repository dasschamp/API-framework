package api.controller.module;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.framework.base.GenericActions;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UniqueEmail extends GenericActions {
	
public static Response getIsUniqueEmail(String access_Token,String email) throws IOException{
		Map<String,String> json = new HashMap<>();
		json.put("email", email);
		RestAssured.baseURI = LoadEnv.getProperty("IDAM");
		response = given().header("authorization", "Bearer " + access_Token)
				.contentType("application/json")
				.when().body(json)
				.post("user/IsUniqueEmail");
		
		return response;
		
	}

}
