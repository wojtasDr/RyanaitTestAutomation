package ryanair.automation.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {

	private WebDriver driver;

	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// ---WEB ELEMENTS---
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	private WebElement userNameTextField;
	private WebElement passwordTextField;
	private WebElement submitLoginButton;

	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// ---LOCATORS---
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	By userNameTextFieldLocator = By.xpath("//input[contains(@ng-model,'credentials.username')]");
	By passwordTextFieldLocator = By.xpath("//input[contains(@ng-model,'ctrl.password')]");
	By submitLoginButtonLocator = By.xpath("//button[@id='']");

	public LoginPage(WebDriver driver) {
		this.driver = driver;

		// // Check that we're on the right page.
		// if (!driver.getCurrentUrl().trim().endsWith("/tumorboard/#/login")) {
		// throw new IllegalStateException("This is not the login page");
		// }
	}

	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// ---PAGE METHODS---
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public LoginPage typeUserName(String userName) {
		userNameTextField = driver.findElement(userNameTextFieldLocator);
		userNameTextField.sendKeys(userName);

		return this;
	}

	public LoginPage typePassword(String password) {
		passwordTextField = driver.findElement(passwordTextFieldLocator);
		passwordTextField.sendKeys(password);

		return this;
	}

	public HomePage submitLogin() {
		submitLoginButton = driver.findElement(submitLoginButtonLocator);
		submitLoginButton.click();

		return new HomePage(driver);
	}

	public HomePage loginUser(String userName, String password) {
		this.typeUserName(userName);
		this.typePassword(password);

		return submitLogin();
	}
}
