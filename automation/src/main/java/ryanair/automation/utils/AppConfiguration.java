package ryanair.automation.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class AppConfiguration {

	private static Logger APP = Logger.getLogger("APP");
	private final static String CONFIG_FILE = "application.properties";

	private static Properties properties = null;

	private static Properties config() {
		if (properties == null) {
			properties = new Properties();
			URL preferencesUrl = AppConfiguration.class.getClassLoader().getResource(CONFIG_FILE);

			if (preferencesUrl == null) {
				APP.warn("There is no configuration for tested web page.");
			} else {
				InputStream inputStream = null;
				try {
					inputStream = AppConfiguration.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
					properties.load(inputStream);

					for (String key : properties.stringPropertyNames()) {
						String value = properties.getProperty(key);
						APP.trace("Found preference of tested app: {" + key + "} = {" + value + "}");
					}
				} catch (IOException e) {
					APP.debug("Exception while Application Configuration being read: " + e.getMessage());
				} finally {
					IOUtils.closeQuietly(inputStream);
				}
			}
		}

		return properties;
	}

	public static String getTestedAppUrl() {
		return config().getProperty("appUrl");
	}

	public static String getTestUserLogin() {
		return config().getProperty("appUserLogin");
	}

	public static String getTestUserPass() {
		return config().getProperty("appUserPass");
	}

	public static String getProperty(final String propertyName) {
		return config().getProperty(propertyName);
	}

}
