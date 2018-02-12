package view;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Calendar;
import java.util.List;
import java.util.TreeSet;
import java.util.StringTokenizer;

import model.Job;
import model.JobCollection;
import model.ParkManager;
import model.User;
import model.UserCollection;
import model.Volunteer;
import model.Volunteer.jobSignupTooLateException;
import model.Volunteer.volunteerJobOverlapException;

/***
	The main view class for a terminal-based menu-driven interface for the Urban Parks system.

	@author: rrki@uw.edu
	@version: 2/8/2018
*/


/**
	
*/
public class MainView {

	private static final int JOB_LIST_PAGE_END = 5;


	/* Member variable initialization */
	private Scanner scanner = new Scanner(System.in);
	private String inputPrompt = "Please enter the number corresponding with your menu choice:";
	private JobCollection jobCollection = new JobCollection(); // TODO: this will need to be loaded from file later
	private UserCollection userCollection = new UserCollection(); // TODO: thi also needs to be loaded from file
	private User user;

	public MainView () {
		System.out.println("Welcome to Urban Parks!");
		showLoginMenu();
	}



	/**
		Login menu

		The initial menu. The user will identify themself.
	*/
	private void showLoginMenu() {
		System.out.println(inputPrompt);
		System.out.println("0. Exit");
		System.out.println("1. Sign in");
		System.out.println("2. Create a new account");
		int choice = scanner.nextInt();
		scanner.nextLine();

		switch(choice) {

			case 0:
				System.exit(0);

			case 1:
				System.out.println("Please enter your email address:");
				String username = scanner.nextLine();
				try {
                    user = userCollection.getUser(username);
                    showMainMenu();
                } catch (NullPointerException e) {
                    System.out.println("User " + username + " does not exist. If you do not have an account, please create one.");
                    showLoginMenu();
                }
				break;
			
			case 2:
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
	private void showMainMenu() {

		// show Park Manager options
		if (user instanceof ParkManager) {
			System.out.println("Hi, " + user.getFirstName() + "! What would you like to do?");
			System.out.println(inputPrompt);
			System.out.println("0. Go back to login menu");
			System.out.println("1. Submit a new job");
			int choice = scanner.nextInt();
			scanner.nextLine();

			switch(choice) {
				case 0:
					showLoginMenu();
					break;

				case 1:
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
			int choice = scanner.nextInt();

			switch(choice) {
				case 0:
					showLoginMenu();
					break;

				case 1:
					showSignupForJobMenu((Volunteer) user);
					break;

				default:
					System.out.println(choice + " is not valid input");
					showMainMenu();
			}

		} // end Volunteer options

	} // end showMainMenu


	/**
		Submit new job menu
		Park Managers can create a new job here.
	*/
	private void showSubmitNewJobMenu() {

		System.out.println("Please enter a start date and time for this job in " + 
						   "this format (Year Month Date Hour Minute):");
		Calendar start = getDateTime(scanner.nextLine());
		System.out.println("Please enter an end date and time for this job in this " + 
						    "format (Year Month Date Hour Minute):");
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
			System.out.println("Your job has been created!");
			showMainMenu();
		} catch (Exception e) { // TODO: JobCollection person, add business rules in story 2 here
			System.out.println(e);
		}

	} // end showSubmitNewJobMenu

	/**
	 * Create from the given date and time as string and create a calendar object and return it.
	 * 
	 * @param theDateTime the given date and time.
	 * @return return the calendar.
	 */
	private Calendar getDateTime(String theDateTime) {
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
	private void showSignupForJobMenu(Volunteer volunteer) {

		System.out.println("Select a job from the list below to sign up for.\n" +  
			"Input the corresponding number, and press enter, or press '0' to go back.");

		// display the list of jobs
		/**
		 *  TODO: Make jobCollection sortable by start date with Comparator,
		 *  sort it,
		 *  then iterate each Job in it.
		 */
		// Temp code:
		Job[] jobs = jobCollection.getSortedJobs();
		for(int i = 0; i < jobCollection.getJobCollection().size(); i++) {
			Job currJob = jobs[i];
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd.yyyy.hh.mm");
			System.out.println((i+1) + ". " + dateFormat.format(currJob.getStartDateTime().getTime()) +
                    " - " + currJob.getDescription());
		}
		
		int choice = scanner.nextInt();
		if (choice == '0') {
			showMainMenu();
		}
		int jobIndex = choice - 1;
		Job selectedJob = jobs[jobIndex];
		showJobDetails(selectedJob);
		System.out.println("0. Go back to jobs list");
		System.out.println("1. Sign up for this job");
		choice = scanner.nextInt();
		scanner.nextLine();

		// Needed? This feature isn't in other menus. The default case gives a similar result.
		//List<String> validChoices = Arrays.asList('0', '1');
		//validateJobMenuChoice(choice, validChoices);
		
		switch (choice) {
			case 0:
				showSignupForJobMenu(volunteer);
				break;

			case 1:
				//volunteersList = selectedJob.getVolunteersList();
				
				try {
					volunteer.signUpForJob(selectedJob);
					
				} catch (volunteerJobOverlapException e) {
					System.out.println("The job you selected overlaps with another job you are "
							+ "signed up for. Please try another job.");
					showSignupForJobMenu(volunteer);
					
				} catch (jobSignupTooLateException e) {
					System.out.println("The job you selected starts too soon (less than " 
							+ Volunteer.MIN_DAYS_BEFORE_SIGNUP + " than now. Please try another job.");
					showSignupForJobMenu(volunteer);
				}
				
				//volunteersList.add(user);
				System.out.println("You are now signed up for job " + selectedJob.toString());
				showMainMenu();
				break;

			default:
				System.out.println("Invalid option. Please try again.");
				showSignupForJobMenu(volunteer);
		}	
	} // end showSignupForJobMenu


	/**
		Shows the job's information in more detail

		Job job - the job to show extra details for
	*/
	private void showJobDetails(Job job) {
		System.out.println("Starting time: " + job.getStartDateTime());
		System.out.println("Ending time: " + job.getEndDateTime());
		System.out.println("Park name: " + job.getParkName());
		System.out.println("Location: " + job.getLocation());
		System.out.println("Job description: " + job.getDescription());
		System.out.println("Work levels: Light - " + job.getLight() +
			"Medium - " + job.getMedium() +
			"Heavy - " + job.getHeavy());
	} // end showJobDetails


	/**
		Validate the choice taken in the job menu

		char choice - the character to validate
	*/
	private void validateJobMenuChoice(int choice, List<Integer> validChoices) {
		if (!validChoices.contains(choice)) {
			System.out.println(Integer.toString(choice) + " is not a valid option. Please enter a choice as listed above.");
			validateJobMenuChoice(choice, validChoices);
		} 
	} // end showJobDetails


	/**
		Signup for job
		Volunteers can volunteer for jobs here.
	*/
	private void showSignupMenu() {

		System.out.println("Please enter the number corresponding with your user type, or '0' to go back:");
		System.out.println("0. Go back to main menu");
		System.out.println("1. Volunteer");
		System.out.println("2. Park Manager");

		int choice = scanner.nextInt();
		if (choice == 0) {
			showMainMenu();
		}
		scanner.nextLine();
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

			case 1:
				try {
					Volunteer newVolunteer = new Volunteer(firstName, lastName, email, phone);
					user = newVolunteer;
					System.out.println("Welcome, volunteer " + firstName + "!");
					showMainMenu();
				} catch (Exception e) {
					// TODO: Volunteer class person - put the correct type of exception and descriptive text here
					System.out.println(e);
				}
				break;

			case 2:
				try {
					ParkManager newParkManager = new ParkManager(firstName, lastName, email, phone);
					user = newParkManager;
					System.out.println("Welcome, park manager " + firstName + "!");
					showMainMenu();
				} catch (Exception e) {
					// TODO: ParkManager class person - put the correct type of exception and descriptive text here
					System.out.println(e);
				}
				
				break;

			default:
				showSignupMenu();

		} // end switch

	} // end showSignupMenu

} // end MainView