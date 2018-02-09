package tests;
import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import Job;
import Volunteer;


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
	 * 1.a.i
	 * Volunteer has no jobs already signed up for
	 */
	@Test
	public void isSameDay_VolunteerNoJobsYet_false {
		Job condidateJob = new Job(1517800001000, 1517800002000);
		Volunteer volunteerNoJobs = new Volunteer();
		assertFalse(volunteerNoJobs.isSameDay(condidateJob));
	}

	/**
	 * 1.a.ii
	 * Volunteer has jobs already signed up for, 
	 * this job does not extend across any days as jobs already signed up for
	 */
	@Test
	public void isSameDay_VolunteerHasJobsNoOverlap_false {
		Job signedUpJob1 = new Job(1517800001000, 1517800002000);
		Job signedUpJob2 = new Job(1517800003000, 1517800004000);
		Job signedUpJob3 = new Job(1517800005000, 1517800006000);
		Job condidateJob = new Job(1517800007000, 1517800008000);

		Volunteer volunteerWithJobs = new Volunteer();
		volunteerWithJobs.signUpForJob(signedUpJob1);
		volunteerWithJobs.signUpForJob(signedUpJob2);
		volunteerWithJobs.signUpForJob(signedUpJob3);

		assertFalse(volunteerWithJobs.isSameDay(condidateJob));
	}

	/**
	 * 1.a.iii
	 * Volunteer has jobs already signed up for, 
	 * this job starts the same day as the end of some job already signed up for
	 */
	@Test
	public void isSameDay_CurrentJobEndOverlapsNewJobStart_true {
		Job signedUpJob1 = new Job(1517800001000, 1517800002000);
		Job signedUpJob2 = new Job(1517800003000, 1517800004000);
		Job signedUpJob3 = new Job(1517800005000, 1517800006000);
		Job condidateJob = new Job(1517800006003, 1517800007000);

		Volunteer volunteerWithJobs = new Volunteer();
		volunteerWithJobs.signUpForJob(signedUpJob1);
		volunteerWithJobs.signUpForJob(signedUpJob2);
		volunteerWithJobs.signUpForJob(signedUpJob3);

		assertTrue(volunteerWithJobs.isSameDay(condidateJob));
	}

	/**
	 * 1.a.iv
	 * Volunteer has jobs already signed up for, 
	 * this job ends the same day as the start of some job already signed up for
	 */
	@Test
	public void isSameDay_CurrentJobStartOverlapsNewJobEnd_true {
		Job signedUpJob1 = new Job(1517800001000, 1517800002000);
		Job signedUpJob2 = new Job(1517800003000, 1517800004000);
		Job signedUpJob3 = new Job(1517800009000, 1517800010000);
		Job condidateJob = new Job(1517800008000, 1517800009009);

		Volunteer volunteerWithJobs = new Volunteer();
		volunteerWithJobs.signUpForJob(signedUpJob1);
		volunteerWithJobs.signUpForJob(signedUpJob2);
		volunteerWithJobs.signUpForJob(signedUpJob3);

		assertTrue(volunteerWithJobs.isSameDay(condidateJob));
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