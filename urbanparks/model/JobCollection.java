package model;

import java.util.Set;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public class JobCollection implements Serializable {

	private static final String JOB_DATA_FILE = "joblist.data";
	
	private static HashMap<Integer, Job> jobsList;
	private static SecureRandom random;
	
	public JobCollection() throws NoSuchAlgorithmException {
		random = SecureRandom.getInstance("SHA1PRNG");
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
	
	public static Job findJob(Integer id) {
		return jobsList.get(id);
	}
	
	public void addJob(Job j) {
		int jobID = random.nextInt();
		System.out.println("RANDOM NUM IS " + jobID);
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
