package urbanparks.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static urbanparks.model.ModelConstants.*;

/**
 * Class that contains all the methods that is not belonging to the other classes.
 */
public class DateUtils {

	/**
	 * Checks if the given two dates are on same day.
	 * Precondition : The given two dates should not be NULL.
	 * Postcondition: The value returned by the function is true if two dates 
	 * are in the same day; otherwise the value returned by the function is false.
	 * @return true if the two dates are on same day, false otherwise.
	 */
	public static boolean are2DatesOnSameDay(LocalDateTime date1, LocalDateTime date2) {
		return date1.getYear() == date2.getYear() && 
				date1.getDayOfYear() == date2.getDayOfYear();
	}

	/**
	 * Calculates the days between a future date and now.
	 * Precondition : The given date should not be NULL.
	 * Postcondition: The value returned by the function is number of days between a future date and now; 
	 * positive if it is in the future and negative if it is in the past. 
	 * @return The number of days between a future date and now, 
	 * 			negative if that date is in the past
	 */
	public static int daysBetweenNowAndDate(LocalDateTime date) {
		return daysBetween2Dates(LocalDateTime.now(), date);
	}

	/**
	 * Calculates the number of days between two dates.
	 * Precondition : The given date should not be NULL.
	 * Postcondition: The value returned by the function is number of days between two dates;
	 * positive if it is in the future and negative if it is in the past.
	 * @param date1 The past date.
	 * @param date2 The future date.
	 * @return The number of days between a the two dates, 
	 * 			negative if that future/past dates are switched.
	 */
	public static int daysBetween2Dates(LocalDateTime date1, LocalDateTime date2) {
		int yearDiff = DAYS_IN_YEAR * (date2.getYear() - date1.getYear());
		int daysDiff = date2.getDayOfYear() - date1.getDayOfYear();
		int totalDiff = yearDiff + daysDiff;
		return totalDiff;
	}

	/**
	 * String represents the formated date "yyyy-MM-dd HH:mm".
	 * Precondition : The given date should not be NULL.
	 * Postcondition: The value returned by the function is the formated date.
	 * @return the string represents the formated date.
	 */
	public static String formatDateTime(LocalDateTime date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return date.format(formatter);
	}
}
