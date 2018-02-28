//package urbanparks.view;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//
//import urbanparks.model.Job;
//import urbanparks.model.JobCollection;
//import urbanparks.model.Volunteer;
//
//public class VolunteerMenu {
//	
//	private MainMenu mainMenu;
//	private Volunteer volunteer;
//	private JobCollection jobCollection;
//	
//	/**
//	 * Constructor for VolunteerMenu
//	 * 
//	 * @param mainMenu Instance of main menu
//	 * @param volunteer The volunteer this menu is for managing
//	 * @param jobCollection Instance of job collection
//	 */
//	public VolunteerMenu(MainMenu mainMenu, Volunteer volunteer, JobCollection jobCollection) {
//		this.mainMenu = mainMenu;
//		this.volunteer = volunteer;
//		this.jobCollection = jobCollection;
//		showVolunteerMenu();
//	}
//	
//	/**
//	 * Shows the main volunteer menu
//	 */
//	public void showVolunteerMenu() {
//		System.out.println("What would you like to do?");
//		
//		ArrayList<String> choices = new ArrayList<String>();
//		choices.add("Go back to login menu");
//		choices.add("Sign up for a job");
//		int choice = InputUtils.getChoice(choices);
//		
//		switch(choice) {
//			case 0:
//				mainMenu.showMainMenu();
//				break;
//			case 1:
//				showAvailableJobs(volunteer);
//		}
//	}
//	
//	/**
//	 * Shows a list of jobs available (able to sign up for) to the volunteer
//	 * 
//	 * @param volunteer
//	 */
//	private void showAvailableJobs(Volunteer volunteer) {
//		System.out.println("Select a job from the list below to sign up for:");
//		
//		ArrayList<String> choices = new ArrayList<String>();
//		choices.add("Go back to main menu");
//		
//		// display the list of jobs by date
//		ArrayList<Job> availableJobs = jobCollection.getAvilableJobs(volunteer);
//		for(int i = 0; i < availableJobs.size(); i++) {
//			Job currJob = availableJobs.get(i);
//			String isAvailable = "";
//			if (!currJob.getIsAvailable()) {
//				isAvailable = "NOT AVAILABLE: ";
//			}
//			SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d, yyyy HH:mm");
//			choices.add(isAvailable + dateFormat.format(currJob.getStartDateTime()) 
//				+ " - " + currJob.getDescription());
//		}
//		
//		int choice = InputUtils.getChoice(choices);
//		if (choice == 0) {
//			mainMenu.showMainMenu();
//		}
//		int selectedJobID = availableJobs.get(choice - 1).getJobId();
//		Job selectedJob = jobCollection.findJob(selectedJobID);
//		if (!selectedJob.getIsAvailable()) {
//			System.out.println("That job is not available for you to select!");
//			showAvailableJobs(volunteer);
//		}
//		showSignupConfirm(selectedJob);
//	}
//	
//	/**
//	 * Asks the volunteer if they want to sign up for a job
//	 * 
//	 * @param selectedJob A job that must be available to the volunteer
//	 */
//	private void showSignupConfirm(Job selectedJob) {
//		// show job details
//		System.out.println("\nDetails of selected job:");
//		selectedJob.showInfo();
//		
//		System.out.println("\nWould you like to sign up for this job?");
//		ArrayList<String> choices = new ArrayList<String>();
//		choices.add("Go back to jobs list");
//		choices.add("Sign up for this job");
//		int choice = InputUtils.getChoice(choices);
//		
//		switch (choice) {
//			case 0:
//				showAvailableJobs(volunteer);
//				break;
//			case 1:
//				volunteer.signUpForJob(selectedJob);
//				System.out.println("You are now signed up for job " + selectedJob.getDescription());
//				showVolunteerMenu();
//		}
//	}
//}
