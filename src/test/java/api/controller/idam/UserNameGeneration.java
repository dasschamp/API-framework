package api.controller.idam;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.testng.annotations.Test;

import com.framework.base.GenericActions;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UserNameGeneration extends GenericActions {

//	static String Access_Token = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhbXIiOlsicGFzc3dvcmQiXSwiYXVkIjpbImh0dHBzOi8vaWFtYXBpLXFhLndhbGxzdHJlZXRlbmdsaXNoLmNvbS9yZXNvdXJjZXMiXSwiYXV0aF90aW1lIjoxNjA5MjkzNzQwLCJjZW50ZXJfaWQiOiJJNDI4IiwiY2xpZW50X2lkIjoiV1NFLUZyb250RW5kIiwiZXhwIjoxNjA5MzI5NzQwLCJlbmFibGVfcGFzc3dvcmRfcmVzZXQiOnRydWUsImlzcyI6Imh0dHBzOi8vaWFtYXBpLXFhLndhbGxzdHJlZXRlbmdsaXNoLmNvbSIsIm5iZiI6MTYwOTI5Mzc0MCwicm9sZXMiOlsic2VydmljZW1hbmFnZXIiXSwic3ViIjoiZTQ1MzZlMGItYzBlMi00OTFlLWIzMDMtZDA2ZDc0YTQ5YjY1Iiwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsImJsaW5rIiwibHNhcGkiLCJyZXBvcnRhcGkiLCJzYWlsYXBpIiwic25iYXBpIiwib2ZmbGluZV9hY2Nlc3MiXX0.Cuh2NgdWMj0q4fb6BKCdcH4lIPcf13V87xCK09ppTBlUzxmpPN7WNZ1uKxPbNgsPysYwv8bZITmvHxfSU2tgP_j3bey0yZ6eZstQxoGlZ0B8Vk_ZwyMLzvdggibysisZfLlT3IVLBOUXyW9P8CT0tQc6qVpRV034-XzWEgxCQhtVlzjKFgRd9aZwqNT-2iT4f_e1JtH8xKzu63-2XZZjdTsxZkUXPsflL2oyTr2gQhBF4aBcss69mXAVADUb83MPlQJH414drpuVWx_3pOYIF0wQbky8LOzCwpDE4YIm4yaK_atj6cg0AKcWkCZpkehC1XdELhzH-DctUOQNLbEmXQ";
//	static String CenterId = "463dc8a7-95bd-4de4-9201-054b7a79c40d";

//	@Test
//	public static void runcall() throws IOException {
//		GetUsername(Access_Token, CenterId);
//	}

	public static String getUsername(String access_Token, String centerId) throws IOException {

	
		RestAssured.baseURI = LoadEnv.getProperty("IDAM");
		RequestSpecification request = RestAssured.given()
				.contentType("application/json; charset=utf-8")
				.header("authorization", "Bearer " + access_Token);

		JSONObject requestParams = new JSONObject();
		requestParams.put("centerid", centerId);

		request.body(requestParams.toString());
		response = request.post("user/userName");

		String outputString = response.asString();

		return outputString;
	}
	
	public static Response postGenerateUsername(String access_Token, String centerId) throws IOException {
		
		Map<String,String> json = new HashMap<>();
		json.put("centerid", centerId);
		RestAssured.baseURI = LoadEnv.getProperty("IDAM");
		Response response = RestAssured.given()
				.contentType("application/json")
				.header("authorization", "Bearer " + access_Token)
				.body(json)
				.post("user/userName");
		return response;
	}

}
