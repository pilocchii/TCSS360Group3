package classes;

import java.util.ArrayList;
import java.util.Calendar;

public class Volunteer extends User {

	
	private static final long serialVersionUID = -2648387901303116577L;
	private ArrayList<Job> acceptedJobs;
	public Volunteer(String theFirstName, String theLastName, String theEmail, String ThePhone) {
		super(theFirstName, theLastName, theEmail, ThePhone);
		acceptedJobs = new ArrayList<Job>();
	}
	public void signUpForJob(Job newJob) {
		// TODO Auto-generated method stub
	}
	
	
	
	// =======================================================================================================================
	// Walker - Please merge this code with your code ========================================================================
	
	private final int MINIMUM_NUMBER_OF_DAYS = 2;

	/**
	 * Check weather the given job start more, less or exactly the minimum days.
	 * 
	 * @param theCondidateJob
	 * @return true if it starts exactly or more than the current date and false otherwise.
	 */
	public boolean isMinimumDays(Job theCondidateJob) {
		
		Calendar minimumDate = Calendar.getInstance();
		minimumDate.add(Calendar.DAY_OF_MONTH, MINIMUM_NUMBER_OF_DAYS);
		
		return compare2Date(theCondidateJob.getStartDateTime(), minimumDate) >= 0;
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
	
	// Walker - Please merge this code with your code ========================================================================

	
	
}
