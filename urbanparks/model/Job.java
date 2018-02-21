package urbanparks.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Comparator;
import java.util.TreeSet;

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
	
	/**
	 * Constructor to initialize all the fields for this job.
	 * 
	 * @param theDescription the job description.
	 * @param theStartDateTime the job start date and time.
	 * @param theEndDateTime the job end date and time.
	 * @param theParkName the park name.
	 * @param theLocation the job location.
	 * @param theLight the number of volunteers required for light workload.
	 * @param theMediumm the number of volunteers required for medium workload.
	 * @param theHeavy the number of volunteers required for heavy workload.
	 * @param theMinVolunteers the minimum number of volunteers required for this job.
	 */
	public Job(final String theDescription, final Calendar theStartDateTime, final Calendar theEndDateTime, 
			   final String theParkName, final String theLocation, final int theLight, final int theMedium, 
			   final int theHeavy, final int theMinVolunteers) {
		setDescription(theDescription);
		setStartDateTime(theStartDateTime);
		setEndDateTime(theEndDateTime);
		setParkName(theParkName);
		setLocation(theLocation);
		setLight(theLight);
		setMedium(theMedium);
		setHeavy(theHeavy);
		setMinimumVolunteers(theMinVolunteers);
	}
	
	/**
	 * Constructor for Job with the job ID
	 */
	public Job(final int jobID, final String theDescription, final Calendar theStartDateTime, final Calendar theEndDateTime, 
			   final String theParkName, final String theLocation, final int theLight, final int theMedium, 
			   final int theHeavy, final int theMinVolunteers) {
		setJobId(jobID);
		setDescription(theDescription);
		setStartDateTime(theStartDateTime);
		setEndDateTime(theEndDateTime);
		setParkName(theParkName);
		setLocation(theLocation);
		setLight(theLight);
		setMedium(theMedium);
		setHeavy(theHeavy);
		setMinimumVolunteers(theMinVolunteers);
	}
	
	/**
	 * Return the job description.
	 * 
	 * @return job description.
	 */
	public Integer getJobId() {
		return jobId;
	}
	
	/**
	 * Return the job description.
	 * 
	 * @return job description.
	 */
	public void setJobId(final Integer theJobId) {
		jobId = theJobId;
	}

	/**
	 * Return the job description.
	 * 
	 * @return job description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Change the job description.
	 * 
	 * @param theDescription the job description.
	 */
	public void setDescription(final String theDescription) {
		description = theDescription;
	}

	/**
	 * Return the job start date and time.
	 * 
	 * @return the job start date and time.
	 */
	public Calendar getStartDateTime() {
		return startDateTime;
	}

	/**
	 * Change the job start date and time.
	 * 
	 * @param theDate the job start date and time.
	 */
	public void setStartDateTime(final Calendar theStartDateTime) {
		startDateTime = theStartDateTime;
	}

	/**
	 * Return the job end date and time.
	 * 
	 * @return the job end date and time.
	 */
	public Calendar getEndDateTime() {
		return endDateTime;
	}

	/**
	 * Change the job end date and time.
	 * 
	 * @param theDate the job end date and time.
	 */
	public void setEndDateTime(final Calendar theEndDateTime) {
		endDateTime = theEndDateTime;
	}

	/**
	 * Return the park name where the job will take a place.
	 * 
	 * @return the park name.
	 */
	public String getParkName() {
		return parkName;
	}

	/**
	 * Change the park name to the new given name.
	 * 
	 * @param theParkName the new park name
	 */
	public void setParkName(String theParkName) {
		parkName = theParkName;
	}

	/**
	 * The location where the job will take a place.
	 * 
	 * @return the location of the job.
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Change the location of the job to the given location.
	 * 
	 * @param theLocation
	 */
	public void setLocation(String theLocation) {
		location = theLocation;
	}

	/**
	 * The number of volunteers required for light workload.
	 * 
	 * @return the number of volunteers for the light workload.
	 */
	public int getLight() {
		return maxLightWorkers;
	}

	/**
	 * Change the number of volunteers that required for light workload.
	 * 
	 * @param theLight the number of volunteers to do the light workload.
	 */
	public void setLight(int theLight) {
		maxLightWorkers = theLight;
	}

	/**
	 * The number of volunteers required for medium workload.
	 * 
	 * @return the number of volunteers for the medium workload.
	 */
	public int getMedium() {
		return maxMediumWorker;
	}

	/**
	 * Change the number of volunteers that required for medium workload.
	 * 
	 * @param theLight the number of volunteers to do the medium workload.
	 */
	public void setMedium(int theMedium) {
		maxMediumWorker = theMedium;
	}

	/**
	 * The number of volunteers required for heavy workload.
	 * 
	 * @return the number of volunteers for the heavy workload.
	 */
	public int getHeavy() {
		return maxHeavyWorkers;
	}

	/**
	 * Change the number of volunteers that required for heavy workload.
	 * 
	 * @param theLight the number of volunteers to do the heavy workload.
	 */
	public void setHeavy(int theHeavy) {
		maxHeavyWorkers = theHeavy;
	}

	/**
	 * Return the minimum number of volunteers required for this job.
	 * 
	 * @return minimum number of volunteers.
	 */
	public int getMinimumVolunteers() {
		return minTotalVolunteers;
	}

	/**
	 * Change the minimum number of volunteers required for this job.
	 * 
	 * @param theMinimumVolunteers minimum number of volunteers.
	 */
	public void setMinimumVolunteers(final int theMinimumVolunteers) {
		minTotalVolunteers = theMinimumVolunteers;
	}
	
	@Override
	public String toString() {
		
		String symbol = ", ";
		
		StringBuffer sb = new StringBuffer();
		sb.append(description);
		sb.append(symbol);
		sb.append(startDateTime);
		sb.append(symbol);
		sb.append(endDateTime);
		sb.append(symbol);
		sb.append(parkName);
		sb.append(symbol);
		sb.append(location);
		sb.append(symbol);
		sb.append(maxLightWorkers);
		sb.append(symbol);
		sb.append(maxMediumWorker);
		sb.append(symbol);
		sb.append(maxHeavyWorkers);
		sb.append(symbol);
//		sb.append(myVolunteersList.toString());
//		sb.append(symbol);
//		sb.append(myMinimumVolunteers);
		return sb.toString();
	}
//	@Override
//	public int compare(Job arg0, Job arg1) {
//		return arg0.getStartDateTime().compareTo(arg1.getEndDateTime());
//	}
}
