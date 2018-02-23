package urbanparks.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * This class represent a user 
 */
public abstract class User implements Serializable {

	private static final long serialVersionUID = -4751543109134325926L;
	
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNum;

	/** List of all jobs that user accepted or created. */
	protected ArrayList<Integer> associatedJobs;

	/**
	 * Constructor to initialize all the fields for this job.
	 * 
	 * @param theFirstName the first name of the user.
	 * @param theLastName the last name of the user.
	 * @param theEmail the email address of the user.
	 * @param thePhone the phone number of the user.
	 */
	public User(String theFirstName, String theLastName, String theEmail, String thePhone) {
		setFirstName(theFirstName);
		setLastName(theLastName);
		setEmail(theEmail);
		setPhoneNumber(thePhone);
		associatedJobs = new ArrayList<Integer>();
	}

	/**
	 * Return the first name of the user.
	 * 
	 * @return the first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Change the first name of the user.
	 * 
	 * @param theFirstName the new first name.
	 */
	public void setFirstName(String theFirstName) {
		firstName = theFirstName;
	}

	/**
	 * Return the last name of the user.
	 * 
	 * @return the last name.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Change the last name of the user.
	 * 
	 * @param theLastName the new last name.
	 */
	public void setLastName(String theLastName) {
		lastName = theLastName;
	}

	/**
	 * Return the email address of the user.
	 * 
	 * @return the email address.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Change the email address of the user.
	 * 
	 * @param theEmail the new email address.
	 */
	public void setEmail(String theEmail) {
		email = theEmail;
	}

	/**
	 * Return the phone number of the user.
	 * 
	 * @return the phone number.
	 */
	public String getPhoneNumber() {
		return phoneNum;
	}

	/**
	 * Change the phone number of the user.
	 * 
	 * @param thePhone the phone number.
	 */
	public void setPhoneNumber(String thePhone) {
		phoneNum = thePhone;
	}

	/**
	 * Add the given job key to the list of jobs.
	 * 
	 * @param theJobKey the new job to be added to the list.
	 */
	public void addJob(int theJobKey) {
		associatedJobs.add(theJobKey);
	}

	/**
	 * Return the jobs list for this user.
	 * 
	 * @return list of all jobs.
	 */
	public ArrayList<Integer> getJobsList() {
		return associatedJobs;
	}

	/**
	 * Return the total number of jobs for this user.
	 * 
	 * @return total number of jobs.
	 */
	public int getTotalJobs() {
		return associatedJobs.size();
	}

}
