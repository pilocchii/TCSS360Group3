package urbanparks.model;

public class UrbanParksStaff extends User {

	private static final long serialVersionUID = 3702035140593797417L;
	
	/**
	 * Default constructor for Urban park staff.
	 * 
	 * @param firstName the first name of the user.
	 * @param lastName the last name of the user.
	 * @param email the email address of the user.
	 * @param phone the phone number of the user.
	 */
	public UrbanParksStaff(String theFirstName, String theLastName, String theEmail, String thePhone) {
		super(theFirstName, theLastName, theEmail, thePhone);
	}
	
}
