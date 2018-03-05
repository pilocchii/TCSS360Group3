package urbanparks.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import urbanparks.model.ModelConstants;
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
	public void isNumJobsAtMaximum_JobCollectionIsNotAtMaximum_False() {
		LocalDateTime dateTime = LocalDateTime.now();
		jobCollection.addJob(new Job("job", dateTime, dateTime, "Park", "Seattle"));
		assertFalse(jobCollection.isNumJobsAtMaximum());
	}

	/**
	 * Testing if the job collection is at maximum capacity.
	 * @throws FileNotFoundException 
	 */
	@Test 
	public void isNumJobsAtMaximum_JobCollectionIsAtMaximum_True() {
		LocalDateTime dateTime = LocalDateTime.now();
		ModelConstants.setDefaultMaxPendingJobs();
		for(int i = jobCollection.getPendingJobsCount(); i < ModelConstants.getMaxPendingJobs(); i++) {
			// set job to end in future, so it can be counted as pending
			jobCollection.addJob(new Job("job # " + i, dateTime, dateTime.plusMinutes(1), "Park ", "Seattle"));
		}
		assertTrue(jobCollection.isNumJobsAtMaximum());
	}
	
	@Test
	public void getPendingCount_PendingJobsAndCancelledJobInCollection_True() {
		LocalDateTime dateTime = LocalDateTime.now();
		int size = jobCollection.getPendingJobsCount();
		for(int i = size; i < (size + 5); i++) {
			// set job to end in future, so it can be counted as pending
			jobCollection.addJob(new Job("job # " + i, dateTime, dateTime.plusMinutes(1), "Park ", "Seattle"));
		}
		Job cancelled = new Job("This is cancelled", dateTime, dateTime, "Shouldn't", "Count");
		cancelled.cancelJob();
		assertTrue(5 == jobCollection.getPendingJobsCount());
	}

}
