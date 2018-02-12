package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
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
import model.ParkManager.managerJobDaysException;
import model.ParkManager.managerJobEndDaysException;
import model.ParkManager.numJobsAtMaximumException;
import model.User;
import model.UserCollection;
import model.Volunteer;
import model.Volunteer.jobSignupTooLateException;
import model.Volunteer.alreadySignedUpException;
import model.Volunteer.volunteerJobOverlapException;

/***
	The main view class for a terminal-based menu-driven interface for the Urban Parks system.

	@author: rrki@uw.edu
	@version: 2/8/2018
*/
public class MainView {

	private static final int JOB_LIST_PAGE_END = 5;

	/* Member variable initialization */
	private Scanner scanner = new Scanner(System.in);
	private String generalPrompt = "Please enter the number corresponding with your menu choice:";
	private JobCollection jobCollection;
	private UserCollection userCollection;
	private User user;

	public MainView () throws NoSuchAlgorithmException {
		jobCollection = new JobCollection();
		userCollection = new UserCollection();
		loadPersistentData();
		System.out.println("Welcome to Urban Parks!");
		showLoginMenu();
	}

	/**
		Login menu

		The initial menu. The user will identify themself.
	*/
	private void showLoginMenu() {
		System.out.println("\n" + generalPrompt);
		System.out.println("0. Exit");
		System.out.println("1. Sign in");
		System.out.println("2. Create a new account");
		int choice = scanner.nextInt();
		scanner.nextLine();

		switch(choice) {
			case 0:
				try {
					userCollection.saveData();
					jobCollection.saveData();
				} catch (IOException e) {
					System.out.println("Could not save your data to disk.");
				}
				System.out.println("Data saved. Goodbye!");
				System.exit(0);

			case 1:
				System.out.println("Please enter your email address:");
				String email = scanner.nextLine();
				try {
                    user = userCollection.getUser(email);
                    showMainMenu();
                } catch (NullPointerException e) {
                    System.out.println("User " + email + " does not exist. If you do not have an account, please create one.");
                    showLoginMenu();
                }
				break;
			
			case 2:
				showUserRegisterMenu();
				break;

			default:
				System.out.println(choice + " is not valid input");
				showLoginMenu();
		}
	}


	/**
		Main menu

		Follows the login menu. The user can select a choice based on the type of user they are.
	*/
	private void showMainMenu() {

		// show Park Manager options
		if (user instanceof ParkManager) {
			System.out.println("\nYou are now signed in as a park manager, " 
					+ user.getFirstName() + "! What would you like to do?");
			System.out.println(generalPrompt);
			System.out.println("0. Go back to login menu");
			System.out.println("1. Submit a new job");
			int choice = scanner.nextInt();
			scanner.nextLine();

			switch(choice) {
				case 0:
					showLoginMenu();
					break;

				case 1:
					showSubmitNewJobMenu((ParkManager)user);
					break;

				default:
					System.out.println(choice + " is not valid input");
					showMainMenu();
			}

		}

		// show Volunteer options
		else if (user instanceof Volunteer) {
			System.out.println("\nYou are now signed in as a volunteer, " 
					+ user.getFirstName() + "! What would you like to do?");
			System.out.println(generalPrompt);
			System.out.println("0. Go back to login menu");
			System.out.println("1. Sign up for a job");
			int choice = scanner.nextInt();

			switch(choice) {
				case 0:
					showLoginMenu();
					break;

				case 1:
					showSignupForJobMenu((Volunteer)user);
					break;

				default:
					System.out.println(choice + " is not valid input");
					showMainMenu();
			}
		}
	}


	/**
		Submit new job menu
		Park Managers can create a new job here.
	*/
//	private void showSubmitNewJobMenu() {
//
//		System.out.println("Please enter a start date and time for this job in " + 
//						   "this format (Year Month Date Hour Minute):");
//		Calendar start = getDateTime(scanner.nextLine());
//		System.out.println("Please enter an end date and time for this job in this " + 
//						    "format (Year Month Date Hour Minute):");
//		Calendar end = getDateTime(scanner.nextLine());
////		System.out.println("Please enter a JobId:");
////		Integer jobId = scanner.nextInt();
//		System.out.println("Please enter a description:");
//		String description = scanner.nextLine();
//		System.out.println("Please enter the park name:");
//		String parkName = scanner.nextLine();
//		System.out.println("Please enter the location:");
//		String location = scanner.nextLine();
//		System.out.println("Please enter the work level (Light):");
//		int light = scanner.nextInt();
//		System.out.println("Please enter the work level (Medium):");
//		int medium = scanner.nextInt();
//		System.out.println("Please enter the work level (Heavy):");
//		int heavy = scanner.nextInt();
//		System.out.println("Please enter the minimum required volunteers:");
//		int minimumVolunteers = scanner.nextInt();
//		
//
//		try {
//			Job newJob = new Job(description, start, end, parkName, location, light, medium, 
//								 heavy, minimumVolunteers);
//			jobCollection.addJob(newJob); // TODO: make sure the specifics are right, i.e. method call
//			System.out.println("Your job has been created!");
//			showMainMenu();
//		} catch (Exception e) { // TODO: JobCollection person, add business rules in story 2 here
//			System.out.println(e);
//		}
//
//	}

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

