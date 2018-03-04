package urbanparks.model;

import static urbanparks.model.Constants.JOB_DATA_FILE;

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
		currentJobId = Constants.DEFAULT_JOB_ID;
	}
	
	/**
	 * Checks if the number of pending jobs is at capacity
	 * 
	 * @return true if the number of pending jobs is at capacity, false otherwise.
	 */
	public boolean isNumJobsAtMaximum() {
		return getPendingJobsCount() >= Constants.getMaxPendingJobs();
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
	public ArrayList<Job> getAvailableForSignup(Volunteer volunteer) {
		ArrayList<Job> availableJobs = new ArrayList<Job>();
		for(Map.Entry<Long, Job> entry : jobsList.entrySet()) {
			Job job = entry.getValue();
			// check the signing up for job business rules
			if (!job.getIsCancelled() && !volunteer.doesJobOverlap(job, this) && job.isSignupEarlyEnough()) {
				job.setIsAvailable(true);
			} else {
				job.setIsAvailable(false);
			}
			availableJobs.add(job);
		}
		sortJobsByStartDate(availableJobs);
		return availableJobs;
	}
	
	public ArrayList<Job> getAvailableForUnvolunteer(Volunteer volunteer) {
		ArrayList<Job> availableJobs = new ArrayList<Job>();
		for(Map.Entry<Long, Job> entry : jobsList.entrySet()) {
			Job job = entry.getValue();
			// check the unvolunteer from job business rules
			if (volunteer.isAssociatedWithJob(job)) {
				if (!job.getIsCancelled() && job.isUnvolunteerEarlyEnough()) {
					job.setIsAvailable(true);
				} else {
					job.setIsAvailable(false);
				}
				availableJobs.add(job);
			}
		}
		sortJobsByStartDate(availableJobs);
		return availableJobs;
	}
	
	public ArrayList<Job> getJobsBetweenDates(boolean basedOnJobStart, LocalDateTime lowerBound, LocalDateTime upperBound) {
		ArrayList<Job> availableJobs = new ArrayList<Job>();
		for(Map.Entry<Long, Job> entry : jobsList.entrySet()) {
			Job job = entry.getValue();
			boolean meetsLowerBoundCond = job.isJobAfterOrAtDateTime(lowerBound, basedOnJobStart);
			boolean meetsUpperBoundCond = job.isJobBeforeOrAtDateTime(upperBound, basedOnJobStart);
			if (meetsLowerBoundCond && meetsUpperBoundCond) {
				availableJobs.add(job);
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
	public ArrayList<Job> getAvailableForUnsubmit(ParkManager parkManager) {
		ArrayList<Job> availableJobs = new ArrayList<Job>();
		for(Map.Entry<Long, Job> entry : jobsList.entrySet()) {
			Job job = entry.getValue();
			// check unsubmitting job business rules
			if (parkManager.isAssociatedWithJob(job)) {
				if (!job.getIsCancelled() && job.isUnsubmitEarlyEnough()) {
					job.setIsAvailable(true);
				} else {
					job.setIsAvailable(false);
				}
				availableJobs.add(job);
			}
		}
		sortJobsByStartDate(availableJobs);
		return availableJobs;
	}
	
	/**
	 * Gets all available jobs between two given dates.
	 * Precondition: the given two dates should not be null.
	 * 
	 * @return The list of jobs that is between the two dates.
	 */
	public ArrayList<Job> getJobsBetween2Dates(LocalDateTime start, LocalDateTime end) throws Exception {
		final ArrayList<Job> availableJobs = new ArrayList<Job>();
		for(Map.Entry<Long, Job> entry : jobsList.entrySet()) {
			Job job = entry.getValue();
			if (job.isBetween2Dates(start, end)) {
				availableJobs.add(job);
			}
		}
		sortJobsByStartDate(availableJobs);
		return availableJobs;
	}
	
	// TODO: CHECK IF NEEDED WITH NEW GUI
	/**
	 * Sorts a list of jobs by start date, descending.
	 * 
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
	
	public static int getCurrentJobId() {
		return currentJobId;
	}

	public static void incrementCurrentJobId() {
		currentJobId++;
	}
	
	public static void setDefaultJobId() {
		currentJobId = Constants.DEFAULT_JOB_ID;
	}

}
