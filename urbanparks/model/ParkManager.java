package urbanparks.model;

public class ParkManager extends User {
	
	private static final long serialVersionUID = 3766166317518887283L;

	/**
	 * Constructor for ParkManager
	 * 
	 * @param theFirstName
	 * @param theLastName
	 * @param theEmail
	 * @param thePhone
	 */
	public ParkManager(String theFirstName, String theLastName, String theEmail, String thePhone) {
		super(theFirstName, theLastName, theEmail, thePhone);
	}

	/**
	 * Creates a new job
	 * 
	 * @param candidateJob The job to be created
	 */
	public void createNewJob(Job candidateJob, JobCollection jobCollection) {
		associatedJobs.add(candidateJob.getJobId());
		new JobCollection().addJob(candidateJob);
	}
}