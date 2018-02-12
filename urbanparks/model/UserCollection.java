package model;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class UserCollection {
	
	/**A HashMap that stores users based on a string key, represented
	 * by email.*/
	private HashMap<String, User> userList;
	
	private static final String USER_DATA_FILE = "userlist.data";
	
	public UserCollection() {
		userList = new HashMap<String, User>(99);
	}
	
	public User getUser(String email) {
		User user = userList.get(email);
		if (user == null) {
			throw new NullPointerException("User " + user.toString() + " does not exist");
		}
		return userList.get(email);
	}
	
	public void saveData() throws IOException {
		FileOutputStream fos = new FileOutputStream(USER_DATA_FILE);
		ObjectOutputStream ois = new ObjectOutputStream(fos);
		ois.writeObject(userList);
	}
	
	/**Makes a new user collection with a capacity of 99;
	 * Reads in the serialized user objects and stores them in 
	 * the collection.
	 * @throws IOException 
	 * @throws ClassNotFoundException */
	public void loadData() throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(USER_DATA_FILE);
		ObjectInputStream ois = new ObjectInputStream(fis);
		userList = (HashMap<String, User>)ois.readObject();
	}
	
	public void addUser(User u) {
		userList.put(u.getEmail(), u);
	}
	
}
