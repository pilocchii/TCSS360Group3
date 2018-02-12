package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Job;
import model.JobCollection;
import model.ParkManager;
import model.User;
import static model.ProgramConstants.*;

/**There can be more than the maximum number of pending jobs at a time in the entire system,
* default of 20
* i)The system has far fewer than the maximum number of pending jobs
* ii)The system has one fewer than the maximum number of pending jobs
* iii)The system has exactly the maximum number of pending jobs*/

public class SubmitJobTest {

	@Before
	public void setup() {
		User u = new ParkManager("JohnDoe@gmail.com");
		JobCollection jc = new JobCollection();
	}
	
	@Test
	public void createNoJobsTest() {
		int code = u.createJob();
		assertEquals(code, 1);
	}
	
	@Test
	public void createMaxMinus1Test() {
		Job j;
		for(int i = 0; i < MAX_PENDING_JOBS - 1; i++) {
			j = new Job();
			jc.add(j);
		}
		int code = u.createJob();
		assertEquals(code, 1);
	}
	
	@Test
	public void createMaxJobsTest() {
		Job j;
		for(int i = 0; i < MAX_PENDING_JOBS; i++) {
			j = new Job();
			jc.add(j);
		}
		int code = u.createJob();
		assertEquals(code, -1);
	}

}
