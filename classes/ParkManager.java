package classes;

/**
 * 
 * @author Abderisaq Tarabi
 *
 */
public class ParkManager extends Administrator {

	/** The park name. */
	private String myParkName;
	
	/** The park zipCode. */
	private int myParkZipCode;
	
	public ParkManager(String theFirstName, String theLastName, String theEmail, String thePhone, String theParkName, int theParkZipCode) {
		super(theFirstName, theLastName, theEmail, thePhone);
		setParkName(theParkName);
		setParkZipCode(theParkZipCode);
		
	}

	public String getParkName() {
		return myParkName;
	}

	public void setParkName(String myParkName) {
		this.myParkName = myParkName;
	}

	public int getParkZipCode() {
		return myParkZipCode;
	}

	public void setParkZipCode(int myParkZipCode) {
		this.myParkZipCode = myParkZipCode;
	}

}
