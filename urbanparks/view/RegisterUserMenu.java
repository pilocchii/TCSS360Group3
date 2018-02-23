package urbanparks.view;

import java.util.ArrayList;
import java.util.StringTokenizer;

import urbanparks.model.ParkManager;
import urbanparks.model.UrbanParksStaff;
import urbanparks.model.UserCollection;
import urbanparks.model.Volunteer;

public class RegisterUserMenu {

	private MainMenu mainMenu;
	private UserCollection userCollection;
	
	/**
	 * Constructor for RegisterUserMenu
	 * @param mainMenu Instance of main menu
	 * @param userCollection Instance of user collection
	 */
	public RegisterUserMenu(MainMenu mainMenu, UserCollection userCollection) {
		this.mainMenu = mainMenu;
		this.userCollection = userCollection;
		showRegisterUserMenu();
	}
	
	/**
	 * Shows the interface for registering as a new user
	 */
	private void showRegisterUserMenu() {
		System.out.println("\nPlease enter the number corresponding with your user type, or '0' to go back:");
		ArrayList<String> choices = new ArrayList<String>();
		choices.add("Go back to main menu");
		choices.add("Register as volunteer");
		choices.add("Register as park manager");
		choices.add("Register as Urban Parks staff");
		int choice = InputUtils.getChoice(choices);
		
		if (choice == 0) {
			mainMenu.showMainMenu();
		}
		System.out.println("Please enter your email address:");
		String email = InputUtils.getStringInput();
		
		// 2 users cannot have the same email
		if (userCollection.getUser(email) != null) {
			System.out.println("You are already registred under the email " + email + "!");
			showRegisterUserMenu();
		}

		System.out.println("Please enter your first and last name:");
		String fullName = InputUtils.getStringInput();
		StringTokenizer st = new StringTokenizer(fullName);
		String firstName = st.nextToken();
		String lastName = st.nextToken();

		System.out.println("Please enter your phone number:");
		String phone = InputUtils.getStringInput();

		switch(choice) {
			case 1:
				Volunteer newVolunteer = new Volunteer(firstName, lastName, email, phone);
				userCollection.addUser(newVolunteer);
				System.out.println("You are now registered as a volunteer, " + firstName + "!");
				break;

			case 2:
				ParkManager newParkManager = new ParkManager(firstName, lastName, email, phone);
				userCollection.addUser(newParkManager);
				System.out.println("You are now registered as a park manager, " + firstName + "!");
				break;
			
			case 3:
				UrbanParksStaff newUrbanParksStaff = new UrbanParksStaff(firstName, lastName, email, phone);
				userCollection.addUser(newUrbanParksStaff);
				System.out.println("You are now registered as an Urban Parks staff member, " + firstName + "!");
		}
		mainMenu.showMainMenu();
	}
}
