package api.test.wiremock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
public class TestWireMock {
	
	//@BeforeMethod
	void createNewInstance() {
		configureFor("localhost",9000);
		stubFor(post("/api/now").withBasicAuth("admin", "admin")
				.withRequestBody(equalToJson("{\"name\":\"dass\"}"))
				.willReturn(aResponse().withBody("{ sucessfully created}")
						.withStatus(201))
						);
		
		stubFor(post("/api/now").withBasicAuth("admin", "admin")
				.withRequestBody(matchingJsonPath(""))
				.willReturn(aResponse().withBody("{ not created}")
						.withStatus(400))
						);
	}


	//@Test
	private void restAssuredTC() {
		
		RestAssured.baseURI="http://localhost:9000/api/now";
		RestAssured.authentication = RestAssured.preemptive().basic("admin","admin");
		Response response = RestAssured.given().body("{\"name\":\"dass\"}").post();
		response.prettyPrint();
		
		Response res = RestAssured.given().body("").post();
		res.prettyPrint();
		
}

}
