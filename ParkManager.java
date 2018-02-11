

package model;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * 
 * @author Aashish Kumar
 *
 */
public class ParkManager extends User {

	/** The park name. */
	private String myParkName;
	
	/** The park zipCode. */
	private int myParkZipCode;
	
	private final int MAXIMUM_NUMBER_OF_DAYS = 3;
	
	private final int MAXIMUM_NUMBER_OF_PENDING_JOBS = 20	;
	
	private final int MAXIMUM_NUMBER_OF_JOB_END_DAYS = 75;
	
	private ArrayList<Job> jobsCreated;
	
	
	
	public ParkManager(String theFirstName, String theLastName, String theEmail, String thePhone, String theParkName, int theParkZipCode) {
		super(theFirstName, theLastName, theEmail, thePhone);
		setParkName(theParkName);
		setParkZipCode(theParkZipCode);
		
	}
	
	/**
	 * Checks if a job can be created or not.
	 * 
	 * @param candidateJob
	 * @return list of jobs created;
	 */
	public ArrayList<Job> createdANewJob(Job candidateJob) {
		if(isMaximumJobDays(candidateJob) && isMaximumEndDays(candidateJob)) {
			jobsCreated.add(candidateJob);
		}
		return jobsCreated;
		
	}
	
	/**
	 * Check that job cann't be specified if it takes more than the maximum number of days
	 * 
	 * @param theCondidateJob
	 * @return true if job takes less than the maximum number of days.
	 */
	public boolean isMaximumJobDays(Job theCondidateJob) {

		Calendar maximumDate = Calendar.getInstance();
		maximumDate.add(Calendar.DAY_OF_MONTH, MAXIMUM_NUMBER_OF_DAYS);
		

		return compare2Date(theCondidateJob.getStartDateTime(), maximumDate) >= 0;
	}
	
	/**
	 * Check that job cann't be specified whose end date is more than the maximum number of days
	 * 
	 * @param theCondidateJob
	 * @return true if it end exactly or less than the maximum number of days.
	 */
	public boolean isMaximumEndDays(Job theCondidateJob) {

		Calendar maximumStartDate = Calendar.getInstance();
		maximumStartDate.add(Calendar.DAY_OF_MONTH, MAXIMUM_NUMBER_OF_JOB_END_DAYS);
		

		return compare2Date(theCondidateJob.getEndDateTime(), maximumStartDate) >= 0;
	}
	
	
	////public boolean pendingJobs();
	
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

		//The second date without the time.
		Calendar secondDate = Calendar.getInstance();
		secondDate.set(theSecondDate.get(Calendar.YEAR), 
				theSecondDate.get(Calendar.MONTH), 
				theSecondDate.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

		// Compare the two dates and return the result.
		return firstDate.compareTo(secondDate);

	}

	public String getParkName() {
		return myParkName;
	}

	public void setParkName(String myParkName) {
		this.myParkName = myParkName;
	}

	public int getParkZipCode() {
		return myParkZipCode;
	}

	public void setParkZipCode(int myParkZipCode) {
		this.myParkZipCode = myParkZipCode;
	}

}
