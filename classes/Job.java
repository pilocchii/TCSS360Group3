package classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Class that represents a single job.
 * 
 * @author Abderisaq Tarabi
 *
 */
public class Job implements Serializable {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 928850375626876361L;

	/** The job description. */
	private String myDescription;
	
	/** The job start date and time. */
	private Calendar myStartDateTime;
	
	/** The job end date and time. */
	private Calendar myEndDateTime;
	
	/** The job located at the park. */
	private ParkManager myParkManager;
	
	/** The workload light, medium and heavy. */
	private int[] myWorkLoad;
	
	/** The job is cancelled or not. */
	private boolean myCancelled;
	
	/** A list of all volunteers of this job. */
	private ArrayList<Volunteer> myVolunteersList;
	
	/** The minimum required of volunteers. */
	private int myMinVolunteers;
	
	/**
	 * Constructor to initialize all the fields for this job.
	 * 
	 * @param theDescription the job description.
	 * @param theStartDateTime the job start date and time.
	 * @param theEndDateTime the job end date and time.
	 * @param theParkManager the park.
	 * @param theLight the number of volunteers required for light workload.
	 * @param theMediumm the number of volunteers required for medium workload.
	 * @param theHeavy the number of volunteers required for heavy workload.
	 * @param theMinVolunteers the minimum number of volunteers required for this job.
	 */
	public Job(final String theDescription, final Calendar theStartDateTime, final Calendar theEndDateTime, final ParkManager theParkManager, 
			   final int theLight, final int theMediumm, final int theHeavy, final int theMinVolunteers) {
		setDescription(theDescription);
		setStartDateTime(theStartDateTime);
		setEndDateTime(theEndDateTime);
		setParkManager(theParkManager);
		myWorkLoad = new int[3];
		setWorkLoad(0, theLight);
		setWorkLoad(1, theMediumm);
		setWorkLoad(2, theHeavy);
		myCancelled = false;
		myVolunteersList = new ArrayList<Volunteer>();
		setMinVolunteers(theMinVolunteers);
	}

	/**
	 * Return the job description.
	 * 
	 * @return job description.
	 */
	public String getDescription() {
		return myDescription;
	}

	/**
	 * Change the job description.
	 * 
	 * @param theDescription the job description.
	 */
	public void setDescription(final String theDescription) {
		myDescription = theDescription;
	}

	/**
	 * Return the job start date and time.
	 * 
	 * @return the job start date and time.
	 */
	public Calendar getStartDateTime() {
		return myStartDateTime;
	}

	/**
	 * Change the job start date and time.
	 * 
	 * @param theDate the job start date and time.
	 */
	public void setStartDateTime(final Calendar theStartDateTime) {
		myStartDateTime = theStartDateTime;
	}

	/**
	 * Return the job end date and time.
	 * 
	 * @return the job end date and time.
	 */
	public Calendar getEndDateTime() {
		return myEndDateTime;
	}

	/**
	 * Change the job end date and time.
	 * 
	 * @param theDate the job end date and time.
	 */
	public void setEndDateTime(final Calendar theEndDateTime) {
		myEndDateTime = theEndDateTime;
	}

	/**
	 * Return the park manager.
	 * 
	 * @return the park manager.
	 */
	public Administrator getManager() {
		return myParkManager;
	}

	/**
	 * Change the park manager.
	 * 
	 * @param theParkManager the park manager.
	 */
	public void setParkManager(final ParkManager theParkManager) {
		myParkManager = theParkManager;
	}

	/**
	 * Return the number of volunteers who sign up for this workload.
	 * 
	 * @param theIndex the index of the workload level.
	 * 
	 * @return the workload value.
	 */
	public int getWorkLoad(final int theIndex) {
		if (theIndex > 3) throw new IllegalArgumentException();
		return myWorkLoad[theIndex];
	}

	/**
	 * Change the workload value using the index.
	 * 
	 * @param theIndex of the workload.
	 * @param theValue of the value.
	 */
	public void setWorkLoad(final int theIndex, final int theValue) {
		if (theIndex > 3) throw new IllegalArgumentException();
		this.myWorkLoad[theIndex] = theValue;
	}
	
	/**
	 * Return the status of the job.
	 * 
	 * @return the status of the job.
	 */
	public boolean isCancelled() {
		return myCancelled;
	}
	
	/**
	 * Cancel this job.
	 */
	public void cancel() {
		myCancelled = true;
	}
	
	/**
	 * Return all volunteers for this job.
	 * 
	 * @return all volunteers.
	 */
	public ArrayList<Volunteer> getVolunteersList() {
		return myVolunteersList;
	}
	
	public void addVolunteer(final Volunteer theVolunteer) {
		myVolunteersList.add(theVolunteer);
	}

	/**
	 * Return the minimum number of volunteers required for this job.
	 * 
	 * @return minimum number of volunteers.
	 */
	public int getMinVolunteers() {
		return myMinVolunteers;
	}

	/**
	 * Change the minimum number of volunteers required for this job.
	 * 
	 * @param theMinVolunteers minimum number of volunteers.
	 */
	public void setMinVolunteers(final int theMinVolunteers) {
		myMinVolunteers = theMinVolunteers;
	}
	
	/**
	 * Return the total number of volunteers.
	 * 
	 * @return total number of volunteers.
	 */
	public int getTotalVolunteers() {
		return myVolunteersList.size();
	}
	
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public boolean equals(final Object theOther) {
//        
//        boolean result;
//        
//        if (this == theOther) {
//            return true;
//        } else if (theOther == null || getClass() != theOther.getClass()) {
//            result = false;
//        } else {
//            final Job otherJob = (Job) theOther;
//            
//            
//            result = myDescription.equals(otherJob.myDescription) 
//                    && myStartDateTime.equals(otherJob.myStartDateTime) 
//                    && myEndDateTime.equals(otherJob.myEndDateTime) 
//                    && myManager.equals(otherJob.myManager)
//                    && Arrays.equals(myWorkLoad, otherJob.myWorkLoad)
//                    && myCancelled == otherJob.myCancelled
//                    && myMinVolunteers == otherJob.myMinVolunteers
//                    && myVolunteersList.size() == otherJob.myVolunteersList.size();
//            
//            for (int index = 0; index < myVolunteersList.size() && result; index++) {
//            	result = myVolunteersList.get(index).equals(otherJob.myVolunteersList.get(index));
//            }
//        }
//        
//        return result;
//    }
	
	@Override
	public String toString() {
		
		/*
		 * 	 * @param theDescription the job description.
	 * @param theStartDateTime the job start date and time.
	 * @param theEndDateTime the job end date and time.
	 * @param thePark the park.
	 * @param theLight the number of volunteers required for light workload.
	 * @param theMediumm the number of volunteers required for medium workload.
	 * @param theHeavy the number of volunteers required for heavy workload.
	 * @param theMinVolunteers the minimum number of volunteers required for this job.
		 */
		StringBuffer sb = new StringBuffer();
		sb.append(myDescription);
		//sb.append("\nStart Date and Time = " + myStartDateTime);
		//sb.append("\nEnd Date and Time = " + myEndDateTime);
		//sb.append("\nPark = " + myPark);
		//sb.append("\nWorkload = " + Arrays.toString(myWorkLoad));
		//sb.append("\n");
		return sb.toString();
	}

}
