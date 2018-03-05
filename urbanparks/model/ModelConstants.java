package urbanparks.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Class that holds all the constants that are used in urbanparks.
 */
public final class ModelConstants {
	
	/**
	 * TODO: many of these are unused
	 */
	
	//For creating job
	public static final int DEFAULT_MAX_PENDING_JOBS = 10;
	private static int maxPendingJobs = DEFAULT_MAX_PENDING_JOBS;
	public static final int MIN_VALUE_OF_MAX_PENDING_JOBS = 1;
	public static final int MAX_JOB_LENGTH = 4;
	public static final int MAX_DAYS_BEFORE_JOB_ENDS = 60;
	public static final int DEFAULT_JOB_ID = 0;
	
	//For signing up for job
	public static final int MIN_DAYS_BEFORE_SIGNUP = 2;
	
	//For loading persistent data
	public static final String JOB_DATA_FILE = "joblist.data";
	public static final String USER_DATA_FILE = "userlist.data";
	public static final String SETTINGS_DATA_FILE = "settings.data";
	
	// for DateUtils
	public final static int DAYS_IN_YEAR = 365;
	
	// For Park Manager unsubmitting a job.
	public final static int MIN_DAYS_BETWEEN_UNSUBMIT_AND_JOBSTART = 3;
	
	// For Volunteer unvolunteering from a job
	public final static int MIN_DAYS_BETWEEN_UNVOLUNTEER_AND_JOBSTART = 3;
	
	private ModelConstants() {
		//shouldn't ever happen
	}
	
	/**
	 * Load setting data from the file and store it into the variable(s).
	 * Precondition : The file has to be exist in order to open the file.
	 * Postcondition: The data has been loaded from the file.
	 * @throws FileNotFoundException if the file does not exist.
	 */
	public static void loadSettingsData() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(SETTINGS_DATA_FILE));
		maxPendingJobs = scanner.nextInt();
		scanner.close();
	}
	
	/**
	 * Save setting current date to a file.
	 * Precondition : The file has to be exist otherwise it will create a new one.
	 * Postcondition: The data has been written to the file.
	 * @throws FileNotFoundException if the file does not exist.
	 */
	public static void saveSettingsData() throws FileNotFoundException {
		PrintStream printStream = new PrintStream(new File(SETTINGS_DATA_FILE));
		printStream.print(maxPendingJobs);
		printStream.close();
	}

	/**
	 * Gets the maximum number of pending jobs.
	 * Precondition : maxPendingJobs is an integer that is > 0.
	 * Postcondition: The data has been returned.
	 * @return the maximum number of pending jobs.
	 */
	public static int getMaxPendingJobs() {
		return maxPendingJobs;
	}

	/**
	 * Change the maximum pending jobs value to the given one.
	 * Precondition : theMaxPendingJobs > 0.
	 * Postcondition: The maxPendingJobs has been updated.
	 */
	public static void setMaxPendingJobs(int theMaxPendingJobs) {
		maxPendingJobs = theMaxPendingJobs;
	}

	/**
	 * Change the maximum pending jobs value to the default value.
	 * Precondition : DEFAULT_MAX_PENDING_JOBS is an integer that is > 0.
	 * Postcondition: The maxPendingJobs has been updated to the default value.
	 */
	public static void setDefaultMaxPendingJobs() {
		maxPendingJobs = DEFAULT_MAX_PENDING_JOBS;
	}

}