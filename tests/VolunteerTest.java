package tests;
import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import classes.Job;
import classes.Volunteer;


/**
 * JUnit testing to test sign up for job using all possible acceptance test.
 * 
 * @author Abderisaq Tarabi
 * @version 02/01/2018
 *
 */
public class VolunteerTest {
	
	private Volunteer volunteer;

	@Before
	public final void setup() {
		
		volunteer = new Volunteer("Abdul", "Tarabi", "tarabi@uw.edu", "206000000");

		Calendar startDateTime = Calendar.getInstance();
		Calendar endDateTime = Calendar.getInstance();
		
		startDateTime.set(2018, 2, 5, 6, 0, 0);
		endDateTime.set(2018, 2, 5, 17, 0, 0);
		
		Job newJob = new Job("Tutoring student at middle school", startDateTime, endDateTime, "jeff@uw.edu", 5, 6, 2, 11);		
		
		volunteer.signUpForJob(newJob);

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

		Job condidateJob = new Job("Cleaning the xyz park", startDateTime, endDateTime, "jeff@uw.edu", 3, 1, 10, 12);

		assertTrue(volunteer.isMinimumDays(condidateJob));
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

		Job condidateJob = new Job("Cleaning the xyz park", startDateTime, endDateTime, "jeff@uw.edu", 3, 1, 10, 12);

		assertTrue(volunteer.isMinimumDays(condidateJob));
	}
	
	/**
	 * Testing: the volunteer cannot sign up for a job that begins less than 
	 * 			the minimum number of calendar days from the current date.
	 */
	@Test
	public void signUpForJob_BeginsLessMinimumDays_ShouldReturnFalse() {
		
		Calendar startDateTime = Calendar.getInstance();
		Calendar endDateTime = Calendar.getInstance();
		
		Job condidateJob = new Job("Cleaning the xyz park", startDateTime, endDateTime, "jeff@uw.edu", 3, 1, 10, 12);
				
		assertFalse(volunteer.isMinimumDays(condidateJob));
	}
	
}
