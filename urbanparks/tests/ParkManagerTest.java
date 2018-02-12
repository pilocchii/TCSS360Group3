package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import model.ParkManager.managerJobDaysException;
import model.ParkManager.managerJobEndDaysException;
import model.ParkManager.mangerPendingJobsException;


class ParkManagerTest {
	private Job myJob;
	private ParkManager myParkManager;


	@Before
	public void setUpParkManagerTest() throws mangerPendingJobsException, 
	managerJobDaysException, managerJobEndDaysException {
		myParkManager = new ParkManager("Aashish Kumar", "aashish1996", "vats@gmail.com", "2525252525");
		Calendar signedUpJobStart = Calendar.getInstance();
		Calendar signedUpJobEnd = Calendar.getInstance();
		signedUpJobStart.set(2018, Calendar.JANUARY, 20, 12, 00);
		signedUpJobEnd.set(2018, Calendar.JANUARY, 21, 14, 00);
		myJob = new Job(1,"This job starts on 1/20/2018.", signedUpJobStart, signedUpJobEnd, 
				"Park Name", "Park Location", 3, 4, 5, 20);
		myParkManager.createdANewJob(myJob);
		
	}
	

	/*Test for The specified job takes one fewer than the maximum number of days*/
	@Test
	public void isJobDays_FewerThanMax_1() {
		Calendar signedUpJobStart = Calendar.getInstance();
		Calendar signedUpJobEnd = Calendar.getInstance();
		signedUpJobStart.set(2018, Calendar.JANUARY, 20, 12, 00);
		signedUpJobEnd.set(2018, Calendar.JANUARY, 21, 14, 00);
		myJob = new Job(2,"This job starts on 1/20/2018.", signedUpJobStart, signedUpJobEnd, 
				"Park Name", "Park Location", 3, 4, 5, 20);
		myParkManager.isMaximumJobDays(myJob);

	}
	
	/*Test for The specified job takes equal to the maximum number of days*/
	@Test
	public void isJobDays_FewerThanMax_3() {
		Calendar signedUpJobStart = Calendar.getInstance();
		Calendar signedUpJobEnd = Calendar.getInstance();
		signedUpJobStart.set(2018, Calendar.JANUARY, 20, 12, 00);
		signedUpJobEnd.set(2018, Calendar.JANUARY, 22, 14, 00);
		myJob = new Job(3,"This job starts on 1/20/2018.", signedUpJobStart, signedUpJobEnd, 
				"Park Name", "Park Location", 3, 4, 5, 20);
		myParkManager.isMaximumJobDays(myJob);

	}
	/*Test for The specified job takes one more than the maximum number of days*/
	@Test
	public void isJobDays_FewerThanMax_8() {
		Calendar signedUpJobStart = Calendar.getInstance();
		Calendar signedUpJobEnd = Calendar.getInstance();
		signedUpJobStart.set(2018, Calendar.JANUARY, 20, 12, 00);
		signedUpJobEnd.set(2018, Calendar.JANUARY, 28, 14, 00);
		myJob = new Job(4,"This job starts on 1/20/2018.", signedUpJobStart, signedUpJobEnd, 
				"Park Name", "Park Location", 3, 4, 5, 20);
		myParkManager.isMaximumJobDays(myJob);

	}
	/*Test for The specified job takes one more than the maximum number of days*/
	@Test
	public void isJob_EndDays_FewerThanMax_95() {
		Calendar signedUpJobStart = Calendar.getInstance();
		Calendar signedUpJobEnd = Calendar.getInstance();
		signedUpJobStart.set(2018, Calendar.JANUARY, 20, 12, 00);
		signedUpJobEnd.set(2018, Calendar.JUNE, 28, 14, 00);
		myJob = new Job(5,"This job starts on 1/20/2018.", signedUpJobStart, signedUpJobEnd, 
				"Park Name", "Park Location", 3, 4, 5, 20);
		myParkManager.isMaximumEndDays(myJob);

	}
	/*Test for The specified job takes one more than the maximum number of days*/
	@Test
	public void isJob_EndDays_FewerThanMax_40() {
		Calendar signedUpJobStart = Calendar.getInstance();
		Calendar signedUpJobEnd = Calendar.getInstance();
		signedUpJobStart.set(2018, Calendar.JANUARY, 20, 12, 00);
		signedUpJobEnd.set(2018, Calendar.MARCH, 28, 14, 00);
		myJob = new Job(6,"This job starts on 1/20/2018.", signedUpJobStart, signedUpJobEnd, 
				"Park Name", "Park Location", 3, 4, 5, 20);
		myParkManager.isMaximumEndDays(myJob);

	}




}




