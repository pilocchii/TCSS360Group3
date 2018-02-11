import java.util.Map.Entry;
import java.util.Set;

public class UserCollection implements Serializable{
	
	/**A HashMap that stores users based on a string key, represented
	 * by email.*/
	private HashMap<String, User> userList;
	
	/**Makes a new user collection with a capacity of 99;
	 * Reads in the serialized user objects and stores them in 
	 * the collection.*/
	public UserCollection() {
		userList = new HashMap<String, User>(99);
		
		// Read from disk using FileInputStream
		FileInputStream f_in = new 
			FileInputStream("myobject.data");

		//Make an ObjectInputStream to read with
		ObjectInputStream obj_in = 
			new ObjectInputStream (f_in);

		Object obj;
		
		//Read all users
		try {
			  while (true) {
			    obj = obj_in.readObject();
			    // make sure its a user
				if (obj instanceof User) {
					// Cast object to a user
					User u = (User) obj;

					// Add it to the map
					userList.put(u.getEmail(), u);
				}
			  }
			} catch (EOFException e) {
			}


	}
	
	public User findUser(String email) {
		return userList.get(email);
	}
	
	public void saveUsers() {
		// Make a file
		FileOutputStream f_out = new 
			FileOutputStream("userlist.data");

		// Make an ObjectOutputStream
		ObjectOutputStream obj_out = new
			ObjectOutputStream (f_out);

		// Write users to the disk
		User u;
		Set<Map.Entry<String,User>> s = userList.entrySet();
		for(Entry<String, User> e : s) {
			u = e.getValue();
			obj_out.writeObject(u);
		}

		
	}
	
	
}
