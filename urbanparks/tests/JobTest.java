package urbanparks.tests;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import urbanparks.model.Job;
import urbanparks.model.ModelConstants;
import urbanparks.model.Volunteer;

public class JobTest {

	/* test jobs with various lengths, name scheme "testJob_{start date}_{end date}"  */
	private static Job testJob_minDaysInFuture_minDaysInFuture;
    private static Job testJob_minDaysInFuture_minDaysInFuturePlusOne;
    private static Job testJob_minDaysInFuture_minDaysInFuturePlusMaxJobLength;
    private static Job testJob_minDaysInFuturePlusOne_minDaysInFuturePlusOne;
	
	
	@BeforeClass
	public static void setupTestJobs() {
		
        LocalDateTime MinDaysInFuture = LocalDateTime.now().plusDays(ModelConstants.MIN_DAYS_BEFORE_SIGNUP);
        LocalDateTime MinDaysInFuturePlusOne = LocalDateTime.now().plusDays(ModelConstants.MIN_DAYS_BEFORE_SIGNUP + 1);
        LocalDateTime minDaysInFuturePlusMaxJobLength = LocalDateTime.now().plusDays(ModelConstants.MIN_DAYS_BEFORE_SIGNUP + ModelConstants.MAX_JOB_LENGTH);

        testJob_minDaysInFuture_minDaysInFuture = new Job("A test job", MinDaysInFuture, MinDaysInFuture,
                "Gasworks", "Seattle");
        testJob_minDaysInFuture_minDaysInFuturePlusOne = new Job("A test job", MinDaysInFuture, MinDaysInFuturePlusOne,
                "Gasworks", "Seattle");
        testJob_minDaysInFuture_minDaysInFuturePlusMaxJobLength = new Job("A test job", MinDaysInFuture, minDaysInFuturePlusMaxJobLength, "Gasworks", "Seattle");
        testJob_minDaysInFuturePlusOne_minDaysInFuturePlusOne = new Job("A test job", MinDaysInFuturePlusOne, MinDaysInFuturePlusOne,
                "Gasworks", "Seattle");
	}
	
	
	@Test
	public void getJobId_FirstJobIdEquals1_True() {
		assertTrue(testJob_minDaysInFuture_minDaysInFuture.getJobId() == 1);
	}
	
	@Test
	public void getJobId_ThirdJobIdEquals3_True() {
		assertTrue(testJob_minDaysInFuture_minDaysInFuturePlusMaxJobLength.getJobId() == 3);
	}
	
	@Test
	public void addVolunteer_AddTwoVolunteers_VolunteersExistInJob() {
        Volunteer volunteer1 = new Volunteer("1", "Volunteer",
                "test1@test.test", "1234567890");

        Volunteer volunteer2 = new Volunteer("2", "Volunteer",
                "test2@test.test", "0987654321");
        
        testJob_minDaysInFuture_minDaysInFuturePlusOne.addVolunteer(volunteer1.getEmail());
        testJob_minDaysInFuture_minDaysInFuturePlusOne.addVolunteer(volunteer2.getEmail());
        assertTrue(testJob_minDaysInFuture_minDaysInFuturePlusOne.getVolunteerCount() == 2);
	}
	
	@Test
	public void removeVolunteer_AddTwoVolunteers_1VolunteerExistsInJob() {
        Volunteer volunteer1 = new Volunteer("1", "Volunteer",
                "test1@test.test", "1234567890");

        Volunteer volunteer2 = new Volunteer("2", "Volunteer",
                "test2@test.test", "0987654321");
        
        testJob_minDaysInFuturePlusOne_minDaysInFuturePlusOne.addVolunteer(volunteer1.getEmail());
        testJob_minDaysInFuturePlusOne_minDaysInFuturePlusOne.addVolunteer(volunteer2.getEmail());
        testJob_minDaysInFuturePlusOne_minDaysInFuturePlusOne.removeVoluneer(volunteer2.getEmail());
        assertTrue(testJob_minDaysInFuturePlusOne_minDaysInFuturePlusOne.getVolunteerCount() == 1);
	}
	
	@Test
	public void doJobsOverlap_NoOverlap_False() {
		assertFalse(testJob_minDaysInFuturePlusOne_minDaysInFuturePlusOne.doJobsOverlap(testJob_minDaysInFuture_minDaysInFuture));
	}
	
	@Test
	public void doJobsOverlap_StartOverlap_True() {
		assertTrue(testJob_minDaysInFuture_minDaysInFuture.doJobsOverlap(testJob_minDaysInFuture_minDaysInFuturePlusOne));
	}
	
	@Test
	public void doJobsOverlap_StartEndOverlap_True() {
		assertTrue(testJob_minDaysInFuture_minDaysInFuturePlusOne.doJobsOverlap(testJob_minDaysInFuturePlusOne_minDaysInFuturePlusOne));
	}
	
