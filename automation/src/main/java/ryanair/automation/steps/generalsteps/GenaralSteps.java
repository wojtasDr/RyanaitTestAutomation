package ryanair.automation.steps.generalsteps;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.apache.log4j.Logger;
import org.assertj.core.util.Preconditions;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

import ryanair.automation.pageobjects.HomePage;
import ryanair.automation.pageobjects.LoginPage;
import ryanair.automation.pageobjects.PaymentPage;
import ryanair.automation.pageobjects.PricePage;
import ryanair.automation.pageobjects.RecommendationsPage;
import ryanair.automation.utils.AppConfiguration;
import ryanair.automation.utils.SetupWebDriver;
import ryanair.automation.utils.Sleep;

public class GenaralSteps {
	private static Logger APP = Logger.getLogger("APP");

	private HomePage homePage;
	private LoginPage loginPage;
	private PricePage pricePage;
	private RecommendationsPage recommendationsPage;
	private PaymentPage paymentPage;
	private SetupWebDriver setupWebDriver = new SetupWebDriver();

	/**
	 * Following Given step sets up chromeDriver and logs user to Ryanair
	 * booking site. Credentials are taken from pom
	 * <app.user.pass></app.user.pass> <app.user.name></app.user.name>
	 * 
	 * When login is done step is waiting untill user name is available compare
	 * its text with text taken from <app.user.fullname></app.user.fullname>
	 * 
	 */
	@Given("User is logged in")
	public void loginWihtCorrectCredentials() {
		APP.info("Login to " + AppConfiguration.getTestedAppUrl() + " with following credentials user: "
				+ AppConfiguration.getTestUserLogin() + ", password: " + AppConfiguration.getTestUserPass());

		homePage = new HomePage(setupWebDriver.setupChromeDriver(AppConfiguration.getChromedriverUrl()));
		loginPage = homePage.chooseLoginMode();
		loginPage.loginUser(AppConfiguration.getTestUserLogin(), AppConfiguration.getTestUserPass());

		homePage.getUserNameText(AppConfiguration.getTestUserFullName());
		APP.info("User " + AppConfiguration.getTestUserFullName() + " was logged in successfully.");
	}

	/**
	 * Following Given step fill in flight details form, confirm chosen price
	 * and checkout booking until payment page. It uses flightParams table:
	 * |departureAirport|destinationAirport|departureDate|adultsNumber|teensNumber|
	 * |Budapest|Barcelona|17-02-2018|2|1| following date format must be used
	 * dd-MM-yyyy
	 * 
	 * Methods use asserts to check if filled fields have correct input text
	 * set.
	 */
	@Given("Passenger makes booking with following data: $flightParams")
	public void fillInBookingForm(ExamplesTable flightParams) {

		Preconditions.checkNotNull(flightParams, "data table can't be null");

		for (Map<String, String> row : flightParams.getRows()) {
			String departureAirport = row.get("departureAirport");
			String destinationAirport = row.get("destinationAirport");
			String departureDate = row.get("departureDate");
			String adultsNumber = row.get("adultsNumber");
			String teensNumber = row.get("teensNumber");

			Preconditions.checkNotNull(departureAirport, "departureAirport can't be null");
			Preconditions.checkNotNull(destinationAirport, "destinationAirport can't be null");
			Preconditions.checkNotNull(departureDate, "departureDate can't be null");
			Preconditions.checkNotNull(adultsNumber, "adultsNumber can't be null");
			Preconditions.checkNotNull(teensNumber, "teensNumber can't be null");

			APP.info("Fill in flight details form with following data:");
			APP.info("- From: " + departureAirport + ", To: " + destinationAirport + ", Flight date: " + departureDate);
			APP.info("- Passengers: Adults - " + adultsNumber + ", Teens - " + teensNumber);
			homePage.fillInFlightDetailsForm(departureAirport, destinationAirport, departureDate, adultsNumber,
					teensNumber);

			APP.info("Check if flight details form was filled in correctly.");
			assertThat(homePage.getDepartureAirportText()).isEqualTo(departureAirport);
			assertThat(homePage.getDestinationAirportText()).isEqualTo(destinationAirport);
			assertThat(homePage.getDepartureDateText()).isEqualTo(departureDate);

			APP.info("Lets GO!");
			pricePage = homePage.clickLetsGoButton();

			APP.info("Set final price and continue.");
			recommendationsPage = pricePage.setFinalPriceAndContinue();

			APP.info("Checkout Booking and go to PaymentPage.");
			recommendationsPage.checkoutBooking();
			paymentPage = recommendationsPage.declineSeatReservation();
		}
	}

	/**
	 * Following When step fills in Passenger Data Form and Credit Card Form
	 */
	@When("Payment is done with card number $cardNumber, card type $cardType, expiry month $expiryMonth, expiry year $expiryYear, security code $securityCode, card owner $cardOwner")
	public void fillInPaymentForm(@Named("cardNumber") String cardNumber, @Named("cardType") String cardType,
			@Named("expiryMonth") String expiryMonth, @Named("expiryYear") String expiryYear,
			@Named("securityCode") String securityCode, @Named("cardOwner") String cardOwner) {

		APP.info("Fill in passengers data form with auto generated data.");
		paymentPage.fillInBPassengersDataForm();

		APP.info("Fill in payment card form:");
		APP.info("- Card number: " + cardNumber + ", card type: " + cardType + ", expiry: " + expiryMonth + "/"
				+ expiryYear + ", security code: " + securityCode + ", owner: " + cardOwner);
		paymentPage.fillInPaymentCardForm(cardNumber, cardType, expiryMonth, expiryYear, securityCode, cardOwner);
	}

	/**
	 * Following When step fills in Billing Address Form
	 */
	@When("Payment is done for billing address $addressLine1, $addressLine2, city $city, postal code $postalCode, country $country")
	public void fillInAddressForm(@Named("addressLine1") String addressLine1,
			@Named("addressLine2") String addressLine2, @Named("city") String city,
			@Named("postalCode") String postalCode, @Named("country") String country) {

		APP.info("Fill in billing address form:");
		APP.info("- Address: " + addressLine1 + ", " + addressLine2 + ", city: " + city + ", postal code: " + postalCode
				+ ", country " + country);
		paymentPage.fillInBillingAddressForm(addressLine1, addressLine2, city, postalCode, country);
	}

	/**
	 * Following Then checks accept policy checkbox and set it if not set. It
	 * also tries to make payment. Payment is declined because of invalid card
	 * number. Step check if invalid card number is visible and payment is
	 * declined.
	 */
	@Then("Payment is declined and following card error text is displayed: $errorText")
	public void checkIfPaymentDeclined(@Named("errorText") String errorText) {
		APP.info("Accept payment if not accepted.");
		paymentPage.acceptPolicy();

		APP.info("Try to make payment.");
		paymentPage.payNowDeclined();

		Sleep.seconds(1);
		APP.info("Check if payment was declined.");
		APP.info("Is -" + errorText + "- error visible: " + paymentPage.isErrorVisibe(errorText));
		assertThat(paymentPage.isErrorVisibe(errorText)).isTrue();

		Sleep.seconds(3);
		APP.info("Quit webdriver.");
		setupWebDriver.quitWebDriver();
	}
}
