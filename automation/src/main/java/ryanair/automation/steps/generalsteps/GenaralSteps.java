package ryanair.automation.steps.generalsteps;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.apache.log4j.Logger;
import org.assertj.core.util.Preconditions;
import org.jbehave.core.annotations.Given;
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

	@Given("User is logged in")
	public void loginWihtCorrectCredentials() {
		homePage = new HomePage(setupWebDriver.setupChromeDriver(AppConfiguration.getChromedriverUrl()));
		loginPage = homePage.chooseLoginMode();
		loginPage.loginUser(AppConfiguration.getTestUserLogin(), AppConfiguration.getTestUserPass());

		// Sleep.seconds(1);

		// TODO add user full name to pom
		assertThat(homePage.getUserNameText()).isEqualTo(AppConfiguration.getTestUserFullName());
	}

	// @Given ("I make a booking from $departureAirport to $destinationAirport
	// on $departureDate for $passengerGroup")
	// public void fillinFlightForm(@Named("departureAirport") String
	// departureAirport,
	// @Named("destinationAirport") String destinationAirport,
	// @Named("departureDate") String departureDate,
	// @Named("passengerGroup") String passengerGroup) {
	//
	// homePage.setOneWayRadioButton();
	// homePage.chooseDepartureAirport(departureAirport);
	// homePage.chooseDestinationAirport(destinationAirport);
	// homePage.setDepartureDate(departureDate);
	// homePage.selectPassengersDropdown(passengerGroup);
	// homePage.clickLetsGoButton();
	//
	// Sleep.seconds(5);
	// }

	@Given("Passenger makes booking: $flightParams")
	public void fillInBookingForm(ExamplesTable flightParams) {

		Preconditions.checkNotNull(flightParams, "data table can't be null");

		for (Map<String, String> row : flightParams.getRows()) {

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
			homePage.setOneWayRadioButton();
			homePage.chooseDepartureAirport(departureAirport);
			homePage.chooseDestinationAirport(destinationAirport);
			homePage.setDepartureDate(departureDate);
			homePage.selectPassengersDropdown(adults, Integer.parseInt(adultsNumber));

			// Check if flight details form was filled in correctly
			assertThat(homePage.getDepartureAirportText()).isEqualTo(departureAirport);
			assertThat(homePage.getDestinationAirportText()).isEqualTo(destinationAirport);
			assertThat(homePage.getDepartureDateText()).isEqualTo(departureDate);
			// System.out.println("DepartureAirport: " +
			// homePage.getDepartureAirportText());
			// System.out.println("DestinationAirport: " +
			// homePage.getDestinationAirportText());
			// System.out.println("Dep date: " +
			// homePage.getDepartureDateText());

			// Got to PricePage
			pricePage = homePage.clickLetsGoButton();

			// Sleep.seconds(10);
			// Confirm Price
			pricePage.confirmSelectedPrice();
			pricePage.selectStandardFare();// It may be parametrized

			// Go to RecomendationsPage
			recommendationsPage = pricePage.clickContinueButton();

			//
			recommendationsPage.checkoutBooking();
			paymentPage = recommendationsPage.declineSeatReservation();

			// Sleep.seconds(5);
		}
	}

	@When("LCJ test when")
	public void fillInPaymentForm() {
		paymentPage.typePassangersFirstName();
		paymentPage.typePassangersLastName();
		paymentPage.typePassangersTitle();
		
		paymentPage.fillInPaymentCardForm("5188608900375138", "MasterCard Debit", "8", "2019", "123", "Adam Nowak");
		
		Sleep.seconds(5);
	}

	@Then("LCJ test then")
	public void checkBeginSequence() {
		APP.info("THEN");
		System.out.println("THEN step");
		setupWebDriver.quitWebDriver();
	}
}
