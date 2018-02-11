import static ProgramConstants.*;

import java.util.Set;
import java.util.Map.Entry;


public class JobCollection {

	private HashMap<String, Job> jobsList;
	
	
	public JobCollection() {
		
		jobsList = new HashMap<String, Job>(ProgramConstants.MAX_PENDING_JOBS);
		// Read from disk using FileInputStream
		FileInputStream f_in = new 
			FileInputStream("myobject.data");

		//Make an ObjectInputStream to read with
		ObjectInputStream obj_in = 
			new ObjectInputStream (f_in);

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
					jobsList.put(j.getParkName(), j);
				}
			  }
			} catch (EOFException e) {
			}

	}
	
	public Job findJob(String name) {
		return jobsList.get(name);
	}
	
	public void saveJobs() {
		// Make a file
		FileOutputStream f_out = new 
			FileOutputStream("joblist.data");

		// Make an ObjectOutputStream
		ObjectOutputStream obj_out = new
			ObjectOutputStream (f_out);

		// Write jobs to the disk
		Job j;
		Set<Map.Entry<String,Job>> s = jobsList.entrySet();
		for(Entry<String, Job> e : s) {
			j = e.getValue(); 
			obj_out.writeObject(j);
		}

		
	}
	
}
