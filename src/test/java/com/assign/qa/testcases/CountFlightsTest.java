package com.assign.qa.testcases;

import java.io.IOException;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.assign.qa.base.TestBase;
import com.assign.qa.pages.FlightInfoPage;
import com.assign.qa.util.utilFunctions;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class CountFlightsTest extends TestBase {

	public static FlightInfoPage flights;
	
	@BeforeClass
	public void initTest() {
		flights = new FlightInfoPage();
	}
	
	@Test(priority=1)
	public void flightsCountNoFilterTest() throws Exception {	
		test = extent.createTest("Validate flightsCounts with/without Filters");
		test.assignCategory("Regression Test");
		
		childTest = test.createNode("NoFilter");
		
		Map<String, Integer> counts = flights.departureCountNoFilter();
		
		childTest.info(MarkupHelper.createLabel("Total number of flights available without any filter", ExtentColor.BLUE));
		childTest.log(Status.INFO, "Departure : "+ counts.get("departure") + "\t Return : "+ counts.get("return"));
		
		System.out.println("Total number of flights available without any filter");
		System.out.println("Departure : "+ counts.get("departure") + "\n Return : "+ counts.get("return"));

	}
	
	@Test(priority=2)
	public void flightsCountNonStopFilterTest() throws Exception {
		//test = extent.createTest("Validate flightsCounts with Nonstop Filter");
		childTest = test.createNode("Nonstop Filter");
		test.assignCategory("Regression Test");
		
		Map<String, Integer> counts = flights.flightsCountNonStopFilter();
		
		childTest.info(MarkupHelper.createLabel("Total number of flights available with NonStop filter", ExtentColor.BLUE));
		childTest.log(Status.INFO, "Departure : "+ counts.get("departure") + "\t Return : "+ counts.get("return"));
		
		System.out.println("Total number of flights available with NonStop filter");
		System.out.println("Departure : "+ counts.get("departure") + "\n Return : "+ counts.get("return"));
	}
	
	@Test(priority=3)
	public void flightsCountOneStopFilterTest() throws Exception {
		//test = extent.createTest("Validate flightsCounts with Onestop Filter");
		childTest = test.createNode("Onestop Filter");
		test.assignCategory("Regression Test");
		
		Map<String, Integer> counts = flights.flightsCountOneStopFilter();
		
		childTest.info(MarkupHelper.createLabel("Total number of flights available with One Stop filter", ExtentColor.BLUE));
		childTest.log(Status.INFO, "Departure : "+ counts.get("departure") + "\t Return : "+ counts.get("return"));
		
		System.out.println("Total number of flights available with One Stop filter");
		System.out.println("Departure : "+ counts.get("departure") + "\n Return : "+ counts.get("return"));
		
		
	}
	
	

	@AfterMethod
	 public void checkResult(ITestResult result) throws Exception   {
	  
		if (result.getStatus() == ITestResult.FAILURE) {
			
			childTest.log(Status.FAIL, result.getName()+" Test Failed.");
//			   test.log(Status.FAIL, "Test Case Failed is " + result.getThrowable());
//			   test.fail(MarkupHelper.createLabel(result.getName()+" Test case Failed", ExtentColor.RED));
			   
			   String screenshotPath = utilFunctions.getScreenshot(result.getName());
			   childTest.fail(result.getThrowable().getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
			   childTest.addScreenCaptureFromPath(screenshotPath);
			   
		} else if (result.getStatus() == ITestResult.SKIP) {
			  //test.log(Status.SKIP, result.getName()+ " Test Case Skipped");
			childTest.skip(MarkupHelper.createLabel(result.getName()+" Test case Skipped", ExtentColor.YELLOW));
			  childTest.skip(result.getThrowable());
			  
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			  //test.log(Status.PASS, result.getName()+ " Test Case Passed");
			childTest.log(Status.PASS, MarkupHelper.createLabel("Test passed", ExtentColor.GREEN));
			  childTest.pass(MarkupHelper.createLabel(result.getName()+" Test passed", ExtentColor.GREEN));
		  }
	 
	 }
	
	
}
