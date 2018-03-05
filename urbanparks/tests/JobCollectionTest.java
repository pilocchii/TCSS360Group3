package urbanparks.tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import urbanparks.model.*;

import static org.junit.Assert.*;

/**
 * JUnit testing to test if the job collection is at maximum capacity or not.
 */
public class JobCollectionTest {

    JobCollection jobCollection;
    LocalDateTime dateTimeToday;
    LocalDateTime dateTimeTomorrow;
    LocalDateTime pastDateTime;
    LocalDateTime minUnvolunteerDaysDateTime;
    LocalDateTime minUnsubmitDaysDateTime;
    Job validJob;
    Job validJobTwo;
    Job validJob_today_tomorrow;
    Job validJob_tomorrow_tomorrow;
    Job pastJob;
    Job validJob_minUnvolunteerDays;
    Job validJob_minUnsubmitDays;
    Volunteer testVolunteer;
    ParkManager testParkManager;

    @Before
    public void setUpJobCollectionTest() {
        jobCollection = new JobCollection();
        dateTimeToday = LocalDateTime.now();
        dateTimeTomorrow = LocalDateTime.now().plusDays(1);
        pastDateTime = LocalDateTime.now().minusDays(1);
        minUnvolunteerDaysDateTime = LocalDateTime.now().plusDays(ModelConstants.MIN_DAYS_BETWEEN_UNVOLUNTEER_AND_JOBSTART);
        minUnsubmitDaysDateTime = LocalDateTime.now().plusDays(ModelConstants.MIN_DAYS_BETWEEN_UNSUBMIT_AND_JOBSTART);
        validJob = new Job("job", dateTimeToday, dateTimeToday, "Park", "Seattle");
        validJobTwo = new Job("job", dateTimeToday, dateTimeToday, "Park", "Seattle");
        validJob_today_tomorrow = new Job("job", dateTimeToday, dateTimeTomorrow, "Park", "Seattle");
        validJob_minUnvolunteerDays = new Job("job", minUnvolunteerDaysDateTime, minUnvolunteerDaysDateTime, "Park", "Seattle");
        validJob_minUnsubmitDays = new Job("job", minUnsubmitDaysDateTime, minUnsubmitDaysDateTime, "Park", "Seattle");
        validJob_tomorrow_tomorrow = new Job("job", dateTimeTomorrow, dateTimeTomorrow, "Park", "Seattle");

        pastJob = new Job("job", pastDateTime, pastDateTime, "Park", "Seattle");

        testVolunteer = new Volunteer("Test", "Volunteer", "test@test.test", "1234567890");
        testParkManager = new ParkManager("Test", "Volunteer", "test@test.test", "1234567890");

    }

    /**
     * Testing if the job collection is NOT at maximum capacity.
     */
    @Test
    public void isNumJobsAtMaximum_JobCollectionIsEmpty_False() {
        assertFalse(jobCollection.isNumJobsAtMaximum());
    }

    /**
     * Testing if the job collection IS at maximum capacity.
     */
    @Test
    public void isNumJobsAtMaximum_JobCollectionIsFull_True() {
        for (int i = 0; i < ModelConstants.getMaxPendingJobs(); i++) {
            jobCollection.addJob(new Job("job", dateTimeTomorrow, dateTimeTomorrow, "Park", "Seattle"));
        }
        assertTrue(jobCollection.isNumJobsAtMaximum());
    }


    /**
     * Testing if the ID is generated predictably.
     * This number can change at any time, so can only be tested relatively.
     */
    @Test
    public void generateNewJobID_JobCollectionIsEmpty_PlusOne() {
        int currentID = JobCollection.generateNewJobID();
        int nextID = JobCollection.generateNewJobID();
        assertEquals(nextID, currentID + 1);
    }


    /**
     * Tests if a job is correctly added to the collection
     */
    @Test
    public void addJob_AddNewJobToCollection_JobIsAdded() {
        jobCollection.addJob(validJob);
        assertEquals(jobCollection.findJob(validJob.getJobId()), validJob);
    }


    /**
     * Tests if adding duplicate job increases the number of pending jobs
     */
    @Test
    public void addJob_AddExistingJobToCollection_JobCountSame() {
        jobCollection.addJob(validJob);
        int numPending = jobCollection.getPendingJobsCount();
        jobCollection.addJob(validJob);
        int numPendingAfterDuplicate = jobCollection.getPendingJobsCount();
        assertEquals(numPending, numPendingAfterDuplicate);
    }


