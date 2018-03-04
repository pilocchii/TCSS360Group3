package urbanparks.model;

import static urbanparks.model.ModelConstants.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDateTime;

/**
 * This class represents a job with its all information.
 */
public class Job implements Serializable {

	private static final long serialVersionUID = 928850375626876361L;
	
	private long jobId;
	private String description;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private String parkName;
	private String location;
	private boolean isAvailable;
	private boolean isCancelled;

	private ArrayList<String> volunteers;

	/**
	 * Constructor to initialize all the fields for this job.
	 * 
	 * @param description the job description.
	 * @param startDateTime the job start date and time.
	 * @param endDateTime the job end date and time.
	 * @param parkName the park name.
	 * @param location the job location.
	 */
	public Job(final String description, final LocalDateTime startDateTime, final LocalDateTime endDateTime, 
			final String parkName, final String location) {
		this.jobId = JobCollection.generateNewJobID();
		this.description = description;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.parkName = parkName;
		this.location = location;

		volunteers = new ArrayList<String>();
		isAvailable = true;
		isCancelled = false;
	}
	
	// Getters: ----------------------------------------------------------------------------------------------------------
	/**
	 * Return the jobID.
	 * @return job ID.
	 */
	public long getJobId() {
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
	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}
	
	/**
	 * Return the job end date and time.
	 * @return the job end date and time.
	 */
	public LocalDateTime getEndDateTime() {
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
	 * Gets a temporary flag representing the job's availability to a volunteer
	 * @return
	 */
	public boolean getIsAvailable() {
		return isAvailable;
	}
	
	public boolean getIsCancelled() {
		return isCancelled;
	}
	
	/**
	 * Gets the number of volunteers signed up for this job
	 * @return the number of volunteers signed up for this job
	 */
	public int getVolunteerCount() {
		return volunteers.size();
	}
	
	/**
	 * Gets a the start time of a Job as a string
	 * @return The start time of this job formatted as a string
	 */
	public String getStartDateFormatted() {
		return DateUtils.formatDateTime(startDateTime);
	}
	
	/**
	 * Gets a the end time of a Job as a string
	 * @return The end time of this job formatted as a string
	 */
	public String getEndDateFormatted() {
		return DateUtils.formatDateTime(endDateTime);
	}
	
	/**
	 * Gets a formatted string indicating if this job is cancelled.
	 * @return A String indicating if this job is cancelled.
	 */
	public String getIsCancelledFormatted() {
		if (isCancelled) {
			return "Yes";
		} else {
			return "No";
		}
	}
	
	/**
	 * Gets a formatted string indicating if this job is available for an action,
	 * which depends on the context.
	 * @return A String indicating if this job is available for an action.
	 */
	public String getIsAvailableFormatted() {
		if (isAvailable) {
			return "Yes";
		} else {
			return "No";
		}
	}

	// Setters: -----------------------------------------------------------------------------------------------------------
	/**
	 * Sets a temporary flag representing the job's availability to a volunteer
	 * @param isAvailable
	 */
	public void setIsAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
	
	// Other methods: -----------------------------------------------------------------------------------------------------
	/**
	 * Sets the job's state to cancelled. This cannot be undone.
	 */
	public void cancelJob() {
		this.isCancelled = true;
	}

	/**
	 * Adds a volunteer to the list of signed up volunteers. 
	 * @param email the email address of the volunteer
	 */
	public void addVolunteer(String email) {
		volunteers.add(email);
	}
	
	/**
	 * Removes a volunteer from this job when they unvolunteer
	 * @param email the email address of the volunteer
	 */
	public void removeVoluneer(String email) {
		volunteers.remove(email);
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
	public boolean isSignupEarlyEnough() {
		/**
		 * A volunteer may sign up only if the job begins 
		 * at least a minimum number of calendar days after the current date
		 */
		int daysBetween = DateUtils.daysBetweenNowAndDate(getStartDateTime());
		return daysBetween >= MIN_DAYS_BEFORE_SIGNUP;
	}
	
	public boolean isUnvolunteerEarlyEnough() {
		/**
		 * A volunteer can unvolunteer only if the job starts 
		 * at least a minumum number of days in the future..
		 */
		int daysBetween = DateUtils.daysBetweenNowAndDate(getStartDateTime());
		return daysBetween >= MIN_DAYS_BETWEEN_UNVOLUNTEER_AND_JOBSTART;
	}
	
	public boolean isUnsubmitEarlyEnough() {
		/**
		 * A job can be unsubmitted only if the job starts 
		 * at least a minumum number of days in the future.
		 */
		int daysBetween = DateUtils.daysBetweenNowAndDate(getStartDateTime());
		return daysBetween >= MIN_DAYS_BETWEEN_UNSUBMIT_AND_JOBSTART;
	}

	/**
	 * Checks weather this job's start or end is between the given two date and time, inclusive.
	 * Precondition: the given two dates are not null.
	 * 
	 * @param lowerBound the lower bound which the job's start or end times can't be below
	 * @param upperBound the upper bound which the job's start or end times can't be above
	 * @return true if this job starts or ends between the given two dateTimes, false otherwise.
	 */
	public boolean isBetween2DatesInclusive(LocalDateTime lowerBound, LocalDateTime upperBound) {
		boolean startAfterLowerBound = startDateTime.isAfter(lowerBound) || startDateTime.isEqual(lowerBound);
		boolean startBeforeUpperBound = startDateTime.isBefore(upperBound) || startDateTime.isEqual(upperBound);
		boolean startInRange = startAfterLowerBound && startBeforeUpperBound;
		
		boolean endAfterLowerBound = endDateTime.isAfter(lowerBound) || endDateTime.isEqual(lowerBound);
		boolean endBeforeUpperBound = endDateTime.isBefore(upperBound) || endDateTime.isEqual(upperBound);
		boolean endInRange = endAfterLowerBound && endBeforeUpperBound;
		
		return startInRange || endInRange;
	}
	
	/**
	 * Determines if a job has ended, 
	 * meaning its end time is at or before now
	 * @return true if job has ended, false otherwise.
	 */
	public boolean hasJobEnded() {
		return endDateTime.isBefore(LocalDateTime.now()) || endDateTime.isEqual(LocalDateTime.now());
	}
}
