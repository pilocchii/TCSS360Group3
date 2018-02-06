package classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Class that represents a volunteer with all his/here personal information and the jobs that accepted.
 * 
 * @author Abderisaq Tarabi
 *
 */
public class Volunteer extends User implements Serializable {

	/** The minimum number of days. */
	private static final int MINIMUM_NUMBER_OF_DAYS = 2;
	
	/** serialVersionUID */
	private static final long serialVersionUID = -2648387901303116577L;
	
	/** A list of accepted jobs. */
	private ArrayList<Job> acceptedJobs;
	
	/**
	 * Constructor to initialize all the fields of the volunteer.
	 * 
	 * @param theFirstName the volunteer first name.
	 * @param theLastName the volunteer last name.
	 * @param theEmail the volunteer email address.
	 * @param ThePhone the volunteer phone number.
	 */
	public Volunteer(String theFirstName, String theLastName, String theEmail, String ThePhone) {
		super(theFirstName, theLastName, theEmail, ThePhone);
		acceptedJobs = new ArrayList<Job>();
	}

	/**
	 * Sign up method that allow the volunteer to sign up for jobs.
	 * 
	 * @param theJob job to accept
	 * @return the status of sign up for the job.
	 */
	public boolean signUpForJob(Job theJob) {
		
		Job acceptedJob;
		
		Calendar todayDate = Calendar.getInstance();
		todayDate.add(Calendar.DAY_OF_MONTH, MINIMUM_NUMBER_OF_DAYS);
		
		// The job begins less than minimum number of calendar days from the current date.
		if (compare2Date(theJob.getStartDateTime(), todayDate) < 0) return false;

		// Comparing the given job with accepted jobs.
		for (int index = 0; index < acceptedJobs.size(); index++) {
			
			acceptedJob = acceptedJobs.get(index);
			
			// The job starts the same day as the end of some accepted job.
			if (compare2Date(theJob.getStartDateTime(), acceptedJob.getEndDateTime()) == 0) return false;
			
			// The job ends the the same day as the start of some accepted job.
			if (compare2Date(theJob.getEndDateTime(), acceptedJob.getStartDateTime()) == 0) return false;
			
		}
		
		// Add the volunteer to the list of the volunteer for this job.
		theJob.addVolunteer(this);
		
		// Add the job to the list of accepted jobs.
		acceptedJobs.add(theJob);
		
		// Return true as all conditions are passed.
		return true;
	}
	
	/**
	 * Comparing two dates by Year, Month and day.
	 * 
	 * @param theFirstDate the first date.
	 * @param theSecondDate the second date.
	 * @return the compare result.
	 */
	public int compare2Date(final Calendar theFirstDate, final Calendar theSecondDate) {
		
		// The first date without the time.
		Calendar firstDate = Calendar.getInstance();
		firstDate.set(theFirstDate.get(Calendar.YEAR), 
					  theFirstDate.get(Calendar.MONTH), 
					  theFirstDate.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		
		// The second date without the time.
		Calendar secondDate = Calendar.getInstance();
		secondDate.set(theSecondDate.get(Calendar.YEAR), 
					   theSecondDate.get(Calendar.MONTH), 
					   theSecondDate.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		
		// Compare the two dates and return the result.
		return firstDate.compareTo(secondDate);
		
	}

}
