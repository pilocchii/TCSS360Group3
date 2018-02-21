package urbanparks.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import urbanparks.model.Job;
import urbanparks.model.ParkManager;
import urbanparks.model.ParkManager.jobStartTooLongFromNowException;
import urbanparks.model.ParkManager.jobTooLongException;
//import urbanparks.model.ParkManager.managerJobDaysException;
//import urbanparks.model.ParkManager.managerJobEndDaysException;
//import urbanparks.model.ParkManager.mangerPendingJobsException;
import urbanparks.model.ParkManager.numJobsAtMaximumException;


public class ParkManagerTest {
	private Job myJob;
	private ParkManager myParkManager;


	@Before
	public void setUpParkManagerTest() 
			throws NoSuchAlgorithmException, numJobsAtMaximumException, jobTooLongException, jobStartTooLongFromNowException {
		myParkManager = new ParkManager("Aashish Kumar", "aashish1996", "vats@gmail.com", "2525252525");
		Calendar signedUpJobStart = Calendar.getInstance();
		Calendar signedUpJobEnd = Calendar.getInstance();
		signedUpJobStart.set(2018, Calendar.JANUARY, 20, 12, 00);
		signedUpJobEnd.set(2018, Calendar.JANUARY, 21, 14, 00);
		myJob = new Job("This job starts on 1/20/2018.", signedUpJobStart, signedUpJobEnd,
				"Park Name", "Park Location", 3, 4, 5, 20);
		myParkManager.createNewJob(myJob);
		
	}
	

	/*Test for The specified job takes one fewer than the maximum number of days*/
	@Test
	public void isJobDays_FewerThanMax_1() {
		Calendar signedUpJobStart = Calendar.getInstance();
		Calendar signedUpJobEnd = Calendar.getInstance();
		signedUpJobStart.set(2018, Calendar.JANUARY, 20, 12, 00);
		signedUpJobEnd.set(2018, Calendar.JANUARY, 21, 14, 00);
		myJob = new Job("This job starts on 1/20/2018.", signedUpJobStart, signedUpJobEnd, 
				"Park Name", "Park Location", 3, 4, 5, 20);
		assertFalse(myParkManager.isJobTooLong(myJob));
		
	}
	
	/*Test for The specified job takes equal to the maximum number of days*/
	@Test
	public void isJobDays_FewerThanMax_3() {
		Calendar signedUpJobStart = Calendar.getInstance();
		Calendar signedUpJobEnd = Calendar.getInstance();
		signedUpJobStart.set(2018, Calendar.JANUARY, 20, 12, 00);
		signedUpJobEnd.set(2018, Calendar.JANUARY, 22, 14, 00);
		myJob = new Job("This job starts on 1/20/2018.", signedUpJobStart, signedUpJobEnd, 
				"Park Name", "Park Location", 3, 4, 5, 20);
		assertFalse(myParkManager.isJobTooLong(myJob));

	}
	/*Test for The specified job takes one more than the maximum number of days*/
	@Test (expected = jobTooLongException.class)
	public void isJobDays_FewerThanMax_8() {
		Calendar signedUpJobStart = Calendar.getInstance();
		Calendar signedUpJobEnd = Calendar.getInstance();
		signedUpJobStart.set(2018, Calendar.JANUARY, 20, 12, 00);
		signedUpJobEnd.set(2018, Calendar.JANUARY, 28, 14, 00);
		myJob = new Job("This job starts on 1/20/2018.", signedUpJobStart, signedUpJobEnd, 
				"Park Name", "Park Location", 3, 4, 5, 20);
		myParkManager.isJobTooLong(myJob);

	}
	/*Test for The specified job takes one more than the maximum number of days*/
	@Test (expected = jobStartTooLongFromNowException.class)
	public void isJob_EndDays_FewerThanMax_95() {
		Calendar signedUpJobStart = Calendar.getInstance();
		Calendar signedUpJobEnd = Calendar.getInstance();
		signedUpJobStart.set(2018, Calendar.JANUARY, 20, 12, 00);
		signedUpJobEnd.set(2018, Calendar.JUNE, 28, 14, 00);
		myJob = new Job("This job starts on 1/20/2018.", signedUpJobStart, signedUpJobEnd, 
				"Park Name", "Park Location", 3, 4, 5, 20);
		myParkManager.doesJobStartTooLongFromNow(myJob);

	}
	/*Test for The specified job takes one more than the maximum number of days*/
	@Test (expected = jobStartTooLongFromNowException.class)
	public void isJob_EndDays_FewerThanMax_40() {
		Calendar signedUpJobStart = Calendar.getInstance();
		Calendar signedUpJobEnd = Calendar.getInstance();
		signedUpJobStart.set(2018, Calendar.JANUARY, 20, 12, 00);
		signedUpJobEnd.set(2018, Calendar.MARCH, 28, 14, 00);
		myJob = new Job("This job starts on 1/20/2018.", signedUpJobStart, signedUpJobEnd, 
				"Park Name", "Park Location", 3, 4, 5, 20);
		myParkManager.doesJobStartTooLongFromNow(myJob);

	}




}




