package urbanparks.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import static urbanparks.model.Constants.*;

public class JobCollection implements Serializable {
	
	private static final long serialVersionUID = -5732921162292711504L;
	
	private HashMap<Integer, Job> jobsList;
	private SecureRandom random;
	
	/**
	 * Default constructor for JobCollection
	 */
	public JobCollection() {
		jobsList = new HashMap<Integer, Job>();
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks if the number of pending jobs is at capacity
	 * @return true if the number of pending jobs is at capacity, false otherwise.
	 */
	public boolean isNumJobsAtMaximum() {
		// TODO: not actually pending jobs...
		int pendingJobs = jobsList.size();
		return pendingJobs >= Constants.MAX_PENDING_JOBS;
	}
	
	/**
	 * Adds a job to the job hash map.
	 * PRECONDITION: HASHMAP FULL? possible?
	 * @param job The job to add.
	 */
	public void addJob(Job job) {
		int jobID = random.nextInt();
		job.setJobId(jobID);
		jobsList.put(jobID, job);
	}
	
	/**
	 * Finds a job in the hash map
	 * @param id The ID of the job
	 * @return The job that was found. Can be null to indicate an invalid ID.
	 */
	public Job findJob(Integer id) {
		return jobsList.get(id);
	}
	
	/**
	 * Gets a sorted list of jobs available for a specific volunteer to sign up for.
	 * @param volunteer The volunteer to check availability for
	 * @return The list of jobs that volunteer can sign up for
	 */
	public ArrayList<Job> getAvilableJobs(Volunteer volunteer) {
		ArrayList<Job> availableJobs = new ArrayList<Job>();
		for(Map.Entry<Integer, Job> entry : jobsList.entrySet()) {
			Job j = entry.getValue();
			// check the signing up for job business rules
			boolean isAvailable = true;
			if (volunteer.doesJobOverlap(j, this) || !Job.isSignupEarlyEnough(j)) {
				isAvailable = false;
			}
			j.setIsAvailable(isAvailable);
			availableJobs.add(j);
		}
		sortJobsByStartDate(availableJobs);
		return availableJobs;
	}
	
	/**
	 * Sorts a list of jobs by start date, descending.
	 * @param jobs The jobs to sort
	 */
	private void sortJobsByStartDate(ArrayList<Job> jobs) {
		Collections.sort(jobs, new Comparator<Job>() {
	        @Override
	        public int compare(Job job1, Job job2)
	        {
	            return  job1.getStartDateTime().compareTo(job2.getEndDateTime());
	        }
	    });
	}
	
	/**
	 * Saves this job collection's data
	 * @throws IOException
	 */
	public void saveData() throws IOException {
		FileOutputStream fos = new FileOutputStream(JOB_DATA_FILE);
		ObjectOutputStream ois = new ObjectOutputStream(fos);
		ois.writeObject(jobsList);
		ois.close();
	}
	
	/**
	 * Loads this job collection's data
	 * @throws IOException 
	 * @throws ClassNotFoundException */
	@SuppressWarnings("unchecked")
	public void loadData() throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(JOB_DATA_FILE);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object object = ois.readObject();
		jobsList = (HashMap<Integer, Job>) object;
		ois.close();
	}
	
}
