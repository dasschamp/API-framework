package api.controller.module;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.framework.base.GenericActions;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TokenCreation extends GenericActions {
	


	public static String createToken(String username, String password) throws IOException {
		RestAssured.baseURI = LoadEnv.getProperty("IDAM");
		response = given().contentType("multipart/form-data")
				.multiPart("username", username)
				.multiPart("password", password)
				.multiPart("clientId", "WSE-FrontEnd")
				.multiPart("clientSecret", "bnNlLXBhc3N3b3Jk")
				.when().post("connect/token");

		//String ResponseJson=(response.asString());

		String access_Token = response.jsonPath().getString("access_token");
		
		return access_Token;
	}

}
