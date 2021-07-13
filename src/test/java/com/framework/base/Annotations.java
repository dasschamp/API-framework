
package com.framework.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.framework.base.GenericActions;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import api.controller.idam.TokenCreation;

public class Annotations {

	public static ExtentReports Exreport;
	public static ExtentTest test;
	public static Properties LoadEnv =new Properties();
	public static String configPath = System.getProperty("user.dir") + "/src/test/java/com/util/config/";
	public static String token;
	
	/**
	* This method is used to initialze extend report and set environment from testng xml parameters. 
	* Load environment values and set SM token value
	* @param environment from testng xml
	* @author  Kalidass Mani
	*/	
	
	@Parameters({ "environment" })
	@BeforeSuite
	public static void beforeSuite(String env) {
		try {
		Exreport = new ExtentReports(System.getProperty("user.dir")+ File.separator+
				"test-output"+File.separator+ "ExtentReportTesting.html");	
		
		Exreport.loadConfig(new File(System.getProperty("user.dir")+File.separator +"src"+File.separator +"test"+File.separator +"java"
				+File.separator +"com"+File.separator +"util"+File.separator +"resources"+File.separator + "extent-config.xml"));
		loadEnvironment(env);	
		token = TokenCreation.createToken(LoadEnv.getProperty("SMUsername"),LoadEnv.getProperty("SMPassword"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	* This method is used to create test in extend report and assign a name & tag from testng xml 
	* @author  Kalidass Mani
	*/		
	@BeforeMethod
	 public void beforeMethod(Method testMethod,ITestContext context){     
		//test = Exreport.startTest(Arrays.toString(testMethod.getDeclaredAnnotations()));
		//test = Exreport.startTest(testMethod.getName());
		 String descriptiveTestName = testMethod.getAnnotation(Test.class).description();
		 test = Exreport.startTest(descriptiveTestName);
		// String suite=context.getCurrentXmlTest().getSuite().getName();
		 test.assignCategory(context.getName());
		 
		
	}
	
	/**
	* This method is used to end the test created in before method
	* @author  Kalidass Mani
	*/	
	@AfterMethod
	 public void afterMethod(){     
		Exreport.endTest(test);
	}
	
	/**
	* This method is used to flush the report once execution is done
	* @author  Kalidass Mani
	*/	
	@AfterSuite
	public void flushReport() {
		Exreport.flush();
		Exreport.close();
	}
	
	

	public static void loadEnvironment(String input) throws IOException {
		
		if (input == "QualChina") {
			FileInputStream fis = new FileInputStream(configPath + "EnvFile_QualChina.properties");
			LoadEnv.load(fis);
		} else if (input == "StageChina") {
			FileInputStream fis = new FileInputStream(configPath + "EnvFile_StageChina.properties");
			LoadEnv.load(fis);
		} else if (input == "StageRow") {
			FileInputStream fis = new FileInputStream(configPath + "EnvFile_StageRow.properties");
			LoadEnv.load(fis);
		} else if (input == "ProdChina") {
			FileInputStream fis = new FileInputStream(configPath + "EnvFile_ProdChina.properties");
			LoadEnv.load(fis);
		} else if (input == "ProdRow") {
			FileInputStream fis = new FileInputStream(configPath + "EnvFile_ProdRow.properties");
			LoadEnv.load(fis);
		} else {
			FileInputStream fis = new FileInputStream(configPath + "EnvFile_QualRow.properties");
			LoadEnv.load(fis);
		}
		
		
	}

}