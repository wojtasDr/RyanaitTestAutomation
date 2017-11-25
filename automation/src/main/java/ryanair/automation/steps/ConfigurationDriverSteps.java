package ryanair.automation.steps;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.ScenarioType;
import org.jbehave.web.selenium.WebDriverProvider;

public class ConfigurationDriverSteps {
	private static Logger APP = Logger.getLogger("APP");
	
	public ConfigurationDriverSteps(WebDriverProvider driverProvider) {
	}

	@BeforeStories
	public void beforeAllStories() throws IOException, InterruptedException {
		System.out.println("before all stories");
	}

	@BeforeStory
	public void beforeStory() throws IOException, InterruptedException {
		APP.info("");
		APP.info("***-----------***");
		APP.info("STORY WAS STARTED");
		APP.info("***-----------***");
		APP.info("");
	}

	@BeforeScenario()
	public void beforeScenario() {
		APP.info("");
		APP.info("____________________");
		APP.info("SCENARIO WAS STARTED");
		APP.info("____________________");
		APP.info("");
	}

	@BeforeScenario(uponType = ScenarioType.EXAMPLE)
	public void beforeEachExampleScenario() {
		System.out.println("before example scenario");
	}

	@AfterScenario
	public void afterScenario() {
		APP.info("");
		APP.info("_____________________");
		APP.info("SCENARIO WAS FINISHED");
		APP.info("_____________________");
		APP.info("");
	}

	@AfterScenario(uponType = ScenarioType.EXAMPLE)
	public void afterEachExampleScenario() {
		System.out.println("after example scenario");
	}

	@AfterStory
	public void afterStory() throws IOException, InterruptedException {
		APP.info("");
		APP.info("***------------***");
		APP.info("STORY WAS FINISHED");
		APP.info("***------------***");
		APP.info("");
	}

	@AfterStories()
	public void afterAllStories() {
		System.out.println("after all scenarios");
	}
}