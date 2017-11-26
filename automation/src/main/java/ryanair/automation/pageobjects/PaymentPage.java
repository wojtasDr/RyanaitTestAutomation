package ryanair.automation.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import ryanair.automation.utils.AppConfiguration;

public class PaymentPage {
	private WebDriver driver;
	private Select select;
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// ---WEB ELEMENTS---
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	private WebElement cardTypeInput;
	private WebElement cardNumberInput;
	private WebElement cardExpiryMonthInput;
	private WebElement cardExpiryYearInput;
	private WebElement cardSecurityCodeInput;
	private WebElement cardHolderNameInput;
	private List<WebElement> firstNameInputs;
	private List<WebElement> lastNameInputs;
	private List<WebElement> passengerTitleSelects;
	private List<WebElement> passangerTitleOptions;
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// ---LOCATORS---
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	By firstNameInputsLocator = By.cssSelector("input[ng-model='passenger.name.first']");
	By lastNameInputsLocator = By.cssSelector("input[ng-model='passenger.name.last']");
	By passangerTitleSelectsLocator = By.cssSelector("select[ng-model='passenger.name.title']");
	By passangerTitleOptionsLocator = By
			.cssSelector("div.core-select>select[ng-disabled='passenger.locked']>option[label]");
	By cardNumberInputLocator = By.name("cardNumber");
	By cardTypeInputLocator = By.name("cardType");
	By cardExpiryMonthInputLocator = By.name("expiryMonth");
	By cardExpiryYearInputLocator = By.name("expiryYear");
	By cardSecurityCodeInputLocator = By.name("securityCode");
	By cardHolderNameInputLocator = By.name("cardHolderName");

	public PaymentPage(WebDriver driver) {
		this.driver = driver;

		// // Check th9at we're on the right page.
		// if (!driver.getCurrentUrl().trim().endsWith("/tumorboard/#/login")) {
		// throw new IllegalStateException("This is not the login page");
		// }
	}

	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// ---PAGE METHODS---
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public PaymentPage typePassangersFirstName() {
		firstNameInputs = driver.findElements(firstNameInputsLocator);

		firstNameInputs.get(0).clear();
		firstNameInputs.get(0).sendKeys(AppConfiguration.getTestUserFullName());
		firstNameInputs.get(0).sendKeys(Keys.ENTER);

		int i = 1;
		while (i < firstNameInputs.size()) {
			firstNameInputs.get(i).sendKeys("PassangerFirstName" + String.valueOf(Character.toChars(64 + i)));
			i++;
		}

		return this;
	}

	public PaymentPage typePassangersLastName() {
		lastNameInputs = driver.findElements(lastNameInputsLocator);

		lastNameInputs.get(0).clear();
		lastNameInputs.get(0).sendKeys(AppConfiguration.getTestUserFullName());
		lastNameInputs.get(0).sendKeys(Keys.ENTER);

		int i = 1;
		while (i < lastNameInputs.size()) {
			lastNameInputs.get(i).sendKeys("PassangerLastName" + String.valueOf(Character.toChars(64 + i)));
			i++;
		}

		return this;
	}

	public PaymentPage typePassangersTitle() {
		int i = 1;

		passengerTitleSelects = driver.findElements(passangerTitleSelectsLocator);
		passangerTitleOptions = driver.findElements(passangerTitleOptionsLocator);

		while (i < passengerTitleSelects.size()) {
			select = new Select(passengerTitleSelects.get(i));
			select.selectByIndex(i % (passangerTitleOptions.size() / passengerTitleSelects.size()));
			i++;
		}

		return this;
	}

	public PaymentPage selectCardType(String cardType) {
		cardTypeInput = driver.findElement(cardTypeInputLocator);
		cardTypeInput.click();

		select = new Select(cardTypeInput);
		select.selectByVisibleText(cardType);
		return this;
	}

	public PaymentPage setCardNumber(String cardNumber) {
		cardNumberInput = driver.findElement(cardNumberInputLocator);
		cardNumberInput.sendKeys(cardNumber);

		return this;
	}

	public PaymentPage selectExpiryMonth(String expiryMonth) {
		cardExpiryMonthInput = driver.findElement(cardExpiryMonthInputLocator);
		cardExpiryMonthInput.click();

		select = new Select(cardExpiryMonthInput);
		select.selectByVisibleText(expiryMonth);
		return this;
	}

	public PaymentPage selectExpiryYear(String expiryYear) {
		cardExpiryYearInput = driver.findElement(cardExpiryYearInputLocator);
		cardExpiryYearInput.click();

		select = new Select(cardExpiryYearInput);
		select.selectByVisibleText(expiryYear);
		return this;
	}

	public PaymentPage setSecurityCode(String securityCode) {
		cardSecurityCodeInput = driver.findElement(cardSecurityCodeInputLocator);
		cardSecurityCodeInput.sendKeys(securityCode);

		return this;
	}

	public PaymentPage setCardHolderName(String cardHolderName) {
		cardHolderNameInput = driver.findElement(cardHolderNameInputLocator);
		cardHolderNameInput.sendKeys(cardHolderName);

		return this;
	}

	public PaymentPage fillInPaymentCardForm(String cardNumber, String cardType, String expiryMonth, String expiryYear,
			String securityCode, String cardHolderName) {
		this.setCardNumber(cardNumber);
		this.selectCardType(cardType);
		this.selectExpiryMonth(expiryMonth);
		this.selectExpiryYear(expiryYear);
		this.setSecurityCode(securityCode);
		this.setCardHolderName(cardHolderName);

		return this;
	}
}
