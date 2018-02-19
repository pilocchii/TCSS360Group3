package urbanparks.model;

public final class ProgramConstants {
	
	//For creating job
	public static final int MAX_JOB_LENGTH = 3;
	public static final int MAX_PENDING_JOBS = 20;
	public static final int MAX_DAYS_BEFORE_JOB_START = 75;
	
	//For signing up for job
	public static final int MIN_DAYS_BEFORE_SIGNUP = 2;
	public static final int MILLISECONDS_IN_DAY = 86400000;
	
	//For loading persistent data
	public static final String JOB_DATA_FILE = "joblist.data";
	public static final String USER_DATA_FILE = "userlist.data";
	
	private ProgramConstants() {
		//shouldn't ever happen
	}
}