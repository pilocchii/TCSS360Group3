package urbanparks.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represent a user 
 */
public abstract class User implements Serializable {

	private static final long serialVersionUID = -4751543109134325926L;
	
	/** First name of user*/
	private String firstName;
	
	/** Last name of user*/
	private String lastName;
	
	/** Email of user for log in*/
	private String email;
	
	/** Phone number of user*/
	private String phoneNum;

	/** List of all jobs that user accepted or created. */
	protected ArrayList<Long> associatedJobs;

	/**
	 * Constructor to initialize all the fields for this job.
	 * 
	 * @param firstName the first name of the user.
	 * @param lastName the last name of the user.
	 * @param email the email address of the user.
	 * @param phone the phone number of the user.
	 */
	public User(String firstName, String lastName, String email, String phone) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNum = phone;
		this.associatedJobs = new ArrayList<Long>();
	}
	
	/**
	 * Return the first name of the user.
	 * @return the first name.
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Return the last name of the user.
	 * @return the last name.
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * Return the email address of the user.
	 * @return the email address.
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Gets this user's phone number.
	 * @return the phone number associated with this user
	 */
	public String getPhoneNum() {
		return phoneNum;
	}
	
	/**
	 * Match the Job Id in with jobs in collections.
	 * @return true if job id matches job in jobs collections.
	 */
	public boolean isAssociatedWithJob(Job job) {
		for (long jobID : associatedJobs) {
			if (jobID == job.getJobId()) {
				return true;
			}
		}
		return false;
	}
}
