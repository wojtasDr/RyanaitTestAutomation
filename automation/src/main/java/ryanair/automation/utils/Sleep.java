package ryanair.automation.utils;

import org.apache.log4j.Logger;

public class Sleep {

	private static Logger APP = Logger.getLogger("APP");

	/**
	 * Sleeps for the specified number of seconds
	 * 
	 * @param timeout
	 *            the length of time to sleep in seconds
	 */
	public static void seconds(final int timeout) {
		APP.trace("Sleeps for " + timeout + " seconds" + timeout);
		long begin, end;

		begin = System.currentTimeMillis();
		do {
			end = System.currentTimeMillis();
		} while ((end - begin) < (timeout * 1000));
	}

	/**
	 * Sleeps for the specified number of milliseconds
	 * 
	 * @param timeout
	 *            the length of time to sleep in milliseconds
	 */
	public static void milliSeconds(final long timeout) {
		APP.trace("Sleeps for " + timeout + " miliseconds" + timeout);
		long begin, end;

		begin = System.currentTimeMillis();
		do {
			end = System.currentTimeMillis();
		} while ((end - begin) < (timeout));
	}
}

