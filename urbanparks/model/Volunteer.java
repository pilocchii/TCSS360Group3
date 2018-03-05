package urbanparks.model;

import java.util.ArrayList;

/**
 * Class that represent a volunteer with all its functionality.
 */
public class Volunteer extends User {

	private static final long serialVersionUID = 8513473946371713321L;

	/**
	 * Constructor for Volunteer class.
	 */
	public Volunteer(String firstName, String lastName, String email, String phoneNum) {
		super(firstName, lastName, email, phoneNum);
	}

	/**
	 * Signs up this volunteer for a job; returns a boolean value
	 * true if the volunteer signed up successfully, false otherwise.
	 * Precondition : The given job should not be NULL.
	 * @param candidateJob the job to be signed up for.
	 * @return true if the volunteer signed up successfully, false otherwise.
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

	/**
	 * Unvolunteer from the given job if the volunteer is already signed up for.
	 * Precondition : The given job should not be NULL.
	 * Postcondition: The job has been removed from the volunteer job list.
	 * @param job the job to remove from the volunteer signed up jobs.
	 */
	public void unVolunteerFromJob(Job job) {
		job.removeVoluneer(getEmail());
		associatedJobs.remove(job.getJobId());
	}

	/**
	 * Checks if a candidate job's start/end days equal those of any other 
	 * job this Volunteer is signed up for
	 * Precondition : The given job should not be NULL.
	 * @param candidateJob the job to check if its overlap with volunteer signed up jobs.
	 * @param jobCollection the job collection.
	 * @return true if the jobs overlap with one of the jobs that 
	 * the volunteer already signed up for.
	 */
	public boolean doesJobOverlap(Job candidateJob, JobCollection jobCollection) {
		for (long jobID : associatedJobs) {
			Job job = jobCollection.findJob(jobID);
			if (job.doJobsOverlap(candidateJob)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns all the jobs that this volunteer signed up for.
	 * Precondition : The given jobCollection should not be NULL.
	 * @param jobCollection the job collection.
	 * @return all jobs that this volunteer signed up for.
	 */
	public ArrayList<Job> getSignedUpJobs(JobCollection jobCollection) {
		ArrayList<Job> signedUpJobs = new ArrayList<Job>();
		for (long i : associatedJobs) {
			signedUpJobs.add(jobCollection.findJob(i));
		}
		return signedUpJobs;
	}
}
