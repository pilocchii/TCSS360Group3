package urbanparks.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import static urbanparks.model.ModelConstants.*;

/**
 * Holds and manages the collection of all users in the system.
 * invariants: all fields non-null
 */
public class UserCollection implements Serializable {

	private static final long serialVersionUID = -271344141538164296L;
	
	/**
	 * A HashMap that stores users based on a string key, represented by email.
	 * 
	 */
	private HashMap<String, User> userList;
	
	/**
	 * Default constructor for user collection
	 */
	public UserCollection() {
		userList = new HashMap<String, User>();
	}
	
	/**
	 * Retrieves an User object based on its email
	 * Pre: The non-null email is associated with the user to fetch 
	 * @param email the email the user is associated with
	 * @return the user object represented by the email
	 */
	public User getUser(String email) {
		return userList.get(email);
	}
	
	/**
	 * Adds an user to the collection.
	 * Pre: the unique user object is non-null
	 * @param u the user to add to the collection
	 */
	public void addUser(User u) {
		userList.put(u.getEmail(), u);
	}
	
	/**
	 * Saves the user collection to a file by the name specified in the ModelConstants class.
	 * Pre: The file by the specified name either exists, or is able to be created/written to
	 * in the directory the program resides in.
	 * @throws IOException If the file is unable to be written to (doesn't exist or permissions are not enabled)
	 */
	public void saveData() throws IOException {
		FileOutputStream fos = new FileOutputStream(USER_DATA_FILE);
		ObjectOutputStream ois = new ObjectOutputStream(fos);
		ois.writeObject(userList);
		ois.close();
	}
	
	/**
	 * Loads the user collection from the file by the name specified in the ModelConstants class.
	 * Pre: The file exists and contains a hashmap object representing the user collection.
	 * @throws IOException If the file does not exist
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	public void loadData() throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(USER_DATA_FILE);
		ObjectInputStream ois = new ObjectInputStream(fis);
		userList = (HashMap<String, User>)ois.readObject();
		ois.close();
	}
}
