package view

import java.util.Scanner;
import java.util.Calendar;

import model.Job;
import model.JobCollection;
import model.User;
import model.UserCollection;

/***
	The main view class for a terminal-based menu-driven interface for the Urban Parks system.

	@author: rrki@uw.edu
	@version: 2/8/2018
*/

public class mainView() {

	private static final JOB_LIST_START = 0;
	private static final JOB_LIST_END = 5;


	Scanner myScanner = new Scanner(System.in);
	String myInputPrompt = "Please enter the number corresponding with your menu choice:"
	JobCollection myJobCollection = new JobCollection(); // TODO: this will need to be loaded from file later
	User myUser;

	// entrypoint
	System.out.println("Welcome to Urban Parks!);
	System.out.println(myInputPrompt);
	System.out.println("1. Sign in");
	System.out.println("2. Create a new user");
	System.out.println("3. Exit this application");
	char choice = scanner.nextChar();

	switch(choice) {

		case '1':
			System.out.println("Please enter your email address:");
			String username = scanner.nextLine();
			myUser = UserCollection.getUser(username);
			showMainMenu();
			break;
		
		case '2':
			showSignupMenu();
			break;

		case '3':
			System.exit(0);

		default:
			System.out.println(choice + " is not valid input");
	}


	private static showSubmitNewJobMenu() {

		System.out.println("Please enter a start date and time for this job:");
		Calendar start = new Calendar(scanner.nextLine());
		System.out.println("Please enter an end date and time for this job:");
		Calendar end = new Calendar(scanner.nextLine());
		System.out.println("Please enter a description:");
		String description = scanner.nextLine();

		try {
			Job newJob = new Job(description, start, end);
			myJobCollection.addJob(newJob); // TODO: make sure the specifics are right, i.e. method call
			System.out.println("Your job has been created! Press any button to continue...");
			char tempInput = scanner.nextChar;
			showMainMenu();
		} catch () { // TODO: Job collection person, add business rules in story 2 here

		}

	} // end showSubmitNewJobMenu


	private static showSignupForJobMenu() {

		System.out.println("Select a job from the list below to sign up for, input the corresponding number, and press enter:");
		for(int i = JOB_LIST_START; i < myJobCollection.size(); i++) { // TODO: JobCollection person, whatever your internal workings are, make them work here
			// TODO: Job person, fill in whatever your field names are here
			currJob = myJobCollection.get(i);
			System.out.println(i + ": " + currJob.toString());
		}

		String choice = scanner.nextLine();
		
		try {
			selectedJob = myJobCollection.getJob(Integer.parseInt(choice));
			volunteersList = selectedJob.getVolunteersList();
			volunteersList.add(myUser);
			System.out.println("You are now signed up for job " + selectedJob.toString());
			showMainMenu();
		} catch () { // TODO: Volunteer person, put the exception type here that triggers on business rule

		}
		
	} // end showSignupForJobMenu


	private static void showMainMenu() {

		// show Park Manager options
		if (myUser instanceof ParkManager) {
			System.out.println("Hi, " + myUser.getFirstName() + "! What would you like to do?");
			System.out.println(myInputPrompt);
			System.out.println("1. Submit a new job");
			String choice = scanner.nextChar();

			switch(choice) {
				case '1':
					showSubmitNewJobMenu();
					break;
				default:
					System.out.println(choice + " is not valid input");
					showMainMenu();
			}

		} // end Park Manager options

		// show Volunteer options
		else if (myUser instanceof Volunteer) {
			System.out.println("Hi, " + myUser.getFirstName() + "! What would you like to do?");
			System.out.println(myInputPrompt);
			System.out.println("1. Sign up for a job");
			String choice = scanner.nextChar();

			switch(choice) {
				case '1':
					showSignupForJobMenu();
					break;
				default:
					System.out.println(char + " is not valid input");
					showMainMenu();
			}

		} // end Volunteer options

	} // end showMainMenu


	private static void showSignupMenu() {

		System.out.println("Please enter the number corresponding with your user type:");
		System.out.println("1. Volunteer");
		System.out.println("2. Park Manager");
		String choice = scanner.nextChar();

		System.out.println("Please enter your email address:");
		String email = scanner.nextLine();

		System.out.println("Please enter your first and last name:");
		String name = scanner.nextLine();
		StringTokenizer st = new StringTokenizer(name);
		String firstName = st.nextToken();
		String lastName = st.nextToken();

		System.out.println("Please enter your phone number:");
		String phone = scanner.nextLine();

		switch(choice) {

			case '1':
				try {
					Volunteer newVolunteer = Volunteer(email, firstName, lastName, phone);
					myUser = newVolunteer;
					System.out.println("Welcome, volunteer " + firstName + "!");
					showMainMenu();
				} catch () { 
					// TODO: Volunteer class person - put the correct type of exception and descriptive text here
				}
				break;

			case '2': 
				try {
					ParkManager newParkManager = ParkManager(email, firstName, lastName, phone);
					myUser = newParkManager;
					System.out.println("Welcome, park manager " + firstName + "!");
					showMainMenu();
				} catch () {
					// TODO: ParkManager class person - put the correct type of exception and descriptive text here
				}
				
				break;
			default:
				showSignupMenu();

		} // end switch

	} // end showSignupMenu

} // end MainView
