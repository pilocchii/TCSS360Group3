

package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


/**
 * 
 * @author Aashish Kumar
 *
 */
public class ParkManager extends User {

	private final int MAXIMUM_NUMBER_OF_DAYS = 3;
	private final int MAXIMUM_NUMBER_OF_PENDING_JOBS = 20	;
	private final int MAXIMUM_NUMBER_OF_JOB_END_DAYS = 75;
	

	public ParkManager(String theFirstName, String theLastName, String theEmail, String thePhone) {
		super(theFirstName, theLastName, theEmail, thePhone);
	}

	public class numJobsAtMaximumException extends Exception {
		public numJobsAtMaximumException() {}
		public numJobsAtMaximumException(String message) {
			super(message);
		}
	}
	public class managerJobDaysException extends Exception {
		public managerJobDaysException() {}
		public managerJobDaysException(String message) {
			super(message);
		}
	}
	public class managerJobEndDaysException extends Exception {
		public managerJobEndDaysException() {}
		public managerJobEndDaysException(String message) {
			super(message);
		}
	}

	/**
	 * Checks if a job can be created or not.
	 * 
	 * @param candidateJob
	 * @return list of jobs created;
	 */
	public void createNewJob(Job candidateJob, JobCollection jobcollection) 
			throws numJobsAtMaximumException, managerJobDaysException, managerJobEndDaysException {
		
		if(isMaximumJobDays(candidateJob))  {
			//throw new managerJobDaysException();

		}
		if(isMaximumEndDays(candidateJob)) {
			//throw new managerJobEndDaysException();

		}
		if(isNumJobsAtMax(jobcollection)) {
			//throw new numJobsAtMaximumException();
		}
		
		if(jobcollection.getJobCollection().size() == ProgramConstants.MAX_PENDING_JOBS) {
			throw new IllegalStateException("Job Collection is full!");
		
		} else if ((int)((candidateJob.getEndDateTime().getTimeInMillis() - 
				candidateJob.getStartDateTime().getTimeInMillis()) / (1000*60*60*24l)) >= ProgramConstants.MAX_JOB_LENGTH) {
			throw new IllegalArgumentException("Job must not be longer than " +  ProgramConstants.MAX_JOB_LENGTH + " days!");
		
		} else if ((int)((candidateJob.getEndDateTime().getTimeInMillis() - 
				candidateJob.getStartDateTime().getTimeInMillis()) / (1000*60*60*24l)) >= ProgramConstants.MAX_JOB_OFFSET) {
			throw new IllegalArgumentException("Job must not start more than " 
				+ ProgramConstants.MAX_JOB_OFFSET + " days from now!");
		}
		
		myJobsList.add(candidateJob.getJobId());
		jobcollection.addJob(candidateJob);
	}
	
	public boolean isNumJobsAtMax(JobCollection jobcollection) {
		return (jobcollection.getJobCollection().size() < MAXIMUM_NUMBER_OF_PENDING_JOBS);
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

		return compare2Date(theCondidateJob.getStartDateTime(), maximumDate) <=0;
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

		return compare2Date(theCondidateJob.getEndDateTime(), maximumStartDate) <=0;
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

		//The second date without the time.
		Calendar secondDate = Calendar.getInstance();
		secondDate.set(theSecondDate.get(Calendar.YEAR), 
				theSecondDate.get(Calendar.MONTH), 
				theSecondDate.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

		// Compare the two dates and return the result.
		return firstDate.compareTo(secondDate);

	}




}