		System.out.println("Select a job from the list below to sign up for:");
		System.out.println("0. Go back to main menu");
		// display the list of jobs by date
		Job[] sortedJobs = jobCollection.getSortedJobs();
		for(int i = 0; i < sortedJobs.length; i++) {
			Job currJob = sortedJobs[i];
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
			System.out.println((i+1) + ". " + dateFormat.format(currJob.getStartDateTime().getTime()) + " - " + currJob.getDescription());
		}
		
		int choice = scanner.nextInt();
		if (choice == 0) {
			showMainMenu();
		}
		int jobIndex = choice - 1;
		int jobSelectedID = sortedJobs[jobIndex].getJobId();
		Job selectedJob = JobCollection.findJob(jobSelectedID);
		
		System.out.println("\nDetails of selected job:");
		showJobDetails(selectedJob);
		System.out.println("\nWould you like to sign up for this job?");
		System.out.println("0. Go back to jobs list");
		System.out.println("1. Sign up for this job");
		choice = scanner.nextInt();
		scanner.nextLine();
		
		switch (choice) {
			case 0:
				showSignupForJobMenu(volunteer);
				break;

			case 1:
				System.out.println("\nSigning up for id # " + selectedJob.getJobId());
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
				
				} catch (alreadySignedUpException e) {
					System.out.println("You are already signed up for that job. Please try another one.");
					showSignupForJobMenu(volunteer);
				}
				
				System.out.println("You are now signed up for job " + selectedJob.getDescription());
				showMainMenu();
				break;

			default:
				System.out.println("Invalid option. Please try again.");
				showSignupForJobMenu(volunteer);
		}
	} // end showSignupForJobMenu
	
	/**
	 * Managers can create a job here.
	 */
	private void showSubmitNewJobMenu(ParkManager parkManager) {

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
		
		
		Job newJob = new Job(description, start, end, parkName, location, light, medium, 
							 heavy, minimumVolunteers);
		
		try {
			parkManager.createNewJob(newJob, jobCollection);

		} catch (numJobsAtMaximumException e) {
			System.out.println("The Job cannot be created as maximum number of pending "
					+ "jobs at a time in the entire system is 20");
			showSubmitNewJobMenu(parkManager);

		} catch (managerJobDaysException e) {
			System.out.println("The job cannot be created as it takes more than the "
					+ "maximum number of days a job should take");
			showSubmitNewJobMenu(parkManager);
		}
		catch (managerJobEndDaysException e) {
			System.out.println("The job cannot be created as its end date is more than "
					+ "the maximum number of days from the current date");
			showSubmitNewJobMenu(parkManager);
		}

		System.out.println("Your job has been created!");
		showMainMenu();

	} // end showSignupForJobMenu



	/**
		Shows the job's information in more detail

		Job job - the job to show extra details for
	*/
	private void showJobDetails(Job job) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
		System.out.println("Starting time: " + dateFormat.format(job.getStartDateTime().getTime()));
		System.out.println("Ending time: " + dateFormat.format(job.getEndDateTime().getTime()));
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
	private void showUserRegisterMenu() {

		System.out.println("\nPlease enter the number corresponding with your user type, or '0' to go back:");
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
				Volunteer newVolunteer = new Volunteer(firstName, lastName, email, phone);
				user = newVolunteer;
				if (userCollection.getUser(email) != null) {
					System.out.println("You are already registred under the email " + email + "!");
					showUserRegisterMenu();
				}
				userCollection.addUser(user);
				System.out.println("You are now registered, " + firstName + "!");
				showMainMenu();
				break;

			case 2:
				ParkManager newParkManager = new ParkManager(firstName, lastName, email, phone);
				user = newParkManager;
				if (userCollection.getUser(email) != null) {
					System.out.println("You are already registred under " + email);
					showUserRegisterMenu();
				}
				userCollection.addUser(user);
				System.out.println("You are now registered, " + firstName + "!");
				showMainMenu();
				break;

			default:
				showUserRegisterMenu();
		}
	}
	
	private void loadPersistentData() {
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
	}

}
