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
	public boolean signUpForJob(Job candidateJob) {
		boolean flag = false;
		candidateJob.addVolunteer(getEmail());
		associatedJobs.add(candidateJob.getJobId());
		if(associatedJobs.contains(candidateJob.getJobId())) {
			flag = true; //both job and volunteer added each other
		}
		return flag;
	}
	
	public void unVolunteerFromJob(Job job) {
		job.removeVoluneer(getEmail());
		associatedJobs.remove(job.getJobId());
	}
	
	/**
	 * Checks if a candidate job's start/end days equal those of any other 
	 * job this Volunteer is signed up for
	 * 
	 * @param candidateJob
	 * @param jobCollection
	 * @return
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
	
	
	public ArrayList<Job> getSignedUpJobs(JobCollection jobCollection) {
	    ArrayList<Job> signedUpJobs = new ArrayList<Job>();
		for (long i : associatedJobs) {
			signedUpJobs.add(jobCollection.findJob(i));
		}
		return signedUpJobs;
	}
}
