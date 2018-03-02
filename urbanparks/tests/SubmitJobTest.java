package urbanparks.tests;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import urbanparks.model.Constants;
import urbanparks.model.Job;
import urbanparks.model.JobCollection;

/**
 * There can be more than the maximum number of pending jobs at a time in the entire system,
 * default of 20
 * i)The system has far fewer than the maximum number of pending jobs
 * ii)The system has one fewer than the maximum number of pending jobs
 * iii)The system has exactly the maximum number of pending jobs
 * */

public class SubmitJobTest {

	
	@Before
	public void setup() throws FileNotFoundException {
		Constants.loadData();
	}
	
	@Test
	public void createMaxMinus1Test() throws NoSuchAlgorithmException {
		JobCollection jc = new JobCollection();
		Job j;
		for(int i = 1; i < Constants.getMaxPendingJobs(); i++) {
			j = new Job("decription of job # " + i, LocalDateTime.now(), LocalDateTime.now(), "XYZ Park", "2343 23rd Ave N", 2, 3, 4, 10);
			jc.addJob(j);
		}
		assertEquals(jc.size(), Constants.getMaxPendingJobs() - 1);
	}
	
	@Test
	public void createMaxJobsTest() throws NoSuchAlgorithmException {
		JobCollection jc = new JobCollection();
		Job j;
		for(int i = 1; i <= Constants.getMaxPendingJobs(); i++) {
			j = new Job("decription of job # " + i, LocalDateTime.now(), LocalDateTime.now(), "XYZ Park", "2343 23rd Ave N", 2, 3, 4, 10);
			jc.addJob(j);
		}
		assertEquals(jc.size(), Constants.getMaxPendingJobs());
	}

}
