package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import model.Job;
import model.ParkManager;

/* Test cases for No job can be specified that takes 
 * more than the maximum number of days, default of 3
 */
public class ParkManagerTests {
	private Job myJob;
	private ParkManager myParkManager;


	@Before
	public void setUp() throws Exception {
		myParkManager = new ParkManager("Aashish Kumar", "aashish1996");
	}

	/*Test for if some one put 0 in job days*/
	@Test
	public void isJobDays_FewerThanMax_0() {
		myJob = new Job();
		myJob.setJobDays(0);
		assertFalse("My Job is taking more 0 or less than 0 days; should return false",
				myParkManager.isJobDaysValid(myJob));

	}

	/*Test for The specified job takes one fewer than the maximum number of days*/
	@Test
	public void isJobDays_FewerThanMax_1() {
		myJob = new Job();
		myJob.setJobDays(1);
		assertTrue("My Job is not taking more than 3 days; should return true",
				myParkManager.isJobDaysValid(myJob));

	}

	/*Test for The specified job takes one fewer than the maximum number of days*/
	@Test
	public void isJobDays_FewerThanMax_2() {
		myJob = new Job();
		myJob.setJobDays(2);
		assertTrue("My Job is not taking more than 3 days; should return true",
				myParkManager.isJobDaysValid(myJob));

	}

	/*Test for The specified job takes the maximum number of days */
	@Test 
	public void isJobDays_FewerThanMax_3() {
		myJob = new Job();
		myJob.setJobDays(3);
		assertTrue("My Job is not taking more than 3 days; should return true",
				myParkManager.isJobDaysValid(myJob));

	}

	/*Test for The specified job takes one more than the maximum number of days*/
	@Test
	public void isJobDays_FewerThanMax_4() {
		myJob = new Job();
		myJob.setJobDays(4);
		assertFalse("My Job is taking more than 3 days; should return false",
				myParkManager.isJobDaysValid(myJob));

	}

	/*Test for The specified job takes one more than the maximum number of days*/
	@Test
	public void isJobDays_FewerThanMax_8() {
		myJob = new Job();
		myJob.setJobDays(8);
		assertFalse("My Job is taking more than 3 days; should return false",
				myParkManager.isJobDaysValid(myJob));

	}

}

