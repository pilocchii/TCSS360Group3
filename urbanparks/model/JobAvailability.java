package urbanparks.model;

import java.time.LocalDateTime;

/**
 * wapper class
 *
 */
public class JobAvailability {

	private Job job;
	private boolean isAvailable;
	
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
	
	public boolean getIsAvailable() {
		return isAvailable;
	}
	
	public Job getJob() {
		return job;
	}

	// Setters: -----------------------------------------------------------------------------------------------------------
	/**
	 * Sets a temporary flag representing the job's availability for 
	 * an action, which depends of context
	 * @param isAvailable A flag indicating if this job is available for an action.
	 */
	public void setIsAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}


}
