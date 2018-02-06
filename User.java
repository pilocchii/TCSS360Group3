package classes;

/**
 * 
 * @author Abderisaq Tarabi
 *
 */
public abstract class User {

	/** The first name of the user. */
	private String myFirstName;
	
	/** The last name of the user. */
	private String myLastName;
	
	/** The email of the user. */
	private String myEmail;
	
	/** The phone number of the user. */
	private String myPhone;
	
	public User(String theFirstName, String theLastName, String theEmail, String thePhone) {
		setFirstName(theFirstName);
		setLastName(theLastName);
		setEmail(theEmail);
		setPhoneNumber(thePhone);
	}

	public String getFirstName() {
		return myFirstName;
	}

	public void setFirstName(String myFirstName) {
		this.myFirstName = myFirstName;
	}

	public String getLastName() {
		return myLastName;
	}

	public void setLastName(String myLastName) {
		this.myLastName = myLastName;
	}

	public String getEmail() {
		return myEmail;
	}

	public void setEmail(String myEmail) {
		this.myEmail = myEmail;
	}

	public String getPhoneNumber() {
		return myPhone;
	}

	public void setPhoneNumber(String myPhone) {
		this.myPhone = myPhone;
	}
	
	

}
