package com.assign.qa.testcases;

import java.io.IOException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.assign.qa.base.TestBase;
import com.assign.qa.util.utilFunctions;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class TestSetup extends TestBase {

	@BeforeSuite
	public void setUp() {
		readPropertyFile();
		driverInitialization();
		setExtentReport();
	}
	
	
	
	@AfterSuite
	public void tearDown() {
		extent.flush();
		driver.quit();
	}
	
	
	
	
}
