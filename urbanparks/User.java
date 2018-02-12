package urbanparks;

import java.io.Serializable;
import java.util.TreeSet;

/**
 * This class represent a user 
 * 
 * @author Abderisaq Tarabi
 * @version 2/8/2018
 *
 */
public abstract class User implements Serializable {

	private static final long serialVersionUID = -805912316703380684L;

	private String myFirstName;

	private String myLastName;

	private String myEmail;

	private String myPhone;

	/** List of all jobs that user accepted or created depends on the user. */
	private TreeSet<String> myJobsList;


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
		myJobsList = new TreeSet<String>();
	}

	/**
	 * Return the first name of the user.
	 * 
	 * @return the first name.
	 */
	public String getFirstName() {
		return myFirstName;
	}

	/**
	 * Change the first name of the user.
	 * 
	 * @param theFirstName the new first name.
	 */
	public void setFirstName(String theFirstName) {
		myFirstName = theFirstName;
	}

	/**
	 * Return the last name of the user.
	 * 
	 * @return the last name.
	 */
	public String getLastName() {
		return myLastName;
	}

	/**
	 * Change the last name of the user.
	 * 
	 * @param theLastName the new last name.
	 */
	public void setLastName(String theLastName) {
		myLastName = theLastName;
	}

	/**
	 * Return the email address of the user.
	 * 
	 * @return the email address.
	 */
	public String getEmail() {
		return myEmail;
	}

	/**
	 * Change the email address of the user.
	 * 
	 * @param theEmail the new email address.
	 */
	public void setEmail(String theEmail) {
		myEmail = theEmail;
	}

	/**
	 * Return the phone number of the user.
	 * 
	 * @return the phone number.
	 */
	public String getPhoneNumber() {
		return myPhone;
	}

	/**
	 * Change the phone number of the user.
	 * 
	 * @param thePhone the phone number.
	 */
	public void setPhoneNumber(String thePhone) {
		myPhone = thePhone;
	}

	/**
	 * Add the given job key to the list of jobs.
	 * 
	 * @param theJobKey the new job to be added to the list.
	 */
	public void addJob(String theJobKey) {
		myJobsList.add(theJobKey);
	}

	/**
	 * Return the jobs list for this user.
	 * 
	 * @return list of all jobs.
	 */
	public TreeSet<String> getJobsList() {
		return myJobsList;
	}

	/**
	 * Return the total number of jobs for this user.
	 * 
	 * @return total number of jobs.
	 */
	public int getTotalJobs() {
		return myJobsList.size();
	}

}
