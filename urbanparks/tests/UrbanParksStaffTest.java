package urbanparks.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;

import urbanparks.model.Job;

/**
 * JUnit testing to test changing the max pending job and test if the job is between a start and end date.
 */
public class UrbanParksStaffTest {
	
	/**
	 * Testing if the job is between the two given dates. 
	 */
	@Test
	public void isBetween2Dates_JobIsBetween2Dates_True() {
	
		LocalDateTime dateTime = LocalDateTime.now();
		Job job = new Job("Cleaning XYZ park", dateTime, dateTime, "XYZ", "Seattle");
		
		LocalDateTime startDateTime = LocalDateTime.now();
		startDateTime = startDateTime.minusDays(5);
		LocalDateTime endDateTime = LocalDateTime.now();
		endDateTime = endDateTime.plusDays(5);

		assertTrue(job.isBetween2DatesInclusive(startDateTime, endDateTime));
	}
	
	/**
	 * Testing if the job is NOT between the two given dates. 
	 * The Job start and end date is before the given two dates.
	 */
	@Test
	public void isBetween2Dates_JobDateIsBeforeGiven2Dates_False() {
	
		LocalDateTime dateTime = LocalDateTime.now();
		Job job = new Job("Cleaning XYZ park", dateTime, dateTime, "XYZ", "Seattle");
		
		LocalDateTime startDateTime = LocalDateTime.now();
		startDateTime = startDateTime.plusDays(5);
		LocalDateTime endDateTime = LocalDateTime.now();
		endDateTime = endDateTime.plusDays(10);

		assertFalse(job.isBetween2DatesInclusive(startDateTime, endDateTime));
	}
	
	/**
	 * Testing if the job is NOT between the two given dates. 
	 * The Job start and end date is after the given two dates.
	 */
	@Test
	public void isBetween2Dates_JobDateIsAfterGiven2Dates_False() {
	
		LocalDateTime dateTime = LocalDateTime.now();
		Job job = new Job("Cleaning XYZ park", dateTime, dateTime, "XYZ", "Seattle");
		
		LocalDateTime startDateTime = LocalDateTime.now();
		startDateTime = startDateTime.minusDays(5);
		LocalDateTime endDateTime = LocalDateTime.now();
		endDateTime = endDateTime.plusDays(10);

		assertFalse(job.isBetween2DatesInclusive(startDateTime, startDateTime));
	}

}
