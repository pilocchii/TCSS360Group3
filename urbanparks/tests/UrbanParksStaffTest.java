package urbanparks.tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import urbanparks.model.Constants;
import urbanparks.model.Job;

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
		
		assertEquals(Constants.getMaxPendingJobs(), maxPendingJobs);
		
	}
	
	/**
	 * Testing if the job is NOT between two given dates. 
	 */
	@Test
	public void isBetween2Dates_JobIsNotBetween2Dates_False() {
	
		LocalDateTime dateTime = LocalDateTime.now();
		Job job = new Job("Cleaning XYZ park", dateTime, dateTime, "XYZ", "Seattle", 2, 3, 4, 15);
		
		LocalDateTime startDateTime = LocalDateTime.now();
		startDateTime = startDateTime.minusDays(10);
		LocalDateTime endDateTime = LocalDateTime.now();
		endDateTime = endDateTime.minusDays(5);

		assertFalse(job.isBetween2Dates(startDateTime, endDateTime));
	}


}
