package urbanparks.model;

import static urbanparks.model.ModelConstants.JOB_DATA_FILE;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class JobCollection implements Serializable {
	
	private static final long serialVersionUID = -5732921162292711504L;

	private static int currentJobId;

	private HashMap<Long, Job> jobsList;
	
	/**
	 * Default constructor for JobCollection
	 */
	public JobCollection() {
		jobsList = new HashMap<Long, Job>();
		currentJobId = ModelConstants.DEFAULT_JOB_ID;
	}
	
	/**
	 * Checks if the number of pending jobs is at capacity
	 * 
	 * @return true if the number of pending jobs is at capacity, false otherwise.
	 */
	public boolean isNumJobsAtMaximum() {
		return getPendingJobsCount() >= ModelConstants.getMaxPendingJobs();
	}
	
	/**
	 * Generate a new unique jobID using the increment of the current jobID.
	 * postcondition: ID must be unique to all other job IDs in the system 
	 *  
	 * @return a new valid unique JobID
	 */
	public static int generateNewJobID() {
		currentJobId++;
		return currentJobId;
	}
	
	/**
	 * Adds a job to the job hash map.
	 * 
	 * PRECONDITION: HASHMAP FULL? possible?
	 * @param job The job to add.
	 */
	public void addJob(Job job) {
		jobsList.put(job.getJobId(), job);
	}
	
	/**
	 * Finds a job in the hash map
	 * 
	 * @param id The ID of the job
	 * @return The job that was found. Can be null to indicate an invalid ID.
	 */
	public Job findJob(long id) {
		return jobsList.get(id);
	}
	
	/**
	 * Returns an int representing the number of PENDING jobs in the collection.
	 * Pending means the job is not cancelled and has not yet ended.
	 * @return Pending jobs in the system
	 */
	public int getPendingJobsCount() {
		int pendingCount = 0;
		for(Map.Entry<Long, Job> entry : jobsList.entrySet()) {
			Job job = entry.getValue();
			if(!job.getIsCancelled() && !job.hasJobEnded()) {
				pendingCount++;
			}
		}
		return pendingCount;
	}
	
	/**
	 * Gets a sorted list of jobs available for a specific volunteer to sign up for.
	 * 
	 * @param volunteer The volunteer to check availability for
	 * @return The list of jobs that volunteer can sign up for
	 */
	public ArrayList<JobAvailability> getAvailableForSignup(Volunteer volunteer) {
		ArrayList<JobAvailability> availableJobs = new ArrayList<JobAvailability>();
		for(Map.Entry<Long, Job> entry : jobsList.entrySet()) {
			Job job = entry.getValue();
			// no jobs that have started should be displayed to user
			if (!job.hasJobStarted()) {
				JobAvailability ja = new JobAvailability(job);
				// check the signing up for job business rules
				// jobs that violate business rules are marked as unavailable
				if (!job.getIsCancelled() && !volunteer.doesJobOverlap(job, this) && job.isSignupEarlyEnough()) {
					ja.setIsAvailable(true);
				} else {
					ja.setIsAvailable(false);
				}
				availableJobs.add(ja);
			}
		}
		sortJobsByStartDate(availableJobs);
		return availableJobs;
	}
	
	public ArrayList<JobAvailability> getAvailableForUnvolunteer(Volunteer volunteer) {
		ArrayList<JobAvailability> availableJobs = new ArrayList<JobAvailability>();
		for(Map.Entry<Long, Job> entry : jobsList.entrySet()) {
			Job job = entry.getValue();
			JobAvailability ja = new JobAvailability(job);
			// check the unvolunteer from job business rules
			// jobs that violate business rules are marked as unavailable
			if (volunteer.isAssociatedWithJob(job.getJobId())) {
				if (!job.getIsCancelled() && job.isUnvolunteerEarlyEnough()) {
					ja.setIsAvailable(true);
				} else {
					ja.setIsAvailable(false);
				}
				availableJobs.add(ja);
			}
		}
		sortJobsByStartDate(availableJobs);
		return availableJobs;
	}
	
	/**
	 * Gets a sorted list of jobs available for a specific park manager to 
	 * view these jobs which they are in the future.
	 * 
	 * @param parkManager The park manager.
	 * @return The list of jobs that park manager created.
	 */
	public ArrayList<JobAvailability> getAvailableForUnsubmit(ParkManager parkManager) {
		ArrayList<JobAvailability> availableJobs = new ArrayList<JobAvailability>();
		for(Map.Entry<Long, Job> entry : jobsList.entrySet()) {
			Job job = entry.getValue();
			if (parkManager.isAssociatedWithJob(job.getJobId())) {
				JobAvailability ja = new JobAvailability(job);
				// check unsubmitting job business rules
				// jobs that violate business rules are marked as unavailable
				if (parkManager.isAssociatedWithJob(job.getJobId())) {
					if (!job.getIsCancelled() && job.isUnsubmitEarlyEnough()) {
						ja.setIsAvailable(true);
					} else {
						ja.setIsAvailable(false);
					}
					availableJobs.add(ja);
				}
			}
		}
		sortJobsByStartDate(availableJobs);
		return availableJobs;
	}
	
	/**
	 * Gets all jobs whose start or end times are between the 2 times, inclusive.
	 * @param lowerBound the lower bound which the jobs' start or end times can't be below
	 * @param upperBound the upper bound which the jobs' start or end times can't be above
	 * @return An ArrayList of all jobs that are between the dates
	 */
	public ArrayList<JobAvailability> getJobsBetween2DateTimes(LocalDateTime lowerBound, LocalDateTime upperBound) {
		final ArrayList<JobAvailability> availableJobs = new ArrayList<JobAvailability>();
		for(Map.Entry<Long, Job> entry : jobsList.entrySet()) {
			Job job = entry.getValue();
			JobAvailability ja = new JobAvailability(job);
			if (job.isBetween2DatesInclusive(lowerBound, upperBound)) {
				availableJobs.add(ja);
			}
		}
		sortJobsByStartDate(availableJobs);
		return availableJobs;
	}
	
	/**
	 * Sorts a list of jobs by start date, descending.
	 * 
	 * @param jobs The jobs to sort
	 */
	private void sortJobsByStartDate(ArrayList<JobAvailability> jobs) {
		Collections.sort(jobs, new Comparator<JobAvailability>() {
	        @Override
	        public int compare(JobAvailability job1, JobAvailability job2) {
	            return job1.getJob().getStartDateTime().compareTo(job2.getJob().getEndDateTime());
	        }
	    });
	}
	
	/**
	 * Saves this job collection's data
	 * 
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
	 * 
	 * @throws IOException 
	 * @throws ClassNotFoundException */
	@SuppressWarnings("unchecked")
	public void loadData() throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(JOB_DATA_FILE);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object object = ois.readObject();
		jobsList = (HashMap<Long, Job>) object;
		currentJobId = jobsList.size();
		ois.close();
	}
}
