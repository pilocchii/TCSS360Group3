package urbanparks.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public final class Constants {
	
	/**
	 * TODO: many of these are unused
	 */
	
	//For creating job
	public static final int MAX_JOB_LENGTH = 4;
	private static final int DEFAULT_MAX_PENDING_JOBS = 20;
	private static int maxPendingJobs;
	public static final int MAX_DAYS_BEFORE_JOB_ENDS = 60;
	
	//For signing up for job
	public static final int MIN_DAYS_BEFORE_SIGNUP = 2;
	public static final int MILLISECONDS_IN_DAY = 86400000;
	
	//For loading persistent data
	public static final String JOB_DATA_FILE = "joblist.data";
	public static final String USER_DATA_FILE = "userlist.data";
	private static final String fileName = "ConstantsFile.txt";
	
	// for DateUtils
	public final static int DAYS_IN_YEAR = 365;
	
	//for ParkManagerMenu
	public static final int MIN_WORKERS_FLOOR = 1;
	public static final int MIN_WORKERS_CEILING = Integer.MAX_VALUE;
	public static final int MAX_WORKERS_FLOOR = 0;
	public static final int MAX_WORKERS_CEILING = Integer.MAX_VALUE;
	
	public static final int RANDOM_NEXTINT = 100;
	
	
	private Constants() {
		//shouldn't ever happen
	}
	
	/**
	 * Load the data from the text file and store it into the variables.
	 * 
	 * @return the status of the load true if it was successful and false otherwise.
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