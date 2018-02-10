package urbanparks;

import java.util.Arrays;
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


/**
	
*/
public class MainView() {

	private static final JOB_LIST_PAGE_END = 5;

	/* Member variable initialization */
	Scanner scanner = new Scanner(System.in);
	String inputPrompt = "Please enter the number corresponding with your menu choice:"
	JobCollection jobCollection = new JobCollection(); // TODO: this will need to be loaded from file later
	UserCollection userCollection = new UserCollection(); // TODO: thi also needs to be loaded from file
	User user;

	System.out.println("Welcome to Urban Parks!);
	showLoginMenu();

	/**
		Login menu

		The initial menu. The user will identify themself.
	*/
	private static void showLoginMenu() {
		System.out.println(inputPrompt);
		System.out.println("0. Exit");
		System.out.println("1. Sign in");
		System.out.println("2. Create a new user");
		char choice = scanner.nextChar();

		switch(choice) {

			case '0':
				System.exit(0);

			case '1':
				System.out.println("Please enter your email address:");
				String username = scanner.nextLine();
				user = userCollection.getUser(username);
				showMainMenu();
				break;
			
			case '2':
				showSignupMenu();
				break;

			default:
				System.out.println(choice + " is not valid input");
				showLoginMenu();
				break;
		}
	}


	/**
		Main menu

		Follows the login menu. The user can select a choice based on the type of user they are.
	*/
	private static void showMainMenu() {

		// show Park Manager options
		if (user instanceof ParkManager) {
			System.out.println("Hi, " + user.getFirstName() + "! What would you like to do?");
			System.out.println(inputPrompt);
			System.out.println("0. Go back to login menu");
			System.out.println("1. Submit a new job");
			String choice = scanner.nextChar();

			switch(choice) {
				case '0':
					showLoginMenu();
					break;

				case '1':
					showSubmitNewJobMenu();
					break;

				default:
					System.out.println(choice + " is not valid input");
					showMainMenu();
			}

		} // end Park Manager options

		// show Volunteer options
		else if (user instanceof Volunteer) {
			System.out.println("Hi, " + user.getFirstName() + "! What would you like to do?");
			System.out.println(inputPrompt);
			System.out.println("0. Go back to login menu");
			System.out.println("1. Sign up for a job");
			String choice = scanner.nextChar();

			switch(choice) {
				case '0':
					showLoginMenu();
					break;

				case '1':
					showSignupForJobMenu();
					break;

				default:
					System.out.println(char + " is not valid input");
					showMainMenu();
			}

		} // end Volunteer options

	} // end showMainMenu


	/**
		Submit new job menu
		Park Managers can create a new job here.
	*/
	private static void showSubmitNewJobMenu() {

		System.out.println("Please enter a start date and time for this job in " + 
						   "this formate (Year Month Date Hour Minute):");
		Calendar start = getDateTime(scanner.nextLine());
		System.out.println("Please enter an end date and time for this job in this " + 
						    "formate (Year Month Date Hour Minute):");
		Calendar end = getDateTime(scanner.nextLine());
		System.out.println("Please enter a description:");
		String description = scanner.nextLine();
		System.out.println("Please enter the park name:");
		String parkName = scanner.nextLine();
		System.out.println("Please enter the location:");
		String location = scanner.nextLine();
		System.out.println("Please enter the work level (Light):");
		int light = scanner.nextInt();
		System.out.println("Please enter the work level (Medium):");
		int medium = scanner.nextInt();
		System.out.println("Please enter the work level (Heavy):");
		int heavy = scanner.nextInt();
		System.out.println("Please enter the minimum required volunteers:");
		int minimumVolunteers = scanner.nextInt();
		

		try {
			Job newJob = new Job(description, start, end, parkName, location, light, medium, 
								 heavy, minimumVolunteers);
			jobCollection.addJob(newJob); // TODO: make sure the specifics are right, i.e. method call
			System.out.println("Your job has been created! Press any button to continue...");
			char tempInput = scanner.nextChar;
			showMainMenu();
		} catch () { // TODO: JobCollection person, add business rules in story 2 here

		}

	} // end showSubmitNewJobMenu

	/**
	 * Create from the given date and time as string and create a calendar object and return it.
	 * 
	 * @param theDateTime the given date and time.
	 * @return return the calendar.
	 */
	private static Calendar getDateTime(String theDateTime) {
		Calendar calendar = Calendar.getInstance();
		Scanner scanner = new Scanner(theDateTime);
		calendar.set(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), 
					 scanner.nextInt(), scanner.nextInt(), 0);
		scanner.close();
		return calendar;
	}
	
