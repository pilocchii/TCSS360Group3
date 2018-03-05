package urbanparks.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Class that holds all the constants that are used in urbanparks.
 */
public final class ModelConstants {
	

	//For creating a job
	
	/**
	 * Represents the maximum length of time a job may last.
	 */
	public static final int MAX_JOB_LENGTH = 4;
	/**
	 * Represents default value for the max pending jobs that can exist in the system.
	 */
	public static final int DEFAULT_MAX_PENDING_JOBS = 10;
	/**
	 * Represents the max allowable pending jobs in the system. This can be changed at run-time 
	 * and is saved on program close and updated on program launch.
	 */
	private static int maxPendingJobs = DEFAULT_MAX_PENDING_JOBS;
	
	//For creating job
	/**
	 * Represents the MINIMUM value that maxPendingJobs can be set to.
	 */
	public static final int MIN_VALUE_OF_MAX_PENDING_JOBS = 1;
	/**
	 * Represents the number of days a job is allowed to be scheduled out.
	 */
	public static final int MAX_DAYS_BEFORE_JOB_ENDS = 60;
	/**
	 * Default value for the job ids; the first job will contain this value + 1, each
	 * job after will take sequential values.
	 */
	public static final int DEFAULT_JOB_ID = 0;
	
	//For signing up for job
	
	/**
	 * Represents the minimum number of days beforehand that a volunteer can signup.
	 * If a job starts sooner than this value, they will not be able to signup.
	 */
	public static final int MIN_DAYS_BEFORE_SIGNUP = 3;

	/**
	 * Represents the number of milliseconds in a day; for calculation purposes.
	 */
	public static final int MILLISECONDS_IN_DAY = 86400000;

	
	//For loading persistent data
	
	/**
	 * The name of the file to store the list of jobs.
	 */
	public static final String JOB_DATA_FILE = "joblist.data";
	/**
	 * The name of the file to store the list of users.
	 */
	public static final String USER_DATA_FILE = "userlist.data";

	/**
	 * The name of the file to store the program settings values.
	 */
	public static final String SETTINGS_DATA_FILE = "settings.data";
	
	// for DateUtils
	
	/**
	 * Number of days in a year; used for DateUtils calculations.
	 */
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
	 * Returns the value of max allowable pending jobs in the system.
	 * @return an int representing the max allowable pending jobs
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
