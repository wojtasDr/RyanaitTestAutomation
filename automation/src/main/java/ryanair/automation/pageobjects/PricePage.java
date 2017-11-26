package ryanair.automation.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ryanair.automation.utils.AppConfiguration;
import ryanair.automation.utils.Sleep;

public class PricePage {
	private WebDriver driver;
	private static Logger APP = Logger.getLogger("APP");
	private final String pageUrlSuffix = "booking/home";
	private String pageCurrentUrl;
	private String pageExpectedUrl;
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
	By selectedPriceButtonLocator = By
			.xpath("//div[@class='flight-header__min-price hide-mobile']/flights-table-price");
	By standardFareBoxLocator = By.xpath("//div[@class='flights-table-fares__head']/span[text()='Standard fare']");
	By plusFareBoxLocator = By.xpath("//div[@class='flights-table-fares__head']/span[text()='Plus']");
	By flexiPlusFareBoxLocator = By.xpath("//div[@class='flights-table-fares__head']/span[text()='Flexi Plus']");
	// By continueButtonLocator = By.cssSelector("button#continue");
	By continueButtonLocator = By.xpath("//button[@ng-if]/span[text()='Continue']");

	public PricePage(WebDriver driver) {
		this.driver = driver;

		Sleep.seconds(2);
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

	public PricePage confirmSelectedPrice() {
		selectedPriceButton = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.visibilityOfElementLocated(selectedPriceButtonLocator));
		selectedPriceButton.click();

		return this;
	}

	public PricePage selectStandardFare() {
		standardFareBox = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.visibilityOfElementLocated(standardFareBoxLocator));
		standardFareBox.click();

		return this;
	}

	public PricePage selectPlusFare() {
		plusFareBox = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.visibilityOfElementLocated(plusFareBoxLocator));
		plusFareBox.click();

		return this;
	}

	public PricePage selectFlexiPlusFare() {
		flexiPlusFareBox = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.visibilityOfElementLocated(flexiPlusFareBoxLocator));
		flexiPlusFareBox.click();

		return this;
	}

	public RecommendationsPage clickContinueButton() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(continueButtonLocator));

		continueButton = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.elementToBeClickable(continueButtonLocator));
		continueButton.click();

		return new RecommendationsPage(driver);
	}

	public RecommendationsPage setFinalPriceAndContinue() {
		this.confirmSelectedPrice();
		Sleep.seconds(1);
		this.selectStandardFare();// TODO It may be parametrized
		Sleep.seconds(3);

		return this.clickContinueButton();
	}
}
