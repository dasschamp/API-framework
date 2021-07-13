package com.framework.base;

import org.apache.commons.configuration.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import com.cedarsoftware.util.io.JsonWriter;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.response.Response;

public class GenericActions extends Annotations {

	

	public static String configPath = System.getProperty("user.dir") + "/src/test/java/com/util/config/";

	
	public static PropertiesConfiguration WritePropFile = null;
	public static Properties ReadPropFile = new Properties();

	public static String Foldername;
	public static String Dateformat;
	public static Response response;
	public static String defaultSLA;
	
	

	public static void ReadWritePropTransferFile() throws IOException {

		// Read Values from prop File
		FileInputStream fis = new FileInputStream(configPath + "PropTransfer.properties");
		ReadPropFile.load(fis);

		// Write Values in prop File
		File file = new File(configPath + "PropTransfer.properties");

		try {
			WritePropFile = new PropertiesConfiguration(file);
			WritePropFile.setProperty("key", "value");

			OutputStream out = new FileOutputStream(file);
			WritePropFile.save(out);

		} catch (ConfigurationException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}



	public JSONObject converFileToJSONObject(String Foldername, String Filename) {

		try {

			File jsonfile = new File(
					System.getProperty("user.dir") + "/src/test/java/testData/" + Foldername + "/" + Filename);

			Object obj = new JSONParser().parse(new FileReader(jsonfile));

			JSONObject jsonobj = (JSONObject) obj;

			test.log(LogStatus.PASS, "Successfully Created JSON Object from File");

			return jsonobj;

		} catch (FileNotFoundException e) {
			test.log(LogStatus.FAIL, "Failed JSON Object Creation Error Message:" + e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			test.log(LogStatus.FAIL, "Failed JSON Object Creation Error Message:" + e.toString());
			e.printStackTrace();
		} catch (ParseException e) {
			test.log(LogStatus.FAIL, "Failed JSON Object Creation Error Message:" + e.toString());
			e.printStackTrace();
		}
		return null;
	}

	public void converJSONObjectToFile(JSONObject obj, String Foldername, String Filename) throws IOException {

		FileWriter file = new FileWriter(
				System.getProperty("user.dir") + "/src/test/java/wse/testdata/dsw/" + Foldername + "/" + Filename);

		try {

			file.write(obj.toJSONString());

			test.log(LogStatus.PASS, "Successfully Copied JSON Object to File");

		}

		catch (IOException e) {
			test.log(LogStatus.FAIL, "Failed to copy JSON Object to File Error Message:" + e.toString());
			e.printStackTrace();
		} finally {
			file.flush();
			file.close();
		}
	}

	public static int getRandomNum(int limit) {

		Random rand = new Random();

		int RandomNumber = rand.nextInt(limit);

		return RandomNumber;

	}

	public static void verifyResponse(String responseCode, String value, int sla) throws Exception {
		if (String.valueOf(response.getStatusCode()) == responseCode) {
			Assert.assertTrue(true);
		} else {
			Assert.assertFalse(false);
		}

		if (sla == 0) {
			sla = Integer.parseInt(LoadEnv.getProperty(defaultSLA));
		}
		if (response.getTime() < sla) {
			Assert.assertTrue(true);
		} else {
			Assert.assertFalse(false);
		}
		if (value != null) {
			String bodyAsString = response.getBody().asString();
			Assert.assertEquals(bodyAsString.contains(value), true, "Response body contains expected data");
		}
	}

	public static void verifyResponse(String responseCode) throws Exception {
		String value = null;
		int sla = 0;
		verifyResponse(responseCode, value, sla);
	}

	public static void verifyResponse(String responseCode, int sla) throws Exception {
		String value = null;
		verifyResponse(responseCode, value, sla);
	}

	public static void verifyResponse(String responseCode, String value) throws Exception {
		int sla = 0;
		verifyResponse(responseCode, value, sla);
	}

	/**
	 * This method is used to validate all response fields using a map.
	 * 
	 * @param convert response to map and send as param with set as the values that
	 *                needs to be validated against the response
	 * @author Kalidass Mani
	 */

	public static void verifyResponseAllFields(Map<String, String> map, Set<String> set) {
		boolean flag = true;
		List<String> responseList = new ArrayList<>();
		if (map.isEmpty() || set.isEmpty() || map.size() < set.size()) {
			test.log(LogStatus.FAIL, "The response is not validated as there is a mismatch from expected and actual ");
			return;
		}
		for (String field : set) {
			if (!map.containsKey(field)) {
				flag = false;
				responseList.add(field);
			}
		}
		if (flag)
			test.log(LogStatus.PASS, "All response fields are validated sucessfully");
		else
			test.log(LogStatus.FAIL, "The expected fields are missing in the response :" + responseList.toString());
	}

	/**
	 * This method is used to validate all response fields using a Response.
	 * 
	 * @param send Response with set as the values that needs to be validated
	 *             against the response using contains text method in string
	 * @author Kalidass Mani
	 */
	public static void verifyResponseAllFieldsUsingContainsText(Response res, Set<String> set) {
		boolean flag = true;
		ArrayList<String> responseList = new ArrayList<>();
		String jsonResponse = res.jsonPath().prettyPrint().toLowerCase();
		if (jsonResponse == null && jsonResponse.equals("")) {
			test.log(LogStatus.FAIL, "The response is not validated as there is a mismatch from expected and actual ");
			return;
		}
		for (String field : set) {
			if (!jsonResponse.contains(field.toLowerCase())) {
				flag = false;
				responseList.add(field);
			}
		}
		if (flag)
			test.log(LogStatus.PASS, "All response fields are validated sucessfully");
		else
			test.log(LogStatus.FAIL, "The expected fields are missing in the response :" + responseList.toString());
	}

	/**
	 * This method is used to validate all response fields using a map.
	 * 
	 * @param convert response to map and send as param with one string value that
	 *                needs to be validated against the response
	 * @author Kalidass Mani
	 */
	public static void verifyResponseField(Map<String, String> map, String str) {
		if (map.isEmpty()) {
			test.log(LogStatus.FAIL, "The response is not validated as there is a mismatch from expected and actual ");
			return;
		}
		if (map.containsKey(str))
			test.log(LogStatus.PASS, "The response field '" + str + "' is validated sucessfully");
		else
			test.log(LogStatus.FAIL, "The expected field is missing in the response :" + str);
	}

	/**
	 * This method is used to validate the response is not equals null
	 * 
	 * @param Response
	 * @author Kalidass Mani
	 */
	public static void verifyResponseFieldAnyValue(Response res) {
		if (res.asString() != null)
			test.log(LogStatus.PASS, "The response field is validated sucessfully");
		else
			test.log(LogStatus.FAIL, "The expected field is missing in the response");
	}

	/**
	 * This method is used to validate single response field using a map.
	 * 
	 * @param convert response to map and send as param with one field name and
	 *                expected value of the field
	 * @author Kalidass Mani
	 */
	public static void verifyResponseFieldWithValue(Map<String, String> map, String key, String value) {
		if (map.isEmpty()) {
			test.log(LogStatus.FAIL, "The response is not validated as there is a mismatch from expected and actual ");
			return;
		}
		if (map.containsKey(key) && map.get(key).equals(value)) {
			test.log(LogStatus.PASS, "The response field is validated sucessfully");
		} else
			test.log(LogStatus.FAIL, "The expected field is missing in the response :" + key);
	}

	/**
	 * This method is used to convert Response to a map.
	 * 
	 * @param Response
	 * @author Kalidass Mani
	 */
	public static Map<String, String> convertResponseToMap(Response Jsonresponse) {
		try {
			return Jsonresponse.jsonPath().getMap("");
		} catch (Exception e) {
			return new HashMap<String, String>();
		}
	}

	/**
	 * This method is used to validate response code and log the response to report
	 * 
	 * @param Response, Expected status code
	 * @author Kalidass Mani
	 */
	public static void verifyStatusCode(Response Jsonresponse, int statuscode) {
		if (Jsonresponse == null || Jsonresponse.equals("")) {
			test.log(LogStatus.FAIL, "Response is empty");
			return;
		}

		try {
			if (Jsonresponse != null && Jsonresponse.getStatusCode() == statuscode)
				test.log(LogStatus.PASS,
						"The response status code is validated sucessfully, status code :" + statuscode);
			else
				test.log(LogStatus.FAIL, "The response code is not as expected, actual :" + Jsonresponse.getStatusCode()
						+ " but expected :" + statuscode);
		} catch (Exception e) {
			test.log(LogStatus.FAIL, e.getMessage());
		} finally {
			// test.log(LogStatus.INFO,Jsonresponse.asString());
			test.log(LogStatus.INFO, "<pre>" + "Response JSON: " + JsonWriter.formatJson(Jsonresponse.prettyPrint())
					.replace("\t", "&nbsp;&nbsp;").replace(" ", "&nbsp;&nbsp;") + "</pre>");
		}
	}

}