    /**
     * Tests if adding a second job increases the number of pending jobs
     */
    @Test
    public void addJob_AddTwoJobsToCollection_JobCountPlusOne() {
        jobCollection.addJob(validJob_tomorrow_tomorrow);
        int numPending = jobCollection.getPendingJobsCount();
        jobCollection.addJob(validJob_today_tomorrow);
        int numPendingAfterDuplicate = jobCollection.getPendingJobsCount();
        assertEquals(numPending, numPendingAfterDuplicate - 1);
    }


    /**
     * Testing if the expected job is found by ID
     */
    @Test
    public void findJob_NewJobInCollection_JobIsFound() {
        long jobID = validJob.getJobId();
        jobCollection.addJob(validJob);
        assertEquals(jobCollection.findJob(jobID), validJob);
    }


    /**
     * Testing if the job differs
     */
    @Test
    public void findJob_TwoNewJobsInCollection_JobIsDifferent() {
        long firstJobID = validJob.getJobId();
        long secondJobID = validJobTwo.getJobId();
        jobCollection.addJob(validJob);
        jobCollection.addJob(validJobTwo);
        assertNotEquals(jobCollection.findJob(firstJobID), jobCollection.findJob(secondJobID));
    }


    /**
     * Testing if the ID does not exist in the collection
     */
    @Test
    public void findJob_JobDoesNotExist_null() {
        int testID = JobCollection.generateNewJobID() + 1;
        assertEquals(jobCollection.findJob(testID), null);
    }


    /**
     * Testing if the pending jobs count increases when a new pending job is added.
     * Note: There is no way to remove a job from the collection.
     */
    @Test
    public void getPendingJobsCount_NewJobIncreasesCount_true() {
        int currentCount = jobCollection.getPendingJobsCount();
        jobCollection.addJob(validJob_tomorrow_tomorrow);
        int newCount = jobCollection.getPendingJobsCount();
        assertEquals(newCount, currentCount + 1);
    }


    /**
     * Tests if the expected job is available for the volunteer to sign up
     */
    @Test
    public void getAvailableForSignup_ExpectedJobIsAvailable_true() {
        jobCollection.addJob(validJob);
        assertTrue(jobCollection.getAvailableForSignup(testVolunteer).contains(validJob));
    }


    /**
     * Tests if the expected job is not available for the volunteer to sign up
     */
    @Test
    public void getAvailableForSignup_ExpectedJobIsAvailable_false() {
        assertFalse(jobCollection.getAvailableForSignup(testVolunteer).contains(validJob));
    }


    /**
     * Tests if a past job is available for signup
     */
    @Test
    public void getAvailableForSignup_JobIsInPast_false() {
        jobCollection.addJob(pastJob);
        ArrayList<Job> availJobs = jobCollection.getAvailableForSignup(testVolunteer);
        assertFalse(availJobs.get(availJobs.indexOf(pastJob)).getIsAvailable());
    }


    /**
     * Tests if a job with overlap is available for signup
     */
    @Test
    public void getAvailableForSignup_JobOverlaps_false() {
        jobCollection.addJob(validJob);
        testVolunteer.signUpForJob(validJob);
        jobCollection.addJob(validJob_today_tomorrow);
        ArrayList<Job> availJobs = jobCollection.getAvailableForSignup(testVolunteer);
        assertFalse(availJobs.get(availJobs.indexOf(validJob_today_tomorrow)).getIsAvailable());
    }


    /**
     * Tests if the expected job is unavailable for the volunteer to unvolunteer from
     */
    @Test
    public void getAvailableForUnvolunteer_ExpectedJobIsAvailable_false() {
        jobCollection.addJob(validJob);
        testVolunteer.signUpForJob(validJob);
        ArrayList<Job> availJobs = jobCollection.getAvailableForSignup(testVolunteer);
        assertFalse(availJobs.get(availJobs.indexOf(validJob)).getIsAvailable());
    }


    /**
     * Tests if the expected job is available for the volunteer to unvolunteer from
     */
    @Test
    public void getAvailableForUnvolunteer_ExpectedJobIsAvailable_true() {
        jobCollection.addJob(validJob_minUnvolunteerDays);
        testVolunteer.signUpForJob(validJob_minUnvolunteerDays);
        ArrayList<Job> availJobs = jobCollection.getAvailableForUnvolunteer(testVolunteer);
        assertTrue(availJobs.get(availJobs.indexOf(validJob_minUnvolunteerDays)).getIsAvailable());
    }


    /**
     * Tests if a past job is available for unvolunteer
     */
    @Test
    public void getAvailableForUnvolunteer_JobIsInPast_false() {
        jobCollection.addJob(pastJob);
        testVolunteer.signUpForJob(pastJob);
        ArrayList<Job> availJobs = jobCollection.getAvailableForUnvolunteer(testVolunteer);
        assertFalse(availJobs.get(availJobs.indexOf(pastJob)).getIsAvailable());
    }

////////////////////////////////////

