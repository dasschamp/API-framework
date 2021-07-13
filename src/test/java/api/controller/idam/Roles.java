package api.controller.idam;

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

public class Roles extends GenericActions {

//	static String Access_Token = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhbXIiOlsicGFzc3dvcmQiXSwiYXVkIjpbImh0dHBzOi8vaWFtYXBpLXFhLndhbGxzdHJlZXRlbmdsaXNoLmNvbS9yZXNvdXJjZXMiXSwiYXV0aF90aW1lIjoxNjA5MjkzNzQwLCJjZW50ZXJfaWQiOiJJNDI4IiwiY2xpZW50X2lkIjoiV1NFLUZyb250RW5kIiwiZXhwIjoxNjA5MzI5NzQwLCJlbmFibGVfcGFzc3dvcmRfcmVzZXQiOnRydWUsImlzcyI6Imh0dHBzOi8vaWFtYXBpLXFhLndhbGxzdHJlZXRlbmdsaXNoLmNvbSIsIm5iZiI6MTYwOTI5Mzc0MCwicm9sZXMiOlsic2VydmljZW1hbmFnZXIiXSwic3ViIjoiZTQ1MzZlMGItYzBlMi00OTFlLWIzMDMtZDA2ZDc0YTQ5YjY1Iiwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsImJsaW5rIiwibHNhcGkiLCJyZXBvcnRhcGkiLCJzYWlsYXBpIiwic25iYXBpIiwib2ZmbGluZV9hY2Nlc3MiXX0.Cuh2NgdWMj0q4fb6BKCdcH4lIPcf13V87xCK09ppTBlUzxmpPN7WNZ1uKxPbNgsPysYwv8bZITmvHxfSU2tgP_j3bey0yZ6eZstQxoGlZ0B8Vk_ZwyMLzvdggibysisZfLlT3IVLBOUXyW9P8CT0tQc6qVpRV034-XzWEgxCQhtVlzjKFgRd9aZwqNT-2iT4f_e1JtH8xKzu63-2XZZjdTsxZkUXPsflL2oyTr2gQhBF4aBcss69mXAVADUb83MPlQJH414drpuVWx_3pOYIF0wQbky8LOzCwpDE4YIm4yaK_atj6cg0AKcWkCZpkehC1XdELhzH-DctUOQNLbEmXQ";
//	static String CenterId = "463dc8a7-95bd-4de4-9201-054b7a79c40d";
//	static String Rolename = "consultant";

//	@Test
//	public void runcall() throws IOException {
//		GetUseridViaRoleName(Access_Token, CenterId, Rolename);
//	}

	public static Map<String, String> getUseridViaRoleName(
			String access_Token, 
			String centerId, 
			String rolename)
			throws IOException {

	
		RestAssured.baseURI = LoadEnv.getProperty("IDAM");
		response = given().header("authorization", "Bearer " + access_Token).when()
				.param("rolesList", rolename).get("roles/" + centerId);

		JsonPath apiResponse = response.jsonPath();

		Map<String, String> outputMap = new LinkedHashMap<String, String>();

		List<Map<String, String>> alluserlist = apiResponse.get();

		for (int i = 0; i < alluserlist.size(); i++) {

			Map<String, String> Temp = alluserlist.get(i);

			outputMap.put("User" + i, Temp.get("userId"));

		}

		return outputMap;
	}
	
	public static Response getUserRole(String access_Token,String centerId,Map<String,String> queryParam)	throws IOException {
		RestAssured.baseURI = LoadEnv.getProperty("IDAM");
		response = given().header("authorization", "Bearer " + access_Token).when()
				.params(queryParam).get("roles/");
		return response;
	}

}
