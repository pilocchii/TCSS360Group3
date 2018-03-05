package urbanparks.model;

import java.util.ArrayList;

public class Volunteer extends User {

	private static final long serialVersionUID = 8513473946371713321L;

	/**
	 * Constructor for Volunteer class
	 */
	public Volunteer(String firstName, String lastName, String email, String phoneNum) {
		super(firstName, lastName, email, phoneNum);
	}
	
	/**
	 * Signs up this volunteer for a job; returns a boolean value
	 * true if the volunteer signed up successfully, false otherwise.
	 * 
	 * @param candidateJob the job to be signed up for
	 * @return true if the volunteer signed up successfully, false otherwise
	 */
	public boolean signUpForJob(Long candidateJobId) {
		boolean flag = false;
		associatedJobs.add(candidateJobId);
		if(associatedJobs.contains(candidateJobId)) {
			flag = true; //both job and volunteer added each other
		}
		return flag;
	}
	
	/**
	 * Removes the job from the list of jobs the volunteer is signed up for.
	 * NOTE: THIS DOES NOT REMOVE THE VOLUNTEER FROM THE JOB'S LIST!
	 * Pre: The jobid is associated with this user.
	 * @param jobid the id to unvolunteer from
	 */
	public void unVolunteerFromJob(Long jobid) {
		associatedJobs.remove(jobid);
	}
	
	/**
	 * Checks if a candidate job's start/end days equal those of any other 
	 * job this Volunteer is signed up for
	 * Pre: job and jobcollection are non-null
	 * 
	 * @param candidateJob the job to check for overlap
	 * @param jobCollection the list of all jobs in the system
	 * @return true if the job overlaps with any job the user is signed up for, false otherwise
	 */
	public boolean doesJobOverlap(Job candidateJob, JobCollection jobCollection) {
		for (long i : associatedJobs) {
			Job j = jobCollection.findJob(i);
			if (j.doJobsOverlap(candidateJob)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Constructs an arraylist with all the jobs the user is currently signed up for.
	 * Pre: Jobcollection is non-null
	 * @param jobCollection the list of all jobs in the system
	 * @return an arraylist with all jobs this user is signed up for
	 */
	public ArrayList<Job> getSignedUpJobs(JobCollection jobCollection) {
	    ArrayList<Job> signedUpJobs = new ArrayList<Job>();
		for (long i : associatedJobs) {
			signedUpJobs.add(jobCollection.findJob(i));
		}
		return signedUpJobs;
	}
}
