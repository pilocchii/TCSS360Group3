package urbanparks.tests;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import urbanparks.model.Constants;
import urbanparks.model.DateUtils;
import urbanparks.model.Job;
import urbanparks.model.JobCollection;
import urbanparks.model.ParkManager;


class ParkManagerTest {
	private Job myJob;
	private ParkManager myParkManager;
	private JobCollection jobcollection;


	@Before
	public void setUpParkManagerTest() {
		
		jobcollection = new JobCollection();
		myParkManager = new ParkManager("Aashish Kumar", "aashish1996", "vats@gmail.com", "2525252525");
		LocalDateTime signedUpJobStart = LocalDateTime.of(2018, 1, 20, 12, 00); //Jan 20, 2018 12:00
		LocalDateTime signedUpJobEnd = LocalDateTime.of(2018, 1, 21, 14, 00); //Jan 21, 2018 14:00
		myJob = new Job("This job starts on 1/20/2018.", signedUpJobStart, signedUpJobEnd, 
				"Park Name", "Park Location", 3, 4, 5, 20);
		myJob.setJobId(1234);
		jobcollection.addJob(myJob);
		
		myParkManager.createNewJob(myJob, jobcollection);
		
	}
	

	/*Test for The specified job takes one fewer than the maximum number of days*/
	@Test
	public void daysBetween2Dates_FewerThanMax_1() {
		LocalDateTime signedUpJobStart = LocalDateTime.of(2018, 1, 21, 12, 00);
		LocalDateTime signedUpJobEnd = LocalDateTime.of(2018, 1, 22, 12, 00);
		myJob = new Job("This job starts on 1/21/2018.", signedUpJobStart, signedUpJobEnd, 
				"Park Name", "Park Location", 3, 4, 5, 20);
		myJob.setJobId(234);
		assertTrue(DateUtils.daysBetween2Dates(signedUpJobStart, signedUpJobEnd) < Constants.MAX_JOB_LENGTH);
		
	}
	
	/*Test for The specified job takes equal to the maximum number of days*/
	@Test
	public void daysBetween2Dates_EqualToMax_True() {
		LocalDateTime signedUpJobStart = LocalDateTime.of(2018, 1, 2, 12, 00);
		LocalDateTime signedUpJobEnd = LocalDateTime.of(2018, 1, (2 + Constants.MAX_JOB_LENGTH), 12, 00);
		myJob = new Job("This job starts on 1/2/2018.", signedUpJobStart, signedUpJobEnd, 
				"Park Name", "Park Location", 3, 4, 5, 20);
		assertEquals(DateUtils.daysBetween2Dates(signedUpJobStart, signedUpJobEnd), Constants.MAX_JOB_LENGTH);

	}
	/*Test for The specified job takes one more than the maximum number of days*/
	@Test
	public void daysBetween2Dates_greaterThanMaxValue_True() {
		LocalDateTime signedUpJobStart = LocalDateTime.of(2018, 1, 12, 12, 00);
		LocalDateTime signedUpJobEnd = LocalDateTime.of(2018, 1, 16, 14, 00);
		myJob = new Job("This job starts on 1/12/2018.", signedUpJobStart, signedUpJobEnd, 
				"Park Name", "Park Location", 3, 4, 5, 20);
		assertTrue(DateUtils.daysBetween2Dates(signedUpJobStart, signedUpJobEnd) > Constants.MAX_JOB_LENGTH);

	}
	
	
	/**
	 * Testing if the job start date and time is not in the future.
	 */
	@Test
	public void isInFuture_JobIsInPast_False() {
		LocalDateTime dateTime = LocalDateTime.now();
		dateTime = dateTime.minusDays(1);
		Job job = new Job("Cleaning XYZ park", dateTime, dateTime, "XYZ", "Seattle", 2, 3, 4, 15);
		assertFalse(job.isInFuture());
	}
	
	/**
	 * Testing if the park manager did not create the given job.
	 */
	@Test
	public void isCreator_parkMangerIsNotCreatorOfJob_False() {
		
		ParkManager parkManager = new ParkManager("Abdul", "Tarabi", "a@a.com", "2063728223");
		
		Job job = new Job("Cleaning XYZ park", LocalDateTime.now(), LocalDateTime.now(), "XYZ", "Seattle", 2, 3, 4, 15);
		job.setJobId(123654);

		parkManager.createNewJob(job, new JobCollection());
		
		Job newJob = new Job("Cleaning XYZ park", LocalDateTime.now(), LocalDateTime.now(), "XYZ", "Seattle", 2, 3, 4, 15);
		newJob.setJobId(8888);


		assertFalse(parkManager.isCreator(newJob));
		
	}



}




