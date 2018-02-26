package urbanparks.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import urbanparks.model.Job;
import urbanparks.model.ParkManager;
import urbanparks.model.ParkManager.jobStartTooLongFromNowException;
import urbanparks.model.ParkManager.jobTooLongException;
import urbanparks.model.ParkManager.managerJobDaysException;
import urbanparks.model.ParkManager.managerJobEndDaysException;
import urbanparks.model.ParkManager.mangerPendingJobsException;
import urbanparks.model.ParkManager.numJobsAtMaximumException;


class ParkManagerTest {
	private Job myJob;
	private ParkManager myParkManager;
	private JobCollection jobcollection;


	@Before
	public void setUpParkManagerTest() 
			throws numJobsAtMaximumException, jobTooLongException, jobStartTooLongFromNowException, NoSuchAlgorithmException {
		
		jobcollection = new JobCollection();
		myParkManager = new ParkManager("Aashish Kumar", "aashish1996", "vats@gmail.com", "2525252525");
		Calendar signedUpJobStart = Calendar.getInstance();
		Calendar signedUpJobEnd = Calendar.getInstance();
		signedUpJobStart.set(2018, Calendar.JANUARY, 20, 12, 00);
		signedUpJobEnd.set(2018, Calendar.JANUARY, 21, 14, 00);
		myJob = new Job("This job starts on 1/20/2018.", signedUpJobStart, signedUpJobEnd, 
				"Park Name", "Park Location", 3, 4, 5, 20);
		myJob.setJobId(1234);
		jobcollection.addJob(myJob);
		
		myParkManager.createNewJob(myJob);
		
	}
	

	/*Test for The specified job takes one fewer than the maximum number of days*/
	@Test
	public void isJobDays_FewerThanMax_1() {
		Calendar signedUpJobStart = Calendar.getInstance();
		Calendar signedUpJobEnd = Calendar.getInstance();
		signedUpJobStart.set(2018, Calendar.JANUARY, 21, 12, 00);
		signedUpJobEnd.set(2018, Calendar.JANUARY, 22, 14, 00);
		myJob = new Job("This job starts on 1/21/2018.", signedUpJobStart, signedUpJobEnd, 
				"Park Name", "Park Location", 3, 4, 5, 20);
		myJob.setJobId(234);
		assertTrue(myParkManager.isJobTooLong(myJob));
		
	}
	
	/*Test for The specified job takes equal to the maximum number of days*/
	@Test
	public void isJobDays_FewerThanMax_3() {
		Calendar signedUpJobStart = Calendar.getInstance();
		Calendar signedUpJobEnd = Calendar.getInstance();
		signedUpJobStart.set(2018, Calendar.JANUARY, 2, 12, 00);
		signedUpJobEnd.set(2018, Calendar.JANUARY, 4, 14, 00);
		myJob = new Job("This job starts on 1/2/2018.", signedUpJobStart, signedUpJobEnd, 
				"Park Name", "Park Location", 3, 4, 5, 20);
		assertTrue(myParkManager.isJobTooLong(myJob));

	}
	/*Test for The specified job takes one more than the maximum number of days*/
	@Test
	public void isJobDays_FewerThanMax_8() {
		Calendar signedUpJobStart = Calendar.getInstance();
		Calendar signedUpJobEnd = Calendar.getInstance();
		signedUpJobStart.set(2018, Calendar.JANUARY, 12, 12, 00);
		signedUpJobEnd.set(2018, Calendar.JANUARY, 13, 14, 00);
		myJob = new Job("This job starts on 1/12/2018.", signedUpJobStart, signedUpJobEnd, 
				"Park Name", "Park Location", 3, 4, 5, 20);
		assertFalse(myParkManager.isJobTooLong(myJob));

	}
	/*Test for The specified job takes one more than the maximum number of days*/
	@Test
	public void isJob_EndDays_FewerThanMax_95() {
		Calendar signedUpJobStart = Calendar.getInstance();
		Calendar signedUpJobEnd = Calendar.getInstance();
		signedUpJobStart.set(2018, Calendar.JANUARY, 26, 12, 00);
		signedUpJobEnd.set(2018, Calendar.JUNE, 27, 14, 00);
		myJob = new Job("This job starts on 1/26/2018.", signedUpJobStart, signedUpJobEnd, 
				"Park Name", "Park Location", 3, 4, 5, 20);
		assertFalse(myParkManager.doesJobStartTooLongFromNow(myJob));

	}
	/*Test for The specified job takes one more than the maximum number of days*/
	@Test
	public void isJob_EndDays_FewerThanMax_40() {
		Calendar signedUpJobStart = Calendar.getInstance();
		Calendar signedUpJobEnd = Calendar.getInstance();
		signedUpJobStart.set(2018, Calendar.JANUARY, 17, 12, 00);
		signedUpJobEnd.set(2018, Calendar.MARCH, 18, 14, 00);
		myJob = new Job("This job starts on 1/17/2018.", signedUpJobStart, signedUpJobEnd, 
				"Park Name", "Park Location", 3, 4, 5, 20);
		assertTrue(myParkManager.doesJobStartTooLongFromNow(myJob));

	}
	/*Test for The specified job takes one more than the maximum number of days*/
	@Test
	public void are_Jobs_Pending() throws NoSuchAlgorithmException {
		Calendar signedUpJobStart = Calendar.getInstance();
		Calendar signedUpJobEnd = Calendar.getInstance();
		signedUpJobStart.set(2018, Calendar.MARCH, 7, 12, 00);
		signedUpJobEnd.set(2018, Calendar.MARCH, 8, 14, 00);
		myJob = new Job("This job starts on 3/7/2018.", signedUpJobStart, signedUpJobEnd, 
				"Park Name", "Park Location", 3, 4, 5, 20);
		assertTrue(myParkManager.isNumJobsAtMaximum());

	}	/*Test for The specified job takes one more than the maximum number of days*/
	@Test
	public void are_Jobs_Pending1() throws NoSuchAlgorithmException {
		Calendar signedUpJobStart = Calendar.getInstance();
		Calendar signedUpJobEnd = Calendar.getInstance();
		signedUpJobStart.set(2018, Calendar.MARCH, 19, 12, 00);
		signedUpJobEnd.set(2018, Calendar.MARCH, 20, 14, 00);
		myJob = new Job("This job starts on 3/19/2018.", signedUpJobStart, signedUpJobEnd, 
				"Park Name", "Park Location", 3, 4, 5, 20);
		assertTrue(myParkManager.isNumJobsAtMaximum());

	}



}




