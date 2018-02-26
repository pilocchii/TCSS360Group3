package urbanparks.model;

public final class Constants {
	
	/**
	 * todo: many of these are unused
	 */
	
	//For creating job
	public static final int MAX_JOB_LENGTH = 4;
	public static final int MAX_PENDING_JOBS = 20;
	public static final int MAX_DAYS_BEFORE_JOB_ENDS = 60;
	
	//For signing up for job
	public static final int MIN_DAYS_BEFORE_SIGNUP = 2;
	public static final int MILLISECONDS_IN_DAY = 86400000;
	
	//For loading persistent data
	public static final String JOB_DATA_FILE = "joblist.data";
	public static final String USER_DATA_FILE = "userlist.data";
	
	// for DateUtils
	public final static int DAYS_IN_YEAR = 365;
	
	//for ParkManagerMenu
	public static final int MIN_WORKERS_FLOOR = 1;
	public static final int MIN_WORKERS_CEILING = Integer.MAX_VALUE;
	public static final int MAX_WORKERS_FLOOR = 0;
	public static final int MAX_WORKERS_CEILING = Integer.MAX_VALUE;
	
	
	private Constants() {
		//shouldn't ever happen
	}
}