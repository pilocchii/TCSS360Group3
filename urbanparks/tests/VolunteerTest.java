package urbanparks.tests;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import urbanparks.model.Constants;
import urbanparks.model.Job;
import urbanparks.model.JobCollection;
import urbanparks.model.Volunteer;


/**
 * JUnit testing to test sign up for job using all possible acceptance test.
 */
public class VolunteerTest {

	private Volunteer volunteerNoJobs;
	private Volunteer volunteerWithJobs;
	private JobCollection jobCollection;

	/* test jobs with various lengths, name scheme "testJob_{start date}_{end date}"  */
	private Job testJob_minDaysInFuture_minDaysInFuture;
    private Job testJob_minDaysInFuture_minDaysInFuturePlusOne;
    private Job testJob_minDaysInFuture_minDaysInFuturePlusMaxJobLength;
    private Job testJob_minDaysInFuturePlusOne_minDaysInFuturePlusOne;

	@Before
	public final void setUpTestVolunteers() {

        volunteerNoJobs = new Volunteer("NoJobs", "Volunteer",
                "test1@test.test", "1234567890");

        volunteerWithJobs = new Volunteer("HasJobs", "Volunteer",
                "test2@test.test", "0987654321");

        LocalDateTime MinDaysInFuture = LocalDateTime.now().plusDays(Constants.MIN_DAYS_BEFORE_SIGNUP);
        LocalDateTime MinDaysInFuturePlusOne = LocalDateTime.now().plusDays(Constants.MIN_DAYS_BEFORE_SIGNUP + 1);
        LocalDateTime minDaysInFuturePlusMaxJobLength = LocalDateTime.now().plusDays(Constants.MIN_DAYS_BEFORE_SIGNUP + Constants.MAX_JOB_LENGTH);

        jobCollection = new JobCollection();

        testJob_minDaysInFuture_minDaysInFuture = new Job("A test job", MinDaysInFuture, MinDaysInFuture,
                "Gasworks", "Seattle");
        testJob_minDaysInFuture_minDaysInFuturePlusOne = new Job("A test job", MinDaysInFuture, MinDaysInFuturePlusOne,
                "Gasworks", "Seattle");
        testJob_minDaysInFuture_minDaysInFuturePlusMaxJobLength = new Job("A test job", MinDaysInFuture, minDaysInFuturePlusMaxJobLength, "Gasworks", "Seattle");
        testJob_minDaysInFuturePlusOne_minDaysInFuturePlusOne = new Job("A test job", MinDaysInFuturePlusOne, MinDaysInFuturePlusOne,
                "Gasworks", "Seattle");
	}


    /**
     * Volunteer has no jobs already signed up for.
     */
	@Test
	public void signUpForJob_NoCurrentJobs_true() {
	    assertTrue(volunteerNoJobs.signUpForJob(testJob_minDaysInFuture_minDaysInFuture));
    }


    /**
     * Volunteer has jobs already signed up for,
     * this job does not extend across any days as jobs already signed up for.
     */
    @Test
    public void signUpForJob_HasCurrentJobs_true() {
        volunteerNoJobs.signUpForJob(testJob_minDaysInFuture_minDaysInFuture);
        assertTrue(volunteerNoJobs.signUpForJob(testJob_minDaysInFuturePlusOne_minDaysInFuturePlusOne));
    }


	/**
	 * Volunteer has jobs already signed up for, 
	 * this job starts the same day as the end of some job already signed up for.
	 */
	@Test
	public void doesJobOverlap_CurrentJobEndIsNewJobStart_true() {
        jobCollection.addJob(testJob_minDaysInFuture_minDaysInFuturePlusOne);
        volunteerNoJobs.signUpForJob(testJob_minDaysInFuture_minDaysInFuturePlusOne);
        assertTrue(volunteerNoJobs.doesJobOverlap(testJob_minDaysInFuturePlusOne_minDaysInFuturePlusOne,
                jobCollection));
    }


	/**
	 * Volunteer has jobs already signed up for, 
	 * this job ends the same day as the start of some job already signed up for.
	 */
	@Test
	public void doesJobOverlap_CurrentJobStartIsNewJobEnd_true() {
        jobCollection.addJob(testJob_minDaysInFuturePlusOne_minDaysInFuturePlusOne);
        volunteerNoJobs.signUpForJob(testJob_minDaysInFuturePlusOne_minDaysInFuturePlusOne);
        assertTrue(volunteerNoJobs.signUpForJob(testJob_minDaysInFuture_minDaysInFuturePlusOne));
    }


    /**
     * Volunteer has a job already signed up for, and the job is successfully removed.
     */
    @Test
    public void unVolunteerFromJob_JobIsInAssociatedJobs_JobIsRemoved() {
        jobCollection.addJob(testJob_minDaysInFuture_minDaysInFuture);
        volunteerNoJobs.signUpForJob(testJob_minDaysInFuture_minDaysInFuture);
        volunteerNoJobs.unVolunteerFromJob(testJob_minDaysInFuture_minDaysInFuture);
        assertFalse(volunteerNoJobs.getSignedUpJobs(jobCollection).contains(testJob_minDaysInFuture_minDaysInFuture));
    }


    /**
     * Volunteer tries to unvolunteer from a job that doesn't exist.
     * ArrayList remains unchanged, as per ArrayList.remove()
     */
    @Test
    public void unVolunteerFromJob_JobIsNotInAssociatedJobs_ListUnchanged() {
        volunteerNoJobs.unVolunteerFromJob(testJob_minDaysInFuture_minDaysInFuture);
        assertFalse(volunteerNoJobs.getSignedUpJobs(jobCollection).contains(testJob_minDaysInFuture_minDaysInFuture));
    }


    /**
     * Gets a list of jobs containing one existing job from a volunteer.
     */
    @Test
    public void getSignedUpJobs_ListHasJobs_GetJobList() {
        jobCollection.addJob(testJob_minDaysInFuture_minDaysInFuture);
        volunteerNoJobs.signUpForJob(testJob_minDaysInFuture_minDaysInFuture);
        ArrayList<Job> jobsList = volunteerNoJobs.getSignedUpJobs(jobCollection);
        assertTrue(jobsList.contains(testJob_minDaysInFuture_minDaysInFuture));
    }


    /**
     * Gets a list with no existing jobs from a volunteer.
     */
    @Test
    public void getSignedUpJobs_ListHasNoJobs_GetEmptyJobList() {
        ArrayList<Job> jobsList = volunteerNoJobs.getSignedUpJobs(jobCollection);
        assertEquals(jobsList.size(), 0);
    }

}