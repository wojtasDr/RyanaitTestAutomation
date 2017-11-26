package ryanair.automation.pageobjects;

import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import ryanair.automation.utils.AppConfiguration;
import ryanair.automation.utils.Sleep;

public class PaymentPage {
	private WebDriver driver;
	private Select select;
	private static Logger APP = Logger.getLogger("APP");
	private final String pageUrlSuffix = "booking/payment";
	private String pageCurrentUrl;
	private String pageExpectedUrl;
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// ---WEB ELEMENTS---
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	private WebElement cardTypeInput;
	private WebElement cardNumberInput;
	private WebElement cardExpiryMonthInput;
	private WebElement cardExpiryYearInput;
	private WebElement cardSecurityCodeInput;
	private WebElement cardHolderNameInput;
	private WebElement billingAddressL1;
	private WebElement billingAddressL2;
	private WebElement billingAddressCity;
	private WebElement billingAddressPostalCode;
	private WebElement billingAddressCountry;
	private WebElement policyInput;
	private WebElement policyCheckBox;
	private WebElement payNowButton;
	private List<WebElement> firstNameInputs;
	private List<WebElement> lastNameInputs;
	private List<WebElement> passengerTitleSelects;

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
	By billingAddressL1Locator = By.id("billingAddressAddressLine1");
	By billingAddressL2Locator = By.id("billingAddressAddressLine2");
	By billingAddressCityLocator = By.id("billingAddressCity");
	By billingAddressPostCodeLocator = By.id("billingAddressPostcode");
	By billingAddressCountryLocator = By.id("billingAddressCountry");
	By payNowButtonLocator = By.cssSelector("button[translate$='pay_now']");
	By policyInputLocator = By.name("acceptPolicy");
	By policyCheckBoxLocator = By.xpath("//input[@name='acceptPolicy']/..//core-icon");
	By fillInFormErrorsLocator = By.cssSelector("li>span[translate*='card_number']");
	By selectOptionsLocator = By.xpath("./option[@label]");

	public PaymentPage(WebDriver driver) {
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
		Random rand = new Random();

		passengerTitleSelects = driver.findElements(passangerTitleSelectsLocator);

		while (i < passengerTitleSelects.size()) {
			int optionsNumber = passengerTitleSelects.get(i).findElements(selectOptionsLocator).size();
			optionsNumber = rand.nextInt(optionsNumber) + 1;

			select = new Select(passengerTitleSelects.get(i));
			select.selectByIndex(optionsNumber);
			i++;
		}

		return this;
	}

	public PaymentPage fillInBPassengersDataForm() {
		this.typePassangersTitle();
		this.typePassangersFirstName();

		this.typePassangersLastName();

		return this;
	}

	public PaymentPage selectCardType(String cardType) {
		cardTypeInput = driver.findElement(cardTypeInputLocator);
		cardTypeInput.click();

		cardTypeInput = driver.findElement(cardTypeInputLocator);
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

		cardExpiryMonthInput = driver.findElement(cardExpiryMonthInputLocator);
		select = new Select(cardExpiryMonthInput);
		select.selectByVisibleText(expiryMonth);
		return this;
	}

	public PaymentPage selectExpiryYear(String expiryYear) {
		cardExpiryYearInput = driver.findElement(cardExpiryYearInputLocator);
		cardExpiryYearInput.click();

		cardExpiryYearInput = driver.findElement(cardExpiryYearInputLocator);
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

	public PaymentPage typeBillingAddressL1(String addressLine1) {
		billingAddressL1 = driver.findElement(billingAddressL1Locator);
		billingAddressL1.sendKeys(addressLine1);

		return this;
	}

	public PaymentPage typeBillingAddressL2(String addressLine2) {
		billingAddressL2 = driver.findElement(billingAddressL2Locator);
		billingAddressL2.sendKeys(addressLine2);

		return this;
	}

	public PaymentPage typeBillingAddressCity(String addressCity) {
		billingAddressCity = driver.findElement(billingAddressCityLocator);
		billingAddressCity.sendKeys(addressCity);

		return this;
	}

	public PaymentPage typeBillingAddressPostalCode(String addressPostalCode) {
		billingAddressPostalCode = driver.findElement(billingAddressPostCodeLocator);
		billingAddressPostalCode.sendKeys(addressPostalCode);

		return this;
	}

	public PaymentPage selectCountry(String addressCountry) {
		billingAddressCountry = driver.findElement(billingAddressCountryLocator);
		billingAddressCountry.click();

		billingAddressCountry = driver.findElement(billingAddressCountryLocator);
		select = new Select(billingAddressCountry);
		select.selectByVisibleText(addressCountry);
		return this;
	}

	public PaymentPage fillInBillingAddressForm(String addressLine1, String addressLine2, String addressCity,
			String addressPostalCode, String addressCountry) {
		this.typeBillingAddressL1(addressLine1);
		this.typeBillingAddressL2(addressLine2);
		this.typeBillingAddressCity(addressCity);
		this.typeBillingAddressPostalCode(addressPostalCode);
		this.selectCountry(addressCountry);

		return this;
	}

	public PaymentPage acceptPolicy() {
		policyInput = driver.findElement(policyInputLocator);

		if (policyInput.getAttribute("class").contains("ng-empty")) {
			policyCheckBox = driver.findElement(policyCheckBoxLocator);
			policyCheckBox.click();
		}

		return this;
	}

	public PaymentPage payNowDeclined() {
		payNowButton = driver.findElement(payNowButtonLocator);
		payNowButton.click();

		return this;
	}

	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// ---GETTER METHODS---
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public List<WebElement> getFillInFormErrors() {
		return driver.findElements(fillInFormErrorsLocator);
	}

	public boolean isErrorVisibe(String errorText) {
		boolean isErrorFound = false;

		List<WebElement> errorsList = this.getFillInFormErrors();

		for (WebElement error : errorsList) {
			if (error.getText().trim().equalsIgnoreCase(errorText.trim())) {
				isErrorFound = true;
				break;
			}
		}

		return isErrorFound;
	}
}
