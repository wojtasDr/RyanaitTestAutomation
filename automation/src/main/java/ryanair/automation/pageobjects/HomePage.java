package ryanair.automation.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ryanair.automation.utils.DateOperations;
import ryanair.automation.utils.Sleep;

public class HomePage {

	private WebDriver driver;

	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// ---WEB ELEMENTS---
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	private WebElement loginViewButton;
	private WebElement departureAirportInput;
	private WebElement destinationAirportInput;
	private WebElement datePickerRightArrow;
	private WebElement datePickerDate;
	private WebElement oneWayRadioButton;
	private WebElement passengersDropdown;
	private WebElement adultsIncrementButton;
	private WebElement letsGoButton;
	
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// ---LOCATORS---
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	By loginViewButtonLocator = By.id("myryanair-auth-login");
	By userNameTextLocator = By.xpath("//div[@class='avatar-user']/../span[@class='username']");
	By departureAirportInputLocator = By.xpath("//input[contains(@placeholder,'eparture')]");
	By departureAirportLabelLocator = By.xpath("//div[@label='From:']");
	By destinationAirportLabelLocator = By.xpath("//div[@label='To:']");
	By destinationAirportInputLocator = By.xpath("//input[contains(@placeholder,'estination')]");
	By departureDateInput = By.xpath("//div[@start-date-label='Fly out:']");
	By datePickerLocator = By.xpath("//div[@id='row-dates-pax']/div"); 
	By datePickerRightArrowLocator = By.xpath("//button[@class='arrow right']/core-icon/div"); 
	By oneWayRadioButtonLocator = By.id("lbl-flight-search-type-one-way");
	By passengersDropdownLocator = By.xpath("//div[@ng-switch-default]/div[@class='value']");
	By letsGoButtonLocator = By.xpath("//button/span[contains(@translate,'lets')]");
	
	public static By incrementButtonLocator(String passengersGroup){
		return By.xpath("//div[text()='"+ passengersGroup + "']/../../div[@class='details-controls']/core-inc-dec/button[contains(@ng-click,'increment')]");
	}
	
	public static By datePickerDateLocator(String date){
		return By.xpath("//li[@data-id='"+ date + "']/span[@ng-bind]");
	}
	
	public HomePage(WebDriver driver) {
		this.driver = driver;

		// // Check that we're on the right page.
		// if (!driver.getCurrentUrl().trim().endsWith("/tumorboard/#/login")) {
		// throw new IllegalStateException("This is not the login page");
		// }
	}

	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// ---PAGE METHODS---
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public LoginPage chooseLoginMode() {
		loginViewButton = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(loginViewButtonLocator));

		// loginViewButton = driver.findElement(loginViewButtonLocator);
		loginViewButton.click();

		return new LoginPage(driver);
	}
	
	//TODO Check max date and past date
	public HomePage setDepartureDate(String date) {
		long monthsInTheFuture = DateOperations.monthsBetweenDates(date);

		for (long i = 0; i <= monthsInTheFuture; i++) {
			datePickerRightArrow = driver.findElement(datePickerRightArrowLocator);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", datePickerRightArrow);
			Sleep.milliSeconds(300);
		}

		datePickerDate = driver.findElement(datePickerDateLocator(date));

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", datePickerDate);

		return this;
	}
	
	public HomePage setOneWayRadioButton(){
		oneWayRadioButton = driver.findElement(oneWayRadioButtonLocator);
		oneWayRadioButton.click();
		
		return this;
	}
	
	public HomePage clickLetsGoButton(){
		letsGoButton = driver.findElement(letsGoButtonLocator);
		letsGoButton.click();
		
		return this;
	}
	
	//TODO Add possibility to add other groups than Adults
	public HomePage selectPassengersDropdown(String adults, Integer adultsNumber) {
		passengersDropdown = driver.findElement(passengersDropdownLocator);
		passengersDropdown.click();

		for(int i = 0; i < (adultsNumber - 1); i++){
			adultsIncrementButton = driver.findElement(incrementButtonLocator(adults));
			adultsIncrementButton.click();
		}

		return this;
	}

	public HomePage chooseDepartureAirport(String departureAirport) {
		departureAirportInput = driver.findElement(departureAirportInputLocator);
		departureAirportInput.clear();
		departureAirportInput.sendKeys(departureAirport);
		departureAirportInput.sendKeys(Keys.ENTER);

		return this;
	}

	public HomePage chooseDestinationAirport(String destinationAirport) {
		destinationAirportInput = driver.findElement(destinationAirportInputLocator);
		destinationAirportInput.clear();
		destinationAirportInput.sendKeys(destinationAirport);
		destinationAirportInput.click();
		destinationAirportInput.sendKeys(Keys.ENTER);

		return this;
	}

	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// ---GETTER METHODS---
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public String getUserNameText() {
		return driver.findElement(userNameTextLocator).getText();
	}
	
	public String getDepartureAirportText() {
		return driver.findElement(departureAirportLabelLocator).getAttribute("value");
	}
	
	public String getDestinationAirportText() {
		return driver.findElement(destinationAirportLabelLocator).getAttribute("value");
	}
	
	public String getDepartureDateText() {
		return driver.findElement(departureDateInput).getAttribute("end-date-min");
	}
}
