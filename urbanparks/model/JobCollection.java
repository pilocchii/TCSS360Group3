package model;

import java.util.Set;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class JobCollection {

	private HashMap<Integer, Job> jobsList;
	private int jobID = 0;
	
	private static final String JOB_DATA_FILE = "joblist.data";
	
	public JobCollection() {
		jobsList = new HashMap<Integer, Job>(ProgramConstants.MAX_PENDING_JOBS);
	}
	
	public void saveData() throws IOException {
		FileOutputStream fos = new FileOutputStream(JOB_DATA_FILE);
		ObjectOutputStream ois = new ObjectOutputStream(fos);
		ois.writeObject(jobsList);
	}
	
	/**Makes a new user collection with a capacity of 99;
	 * Reads in the serialized user objects and stores them in 
	 * the collection.
	 * @throws IOException 
	 * @throws ClassNotFoundException */
	public void loadData() throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(JOB_DATA_FILE);
		ObjectInputStream ois = new ObjectInputStream(fis);
		jobsList = (HashMap<Integer, Job>)ois.readObject();
	}
	
	public Job findJob(Integer id) {
		return jobsList.get(id);
	}
	
	public void addJob(Job j) {
		if(jobsList.size() == ProgramConstants.MAX_PENDING_JOBS) {
			throw new IllegalStateException("Job Collection is full!");
		} else if ((int)((j.getEndDateTime().getTimeInMillis() - 
				j.getStartDateTime().getTimeInMillis()) / (1000*60*60*24l)) >= ProgramConstants.MAX_JOB_LENGTH) {
			throw new IllegalArgumentException("Job must not be longer than " +  ProgramConstants.MAX_JOB_LENGTH + " days!");
		} else if ((int)((j.getEndDateTime().getTimeInMillis() - 
				j.getStartDateTime().getTimeInMillis()) / (1000*60*60*24l)) >= ProgramConstants.MAX_JOB_OFFSET) {
			throw new IllegalArgumentException("Job must not start more than " 
				+ ProgramConstants.MAX_JOB_OFFSET + " days from now!");
		}

		jobID++;
		j.setJobId(jobID);
		jobsList.put(jobID, j);
	}
	
	public Job[] getSortedJobs() {
		Job jobs[] = new Job[jobsList.size()];
		Job j;
		int i = 0;
		Set<Map.Entry<Integer,Job>> s = jobsList.entrySet();
		for(Entry<Integer, Job> e : s) {
			j = e.getValue();
			for(Entry<Integer, Job> other : s) {
				if(j.getStartDateTime().compareTo(other.getValue().getStartDateTime()) < 0) {
					j = other.getValue();
				}
			}
			jobs[i] = j;
			i++;
		}
		return jobs;
	}
	
	public HashMap<Integer, Job> getJobCollection() {
		return jobsList;
	}
	
}
