package urbanparks.tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import urbanparks.model.Constants;
import urbanparks.model.Job;

/**
 * JUnit testing to test changing the max pending job and test if the job is between a start and end date.
 */
public class UrbanParksStaffTest {

	@Before
	public void setUpUrbanParksStaffTest() throws Exception {
		try {
			Constants.loadData();
		} catch (Exception e) {
			Constants.setDefaultMaxPendingJobs();
		}
	}
	
	/**
	 * Testing change the max pending jobs value.
	 * 
	 * @throws FileNotFoundException
	 */
	@Test
	public void setMaxPendingJobs_ChangeMaxPendingJobs_Equal() throws FileNotFoundException {
		
		int maxPendingJobs = (new Random()).nextInt(50);

		Constants.setMaxPendingJobs(maxPendingJobs);
		
		Constants.saveData();
		
		assertEquals(Constants.getMaxPendingJobs(), maxPendingJobs);
		
	}
	
	/**
	 * Testing change the max pending jobs to default value.
	 */
	@Test
	public void setDefaultMaxPendingJobs_ChangeMaxPendingJobsToDefault_NotEqual() {
		
		Constants.setMaxPendingJobs((new Random()).nextInt(50));
		int OldMaxPendingJobs = Constants.getMaxPendingJobs();
	
		Constants.setDefaultMaxPendingJobs();
		
		assertNotEquals(Constants.getMaxPendingJobs(), OldMaxPendingJobs);
		
	}
	
	/**
	 * Testing if the job is between the two given dates. 
	 */
	@Test
	public void isBetween2Dates_JobIsBetween2Dates_True() {
	
		LocalDateTime dateTime = LocalDateTime.now();
		Job job = new Job("Cleaning XYZ park", dateTime, dateTime, "XYZ", "Seattle", 2, 3, 4, 15);
		
		LocalDateTime startDateTime = LocalDateTime.now();
		startDateTime = startDateTime.minusDays(5);
		LocalDateTime endDateTime = LocalDateTime.now();
		endDateTime = endDateTime.plusDays(5);

		assertTrue(job.isBetween2Dates(startDateTime, endDateTime));
	}
	
	/**
	 * Testing if the job is NOT between the two given dates. 
	 * The Job start and end date is before the given two dates.
	 */
	@Test
	public void isBetween2Dates_JobDateIsBeforeGiven2Dates_False() {
	
		LocalDateTime dateTime = LocalDateTime.now();
		Job job = new Job("Cleaning XYZ park", dateTime, dateTime, "XYZ", "Seattle", 2, 3, 4, 15);
		
		LocalDateTime startDateTime = LocalDateTime.now();
		startDateTime = startDateTime.plusDays(5);
		LocalDateTime endDateTime = LocalDateTime.now();
		endDateTime = endDateTime.plusDays(10);

		assertFalse(job.isBetween2Dates(startDateTime, endDateTime));
	}
	
	/**
	 * Testing if the job is NOT between the two given dates. 
	 * The Job start and end date is after the given two dates.
	 */
	@Test
	public void isBetween2Dates_JobDateIsAfterGiven2Dates_False() {
	
		LocalDateTime dateTime = LocalDateTime.now();
		Job job = new Job("Cleaning XYZ park", dateTime, dateTime, "XYZ", "Seattle", 2, 3, 4, 15);
		
		LocalDateTime startDateTime = LocalDateTime.now();
		startDateTime = startDateTime.minusDays(5);
		LocalDateTime endDateTime = LocalDateTime.now();
		endDateTime = endDateTime.plusDays(10);

		assertFalse(job.isBetween2Dates(startDateTime, startDateTime));
	}

}
