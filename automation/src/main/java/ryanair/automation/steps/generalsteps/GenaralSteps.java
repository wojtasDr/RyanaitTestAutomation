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
		homePage = new HomePage(setupWebDriver.setupChromeDriver(AppConfiguration.getChromedriverUrl()));
		loginPage = homePage.chooseLoginMode();
		loginPage.loginUser(AppConfiguration.getTestUserLogin(), AppConfiguration.getTestUserPass());

		homePage.getUserNameText(AppConfiguration.getTestUserFullName());
	}

	/**
	 * Following Given step fill in flight details form, confirm chosen price
	 * and checkout booking until payment page. It uses flightParams table:
	 * |departureAirport|destinationAirport|departureDate|adults|adultsNumber|
	 * example data |Budapest|Barcelona|17-02-2018|Adults|3| following date
	 * format must be used dd-MM-yyyy
	 * 
	 * Methods uses asserts to check if filled fields have correct input text
	 * set.
	 */
	@Given("Passenger makes booking with following data: $flightParams")
	public void fillInBookingForm(ExamplesTable flightParams) {

		Preconditions.checkNotNull(flightParams, "data table can't be null");

		for (Map<String, String> row : flightParams.getRows()) {
			// Initialize and check params
			String departureAirport = row.get("departureAirport");
			String destinationAirport = row.get("destinationAirport");
			String departureDate = row.get("departureDate");
			String adults = row.get("adults");
			String adultsNumber = row.get("adultsNumber");

			Preconditions.checkNotNull(departureAirport, "departureAirport can't be null");
			Preconditions.checkNotNull(destinationAirport, "destinationAirport can't be null");
			Preconditions.checkNotNull(departureDate, "departureDate can't be null");
			Preconditions.checkNotNull(adults, "adults can't be null");
			Preconditions.checkNotNull(adultsNumber, "adultsNumber can't be null");

			// Fill in flight details form
			homePage.fillInFlightDetailsForm(departureAirport, destinationAirport, departureDate, adults, adultsNumber);

			// Check if flight details form was filled in correctly
			assertThat(homePage.getDepartureAirportText()).isEqualTo(departureAirport);
			assertThat(homePage.getDestinationAirportText()).isEqualTo(destinationAirport);
			assertThat(homePage.getDepartureDateText()).isEqualTo(departureDate);

			// Got to PricePage
			pricePage = homePage.clickLetsGoButton();

			// Confirm Price
			pricePage.confirmSelectedPrice();
			Sleep.seconds(1);
			pricePage.selectStandardFare();// It may be parametrized

			// Go to RecomendationsPage
			Sleep.seconds(3);
			recommendationsPage = pricePage.clickContinueButton();

			// Checkout Booking and go to PaymentPage
			recommendationsPage.checkoutBooking();
			paymentPage = recommendationsPage.declineSeatReservation();
		}
	}

	/**
	 * Following When step fills in Passanger Data Form and Billing Address Form
	 */
	@When("Payment is done with card number $cardNumber")
	public void fillInPaymentForm(@Named("cardNumber") String cardNumber) {
		// Fill in passengers data form
		paymentPage.fillInBPassengersDataForm();
		// Fill in payment card data form
		paymentPage.fillInPaymentCardForm(cardNumber, "MasterCard Debit", "8", "2019", "123", "Adam Nowak");
		// Fill in billing address form
		paymentPage.fillInBillingAddressForm("Warszawa", "Al. Jerozolimskie", "Warszawa", "00-000", "Poland");
	}

	/**
	 * Following Then checks accept policy checkbox and set it if not set. It
	 * also tries to make payment. Payment is declined because of invalid card
	 * number. Step check if invalid card number is visible and payment is
	 * declined.
	 */
	@Then("Payment is declined and following card error text is displayed: $errorText")
	public void checkIfPaymentDeclined(@Named("errorText") String errorText) {
		//accept payment if not accepted
		paymentPage.acceptPolicy();
		//try to make payment
		paymentPage.payNowDeclined();

		Sleep.seconds(1);
		//Check if payment was declined and form field error is displayed (e.g. card number is invalid")
		APP.info("Is " + errorText + "error visible: " + paymentPage.isErrorVisibe(errorText));
		assertThat(paymentPage.isErrorVisibe(errorText)).isTrue();

		Sleep.seconds(5);
		//quit webdriver
		setupWebDriver.quitWebDriver();
	}
}
