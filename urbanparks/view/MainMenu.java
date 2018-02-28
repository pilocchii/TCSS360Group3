package urbanparks.view;

import java.io.IOException;
import java.util.ArrayList;

import urbanparks.model.JobCollection;
import urbanparks.model.UserCollection;

public class MainMenu {
	
	private JobCollection jobCollection;
	private UserCollection userCollection;
	
	/**
	 * Default constructor for main menu
	 */
	public MainMenu() {
		jobCollection = new JobCollection();
		userCollection = new UserCollection();
		
		System.out.println("Welcome to the Urban Parks system!");
		try {
			jobCollection.loadData();
		} catch (Exception e) {
			System.out.println("Couldn't load job data. Using empty job data!");
		}
		try {
			userCollection.loadData();
		} catch (Exception e) {
			System.out.println("Couldn't load user data. Using empty user data!");
		}
		showMainMenu();
	}
	
	/**
		Shows the main menu for Urban Parks
	*/
	public void showMainMenu() {
		System.out.println("\nPlease enter the number corresponding with your menu choice:");
		
		ArrayList<String> choices = new ArrayList<String>();
		choices.add("Exit");
		choices.add("Sign in");
		choices.add("Create a new account");
		int choice = InputUtils.getChoice(choices);
		
		switch(choice) {
			// exit
			case 0:
				try {
					userCollection.saveData();
					jobCollection.saveData();
					System.out.println("Data saved. Goodbye!");
				} catch (IOException e) {
					System.out.println("Could not save your data to disk: " + e.getMessage());
				}
				System.exit(0);
	
			// sign in
			case 1:
				new SignInMenu(this, userCollection, jobCollection);
				break;
			
			// register
			case 2:
				new RegisterUserMenu(this, userCollection);
		}
	}
}
