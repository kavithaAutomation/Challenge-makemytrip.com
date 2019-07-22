package com.assign.qa.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.assign.qa.base.TestBase;
import com.assign.qa.util.utilFunctions;

public class FlightInfoPage extends TestBase{

	// Pagefactory
	@FindBy(xpath="//input[@id='filter_stop0']/following::label//span[text()='Non Stop']")
	WebElement nonStopFilter;
	
	@FindBy(xpath="//span[@class='checkbox-group list1 append_bottom5 fli-filter-items']//span[@class='labeltext'][contains(text(),'1 Stop')]")
	WebElement oneStopFilter;
	
	@FindBy(xpath="//div[@id='ow_domrt-jrny']//div[@class='fli-list splitVw-listing']")
	List <WebElement> departureFlights;
	
	@FindBy(xpath="//div[@id='rt-domrt-jrny']//div[@class='fli-list splitVw-listing']")
	List <WebElement> returnFlights;
	
	@FindBy(xpath="//div[@id='applied-filter--wrapper']//a[contains(text(),'Clear')]")
	WebElement clearFilterLink;
	
	@FindBy(xpath="//div[@id='ow_domrt-jrny']//input[@data-checked='true']/following::label[1]//p[@class='actual-price']/span")
	WebElement departurePriceBox;
	
	@FindBy(xpath="//div[@id='rt-domrt-jrny']//input[@data-checked='true']/following::label[1]//p[@class='actual-price']/span")
	WebElement returnPriceBox;
	
	@FindBy(xpath="//div[contains(@class,'splitVw-footer-left')]//p[contains(@class,'actual-price')]/span")
	WebElement footerDeparturePriceBox;
	
	@FindBy(xpath="//div[contains(@class,'splitVw-footer-right ')]//p[contains(@class,'actual-price')]/span")
	WebElement footerReturnPriceBox;
	
	@FindBy(xpath="//div[contains(@class,'splitVw-footer-total')]//p/span[contains(@class,'splitVw-total-fare')]")
	WebElement footerTotalPriceBox;
	
	
	
	//initialization
	public FlightInfoPage() {
		PageFactory.initElements(driver, this);
	}
	
	
	//actions
	public Map<String, Integer> departureCountNoFilter() throws Exception {	
		Map<String, Integer> noFilterCounts = new HashMap<String, Integer>();
		
		utilFunctions.ExpWaitForWebelement(nonStopFilter);	
		
		utilFunctions.scrollToBottom();
		
		if(departureFlights.size()<1) 
			throw new Exception("No Departure Flights availabe");
		if(returnFlights.size()<1) 
			throw new Exception("No Return Flights availabe");
		
		noFilterCounts.put("departure", departureFlights.size());
		noFilterCounts.put("return", returnFlights.size());
		return noFilterCounts;
	}
	
	
	public Map<String, Integer> flightsCountNonStopFilter() throws Exception {
		Map<String, Integer> nonStopFilterCounts = new HashMap<String, Integer>();
		
		
		utilFunctions.ScrollToTop();
		
		if(utilFunctions.isVisble(clearFilterLink))
			clearFilterLink.click();
		
		nonStopFilter.click();
		utilFunctions.scrollToBottom();	
		
		if(departureFlights.size()<1) 
			throw new Exception("No Departure Flights availabe");
		if(returnFlights.size()<1) 
			throw new Exception("No Return Flights availabe");
		
		nonStopFilterCounts.put("departure", departureFlights.size());
		nonStopFilterCounts.put("return", returnFlights.size());
		
		return nonStopFilterCounts;
	}
	
	
	public Map<String, Integer> flightsCountOneStopFilter() throws Exception {
		
		Map<String, Integer> oneStopFilterCounts = new HashMap<String, Integer>();
		
		utilFunctions.ScrollToTop();
		if(utilFunctions.isVisble(clearFilterLink))
			clearFilterLink.click();
		
		utilFunctions.ExpWaitForWebelement(oneStopFilter);	
		
		oneStopFilter.click();
		utilFunctions.scrollToBottom();	
		
		if(departureFlights.size()<1) 
			throw new Exception("No Departure Flights availabe");
		if(returnFlights.size()<1) 
			throw new Exception("No Return Flights availabe");
		
		oneStopFilterCounts.put("departure", departureFlights.size());
		oneStopFilterCounts.put("return", returnFlights.size());
		
		return oneStopFilterCounts;
		
	}

	public Map<String, Integer> flightPrices(int dep, int ret) throws Exception{
				
		Map<String, Integer> fees = new HashMap<String, Integer>();
		System.out.println("dep "+dep);
		System.out.println("ret "+ret);
		
		utilFunctions.ScrollToTop();
		if(utilFunctions.isVisble(clearFilterLink))
			clearFilterLink.click();
		
		utilFunctions.scrollToView(departureFlights.get(dep-1)); 
		departureFlights.get(dep).click();
		utilFunctions.scrollToView(returnFlights.get(ret-1)); 
		returnFlights.get(ret).click();
		
		Integer TopDeparturePrice = covertToMoney(departurePriceBox.getText());
		Integer TopReturnPrice = covertToMoney(returnPriceBox.getText());
		Integer FotDeparturePrice = covertToMoney(footerDeparturePriceBox.getText());
		Integer FotReturnPrice = covertToMoney(footerReturnPriceBox.getText());
		Integer TotalPrice = covertToMoney(footerTotalPriceBox.getText());
		
		
		fees.put("TopDeparturePrice", TopDeparturePrice);
		fees.put("TopReturnPrice", TopReturnPrice);
		fees.put("FootDeparturePrice", FotDeparturePrice);
		fees.put("FootReturnPrice", FotReturnPrice);
		fees.put("TotalPrice", TotalPrice);
		
		return fees;
	}
	
	
	public int covertToMoney(String raw) {
		//departurePriceBox.getText() -> Rs 7,924
		//(departurePriceBox.getText()).split(" ")[1] -> 7,924
		//(departurePriceBox.getText()).split(" ")[1].replaceAll(",$", "") -> 7924
		return Integer.parseInt(raw.split(" ")[1].replace(",", ""));
	}
	
	
	
}
