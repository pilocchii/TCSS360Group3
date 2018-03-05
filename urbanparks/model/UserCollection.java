package urbanparks.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import static urbanparks.model.ModelConstants.*;

public class UserCollection implements Serializable {

	private static final long serialVersionUID = -271344141538164296L;
	
	//A HashMap that stores users based on a string key, represented by email.
	private HashMap<String, User> userList;
	
	/**
	 * Default constructor for user collection
	 */
	public UserCollection() {
		userList = new HashMap<String, User>();
	}
	
	/**
	 * Retrieves an User object based on its email
	 * @param email
	 * @return
	 */
	public User getUser(String email) {
		return userList.get(email);
	}
	
	/**
	 * Adds an user to the collection
	 * @param u
	 */
	public void addUser(User u) {
		userList.put(u.getEmail(), u);
	}
	
	/**
	 * 
	 * @throws IOException
	 */
	public void saveData() throws IOException {
		FileOutputStream fos = new FileOutputStream(USER_DATA_FILE);
		ObjectOutputStream ois = new ObjectOutputStream(fos);
		ois.writeObject(userList);
		ois.close();
	}
	
	/**
	 * 
	 * @throws IOException
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
