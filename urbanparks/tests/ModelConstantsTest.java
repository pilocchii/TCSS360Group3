package urbanparks.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.FileNotFoundException;
import java.util.Random;
import java.io.File;

import org.junit.Before;
import org.junit.Test;

import urbanparks.model.ModelConstants;

/**
 * JUnit testing to test Constants class. 
 */
public class ModelConstantsTest {
	

	@Before
	public void setUpModelConstantsTest() {
		try {
			ModelConstants.loadSettingsData();
		} catch (FileNotFoundException e) {
			ModelConstants.setDefaultMaxPendingJobs();
		}
	}
	
	/**
	 * Testing to load a data from a file that does not exist.
	 * @throws FileNotFoundException if the file does not exist.
	 */
	@Test(expected = FileNotFoundException.class)
	public void loadData_LoadDataFromFileThatDoesNotExist_FileNotFoundException() throws FileNotFoundException {
		File file = new java.io.File(ModelConstants.SETTINGS_DATA_FILE);
		file.delete();
		ModelConstants.loadSettingsData();
	}

	/**
	 * Testing to change the max pending jobs value to random value.
	 * @throws FileNotFoundException if the file does not exist.
	 */
	@Test
	public void setMaxPendingJobs_ChangeMaxPendingJobs_Equal() throws FileNotFoundException {
		int maxPendingJobs = 1 + new Random().nextInt(50);
		ModelConstants.setMaxPendingJobs(maxPendingJobs);
		ModelConstants.saveSettingsData();
		assertEquals(ModelConstants.getMaxPendingJobs(), maxPendingJobs);
	}
	
	/**
	 * Testing to change the max pending jobs to the default value.
	 */
	@Test
	public void setDefaultMaxPendingJobs_ChangeMaxPendingJobsToDefault_NotEqual() {
		int newMaxPendingJobs = ModelConstants.getMaxPendingJobs() + new Random().nextInt(50);
		ModelConstants.setMaxPendingJobs(newMaxPendingJobs);
		int OldMaxPendingJobs = ModelConstants.getMaxPendingJobs();
		ModelConstants.setDefaultMaxPendingJobs();
		assertNotEquals(ModelConstants.getMaxPendingJobs(), OldMaxPendingJobs);
	}

}
