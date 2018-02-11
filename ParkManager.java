package model;

public class ParkManager {
	
	/*Name of park manager*/
	private String myName;
	
	/*User Id of park manager*/
	private String myUserId;	
	
	/*Number of days of job*/
	private int myDays;

	/*Constructor of park manager*/
	public ParkManager(String theName, String theUserId) {
		myName = theName;
		myUserId = theUserId;		
	}

	/*Method to check valid job days*/
	public boolean isJobDaysValid(Job myJob) {
		myDays = myJob.getJobDays();
		if(myDays < 4 && myDays >0 ) {
			return true;
		}	
		else {
			return false;
		}
	}

}
