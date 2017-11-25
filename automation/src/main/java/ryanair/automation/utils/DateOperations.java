package ryanair.automation.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateOperations {

	
	public static long monthsBetweenDates(String endDateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		LocalDate currentDate = LocalDate.parse(LocalDate.now().format(formatter), formatter);

		LocalDate endDate = LocalDate.parse(endDateString, formatter);

		return ChronoUnit.MONTHS.between(currentDate, endDate);
	}
}
