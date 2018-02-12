

package model;

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
	public class jobStartIsTooFarAwayException extends Exception {
		public jobStartIsTooFarAwayException() {}
		public jobStartIsTooFarAwayException(String message) {
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
			throws numJobsAtMaximumException, jobTooLongException, jobStartIsTooFarAwayException {

		if(jobcollection.getJobCollection().size() == ProgramConstants.MAX_PENDING_JOBS) {
			throw new numJobsAtMaximumException();
		}
		
		int jobLength = (int)(candidateJob.getEndDateTime().getTimeInMillis() - candidateJob.getStartDateTime().getTimeInMillis()) / MILLISECONDS_IN_DAY;
		if (jobLength >= ProgramConstants.MAX_JOB_LENGTH) {
			throw new jobTooLongException();
		}
		
		int daysBeforeJobStart = (int)(candidateJob.getStartDateTime().getTimeInMillis() - System.currentTimeMillis()) / MILLISECONDS_IN_DAY;
		if (daysBeforeJobStart >= ProgramConstants.MAX_DAYS_BEFORE_JOB_START) {
			throw new jobStartIsTooFarAwayException();
		}
		
		myJobsList.add(candidateJob.getJobId());
		jobcollection.addJob(candidateJob);
	}
}