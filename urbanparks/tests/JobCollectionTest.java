package urbanparks.tests;

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
    LocalDateTime dateTimeYesterday;
    LocalDateTime minUnvolunteerDaysDateTime;
    LocalDateTime minUnsubmitDaysDateTime;
    LocalDateTime minVolunteerDaysDateTime;
    Job validJob_today_today;
    Job validJobTwo_today_today;
    Job validJob_today_tomorrow;
    Job validJob_tomorrow_tomorrow;
    Job validJob_yesterday_yesterday;
    Job validJob_minUnvolunteerDays;
    Job validJob_minUnsubmitDays;
    Job validJob_minVolunteerDays_minVolunteerDays;
    Volunteer testVolunteer;
    ParkManager testParkManager;


    /**
     * Helper method to check if a job is in a job availability list
     * @param availJobs the list of JobAvailabilities
     * @param job the job
     * @return True if job is in list
     */
    private boolean isJobInAvailabilityList(ArrayList<JobAvailability> availJobs, Job job) {
        boolean isIn = false;
        for (int i = 0; i < availJobs.size(); i++) {
            if (availJobs.get(i).getJob().equals(job) && availJobs.get(i).getIsAvailable()) { isIn = true; }
        }
        return isIn;
    }


    @Before
    public void setUpJobCollectionTest() {
        jobCollection = new JobCollection();
        dateTimeToday = LocalDateTime.now();
        dateTimeTomorrow = LocalDateTime.now().plusDays(1);
        dateTimeYesterday = LocalDateTime.now().minusDays(1);
        minUnvolunteerDaysDateTime = LocalDateTime.now().plusDays(ModelConstants.MIN_DAYS_BETWEEN_UNVOLUNTEER_AND_JOBSTART);
        minUnsubmitDaysDateTime = LocalDateTime.now().plusDays(ModelConstants.MIN_DAYS_BETWEEN_UNSUBMIT_AND_JOBSTART);
        minVolunteerDaysDateTime = LocalDateTime.now().plusDays(ModelConstants.MIN_DAYS_BEFORE_SIGNUP);

        validJob_today_today = new Job("job", dateTimeToday, dateTimeToday, "Park", "Seattle");
        validJobTwo_today_today = new Job("job", dateTimeToday, dateTimeToday, "Park", "Seattle");
        validJob_today_tomorrow = new Job("job", dateTimeToday, dateTimeTomorrow, "Park", "Seattle");
        validJob_minUnvolunteerDays = new Job("job", minUnvolunteerDaysDateTime, minUnvolunteerDaysDateTime, "Park", "Seattle");
        validJob_minUnsubmitDays = new Job("job", minUnsubmitDaysDateTime, minUnsubmitDaysDateTime, "Park", "Seattle");
        validJob_tomorrow_tomorrow = new Job("job", dateTimeTomorrow, dateTimeTomorrow, "Park", "Seattle");
        validJob_minVolunteerDays_minVolunteerDays = new Job("job", minVolunteerDaysDateTime, minVolunteerDaysDateTime, "Park", "Seattle");
        validJob_yesterday_yesterday = new Job("job", dateTimeYesterday, dateTimeYesterday, "Park", "Seattle");

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
        jobCollection.addJob(validJob_today_today);
        assertEquals(jobCollection.findJob(validJob_today_today.getJobId()), validJob_today_today);
    }


    /**
     * Tests if adding duplicate job increases the number of pending jobs
     */
    @Test
    public void addJob_AddExistingJobToCollection_JobCountSame() {
        jobCollection.addJob(validJob_today_today);
        int numPending = jobCollection.getPendingJobsCount();
        jobCollection.addJob(validJob_today_today);
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
        long jobID = validJob_today_today.getJobId();
        jobCollection.addJob(validJob_today_today);
        assertEquals(jobCollection.findJob(jobID), validJob_today_today);
    }


    /**
     * Testing if the job differs
     */
    @Test
    public void findJob_TwoNewJobsInCollection_JobIsDifferent() {
        long firstJobID = validJob_today_today.getJobId();
        long secondJobID = validJobTwo_today_today.getJobId();
        jobCollection.addJob(validJob_today_today);
        jobCollection.addJob(validJobTwo_today_today);
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
        jobCollection.addJob(validJob_minVolunteerDays_minVolunteerDays);
        ArrayList<JobAvailability> availJobs = jobCollection.getAvailableForSignup(testVolunteer);
        assertTrue(isJobInAvailabilityList(availJobs, validJob_minVolunteerDays_minVolunteerDays));
    }


    /**
     * Tests if the expected job is not available for the volunteer to sign up
     */
    @Test
    public void getAvailableForSignup_ExpectedJobIsAvailable_false() {
        ArrayList<JobAvailability> availJobs = jobCollection.getAvailableForSignup(testVolunteer);
        assertFalse(isJobInAvailabilityList(availJobs, validJob_tomorrow_tomorrow));
    }


    /**
     * Tests if a past job is available for signup
     */
    @Test
    public void getAvailableForSignup_JobIsInPast_false() {
        jobCollection.addJob(validJob_yesterday_yesterday);
        ArrayList<JobAvailability> availJobs = jobCollection.getAvailableForSignup(testVolunteer);

        assertFalse(isJobInAvailabilityList(availJobs, validJob_yesterday_yesterday));
    }


    /**
     * Tests if a job with overlap is available for signup
     */
    @Test
    public void getAvailableForSignup_JobOverlaps_false() {
        jobCollection.addJob(validJob_today_today);
        testVolunteer.signUpForJob(validJob_today_today.getJobId());
        jobCollection.addJob(validJob_today_tomorrow);
        ArrayList<JobAvailability> availJobs = jobCollection.getAvailableForSignup(testVolunteer);
        assertFalse(isJobInAvailabilityList(availJobs, validJob_today_tomorrow));
    }


    /**
     * Tests if the expected job is unavailable for the volunteer to unvolunteer from
     */
    @Test
    public void getAvailableForUnvolunteer_ExpectedJobIsAvailable_false() {
        jobCollection.addJob(validJob_today_today);
        testVolunteer.signUpForJob(validJob_today_today.getJobId());
        ArrayList<JobAvailability> availJobs = jobCollection.getAvailableForSignup(testVolunteer);
        assertFalse(isJobInAvailabilityList(availJobs, validJob_today_tomorrow));
    }


    /**
     * Tests if the expected job is available for the volunteer to unvolunteer from
     */
    @Test
    public void getAvailableForUnvolunteer_ExpectedJobIsAvailable_true() {
        jobCollection.addJob(validJob_minUnvolunteerDays);
        testVolunteer.signUpForJob(validJob_minUnvolunteerDays.getJobId());
        ArrayList<JobAvailability> availJobs = jobCollection.getAvailableForUnvolunteer(testVolunteer);
        assertTrue(isJobInAvailabilityList(availJobs, validJob_minUnvolunteerDays));
    }


    /**
     * Tests if a past job is available for unvolunteer
     */
    @Test
    public void getAvailableForUnvolunteer_JobIsInPast_false() {
        jobCollection.addJob(validJob_yesterday_yesterday);
        testVolunteer.signUpForJob(validJob_yesterday_yesterday.getJobId());
        ArrayList<JobAvailability> availJobs = jobCollection.getAvailableForUnvolunteer(testVolunteer);
        assertFalse(isJobInAvailabilityList(availJobs, validJob_minUnvolunteerDays));
    }


    /**
     * Tests if the expected job is unavailable for the park manager to unsubmit
     */
    @Test
    public void getAvailableForUnsubmit_ExpectedJobIsAvailable_false() {
        jobCollection.addJob(validJob_today_today);
        testParkManager.createNewJob(validJob_today_today.getJobId());
        ArrayList<JobAvailability> availJobs = jobCollection.getAvailableForUnsubmit(testParkManager);
        assertFalse(isJobInAvailabilityList(availJobs, validJob_today_today));
    }


    /**
     * Tests if the expected job is available for the volunteer to unvolunteer from
     */
    @Test
    public void getAvailableForUnsubmit_ExpectedJobIsAvailable_true() {
        jobCollection.addJob(validJob_minUnsubmitDays);
        testParkManager.createNewJob(validJob_minUnsubmitDays.getJobId());
        ArrayList<JobAvailability> availJobs = jobCollection.getAvailableForUnsubmit(testParkManager);
        assertTrue(isJobInAvailabilityList(availJobs, validJob_minUnsubmitDays));
    }


    /**
     * Tests if a past job is available for unsubmit
     */
    @Test
    public void getAvailableForUnsubmit_JobIsInPast_false() {
        jobCollection.addJob(validJob_yesterday_yesterday);
        testParkManager.createNewJob(validJob_yesterday_yesterday.getJobId());
        ArrayList<JobAvailability> availJobs = jobCollection.getAvailableForUnsubmit(testParkManager);
        assertFalse(isJobInAvailabilityList(availJobs, validJob_yesterday_yesterday));
    }


    /**
     * Tests if a job is prior to a date range
     */
    @Test
    public void getJobsBetween2DateTimes_JobIsTooEarly_false() {
        jobCollection.addJob(validJob_today_today);
        ArrayList<JobAvailability> availJobs = jobCollection.getJobsBetween2DateTimes(dateTimeTomorrow, dateTimeTomorrow);
        assertFalse(isJobInAvailabilityList(availJobs, validJob_today_today));
    }


    /**
     * Tests if a job is after a date range
     */
    @Test
    public void getJobsBetween2DateTimes_JobIsTooLate_false() {
        jobCollection.addJob(validJob_tomorrow_tomorrow);
        ArrayList<JobAvailability> availJobs = jobCollection.getJobsBetween2DateTimes(dateTimeToday, dateTimeToday);
        assertFalse(isJobInAvailabilityList(availJobs, validJob_tomorrow_tomorrow));
    }


    /**
     * Tests if a job is within a date range
     */
    @Test
    public void getJobsBetween2DateTimes_JobIsInRange_true() {
        jobCollection.addJob(validJob_today_today);
        ArrayList<JobAvailability> availJobs = jobCollection.getJobsBetween2DateTimes(dateTimeYesterday, dateTimeTomorrow);
        assertTrue(isJobInAvailabilityList(availJobs, validJob_today_today));
    }


    /**
     * Tests if a job's start date is within, and end date is outside of the range
     */
    @Test
    public void getJobsBetween2DateTimes_JobEndOutsideRange_false() {
        jobCollection.addJob(validJob_tomorrow_tomorrow);
        ArrayList<JobAvailability> availJobs = jobCollection.getJobsBetween2DateTimes(dateTimeYesterday, dateTimeToday);
        assertFalse(isJobInAvailabilityList(availJobs, validJob_tomorrow_tomorrow));
    }


    /**
     * Tests if a job's start date is outside of the range, and end date is within
     */
    @Test
    public void getJobsBetween2DateTimes_JobStartOutsideRange_false() {
        jobCollection.addJob(validJob_today_today);
        ArrayList<JobAvailability> availJobs = jobCollection.getJobsBetween2DateTimes(dateTimeTomorrow, dateTimeTomorrow);
        assertFalse(isJobInAvailabilityList(availJobs, validJob_today_today));
    }


    /**
     * Tests if data is saved and loaded correctly by finding a saved job
     */
    @Test
    public void saveData_IntendedJobFound_true() throws IOException, ClassNotFoundException {
        long jobID = validJob_today_today.getJobId();
        jobCollection.addJob(validJob_today_today);
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
        long jobID = validJob_today_today.getJobId();
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