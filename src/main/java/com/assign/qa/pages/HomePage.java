package com.assign.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.assign.qa.base.TestBase;
import com.assign.qa.util.utilFunctions;

public class HomePage extends TestBase {
	
	// object factory
	
	@FindBy(xpath="//a[contains(@href,'www.makemytrip.com/flights')]/span[contains(@class,'chFlights')]")
	WebElement flightsMenu;
	
	@FindBy(xpath="//li[contains(text(),'Round Trip')]")
	WebElement roundTripMenu;
	
	@FindBy(xpath="//input[@id='fromCity']")
	WebElement fromCityDrop;
	
	@FindBy(xpath="//li[@class='react-autosuggest__suggestion react-autosuggest__suggestion--first']")
	WebElement firstOption;
	
	@FindBy(xpath="//input[@placeholder='From']")
	WebElement searchFromCity;
	
	@FindBy(xpath="//input[@placeholder='To']")
	WebElement searchToCity;
	
	@FindBy(xpath="//div[contains(@class,'dateFiled')][1]")
	WebElement departureDrop;
	
	@FindBy(xpath="//div[contains(@class,'dateFiled')][2]")
	WebElement returnDrop;
	
	String departureDate = "//div[@aria-label='%replace%' and @aria-disabled='false']";
	String returnDate = "//div[@aria-label='%replace%' and @aria-disabled='false']";
	
	@FindBy(xpath="//a[contains(@class,'widgetSearchBtn') and text()='Search']")
	WebElement searchBtn;
	

	
	// constructor
	public HomePage() {
		super();
		PageFactory.initElements(driver, this);
	}
	
	// actions
	public void selectFlightsMenu() {
		flightsMenu.click();
	}
	
	public void selectRoundtripMenu() {
		roundTripMenu.click();
	}
	
	public void enterDepartureCity() {
		fromCityDrop.click();
		searchFromCity.sendKeys(prop.getProperty("From"));
		utilFunctions.ExpWaitForWebelement(firstOption);
		firstOption.click();
	}
	
	public void enterReturnCity() {
		searchToCity.sendKeys(prop.getProperty("To"));
		utilFunctions.ExpWaitForWebelement(firstOption);
		firstOption.click();
	}
		
	public void enterDepartureDate() {
		departureDrop.click();
		utilFunctions date = utilFunctions.getCurrentAndReturnDates();
		driver.findElement(utilFunctions.customXpath(departureDate,date.departureDate)).click();
	}
	
	public void enterReturnDate() {
		returnDrop.click();
		utilFunctions date = utilFunctions.getCurrentAndReturnDates();
		driver.findElement(utilFunctions.customXpath(returnDate, date.returnDate)).click();
	}

	public FlightInfoPage search() {
		searchBtn.click();
		driver.manage().deleteAllCookies();
		return new FlightInfoPage();
	}






}
