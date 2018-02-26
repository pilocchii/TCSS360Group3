package urbanparks.model;

import java.util.Calendar;
import static urbanparks.model.Constants.*;

	public class DateUtils {

		/**
		 * Checks if 2 Calendar objects are on the same calendar day
		 */
		public static boolean are2DatesOnSameDay(Calendar cal1, Calendar cal2) {
			return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && 
					cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
		}
		
		/**
		 * Calculates the days between a future date and now
		 * 
		 * @param cal The date to use. Can be any date.
		 * @return The number of days between a future date and now, 
		 * 			negative if that date is in the past
		 */
		public static int daysBetweenNowAndDate(Calendar cal) {
			return daysBetween2Dates(Calendar.getInstance(), cal);
		}
		
		/**
		 * Calculates the number of days between 2 dates.
		 * 
		 * @param cal1 The past date.
		 * @param cal2 The future date.
		 * @return The number of days between a the 2 dates, 
		 * 			negative if that future/past dates are switched.
		 */
		public static int daysBetween2Dates(Calendar cal1, Calendar cal2) {
			int yearDiff = DAYS_IN_YEAR * (cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR));
			int daysDiff = cal2.get(Calendar.DAY_OF_YEAR) - cal1.get(Calendar.DAY_OF_YEAR);
			int totalDiff = yearDiff + daysDiff;
			return totalDiff;
		}
}
