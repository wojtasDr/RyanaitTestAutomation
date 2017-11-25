package ryanair.automation.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PricePage {
	private WebDriver driver;

	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// ---WEB ELEMENTS---
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	private WebElement selectedPriceButton;
	private WebElement standardFareBox;
	private WebElement plusFareBox;
	private WebElement flexiPlusFareBox;
	private WebElement continueButton;


	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// ---LOCATORS---
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	By selectedPriceButtonLocator = By.xpath("//div[@class='flight-header__min-price hide-mobile']/flights-table-price");
	By standardFareBoxLocator = By.xpath("//div[@class='flights-table-fares__head']/span[text()='Standard fare']");
	By plusFareBoxLocator = By.xpath("//div[@class='flights-table-fares__head']/span[text()='Plus']");
	By flexiPlusFareBoxLocator = By.xpath("//div[@class='flights-table-fares__head']/span[text()='Flexi Plus']");
	//By continueButtonLocator = By.xpath("//button[@class='core-btn-primary core-btn-block core-btn-medium']/span[text()='Continue']");
	By continueButtonLocator = By.cssSelector("button#continue");
	
	public PricePage(WebDriver driver) {
		this.driver = driver;

		// // Check that we're on the right page.
		// if (!driver.getCurrentUrl().trim().endsWith("/tumorboard/#/login")) {
		// throw new IllegalStateException("This is not the login page");
		// }
	}

	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// ---PAGE METHODS---
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public PricePage confirmSelectedPrice(){
		selectedPriceButton = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.visibilityOfElementLocated(selectedPriceButtonLocator));

		
		//selectedPriceButton = driver.findElement(selectedPriceButtonLocator);
		selectedPriceButton.click();
		
		return this;
	}
	
	public PricePage selectStandardFare() {
		standardFareBox = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.visibilityOfElementLocated(standardFareBoxLocator));
		//standardFareBox = driver.findElement(standardFareBoxLocator);
		standardFareBox.click();

		return this;
	}
	
	public PricePage selectPlusFare() {
		plusFareBox = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.visibilityOfElementLocated(plusFareBoxLocator));
		//plusFareBox = driver.findElement(plusFareBoxLocator);
		plusFareBox.click();

		return this;
	}
	
	public PricePage selectFlexiPlusFare() {
		flexiPlusFareBox = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.visibilityOfElementLocated(flexiPlusFareBoxLocator));
		//flexiPlusFareBox = driver.findElement(flexiPlusFareBoxLocator);
		flexiPlusFareBox.click();

		return this;
	}
	
	public RecommendationsPage clickContinueButton(){
		(new WebDriverWait(driver, 10))
				.until(ExpectedConditions.visibilityOfElementLocated(continueButtonLocator));
		
		continueButton = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.elementToBeClickable(continueButtonLocator));
		
		//continueButton = driver.findElement(continueButtonLocator);
		continueButton.click();
		
		return new RecommendationsPage(driver);
	}
}
