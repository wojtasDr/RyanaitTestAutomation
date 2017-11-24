package ryanair.automation.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

	private WebDriver driver;
	private WebDriverWait wait;

	// driver = new ChromeDriver();
	// wait = new WebDriverWait(driver, 10);

	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// ---WEB ELEMENTS---
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	private WebElement loginViewButton;

	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// ---LOCATORS---
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	By loginViewButtonLocator = By.id("myryanair-auth-login");

	public HomePage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, 10);

		// // Check that we're on the right page.
		// if (!driver.getCurrentUrl().trim().endsWith("/tumorboard/#/login")) {
		// throw new IllegalStateException("This is not the login page");
		// }
	}

	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// ---PAGE METHODS---
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public LoginPage chooseLoginMode() {
		loginViewButton = wait.until(ExpectedConditions.presenceOfElementLocated((loginViewButtonLocator)));
		loginViewButton.click();

		return new LoginPage(driver);
	}
}
