package ryanair.automation.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RecommendationsPage {
	private WebDriver driver;

	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// ---WEB ELEMENTS---
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	private WebElement checkoutButton;
	private WebElement declineSeatReservaitionButton;


	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// ---LOCATORS---
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	By checkoutButtonLocator = By.xpath("//button/span[text()='Check out']");
	By declineSeatReservaitionButtonLocator = By.xpath("//button[text()='Ok, thanks']");
	
	public RecommendationsPage(WebDriver driver) {
		this.driver = driver;

		// // Check that we're on the right page.
		// if (!driver.getCurrentUrl().trim().endsWith("/tumorboard/#/login")) {
		// throw new IllegalStateException("This is not the login page");
		// }
	}

	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// ---PAGE METHODS---
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public RecommendationsPage checkoutBooking(){
		checkoutButton = driver.findElement(checkoutButtonLocator);
		checkoutButton.click();
		
		return this;
	}
	
	public RecommendationsPage declineSeatReservation(){
		declineSeatReservaitionButton = driver.findElement(declineSeatReservaitionButtonLocator);
		declineSeatReservaitionButton.click();
		
		return this;
	}
}
