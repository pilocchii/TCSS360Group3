package urbanparks.model;

/**
 * Temporary wrapper class for Job, adding a availability flag.
 * invariants: all fields non-null
 */
public class JobAvailability {

	private Job job;
	/**
	 * Flag indicating availability for action, which depends on context.
	 */
	private boolean isAvailable;
	
	/**
	 * Sets the availability of the job associated with this.
	 * @return the availability of the job associated with this.
	 */
	public boolean getIsAvailable() {
		return isAvailable;
	}
	
	/**
	 * Gets the job associated with this.
	 * @return the job associated with this.
	 */
	public Job getJob() {
		return job;
	}
	
	/**
	 * Sets a temporary flag representing the job's availability for 
	 * an action, which depends of context
	 * @param isAvailable A flag indicating if this job is available for an action.
	 */
	public void setIsAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	/**
	 * Constructor for JobAvailability
	 * @param job The job to wrap.
	 */
	public JobAvailability(Job job) {
		this.job = job;
		this.isAvailable = false;
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
}
