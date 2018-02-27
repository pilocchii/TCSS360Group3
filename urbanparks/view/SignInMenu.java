package urbanparks.view;

import urbanparks.model.JobCollection;
import urbanparks.model.ParkManager;
import urbanparks.model.UrbanParksStaff;
import urbanparks.model.User;
import urbanparks.model.UserCollection;
import urbanparks.model.Volunteer;

public class SignInMenu {
	
	private MainMenu mainMenu;
	private UserCollection userCollection;
	private JobCollection jobCollection;
	
	/**
	 * Constructor for SignInMenu
	 * 
	 * @param mainMenu Instance of main menu
	 * @param userCollection Instance of user collection
	 * @param jobCollection Instance of job collection
	 */
	public SignInMenu(MainMenu mainMenu, UserCollection userCollection, JobCollection jobCollection) {
		this.mainMenu = mainMenu;
		this.userCollection = userCollection;
		this.jobCollection = jobCollection;
		showSignInMenu();
	}
	
	/**
	 * Shows the menu for signing in as a user
	 */
	private void showSignInMenu() {
		System.out.println("Please enter your email address:");
		String email = InputUtils.getStringInput();
        User user = userCollection.getUser(email);
        if (user == null) {
            System.out.println("User with email " + email + " does not exist. If you do not have an account, please create one.");
            mainMenu.showMainMenu();
        }
        if (user instanceof ParkManager) {
        	new ParkManagerMenu(mainMenu, (ParkManager)user, jobCollection);
        } else if (user instanceof Volunteer) {
        	new VolunteerMenu(mainMenu, (Volunteer)user, jobCollection);
        } else if (user instanceof UrbanParksStaff) {
        	new UrbanParksStaffMenu();
        }
	}
}
