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
	
	
	public JobCollection() {
		
		jobsList = new HashMap<Integer, Job>(ProgramConstants.MAX_PENDING_JOBS);

	}
	
	public JobCollection(String filename) {
		
		jobsList = new HashMap<Integer, Job>(ProgramConstants.MAX_PENDING_JOBS);
		
		FileInputStream f_in = null;
		try {
			f_in = new 
					FileInputStream("jobs.data");
		} catch (FileNotFoundException e1) {
			System.out.println("File not found!");
		}

			//Make an ObjectInputStream to read with
			ObjectInputStream obj_in = null;
			try {
				obj_in = new ObjectInputStream (f_in);
			} catch (IOException e1) {
				System.out.println("File not found!");
			}

			Object obj;
			
			//Read all jobs
			try {
				  while (true) {
				    obj = obj_in.readObject();
				    // make sure its a user
					if (obj instanceof Job) {
						// Cast object to a user
						Job j = (Job) obj;

						// Add it to the map
						jobID++;
						jobsList.put(jobID, j);
					}
				  }
				} catch (EOFException e) {
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
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
		jobsList.put(jobID, j);
	}
	
	public void saveJobs() throws IOException {
		// Make a file
		FileOutputStream f_out = new 
			FileOutputStream("joblist.data");

		// Make an ObjectOutputStream
		ObjectOutputStream obj_out = new
			ObjectOutputStream (f_out);

		// Write jobs to the disk
		Job j;
		Set<Map.Entry<Integer,Job>> s = jobsList.entrySet();
		for(Entry<Integer, Job> e : s) {
			j = e.getValue(); 
			obj_out.writeObject(j);
		}

		
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