	/**
		Signup for job
		Volunteers can volunteer for jobs here.
	*/
	private static void showSignupForJobMenu() {

		System.out.println("Select a job from the list below to sign up for.\n" +  
			"Input the corresponding number, and press enter, or press '0' to go back.");

		// display the list of jobs
		for(int i = 0; i < jobCollection.size(); i++) { // TODO: JobCollection person, whatever your internal workings are, make them work here
			// TODO: Job person, fill in whatever your field names are here
			currJob = jobCollection.get(i);
			System.out.println(i + ". " + currJob.getStartDate() + " - " + currJob.getDescription());
		}

		char choice = scanner.nextChar();

		if (choice == '0') {
			showMainMenu();
		}
		
		try {
			selectedJob = jobCollection.getJob(Integer.parseInt(choice));
			showJobDetails(selectedJob);
			System.out.println("0. Go back to jobs list");
			System.out.println("1. Sign up for this job");
			char choice = scanner.nextChar();

			static final List<String> validChoices = Arrays.asList('0', '1');
			validateJobMenuChoice(choice, validChoices);

			switch (choice) {
				case '0':
					showSignupForJobMenu();
					break;

				case '1':
					volunteersList = selectedJob.getVolunteersList();
					volunteersList.add(user);
					System.out.println("You are now signed up for job " + selectedJob.toString());
					showMainMenu();
					break;

				default:
					showSignupForJobMenu();
			} // end switch

		} catch () { // TODO: Volunteer person, put the exception type here that triggers on business rule

		}
		
	} // end showSignupForJobMenu


	/**
		Shows the job's information in more detail

		Job job - the job to show extra details for
	*/
	private static void showJobDetails(Job job) {
		System.out.println("Starting time: " + job.getStartDateTime);
		System.out.println("Ending time: " + job.getEndDateTime);
		System.out.println("Park name: " + job.getParkName);
		System.out.println("Location: " + job.getLocation);
		System.out.println("Job description: " + job.getDescription);
		System.out.println("Work levels: Light - " + job.getLight +
			"Medium - " + job.getMedium + 
			"Heavy - " + job.getHeavy);
	} // end showJobDetails


	/**
		Validate the choice taken in the job menu

		char choice - the character to validate
	*/
	private static void validateJobMenuChoice(char choice, List<String> validChoices) {
		if (!validChoices.contains(choice)) {
			System.out.println(choice + " is not a valid option. Please enter a choice as listed above.")
			validateJobMenuChoice(choice, validChoices);
		} 
	} // end showJobDetails


	/**
		Signup for job
		Volunteers can volunteer for jobs here.
	*/
	private static void showSignupMenu() {

		System.out.println("Please enter the number corresponding with your user type, or '0' to go back:");
		System.out.println("0. Go back to main menu");
		System.out.println("1. Volunteer");
		System.out.println("2. Park Manager");

		String choice = scanner.nextChar();
		if (choice == '0') {
			showMainMenu();
		}

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
					user = newVolunteer;
					System.out.println("Welcome, volunteer " + firstName + "!");
					showMainMenu();
				} catch () { 
					// TODO: Volunteer class person - put the correct type of exception and descriptive text here
				}
				break;

			case '2': 
				try {
					ParkManager newParkManager = ParkManager(email, firstName, lastName, phone);
					user = newParkManager;
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