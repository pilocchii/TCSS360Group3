package tests;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import model.Job;
import model.JobCollection;
import model.Volunteer;
import model.Volunteer.alreadySignedUpException;
import model.Volunteer.jobSignupTooLateException;
import model.Volunteer.volunteerJobOverlapException;


/**
 * JUnit testing to test sign up for job using all possible acceptance test.
 */
public class VolunteerTest {

	private Volunteer volunteerNoJobs;
	private Volunteer volunteerWithJobs;
	private JobCollection jobcollection;

	@Before
	public final void setUpTestVolunteers() 
			throws volunteerJobOverlapException, jobSignupTooLateException, alreadySignedUpException, NoSuchAlgorithmException {

		jobcollection = new JobCollection();
		
		// create a volunteer with no signed up jobs
		volunteerNoJobs = new Volunteer("First", "Last", "email@email.com", "(123)-456-7890");
		
		//create a volunteer with 1 signed up job
		Calendar signedUpJobStart = Calendar.getInstance();
		Calendar signedUpJobEnd = Calendar.getInstance();
		signedUpJobStart.set(2018, Calendar.JANUARY, 20, 12, 00);
		signedUpJobEnd.set(2018, Calendar.JANUARY, 21, 14, 00);
		Job signedUpJob = new Job("This job starts on 1/20/2018.", signedUpJobStart, signedUpJobEnd, 
				"Park Name", "Park Location", 3, 4, 5, 20);
		signedUpJob.setJobId(1234);
		jobcollection.addJob(signedUpJob);
		volunteerWithJobs = new Volunteer("First", "Last", "email@email.com", "(123)-456-7890");
		volunteerWithJobs.signUpForJob(signedUpJob);
	}

	/**
	 * Volunteer has no jobs already signed up for
	 */
	@Test
	public void isSameDay_VolunteerNoJobsYet_false() {
		Calendar candidateStart = Calendar.getInstance();
		Calendar candidateEnd = Calendar.getInstance();
		candidateStart.set(2018, Calendar.JANUARY, 30, 9, 30);
		candidateEnd.set(2018, Calendar.JANUARY, 30, 11, 30);
		Job candidateJob = new Job("This job is on 1/30/2018.", candidateStart, candidateEnd, 
				"Park Name", "Park Location", 3, 4, 5, 20);
		candidateJob.setJobId(5678);
		jobcollection.addJob(candidateJob);
		
		assertFalse(volunteerNoJobs.doesNewJobOverlap(candidateJob));
	}

	/**
	 * Volunteer has jobs already signed up for, 
	 * this job does not extend across any days as jobs already signed up for
	 */
	@Test
	public void isSameDay_VolunteerHasJobsNoOverlap_false() 
			throws volunteerJobOverlapException, jobSignupTooLateException {
				
		Calendar candidateStart = Calendar.getInstance();
		Calendar candidateEnd = Calendar.getInstance();
		candidateStart.set(2018, Calendar.JANUARY, 30, 9, 30);
		candidateEnd.set(2018, Calendar.JANUARY, 30, 11, 30);
		Job candidateJob = new Job("This job is on 1/30/2018.", candidateStart, candidateEnd, 
				"Park Name", "Park Location", 3, 4, 5, 20);
		candidateJob.setJobId(5678);
		jobcollection.addJob(candidateJob);

		assertFalse(volunteerWithJobs.doesNewJobOverlap(candidateJob));
	}

	/**
	 * Volunteer has jobs already signed up for, 
	 * this job starts the same day as the end of some job already signed up for
	 */
	@Test
	public void isSameDay_CurrentJobEndIsNewJobStart_true() 
			throws volunteerJobOverlapException, jobSignupTooLateException {
		
		Calendar candidateStart = Calendar.getInstance();
		Calendar candidateEnd = Calendar.getInstance();
		candidateStart.set(2018, Calendar.JANUARY, 21, 9, 30);
		candidateEnd.set(2018, Calendar.JANUARY, 21, 10, 30);
		Job candidateJob = new Job("This job is on 1/21/2018.", candidateStart, candidateEnd, 
				"Park Name", "Park Location", 3, 4, 5, 20);
		candidateJob.setJobId(5678);
		jobcollection.addJob(candidateJob);

		assertTrue(volunteerWithJobs.doesNewJobOverlap(candidateJob));
	}

	/**
	 * Volunteer has jobs already signed up for, 
	 * this job ends the same day as the start of some job already signed up for
	 */
	@Test
	public void isSameDay_CurrentJobStartIsNewJobEnd_true() 
			throws volunteerJobOverlapException, jobSignupTooLateException {
		
		Calendar candidateStart = Calendar.getInstance();
		Calendar candidateEnd = Calendar.getInstance();
		candidateStart.set(2018, Calendar.JANUARY, 19, 9, 30);
		candidateEnd.set(2018, Calendar.JANUARY, 20, 18, 15);
		Job candidateJob = new Job("This job is on 1/19/2018.", candidateStart, candidateEnd, 
				"Park Name", "Park Location", 3, 4, 5, 20);
		candidateJob.setJobId(5678);
		jobcollection.addJob(candidateJob);

		assertTrue(volunteerWithJobs.doesNewJobOverlap(candidateJob));
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

		Job condidateJob = new Job("Cleaning the xyz park", startDateTime, endDateTime, 
				"Park Name", "Park Location", 3, 1, 10, 12);

		assertTrue(Volunteer.isSignupEarlyEnough(condidateJob));
	}

	/**
	 * Testing: the volunteer can sign for a job that begins exactly 
	 * 			the minimum number of calendar days from the current date.
	 */
	@Test
	public void signUpForJob_ExactlyMinimumDays_ShouldReturnTrue() {

		Calendar startDateTime = Calendar.getInstance();
		Calendar endDateTime = Calendar.getInstance();

		startDateTime.add(Calendar.DAY_OF_MONTH, 2);
		endDateTime.add(Calendar.DAY_OF_MONTH, 2);

		Job condidateJob = new Job("Cleaning the xyz park", startDateTime, endDateTime, 
				"Park Name", "Park Location", 3, 1, 10, 12);

		assertTrue(Volunteer.isSignupEarlyEnough(condidateJob));
	}

	/**
	 * Testing: the volunteer cannot sign up for a job that begins less than 
	 * 			the minimum number of calendar days from the current date.
	 */
	@Test
	public void signUpForJob_BeginsLessMinimumDays_ShouldReturnFalse() {

		Calendar startDateTime = Calendar.getInstance();
		Calendar endDateTime = Calendar.getInstance();

		Job condidateJob = new Job("Cleaning the xyz park", startDateTime, endDateTime, 
				"Park Name", "Park Location", 3, 1, 10, 12);

		assertFalse(Volunteer.isSignupEarlyEnough(condidateJob));
	}

}