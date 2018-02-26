package urbanparks.model;

import java.time.LocalDateTime;
import static urbanparks.model.Constants.*;

	public class DateUtils {

		/**
		 * Checks if 2 Calendar objects are on the same calendar day
		 */
		public static boolean are2DatesOnSameDay(LocalDateTime ld1, LocalDateTime ld2) {
			return ld1.getYear() == ld2.getYear() && 
					ld1.getDayOfYear() == ld2.getDayOfYear();
		}
		
		/**
		 * Calculates the days between a future date and now
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
}
