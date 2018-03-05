package urbanparks.model;

public class UrbanParksStaff extends User {

	private static final long serialVersionUID = 3702035140593797417L;
	
	/**
	 * Constructs a new UrbanParksStaff user.
	 * @param theFirstName First name of the staff  user
	 * @param theLastName Last name of the staff user
	 * @param theEmail Email address associated with the user
	 * @param thePhone The user's phone number

	 */
	public UrbanParksStaff(String theFirstName, String theLastName, String theEmail, String thePhone) {
		super(theFirstName, theLastName, theEmail, thePhone);
	}
	
}
