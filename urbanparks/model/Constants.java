package urbanparks.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public final class Constants {
	
	//For creating a job
	
	/**
	 * Represents the maximum length of time a job may last.
	 */
	public static final int MAX_JOB_LENGTH = 4;
	/**
	 * Represents default value for the max pending jobs that can exist in the system.
	 */
	private static final int DEFAULT_MAX_PENDING_JOBS = 20;
	/**
	 * Represents the max allowable pending jobs in the system. This can be changed at run-time 
	 * and is saved on program close and updated on program launch.
	 */
	private static int maxPendingJobs;
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
	public static final int MIN_DAYS_BEFORE_SIGNUP = 2;
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
	private static final String fileName = "ConstantsFile.txt";
	
	// for DateUtils
	
	/**
	 * Number of days in a year; used for DateUtils calculations.
	 */
	public final static int DAYS_IN_YEAR = 365;
	
	// for park manager unsubmitting a job
	// TODO: find out what this is supposed to be! (value is not in requirements)
	public final static int MIN_DAYS_BETWEEN_UNSUBMIT_AND_JOBSTART = 3;
	
	//or unvolunteering from job
	public final static int MIN_DAYS_BETWEEN_UNVOLUNTEER_AND_JOBSTART = 3;
	
	//for ParkManagerMenu
//	public static final int MIN_WORKERS_FLOOR = 1;
//	public static final int MIN_WORKERS_CEILING = Integer.MAX_VALUE;
//	public static final int MAX_WORKERS_FLOOR = 0;
//	public static final int MAX_WORKERS_CEILING = Integer.MAX_VALUE;
	
	public static final int RANDOM_NEXTINT = 100;
	
	
	private Constants() {
		//shouldn't ever happen
	}
	
	/**
	 * Load the data from the text file and store it into the variables.
	 * 
	 * @return the status of the load true if the file exists and was loaded successfully,
	 * false otherwise.
	 */
	public static void loadData() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(fileName));
		maxPendingJobs = scanner.nextInt();
		scanner.close();
	}
	
	/**
	 * Save the current date to a file.
	 * 
	 * @return the status of the saving true if it was successful and false otherwise.
	 */
	public static void saveData() throws FileNotFoundException {
		PrintStream printStream = new PrintStream(new File(fileName));
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
	 * Precondition: the given value should be integer greater than 0.
	 */
	public static void setMaxPendingJobs(int theMaxPendingJobs) {
		maxPendingJobs = theMaxPendingJobs;
	}

	/**
	 * Change the maximum pending jobs value to the default value.
	 */
	public static void setDefaultMaxPendingJobs() {
		maxPendingJobs = DEFAULT_MAX_PENDING_JOBS;
	}
	
	
	
}