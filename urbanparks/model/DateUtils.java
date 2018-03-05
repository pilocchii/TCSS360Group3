package urbanparks.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static urbanparks.model.Constants.*;

	public class DateUtils {

		/**
		 * Checks if 2 Calendar objects are on the same calendar day.
		 * Pre: Dates are non-null and have a non-null year and dayofyear field.
		 * 
		 * @return true if the two dates are on the same day in the same year, false otherwise.
		 */
		public static boolean are2DatesOnSameDay(LocalDateTime ld1, LocalDateTime ld2) {
			return ld1.getYear() == ld2.getYear() && 
					ld1.getDayOfYear() == ld2.getDayOfYear();
		}
		
		/**
		 * Calculates the days between a future date and now
		 * Pre: Date is non-null
		 * 
		 * @param cal The date to use. Can be any date.
		 * @return The number of days between a future date and now, 
		 * 			negative if that date is in the past
		 */
		public static int daysBetweenNowAndDate(LocalDateTime ld1) {
			return daysBetween2Dates(LocalDateTime.now(), ld1);
		}
		
		/**
		 * Calculates the number of days between 2 dates.
		 * Pre: The dates are non-null
		 * 
		 * @param cal1 The past date.
		 * @param cal2 The future date.
		 * @return The number of days between a the 2 dates, 
		 * 			negative if that future/past dates are switched.
		 */
		public static int daysBetween2Dates(LocalDateTime ld1, LocalDateTime ld2) {
			int yearDiff = DAYS_IN_YEAR * (ld2.getYear() - ld1.getYear());
			int daysDiff = ld2.getDayOfYear() - ld1.getDayOfYear();
			int totalDiff = yearDiff + daysDiff;
			return totalDiff;
		}
		
		/**
		 * Returns a string representation of the date in the following format:
		 * yyyy-MM-dd HH:mm
		 * Pre: Date is non-null
		 * @param ldt a date representing the time to format in a string
		 * @return String in the format yyyy-MM-dd HH:mm equal to the date
		 */
		public static String formatDateTime(LocalDateTime ldt) {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	        return ldt.format(formatter);
		}
}
