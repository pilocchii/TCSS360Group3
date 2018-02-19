package urbanparks.model;

import java.util.Set;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import java.util.Map.Entry;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import static urbanparks.model.ProgramConstants.*;


public class JobCollection implements Serializable {
	
	private static HashMap<Integer, Job> jobsList = new HashMap<Integer, Job>();
	private static SecureRandom random;
	
	public JobCollection() throws NoSuchAlgorithmException {
		random = SecureRandom.getInstance("SHA1PRNG");
		//jobsList = new HashMap<Integer, Job>(ProgramConstants.MAX_PENDING_JOBS);
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
		j.setJobId(jobID);
		jobsList.put(jobID, j);
	}
	
	public ArrayList<Job> getSortedJobs() {
		ArrayList<Job> sortedHashMap = new ArrayList<Job>();
		for(Map.Entry<Integer, Job> entry : jobsList.entrySet()) {
		      sortedHashMap.add(entry.getValue());
		}

		Collections.sort(sortedHashMap, new Comparator<Job>() {
	        @Override
	        public int compare(Job job1, Job job2)
	        {
	            return  job1.getStartDateTime().compareTo(job2.getEndDateTime());
	        }
	    });
		return sortedHashMap;
	}
	
	public HashMap<Integer, Job> getJobCollection() {
		return jobsList;
	}
	
}
