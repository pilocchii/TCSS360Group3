package urbanparks.model;

import static urbanparks.model.Constants.MIN_DAYS_BEFORE_SIGNUP;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * This class represents a job with its all information.
 */
public class Job implements Serializable {
	
	private static final long serialVersionUID = 928850375626876361L;

	private Integer jobId;
	private String description;
	private Calendar startDateTime;
	private Calendar endDateTime;
	private String parkName;
	private String location;
	private int maxLightWorkers;
	private int maxMediumWorker;
	private int maxHeavyWorkers;
	private int minTotalVolunteers;
	private boolean isAvailable;
	
	/**
	 * Constructor to initialize all the fields for this job.
	 * 
	 * @param description the job description.
	 * @param startDateTime the job start date and time.
	 * @param endDateTime the job end date and time.
	 * @param parkName the park name.
	 * @param location the job location.
	 * @param maxLightWorkers the number of volunteers required for light workload.
	 * @param theMediumm the number of volunteers required for medium workload.
	 * @param maxHeavyWorkers the number of volunteers required for heavy workload.
	 * @param minTotalVolunteers the minimum number of volunteers required for this job.
	 */
	public Job(final String description, final Calendar startDateTime, final Calendar endDateTime, 
			   final String parkName, final String location, final int maxLightWorkers, final int maxMediumWorker, 
			   final int maxHeavyWorkers, final int minTotalVolunteers) {
		this.description = description;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.parkName = parkName;
		this.location = location;
		this.maxLightWorkers = maxLightWorkers;
		this.maxMediumWorker = maxMediumWorker;
		this.maxHeavyWorkers = maxHeavyWorkers;
		this.minTotalVolunteers = minTotalVolunteers;
		
		isAvailable = true;
	}
	
	// Getters:
	/**
	 * Return the job description.
	 * @return job description.
	 */
	public Integer getJobId() {
		return jobId;
	}
	/**
	 * Gets the job's description
	 * @return The job's description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Return the job start date and time.
	 * @return the job start date and time.
	 */
	public Calendar getStartDateTime() {
		return startDateTime;
	}
	/**
	 * Return the job end date and time.
	 * @return the job end date and time.
	 */
	public Calendar getEndDateTime() {
		return endDateTime;
	}
	/**
	 * Gets the park name the job takes place in.
	 * @return Job's park name
	 */
	public String getParkName() {
		return parkName;
	}
	/**
	 * Gets the location the job takes place in.
	 * @return Job's location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * Gets the maximum light workers for the job
	 * @return Job's light workers maximum
	 */
	public int getMaxLightWorkers() {
		return maxLightWorkers;
	}
	/**
	 * Gets the maximum medium workers for the job
	 * @return Job's medium workers maximum
	 */
	public int getMaxMediumWorkers() {
		return maxMediumWorker;
	}
	/**
	 * Gets the maximum heavy workers for the job
	 * @return Job's heavy workers maximum
	 */
	public int getMaxheavyWorkers() {
		return maxHeavyWorkers;
	}
	/**
	 * Gets the minimum total volunteers required for the job.
	 * @return Job's minimum total volunteers
	 */
	public int getMinTotalVolunteers() {
		return minTotalVolunteers;
	}
	/**
	 * Gets a temporary flag representing the job's availability to a volunteer
	 * @return
	 */
	public boolean getIsAvailable() {
		return isAvailable;
	}
	
	//Setters:
	/**
	 * Return the job description.
	 * @return job description.
	 */
	public void setJobId(final Integer theJobId) {
		jobId = theJobId;
	}
	/**
	 * Sets a temporary flag representing the job's availability to a volunteer
	 * @param isAvailable
	 */
	public void setIsAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
	/**
	 * Determines if the start or end times of 2 jobs overlap
	 * 
	 * @param otherJob
	 * @return
	 */
	public boolean doJobsOverlap(Job otherJob) {
		if (DateUtils.are2DatesOnSameDay(startDateTime,  otherJob.getStartDateTime())) {
			return true;
		}
		if (DateUtils.are2DatesOnSameDay(startDateTime,  otherJob.getEndDateTime())) {
			return true;
		}
		if (DateUtils.are2DatesOnSameDay(endDateTime,  otherJob.getEndDateTime())) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks whether the time between now and the job start time is at least the minimum value,
	 *  for job sign up.
	 * 
	 * @param theCandidateJob
	 * @return true if there is enough time between now and when the job starts, false otherwise.
	 */
	public static boolean isSignupEarlyEnough(Job theCandidateJob) {
		/**
		 * A volunteer may sign up only if the job begins 
		 * at least a minimum number of calendar days after the current date
		 */
		return DateUtils.daysBetweenNowAndDate(theCandidateJob.getStartDateTime()) >= MIN_DAYS_BEFORE_SIGNUP;
	}
	
	/**
	 * Shows job info
	 * precondition: All Job fields must be non-null
	 */
	public void showInfo() {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
		System.out.println("Starting time: " + dateFormat.format(startDateTime.getTime()));
		System.out.println("Ending time: " + dateFormat.format(endDateTime.getTime()));
		System.out.println("Park name: " + parkName);
		System.out.println("Location: " + location);
		System.out.println("Job description: " + description);
		System.out.println("Max volunteers for work levels: " 
			+ "Light - " + maxLightWorkers
			+ ", Medium - " + maxMediumWorker
			+ ", Heavy - " + maxHeavyWorkers);
		System.out.println("Min total volunteers: " + minTotalVolunteers);
	}

}
