package ryanair.automation.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ryanair.automation.utils.AppConfiguration;
import ryanair.automation.utils.Sleep;

public class RecommendationsPage {
	private WebDriver driver;
	private static Logger APP = Logger.getLogger("APP");
	private final String pageUrlSuffix = "booking/extras";
	private String pageCurrentUrl;
	private String pageExpectedUrl;
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

		Sleep.seconds(1);
		pageCurrentUrl = driver.getCurrentUrl().trim();
		pageExpectedUrl = AppConfiguration.getTestedAppUrl() + pageUrlSuffix;
		// Check that we're on the right page.
		if (!pageCurrentUrl.equals(pageExpectedUrl)) {
			APP.error("This is NOT the " + this.getClass().getSimpleName() + " page: " + pageCurrentUrl);
		} else {
			APP.info("This is " + this.getClass().getSimpleName() + " page: " + pageCurrentUrl);
		}
	}

	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// ---PAGE METHODS---
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public RecommendationsPage checkoutBooking() {
		checkoutButton = driver.findElement(checkoutButtonLocator);
		checkoutButton.click();

		return this;
	}

	public PaymentPage declineSeatReservation() {
		declineSeatReservaitionButton = driver.findElement(declineSeatReservaitionButtonLocator);
		declineSeatReservaitionButton.click();

		return new PaymentPage(driver);
	}
}
