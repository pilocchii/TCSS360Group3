package urbanparks.model;

public class UrbanParksStaff extends User {

	private static final long serialVersionUID = 3702035140593797417L;
	
	/**
	 * Default constructor for Urban park staff.
	 */
	public UrbanParksStaff(String theFirstName, String theLastName, String theEmail, String thePhone) {
		super(theFirstName, theLastName, theEmail, thePhone);
	}
	
}
