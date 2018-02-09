package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import java.util.Date;
import java.util.HashMap;

import mypackage.Job;
import mypackage.ParkManager;


/**
* JUnit test of Job class
* @author Alyssa Ingersoll
*/


int MAXDAYS = 75;

public class JobTest {

    // c) No job can be specified whose end date is more than the maximum number 
    // of days from the current date, default of 75

    // job parameters shared between tests
    @Before
    public final void setup() {
        Date startDate = new Date(todayMillis + maxDaysMillis);
        Date endDate = new Date(todayMillis + maxDaysMillis);
        HashMap<String, Integer> workLoad = new HashMap();
        String description = "";
        int minVolunteers = 1;
        ParkManager manager = new ParkManager("TestManager");
    }


    // i. The specified job ends one fewer than the maximum number of days from the current date
    // ii. The specified job ends the maximum number of days from the current date
    @Test
    public final void jobDateBeyondMaximum() {
        long todayMillis = System.currentTimeMillis();
        long maxDaysMillis = TimeUnit.DAYS.toMillis(MAXDAYS - 1);
        
        Job job = new Job(startDate, endDate, workLoad, description, minVolunteers, manager);

        assertTrue(job.getStartDate() == startDate);
        assertTrue(job.getEndDate() == endDate);
    }


    // iii. The specified job ends one more than the maximum number of days from the current date
    @Test (expected = IllegalArgumentException.class)
    public final void jobDateBeyondMaximum() {
        long todayMillis = System.currentTimeMillis();
        long maxDaysMillis = TimeUnit.DAYS.toMillis(MAXDAYS + 1);

        Job job = new Job(startDate, endDate, workLoad, description, minVolunteers, manager);
    }

    // end requirements part c
}
