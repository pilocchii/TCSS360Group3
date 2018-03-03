package urbanparks.tests;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import urbanparks.model.Job;
import urbanparks.model.JobCollection;
import static urbanparks.model.Constants.*;

/**There can be more than the maximum number of pending jobs at a time in the entire system,
* 
* i)The system has far fewer than the maximum number of pending jobs
* ii)The system has one fewer than the maximum number of pending jobs
* iii)The system has exactly the maximum number of pending jobs*/

public class SubmitJobTest {

	
	@Before
	public void setup() {
		
	}
	
	@Test
	public void createNoJobsTest() {
		Calendar c = Calendar.getInstance();
		Job newJob = new Job("A", c, c, "B", "C", 1, 2, 3, 4);
		assertEquals(newJob, "A, " + c + ", " + c + ", " + "B, C, 1, 2, 3, 4");
	}
	
	@Test
	public void createMaxMinus1Test() throws NoSuchAlgorithmException {
		JobCollection jc = new JobCollection();
		Job j;
		for(int i = 1; i < MAX_PENDING_JOBS - 1; i++) {
			j = new Job("decription of job # " + i, Calendar.getInstance(), Calendar.getInstance(), "XYZ Park", "2343 23rd Ave N", 2, 3, 4, 10);
			jc.addJob(j);
		}
		assertEquals(jc.getSortedJobs().size(), MAX_PENDING_JOBS - 1);
	}
	
	@Test
	public void createMaxJobsTest() throws NoSuchAlgorithmException {
		JobCollection jc = new JobCollection();
		Job j;
		for(int i = 1; i < MAX_PENDING_JOBS; i++) {
			j = new Job("decription of job # " + i, Calendar.getInstance(), Calendar.getInstance(), "XYZ Park", "2343 23rd Ave N", 2, 3, 4, 10);
			jc.addJob(j);
		}
		assertEquals(jc.getSortedJobs().size(), MAX_PENDING_JOBS - 1);
	}

}
