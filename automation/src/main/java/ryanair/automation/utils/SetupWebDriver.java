package ryanair.automation.utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SetupWebDriver {
	private WebDriver driver;

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public WebDriver setupChromeDriver(String chromeDriverLocation) {
		System.setProperty("webdriver.chrome.driver", chromeDriverLocation);

		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(AppConfiguration.getTestedAppUrl());
		
		this.setDriver(driver);
		
		return driver;
	}
	
	public void quitWebDriver(){
		driver.quit();
		driver = null;
	}
}
