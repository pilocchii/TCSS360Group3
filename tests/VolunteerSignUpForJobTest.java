package tests;
import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import classes.Job;
import classes.ParkManager;
import classes.Volunteer;


/**
 * JUnit testing to test sign up for job using all possible acceptance test.
 * 
 * @author Abderisaq Tarabi
 * @version 02/01/2018
 *
 */
public class VolunteerSignUpForJobTest {
	
	/** The volunteer. */
	private Volunteer volunteer;
	
	/** The park manager. */
	private ParkManager parkManager;

	@Before
	public final void setup() {
		
		volunteer = new Volunteer("Abdul", "Tarabi", "tarabi@uw.edu", "206000000");
		parkManager = new ParkManager("Jeff", "Sue", "jeff@uw.edu", "425000000", "Highland Park", 98118);

		Calendar startDateTime = Calendar.getInstance();
		Calendar endDateTime = Calendar.getInstance();
		
		// Job # 1
		startDateTime.set(2018, 2, 5, 6, 0, 0);
		endDateTime.set(2018, 2, 5, 17, 0, 0);
		
		volunteer.signUpForJob(new Job("Tutoring student at middle school", startDateTime, endDateTime, parkManager, 5, 6, 2, 11));

	}
	
	/**
	 * Testing: the volunteer can sign for a job.
	 */
	@Test
	public void signUpForJob_FirstJobToAccept_ShouldReturnTrue() {

		Calendar startDateTime = Calendar.getInstance();
		Calendar endDateTime = Calendar.getInstance();

		startDateTime.set(2018, 5, 1, 6, 0, 0);
		endDateTime.set(2018, 5, 1, 17, 0, 0);

		Job newJob = new Job("Tutoring student at middle school", startDateTime, endDateTime, parkManager, 5, 6, 2, 11);

		// Return the result.
		assertTrue(volunteer.signUpForJob(newJob));
	}
	
	/**
	 * Testing: the volunteer can not sign up for more that one job that extends across any particular day.
	 */
	@Test
	public void signUpForJob_TwoJobsSameDay_ShouldReturnFalse() {
		
		Calendar startDateTime = Calendar.getInstance();
		Calendar endDateTime = Calendar.getInstance();
		
		startDateTime.set(2018, 2, 5, 6, 0, 0);
		endDateTime.set(2018, 2, 5, 17, 0, 0);
		Job newJob = new Job("Cleaning the xyz park", startDateTime, endDateTime, parkManager, 3, 1, 10, 12);
		
		// Return the result.
		assertFalse(volunteer.signUpForJob(newJob));
	}
	
	/**
	 * Testing: the volunteer cannot sign up for a job that start at the end of an accepted job in the same day.
	 */
	@Test
	public void signUpForJob_StartsSameDayAsEndJobSignedUpFor_ShouldReturnFalse() {
		
		Calendar startDateTime = Calendar.getInstance();
		Calendar endDateTime = Calendar.getInstance();
		
		startDateTime.set(2018, 2, 5, 6, 0, 0);
		endDateTime.set(2018, 2, 6, 17, 0, 0);
		
		Job newJob = new Job("Cleaning the xyz park", startDateTime, endDateTime, parkManager, 3, 1, 10, 12);
		
		assertFalse(volunteer.signUpForJob(newJob));
	}
	
	/**
	 * Testing: the volunteer cannot sign up for a job that end at the start of an accepted job in the same day.
	 */
	@Test
	public void signUpForJob_EndsSameDayAsStartJobSignedUpFor_ShouldReturnFalse() {
		
		Calendar startDateTime = Calendar.getInstance();
		Calendar endDateTime = Calendar.getInstance();
		
		startDateTime.set(2018, 2, 4, 17, 0, 0);
		endDateTime.set(2018, 2, 5, 6, 0, 0);
		
		Job newJob = new Job("Cleaning the xyz park", startDateTime, endDateTime, parkManager, 3, 1, 10, 12);
		
		assertFalse(volunteer.signUpForJob(newJob));
	}

	/**
	 * Testing: the volunteer can sign for a job that begins much more than 
	 * 			the minimum number of calendar days from the current.
	 */
	@Test
	public void signUpForJob_MoreThanMinimumDays_ShouldReturnTrue() {

		Calendar startDateTime = Calendar.getInstance();
		Calendar endDateTime = Calendar.getInstance();

		startDateTime.add(Calendar.DAY_OF_MONTH, 10);
		endDateTime.add(Calendar.DAY_OF_MONTH, 10);

		Job newJob = new Job("Cleaning the xyz park", startDateTime, endDateTime, parkManager, 3, 1, 10, 12);

		// Return the result.
		assertTrue(volunteer.signUpForJob(newJob));
	}
	
	/**
	 * Testing: the volunteer can sign for a job that begins exactly 
	 * 			the minimum number of calendar days from the current date..
	 */
	@Test
	public void signUpForJob_ExactlyMinimumDays_ShouldReturnTrue() {

		Calendar startDateTime = Calendar.getInstance();
		Calendar endDateTime = Calendar.getInstance();

		startDateTime.add(Calendar.DAY_OF_MONTH, 2);
		endDateTime.add(Calendar.DAY_OF_MONTH, 2);

		Job newJob = new Job("Cleaning the xyz park", startDateTime, endDateTime, parkManager, 3, 1, 10, 12);

		// Return the result.
		assertTrue(volunteer.signUpForJob(newJob));
	}
	
	/**
	 * Testing: the volunteer cannot sign up for a job that begins less than 
	 * 			the minimum number of calendar days from the current date.
	 */
	@Test
	public void signUpForJob_BeginsLessMinimumDays_ShouldReturnFalse() {
		
		Calendar startDateTime = Calendar.getInstance();
		Calendar endDateTime = Calendar.getInstance();
		
		Job newJob = new Job("Cleaning the xyz park", startDateTime, endDateTime, parkManager, 3, 1, 10, 12);
				
		assertFalse(volunteer.signUpForJob(newJob));
	}
	
}
