package com.assign.qa.testcases;

import java.io.IOException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.assign.qa.base.TestBase;
import com.assign.qa.pages.HomePage;
import com.assign.qa.util.utilFunctions;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class SearchFlightsTest extends TestBase {

	@Test
	public void searchForAFlight(){
		
		test = extent.createTest("Validate search Flight");
		test.assignCategory("Sanity Test");
		
		HomePage home = new HomePage();
		
		home.selectFlightsMenu();
		test.log(Status.INFO, "Clicked on Flight menu");
		
		home.selectRoundtripMenu();	
		test.log(Status.INFO, "Slected round trip");
		
		home.enterDepartureCity();	
		test.log(Status.INFO, "Entered the departure city");
		
		home.enterReturnCity();	
		test.log(Status.INFO, "Entered return city");
		
		home.enterDepartureDate();
		test.log(Status.INFO, "Entered Departure date");
		
		home.enterReturnDate();
		test.log(Status.INFO, "Entered return date");
		
		home.search();
		test.log(Status.INFO, "clicked the search button");
	}
	

	@AfterMethod
	 public void checkResult(ITestResult result) throws Exception   {
	  
		if (result.getStatus() == ITestResult.FAILURE) {
			
			   test.log(Status.FAIL, result.getName()+" Test Failed.");
//			   test.log(Status.FAIL, "Test Case Failed is " + result.getThrowable());
//			   test.fail(MarkupHelper.createLabel(result.getName()+" Test case Failed", ExtentColor.RED));
			   
			   String screenshotPath = utilFunctions.getScreenshot(result.getName());
			   test.fail(result.getThrowable().getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
			   test.addScreenCaptureFromPath(screenshotPath);
			   
		} else if (result.getStatus() == ITestResult.SKIP) {
			  //test.log(Status.SKIP, result.getName()+ " Test Case Skipped");
			  test.skip(MarkupHelper.createLabel(result.getName()+" Test case Skipped", ExtentColor.YELLOW));
			  test.skip(result.getThrowable());
			  
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			  //test.log(Status.PASS, result.getName()+ " Test Case Passed");
			test.log(Status.PASS, MarkupHelper.createLabel("Test passed", ExtentColor.GREEN));
			  test.pass(MarkupHelper.createLabel("Test passed", ExtentColor.GREEN));
		  }
	 
	 }
	
}
