package api.controller.idam;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.framework.base.GenericActions;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UserInfo extends GenericActions {
	
//static String Access_Token="Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhbXIiOlsicGFzc3dvcmQiXSwiYXVkIjpbImh0dHBzOi8vaWFtYXBpLXFhLndhbGxzdHJlZXRlbmdsaXNoLmNvbS9yZXNvdXJjZXMiXSwiYXV0aF90aW1lIjoxNjA5MzkyMDYwLCJjZW50ZXJfaWQiOiJJNDI4IiwiY2xpZW50X2lkIjoiV1NFLUZyb250RW5kIiwiZXhwIjoxNjA5NDI4MDYwLCJlbmFibGVfcGFzc3dvcmRfcmVzZXQiOnRydWUsImlzcyI6Imh0dHBzOi8vaWFtYXBpLXFhLndhbGxzdHJlZXRlbmdsaXNoLmNvbSIsIm5iZiI6MTYwOTM5MjA2MCwicm9sZXMiOlsic2VydmljZW1hbmFnZXIiXSwic3ViIjoiZTQ1MzZlMGItYzBlMi00OTFlLWIzMDMtZDA2ZDc0YTQ5YjY1Iiwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsImJsaW5rIiwibHNhcGkiLCJyZXBvcnRhcGkiLCJzYWlsYXBpIiwic25iYXBpIiwib2ZmbGluZV9hY2Nlc3MiXX0.oKijXHkoCejmRZUVlgHSWtwUTl0Vc8GYrDl0OmdG-0AGkMe4utnTa6IGeYKBYu9UgFDaudZv59Ce_bnftNqZWR9Ls0D7C0ILsIyIG-7X1DbTkczA4meDMfex1FvXT1iQQydeVsge6gi2xFQ62GVkgYl2YXdZj_z7y1qS5vlJlCu4CJqEgYkW8eRvB_CTyJ6_b8qNAoI-h76x1UDxoWYFg3Mqu6l6veU8rB4Lzajq4edhBb2iV6UI4CG9g3VpCj_XFAOue4sPDP1Js-i1NgTeTuTFS170DVS71KWYwpmI3zAJQ0mYiG0xfj8ORdyd40Pl5C8BGvtiL6oVtTJbJ44cgA";
	
//	@Test
//	public static void runcall() throws IOException {
//		
//		GetUserInfo(Access_Token);
//	}
	
	public static Map<String, String> getUserInfo(String access_Token) throws IOException{
		
		
		RestAssured.baseURI = LoadEnv.getProperty("IDAM");
		response = given().header("authorization", "Bearer " + access_Token)
				.when()
				.get("connect/userinfo");
		
		//System.out.println(response.prettyPrint());
		
		Map<String, String> outputMap = new LinkedHashMap<String, String>();
		
		outputMap.put("CenterId", response.jsonPath().getString("centerId"));
		outputMap.put("CenterRefID", response.jsonPath().getString("centerRefID"));
		outputMap.put("RAuserId", response.jsonPath().getString("userId"));
		outputMap.put("TerritoryId", response.jsonPath().getString("territoryId"));
		
		
		System.out.println(outputMap);
		
		return outputMap;
		
	}
	
public static Response getUserInfoDetails(String access_Token) throws IOException{
		
		
		RestAssured.baseURI = LoadEnv.getProperty("IDAM");
		response = given().header("authorization", "Bearer " + access_Token)
				.when()
				.get("connect/userinfo");
		
		return response;
		
	}

}
