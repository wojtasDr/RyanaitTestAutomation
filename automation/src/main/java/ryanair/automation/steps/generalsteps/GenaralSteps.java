package ryanair.automation.steps.generalsteps;

import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import ryanair.automation.utils.AppConfiguration;

public class GenaralSteps {
	private static Logger APP = Logger.getLogger("APP");

	private WebDriver driver;
	private WebDriverWait wait;
	
	@Given("LCJ test given")
	public void sendIplStubMockRequests() throws InterruptedException {
		System.out.println("GIVEN step"+ System.getProperty("current.date"));
		APP.info("GIVEN");
		System.out.println("GIVEN step");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\pulawskw\\Desktop\\RyanairProject\\chromedriver.exe");
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 10);
		System.out.println("URL: "+ AppConfiguration.getTestedAppUrl());
		driver.get(AppConfiguration.getTestedAppUrl());
		Thread.sleep(5000);
	}

	@When("LCJ test when")
	public void isfileIsCreated() {
		APP.info("WHEN");
		System.out.println("WHEN step");
		System.out.println("Login: " + AppConfiguration.getTestUserLogin());
		System.out.println("Pass: " + AppConfiguration.getTestUserPass());
	}

	@Then("LCJ test then")
	public void checkBeginSequence() {
		APP.info("THEN");
		System.out.println("THEN step");
		driver.quit();
		driver = null;
	}
}
