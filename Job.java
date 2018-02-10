package classes;

import java.io.Serializable;
import java.util.Calendar;
import java.util.TreeSet;

/**
 * This class represents a job with its all information.
 * 
 * @author Abderisaq Tarabi
 * @version 2/8/2018
 *
 */
public class Job implements Serializable {
	
	private static final long serialVersionUID = 928850375626876361L;

	private String myDescription;
	
	private Calendar myStartDateTime;
	
	private Calendar myEndDateTime;
	
	private String myParkName;
	
	private String myLocation;
	
	private int myLight;
	
	private int myMedium;
	
	private int myHeavy;
	
	/** The job status and weather it cancelled or not. */
	private boolean myStatus;
	
	/** A list of all volunteers email whom volunteer for this job. */
	private TreeSet<String> myVolunteersList;
	
	private int myMinimumVolunteers;
	
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
		myStatus = false;
		myVolunteersList = new TreeSet<String>();
		setMinimumVolunteers(theMinVolunteers);
	}

	/**
	 * Return the job description.
	 * 
	 * @return job description.
	 */
	public String getDescription() {
		return myDescription;
	}

	/**
	 * Change the job description.
	 * 
	 * @param theDescription the job description.
	 */
	public void setDescription(final String theDescription) {
		myDescription = theDescription;
	}

	/**
	 * Return the job start date and time.
	 * 
	 * @return the job start date and time.
	 */
	public Calendar getStartDateTime() {
		return myStartDateTime;
	}

	/**
	 * Change the job start date and time.
	 * 
	 * @param theDate the job start date and time.
	 */
	public void setStartDateTime(final Calendar theStartDateTime) {
		myStartDateTime = theStartDateTime;
	}

	/**
	 * Return the job end date and time.
	 * 
	 * @return the job end date and time.
	 */
	public Calendar getEndDateTime() {
		return myEndDateTime;
	}

	/**
	 * Change the job end date and time.
	 * 
	 * @param theDate the job end date and time.
	 */
	public void setEndDateTime(final Calendar theEndDateTime) {
		myEndDateTime = theEndDateTime;
	}

	/**
	 * Return the park name where the job will take a place.
	 * 
	 * @return the park name.
	 */
	public String getParkName() {
		return myParkName;
	}

	/**
	 * Change the park name to the new given name.
	 * 
	 * @param theParkName the new park name
	 */
	public void setParkName(String theParkName) {
		myParkName = theParkName;
	}

	/**
	 * The location where the job will take a place.
	 * 
	 * @return the location of the job.
	 */
	public String getLocation() {
		return myLocation;
	}

	/**
	 * Change the location of the job to the given location.
	 * 
	 * @param theLocation
	 */
	public void setLocation(String theLocation) {
		myLocation = theLocation;
	}

	/**
	 * The number of volunteers required for light workload.
	 * 
	 * @return the number of volunteers for the light workload.
	 */
	public int getLight() {
		return myLight;
	}

	/**
	 * Change the number of volunteers that required for light workload.
	 * 
	 * @param theLight the number of volunteers to do the light workload.
	 */
	public void setLight(int theLight) {
		myLight = theLight;
	}

	/**
	 * The number of volunteers required for medium workload.
	 * 
	 * @return the number of volunteers for the medium workload.
	 */
	public int getMedium() {
		return myMedium;
	}

	/**
	 * Change the number of volunteers that required for medium workload.
	 * 
	 * @param theLight the number of volunteers to do the medium workload.
	 */
	public void setMedium(int theMedium) {
		myMedium = theMedium;
	}

	/**
	 * The number of volunteers required for heavy workload.
	 * 
	 * @return the number of volunteers for the heavy workload.
	 */
	public int getHeavy() {
		return myHeavy;
	}

	/**
	 * Change the number of volunteers that required for heavy workload.
	 * 
	 * @param theLight the number of volunteers to do the heavy workload.
	 */
	public void setHeavy(int theHeavy) {
		myHeavy = theHeavy;
	}

	/**
	 * The status of the job false is it is active and true otherwise.
	 * 
	 * @return the status of the job.
	 */
	public boolean status() {
		return myStatus;
	}
	
	/**
	 * Cancel this job.
	 */
	public void cancel() {
		myStatus = true;
	}
	
	/**
	 * Return all volunteers for this job.
	 * 
	 * @return all volunteers.
	 */
	public TreeSet<String> getVolunteers() {
		return myVolunteersList;
	}
	
	public void addVolunteer(final String theVolunteer) {
		myVolunteersList.add(theVolunteer);
	}

	/**
	 * Return the minimum number of volunteers required for this job.
	 * 
	 * @return minimum number of volunteers.
	 */
	public int getMinimumVolunteers() {
		return myMinimumVolunteers;
	}

	/**
	 * Change the minimum number of volunteers required for this job.
	 * 
	 * @param theMinimumVolunteers minimum number of volunteers.
	 */
	public void setMinimumVolunteers(final int theMinimumVolunteers) {
		myMinimumVolunteers = theMinimumVolunteers;
	}
	
	/**
	 * Return the total number of volunteers.
	 * 
	 * @return total number of volunteers.
	 */
	public int getTotalVolunteers() {
		return myVolunteersList.size();
	}
	
	@Override
	public String toString() {
		
		String symbol = ", ";
		
		StringBuffer sb = new StringBuffer();
		sb.append(myDescription);
		sb.append(symbol);
		sb.append(myStartDateTime);
		sb.append(symbol);
		sb.append(myEndDateTime);
		sb.append(symbol);
		sb.append(myParkName);
		sb.append(symbol);
		sb.append(myLocation);
		sb.append(symbol);
		sb.append(myLight);
		sb.append(symbol);
		sb.append(myMedium);
		sb.append(symbol);
		sb.append(myHeavy);
		sb.append(symbol);
		sb.append(myVolunteersList.toString());
		sb.append(symbol);
		sb.append(myMinimumVolunteers);
		return sb.toString();
	}

}
