package com.assign.qa.testcases;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.assign.qa.base.TestBase;
import com.assign.qa.pages.FlightInfoPage;
import com.assign.qa.util.utilFunctions;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class CheckPricesTest extends TestBase {

public static FlightInfoPage flights;
	
	@BeforeClass
	public void initTest() {
		flights = new FlightInfoPage();
	}
	
	@DataProvider
	public Object[][] getFlights() {
		Object data[][] = utilFunctions.getExcelData("flights");
		System.out.println("getFlights"+Arrays.deepToString(data));
		return data;
	}
	
	@Test(priority=0)
	public void parentTest() {	
		test = extent.createTest("Validate the prices");
		test.assignCategory("Functional Test");
		childTest = test.createNode("create to keep 'validateFlightPriceTest()' as a child test.");
	}

	
	@Test(priority=1, dataProvider="getFlights")
	public void validateFlightPriceTest(String dep, String ret) throws Exception {
		
		//test = extent.createTest("Validate the prices for selected flights "+ dep+", "+ret);
		childTest = test.createNode("Selected flights "+ dep+", "+ret);
		test.assignCategory("Functional Test");
		
		Map<String,Integer> fees = flights.flightPrices(Float.valueOf(dep).intValue(),Float.valueOf(ret).intValue());
		
		SoftAssert validatePrices = new SoftAssert();
		
		validatePrices.assertEquals(fees.get("TopDeparturePrice"), fees.get("FootDeparturePrice"));
		childTest.log(Status.INFO,"Departure Price at the top "+fees.get("TopDeparturePrice")+" at the bottom "+fees.get("FootDeparturePrice"));
		
		validatePrices.assertEquals(fees.get("TopReturnPrice"), fees.get("FootReturnPrice"));
		childTest.log(Status.INFO,"Return Price at the top "+fees.get("TopReturnPrice")+" at the bottom "+fees.get("FootReturnPrice"));
		
		long expectedTotal = fees.get("TopDeparturePrice")+fees.get("TopReturnPrice");
		long actualTotal = new Long(fees.get("TotalPrice"));
		childTest.log(Status.INFO,"Expected Total "+expectedTotal+" Actual total "+actualTotal);
		
		validatePrices.assertEquals(actualTotal, expectedTotal);
	
		validatePrices.assertAll();
	}
	


	@AfterMethod
	 public void checkResult(ITestResult result) throws Exception   {
	  
		if (result.getStatus() == ITestResult.FAILURE) {
			
			childTest.log(Status.FAIL, result.getName()+" Test Case Failed");
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
			childTest.pass(MarkupHelper.createLabel(" Test case passed", ExtentColor.GREEN));
		  }
	 
	 }
	
	
}
