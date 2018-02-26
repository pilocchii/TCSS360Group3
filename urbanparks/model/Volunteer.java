package urbanparks.model;

public class Volunteer extends User {

	private static final long serialVersionUID = 8513473946371713321L;

	/**
	 * Constructor for Volunteer class
	 */
	public Volunteer(String firstName, String lastName, String email, String phoneNum) {
		super(firstName, lastName, email, phoneNum);
	}
	
	/**
	 * Signs up this volunteer for a job
	 * 
	 * @param candidateJob the job to be signed up for
	 * @throws volunteerJobOverlapException
	 * @throws jobSignupTooLateException
	 * @throws alreadySignedUpException 
	 */
	public void signUpForJob(Job candidateJob) {
		associatedJobs.add(candidateJob.getJobId());
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
		for (int i : associatedJobs) {
			Job j = jobCollection.findJob(i);
			if (j.doJobsOverlap(candidateJob)) {
				return true;
			}
		}
		return false;
	}
}