	@Test
	public void doJobsOverlap_MiddleOverlap_True() {
		assertTrue(testJob_minDaysInFuture_minDaysInFuturePlusMaxJobLength.doJobsOverlap(testJob_minDaysInFuturePlusOne_minDaysInFuturePlusOne));
	}
	
	@Test
	public void isSignupEarlyEnough_EarlyEnough_True() {
		assertTrue(testJob_minDaysInFuture_minDaysInFuture.isSignupEarlyEnough());
	}
	
	@Test
	public void isSignupEarlyEnough_NotEarlyEnough_False() {
		Job testJob_zeroDaysInFuture_zeroDaysInFuture = new Job("A test job", LocalDateTime.now(), LocalDateTime.now(),
                "Gasworks", "Seattle");
		assertFalse(testJob_zeroDaysInFuture_zeroDaysInFuture.isSignupEarlyEnough());
	}
	
	@Test
	public void isUnvolunteerEarlyEnough_EarlyEnough_True() {
		Job testJob_minVolunteerDaysInFuture_minVolunteerDaysInFuture = new Job("A test job", 
				LocalDateTime.now().plusDays(ModelConstants.MIN_DAYS_BETWEEN_UNVOLUNTEER_AND_JOBSTART),
				LocalDateTime.now().plusDays(ModelConstants.MIN_DAYS_BETWEEN_UNVOLUNTEER_AND_JOBSTART),
                "Gasworks", "Seattle");
		assertTrue(testJob_minVolunteerDaysInFuture_minVolunteerDaysInFuture.isUnvolunteerEarlyEnough());
		
	}
	
	@Test
	public void isUnvolunteerEarlyEnough_NotEarlyEnough_False() {
		Job testJob_zeroDaysInFuture_zeroDaysInFuture = new Job("A test job", LocalDateTime.now(), LocalDateTime.now(),
                "Gasworks", "Seattle");
		assertFalse(testJob_zeroDaysInFuture_zeroDaysInFuture.isUnvolunteerEarlyEnough());
	}
	
	@Test
	public void isUnsubmitEarlyEnough_EarlyEnough_True() {
		Job testJob_minVolunteerDaysInFuture_minVolunteerDaysInFuture = new Job("A test job", 
				LocalDateTime.now().plusDays(ModelConstants.MIN_DAYS_BETWEEN_UNSUBMIT_AND_JOBSTART),
				LocalDateTime.now().plusDays(ModelConstants.MIN_DAYS_BETWEEN_UNSUBMIT_AND_JOBSTART),
                "Gasworks", "Seattle");
		assertTrue(testJob_minVolunteerDaysInFuture_minVolunteerDaysInFuture.isUnsubmitEarlyEnough());
		
	}
	
	@Test
	public void isUnsubmitEarlyEnough_NotEarlyEnough_False() {
		Job testJob_zeroDaysInFuture_zeroDaysInFuture = new Job("A test job", LocalDateTime.now(), LocalDateTime.now(),
                "Gasworks", "Seattle");
		assertFalse(testJob_zeroDaysInFuture_zeroDaysInFuture.isUnsubmitEarlyEnough());
	}
	
	@Test
	public void isBetween2DatesInclusive_MiddleOverlap_True() {
		assertTrue(testJob_minDaysInFuture_minDaysInFuturePlusOne.isBetween2DatesInclusive(
				testJob_minDaysInFuture_minDaysInFuturePlusMaxJobLength.getStartDateTime(),
				testJob_minDaysInFuture_minDaysInFuturePlusMaxJobLength.getEndDateTime()));
	}
	
	@Test
	public void isBetween2DatesInclusive_NoOverlap_False() {
		assertFalse(testJob_minDaysInFuture_minDaysInFuture.isBetween2DatesInclusive(
				testJob_minDaysInFuturePlusOne_minDaysInFuturePlusOne.getStartDateTime(),
				testJob_minDaysInFuturePlusOne_minDaysInFuturePlusOne.getEndDateTime()));
	}
	
	@Test
	public void hasJobEnded_StillPending_False() {
		assertFalse(testJob_minDaysInFuture_minDaysInFuture.hasJobEnded());
	}
	
	@Test
	public void hasJobEnded_EndedNow_True() {
		Job testJob_zeroDaysInFuture_zeroDaysInFuture = new Job("A test job", LocalDateTime.now(), LocalDateTime.now(),
                "Gasworks", "Seattle");
		assertTrue(testJob_zeroDaysInFuture_zeroDaysInFuture.hasJobEnded());
	}
	
	@Test
	public void hasJobEnded_EndedPreviously_True() {
		Job testJob_zeroDaysInFuture_zeroDaysInFuture = new Job("A test job", 
				LocalDateTime.now().minusDays(1), LocalDateTime.now().minusDays(1),
                "Gasworks", "Seattle");
		assertTrue(testJob_zeroDaysInFuture_zeroDaysInFuture.hasJobEnded());
	}
	

}
