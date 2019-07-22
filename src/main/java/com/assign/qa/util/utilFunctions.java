package com.assign.qa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.assign.qa.base.TestBase;



public class utilFunctions extends TestBase {
	
	/*
	 * 1. ExpWaitForWebelement()
	 * 2. getExcelData()
	 * 3. getCurrentAndReturnDates()
	 * 4. customXpath()
	 * 5. scrollToBottom()
	 * 6. ScrollToTop()
	 * 7. scrollToView()
	 * 8. getScreenshot()
	 * 
	 */
	
	
	/**************************************
	 * Function Name: ExpWaitForWebelement
	 * Author: Kavitha
	 * Created Date: 2019-07-10
	 * Purpose: Explicit wait for a web element
	 * Prerequisites: WebElement that takes more time to load.
	 * Change History:	  
	 * 
	 **************************************/
	public static void ExpWaitForWebelement(WebElement element)
	{
		WebDriverWait expWait=new WebDriverWait(driver, 40);
		expWait.until(ExpectedConditions.elementToBeClickable(element));
		
	}

	
	/**************************************
	 * Function Name: getExcelData
	 * Author: Kavitha
	 * Created Date: 2019-07-10
	 * Purpose: Read the test data from Excel file
	 * Prerequisites: apachi poi dependency and Excel file with data.
	 * Change History:	  
	 * 
	 **************************************/
	
	public static String EXCELDATA_SHEET_PATH = prop.getProperty("excelSheetPath");
	
    static Workbook book;
	static Sheet sheet;

	public static  Object[][] getExcelData(String excelSheetName) {
		FileInputStream file = null;
		try {
			file = new FileInputStream(EXCELDATA_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = book.getSheet(excelSheetName);
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		 
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
				data[i][j] = sheet.getRow(i + 1).getCell(j).toString();
			}
		}
		return data;
	}
	
	
	/**************************************
	 * Function Name: getCurrentAndReturnDates
	 * Author: Kavitha
	 * Created Date: 2019-07-10
	 * Purpose: Get the current date and another date that is x(from property file) days from today
	 * Prerequisites: numeric value for x
	 * Change History:	
	 * getCurrentAndReturnDates() should be called to assign the values to departureDate and returnDate
	 * 
	 **************************************/
	public static String departureDate;
	public static String returnDate;
	
	public static utilFunctions getCurrentAndReturnDates() {
		utilFunctions date = new utilFunctions();
		Calendar cal=Calendar.getInstance();
		
		cal.add(Calendar.DATE, 1); // please delete this line. added for test the tomorrow date.
		
		String[] dateArr=cal.getTime().toString().split(" ");
		utilFunctions.departureDate=dateArr[0]+" "+dateArr[1]+" "+dateArr[2]+" "+dateArr[5];
		cal.add(Calendar.DATE, Integer.parseInt(prop.getProperty("NoOfdays")));
		dateArr=cal.getTime().toString().split(" ");
		utilFunctions.returnDate=dateArr[0]+" "+dateArr[1]+" "+dateArr[2]+" "+dateArr[5];
		return date;
	}
	
	
	/**************************************
	 * Function Name: customXpath
	 * Author: Kavitha
	 * Created Date: 2019-07-10
	 * Purpose: Insert a java variable to xpath 
	 * Prerequisites: 
	 * Change History:	  
	 * 
	 **************************************/
	public static By customXpath(String xpath, String param) {
		String rawPath = xpath.replaceAll("%replace%", param);
		return By.xpath(rawPath);
	}
	
	
	/**************************************
	 * Functions Names: scrollToBottom ScrollToTop scrollToView
	 * Author: Kavitha
	 * Created Date: 2019-07-11
	 * Purpose: scroll the page through javascript executer
	 * Prerequisites: 
	 * Change History:	  
	 * @throws InterruptedException 
	 * 
	 **************************************/
	public static void scrollToBottom() throws InterruptedException {
		JavascriptExecutor jsDriver = (JavascriptExecutor)driver;
					
		try {
			long currentHeight = Long.parseLong(jsDriver.executeScript("return document.body.scrollHeight").toString());
			
			while(true) {
				jsDriver.executeScript("window.scrollTo(0,document.body.scrollHeight)");
				Thread.sleep(300);
				
				long newHeight = Long.parseLong(jsDriver.executeScript("return document.body.scrollHeight").toString());
				
				if(currentHeight==newHeight) break;
				currentHeight = newHeight;
			}
		} catch (Exception e) {
			 e.printStackTrace();
		}
		
	}
	
	public static void ScrollToTop() {
		JavascriptExecutor jsDriver = (JavascriptExecutor)driver;
		jsDriver.executeScript("window.scrollTo(document.body.scrollHeight,0)");
	}
	
	public static void scrollToView(WebElement element) {
		JavascriptExecutor jsDriver = (JavascriptExecutor)driver;
		jsDriver.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	
	/**************************************
	 * Function Name: getScreenshot
	 * Author: Kavitha
	 * Created Date: 2019-07-12
	 * Purpose: Get screen shot of the test
	 * Prerequisites: apachi commons.io dependency 
	 * Change History:	  
	 * 
	 **************************************/
	
	public static String getScreenshot(String imageName) {
		  
		String currentDate = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		
		String destination = System.getProperty("user.dir") + "/Screenshots/" + imageName + currentDate + ".png";
		try {
			FileUtils.copyFile(source, new File(destination));
		} catch (IOException e) {
			System.out.println("Failed to capture the screen "+ e.getMessage());
		}
		
		return destination;
	}
	
	
	
	/**************************************
	 * Function Name: isVisble
	 * Author: Kavitha
	 * Created Date: 2019-07-15
	 * Purpose: Check the emement is present in DOM or not. If not, return false
	 * Prerequisites: apachi commons.io dependency 
	 * Change History:	  
	 * 
	 **************************************/
	public static boolean isVisble(WebElement element)
	{
		boolean flag=false;
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		try
		{
			if(element.isDisplayed())
			{
				flag=true;
			}
		}
		catch (Exception e) {
			e.getStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return flag;
		
	}
		

	
	
	
	
	
	
	
	
	
	
	
}
