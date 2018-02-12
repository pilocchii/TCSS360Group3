

package model;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import static model.ProgramConstants.*;


/**
 * 
 */
public class ParkManager extends User {

	public ParkManager(String theFirstName, String theLastName, String theEmail, String thePhone) {
		super(theFirstName, theLastName, theEmail, thePhone);
	}

	public class numJobsAtMaximumException extends Exception {
		public numJobsAtMaximumException() {}
		public numJobsAtMaximumException(String message) {
			super(message);
		}
	}
	public class jobTooLongException extends Exception {
		public jobTooLongException() {}
		public jobTooLongException(String message) {
			super(message);
		}
	}
	public class jobStartTooLongFromNowException extends Exception {
		public jobStartTooLongFromNowException() {}
		public jobStartTooLongFromNowException(String message) {
			super(message);
		}
	}

	/**
	 * Checks if a job can be created or not.
	 * 
	 * @param candidateJob
	 * @return list of jobs created;
	 * @throws NoSuchAlgorithmException 
	 */
	public void createNewJob(Job candidateJob) 
			throws numJobsAtMaximumException, jobTooLongException, jobStartTooLongFromNowException, NoSuchAlgorithmException {

		if(isNumJobsAtMaximum()) {
			throw new numJobsAtMaximumException();
		}

		if (isJobTooLong(candidateJob)) {
			throw new jobTooLongException();
		}
		
		if (doesJobStartTooLongFromNow(candidateJob)) {
			throw new jobStartTooLongFromNowException();
		}
		
		myJobsList.add(candidateJob.getJobId());
		new JobCollection().addJob(candidateJob);
	}
	
	
	public boolean isJobTooLong(Job candidateJob) {
		int jobLength = (int)(candidateJob.getEndDateTime().getTimeInMillis() 
				- candidateJob.getStartDateTime().getTimeInMillis()) / MILLISECONDS_IN_DAY;
		return (jobLength >= ProgramConstants.MAX_JOB_LENGTH);
	}
	
	public boolean doesJobStartTooLongFromNow(Job candidateJob) {
		int daysBeforeJobStart = (int)(candidateJob.getStartDateTime().getTimeInMillis() 
				- System.currentTimeMillis()) / MILLISECONDS_IN_DAY;
		return (daysBeforeJobStart >= ProgramConstants.MAX_DAYS_BEFORE_JOB_START);
	}
	
	public boolean isNumJobsAtMaximum() throws NoSuchAlgorithmException {
		return (new JobCollection().getJobCollection().size() == ProgramConstants.MAX_PENDING_JOBS);
	}
}