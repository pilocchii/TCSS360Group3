package urbanparks.model;

public class ParkManager extends User {
	
	private static final long serialVersionUID = 3766166317518887283L;

	/**
	 * Constructor for ParkManager
	 * 
	 * @param theFirstName First name of the park manager user
	 * @param theLastName Last name of the park manager user
	 * @param theEmail Email address associated with the user
	 * @param thePhone The user's phone number
	 */
	public ParkManager(String theFirstName, String theLastName, String theEmail, String thePhone) {
		super(theFirstName, theLastName, theEmail, thePhone);
	}

	/**
	 * Creates a new job
	 * Pre: The jobid to add is the id assoicated with the job being created
	 * 
	 * @param candidateJob The job to be created
	 */
	public void createNewJob(long candidateJobId) {
		associatedJobs.add(candidateJobId);
	}
	
	/**
	 * Cancels the job by calling it's cancel method.
	 * Pre: The job is non-null
	 * @param job the job to cancel
	 */
	public void unSubmitJob(Job job) {
		job.cancelJob();
	}
}