package urbanparks.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import urbanparks.model.Constants;
import urbanparks.model.Job;
import urbanparks.model.JobCollection;

/**
 * JUnit testing to test if the job collection is at maximum capacity or not.
 */
public class JobCollectionTest {

	JobCollection jobCollection;
	
	@Before
	public void setUpJobCollectionTest() {
		jobCollection = new JobCollection();
	}

	/**
	 * Testing if the job collection is NOT at maximum capacity.
	 */
	@Test
	public void isNumJobsAtMaximum_JobCollectionIsEmpty_False() {
		assertFalse(jobCollection.isNumJobsAtMaximum());
	}

	/**
	 * Testing if the job collection is Not at maximum capacity.
	 * @throws FileNotFoundException 
	 */
	@Test 
	public void isNumJobsAtMaximum_JobCollectionIsNotAtMaximum_False() throws FileNotFoundException {
		LocalDateTime dateTime = LocalDateTime.now();
		jobCollection.addJob(new Job("job # 1", dateTime, dateTime, "Park", "Seattle", 2, 3, 4, 10));
		assertFalse(jobCollection.isNumJobsAtMaximum());
	}

	/**
	 * Testing if the job collection is at maximum capacity.
	 * @throws FileNotFoundException 
	 */
	@Test 
	public void isNumJobsAtMaximum_JobCollectionIsAtMaximum_True() throws FileNotFoundException {
		Constants.loadData();
		LocalDateTime dateTime = LocalDateTime.now();
		for(int i = 1; i <= Constants.getMaxPendingJobs(); i++) {
			jobCollection.addJob(new Job("job # " + i, dateTime, dateTime, "Park", "Seattle", 2, 3, 4, 10));
		}
		assertTrue(jobCollection.isNumJobsAtMaximum());
	}

}