    /**
     * Tests if the expected job is unavailable for the park manager to unsubmit
     */
    @Test
    public void getAvailableForUnsubmit_ExpectedJobIsAvailable_false() {
        testParkManager.createNewJob(validJob, jobCollection);
        ArrayList<Job> availJobs = jobCollection.getAvailableForUnsubmit(testParkManager);
        assertFalse(availJobs.get(availJobs.indexOf(validJob)).getIsAvailable());
    }


    /**
     * Tests if the expected job is available for the volunteer to unvolunteer from
     */
    @Test
    public void getAvailableForUnsubmit_ExpectedJobIsAvailable_true() {
        testParkManager.createNewJob(validJob_minUnvolunteerDays, jobCollection);
        ArrayList<Job> availJobs = jobCollection.getAvailableForUnsubmit(testParkManager);
        assertTrue(availJobs.get(availJobs.indexOf(validJob_minUnvolunteerDays)).getIsAvailable());
    }


    /**
     * Tests if a past job is available for unsubmit
     */
    @Test
    public void getAvailableForUnsubmit_JobIsInPast_false() {
        testParkManager.createNewJob(pastJob, jobCollection);
        ArrayList<Job> availJobs = jobCollection.getAvailableForUnsubmit(testParkManager);
        assertFalse(availJobs.get(availJobs.indexOf(pastJob)).getIsAvailable());
    }


    /**
     * Tests if a job is prior to a date range
     */
    @Test
    public void getJobsBetween2DateTimes_JobIsTooEarly_false() {
        jobCollection.addJob(validJob);
        assertFalse(jobCollection.getJobsBetween2DateTimes(dateTimeTomorrow, dateTimeTomorrow).contains(validJob));
    }


    /**
     * Tests if a job is after a date range
     */
    @Test
    public void getJobsBetween2DateTimes_JobIsTooLate_false() {
        jobCollection.addJob(validJob_tomorrow_tomorrow);
        assertFalse(jobCollection.getJobsBetween2DateTimes(dateTimeToday, dateTimeToday).contains(validJob));
    }


    /**
     * Tests if a job is within a date range
     */
    @Test
    public void getJobsBetween2DateTimes_JobIsInRange_true() {
        jobCollection.addJob(validJob);
        assertTrue(jobCollection.getJobsBetween2DateTimes(pastDateTime, dateTimeTomorrow).contains(validJob));
    }


    /**
     * Tests if a job's start date is within, and end date is outside of the range
     */
    @Test
    public void getJobsBetween2DateTimes_JobEndOutsideRange_false() {
        jobCollection.addJob(validJob_today_tomorrow);
        assertFalse(jobCollection.getJobsBetween2DateTimes(pastDateTime, dateTimeToday).contains(validJob));
    }


    /**
     * Tests if a job's start date is outside of the range, and end date is within
     */
    @Test
    public void getJobsBetween2DateTimes_JobStartOutsideRange_false() {
        jobCollection.addJob(validJob_today_tomorrow);
        assertFalse(jobCollection.getJobsBetween2DateTimes(dateTimeTomorrow, dateTimeTomorrow).contains(validJob));
    }


    /**
     * Tests if data is saved and loaded correctly by finding a saved job
     */
    @Test
    public void saveData_IntendedJobFound_true() throws IOException, ClassNotFoundException {
        long jobID = validJob.getJobId();
        jobCollection.addJob(validJob);
        jobCollection.saveData();
        JobCollection jobCollection = new JobCollection();
        jobCollection.loadData();
        assertTrue(jobCollection.findJob(jobID) != null);
    }


    /**
     * Tests if data is saved and loaded correctly by attempting to
     * find a saved job that does not exist
     */
    @Test
    public void saveData_IntendedJobFound_false() throws IOException, ClassNotFoundException {
        long jobID = validJob.getJobId();
        jobCollection.saveData();
        JobCollection jobCollection = new JobCollection();
        jobCollection.loadData();
        assertFalse(jobCollection.findJob(jobID) != null);
    }


    /**
     * Tests if data is saved and loaded correctly by finding a saved job.
     * Currently this can only be done by testing saveData, so this is the exact same test.
     */
    @Test
    public void loadData_IntendedJobFound_true() throws IOException, ClassNotFoundException {
        saveData_IntendedJobFound_true();
    }


    /**
     * Tests if data is saved and loaded correctly by attempting to
     * find a saved job that does not exist.
     * Currently this can only be done by testing saveData, so this is the exact same test.
     */
    @Test
    public void loadData_IntendedJobFound_false() throws IOException, ClassNotFoundException {
        saveData_IntendedJobFound_false();
    }
}