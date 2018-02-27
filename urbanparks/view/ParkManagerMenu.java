package urbanparks.view;

import static urbanparks.model.Constants.*;

import java.util.ArrayList;
import java.util.Calendar;

import urbanparks.model.DateUtils;
import urbanparks.model.Job;
import urbanparks.model.JobCollection;
import urbanparks.model.ParkManager;

public class ParkManagerMenu {
	
	private MainMenu mainMenu;
	private ParkManager parkManager;
	private JobCollection jobCollection;
	
	/**
	 * Constructor for ParkManagerMenu
	 * 
	 * @param mainMenu Instance of main menu
	 * @param parkmanager The park manager user logged in
	 * @param jobCollection Instance of job collection
	 */
	public ParkManagerMenu(MainMenu mainMenu, ParkManager parkmanager, JobCollection jobCollection) {
		this.mainMenu = mainMenu;
		this.parkManager = parkmanager;
		this.jobCollection = jobCollection;
		showParkManagerMenu();
	}
	
	/**
	 * Shows the park manager's main options
	 */
	public void showParkManagerMenu() {
		System.out.println("\nYou are now signed in as a park manager, " 
				+ parkManager.getFirstName() + "! What would you like to do?");
		
		ArrayList<String> choices = new ArrayList<String>();
		choices.add("Go back to login menu");
		choices.add("Submit a new job");
		int choice = InputUtils.getChoice(choices);

		switch(choice) {
			case 0:
				mainMenu.showMainMenu();
				break;

			case 1:
				showSubmitNewJobMenu();
				break;
		}
	}
	
	/**
	 * Shows the interface for creating a new job.
	 * and asks the user for every detail of the job they are creating
	 */
	private void showSubmitNewJobMenu() {
		System.out.println("Please enter a start date and time for this job in " + 
				   "the format \"MM/dd/yyyy HH:mm\":");
		Calendar startDate = InputUtils.getCalendarInput();
		
		System.out.println("Please enter an end date and time for this job in this " + 
						    "format \"MM/dd/yyyy HH:mm\":");
		Calendar endDate = InputUtils.getCalendarInput();
		
		System.out.println(DateUtils.daysBetweenNowAndDate(endDate));
		//No job can be specified whose end date is more than the maximum number of days from the current date
		if (DateUtils.daysBetweenNowAndDate(endDate) > MAX_DAYS_BEFORE_JOB_ENDS) {
			System.out.println("Your job's end date is more than " 
					+ MAX_DAYS_BEFORE_JOB_ENDS + " days from now! Please try again.");
			showSubmitNewJobMenu();
		}
		//No job can be specified that takes more than the maximum number of days
		if (DateUtils.daysBetween2Dates(startDate, endDate) > MAX_JOB_LENGTH) {
			System.out.println("Your job can't span more than " + MAX_JOB_LENGTH + " Please try again.");
			showSubmitNewJobMenu();
		}
		
		System.out.println("Please enter the minimum number of total volunteers for this job:");
		int minimumVolunteers = InputUtils.getIntInput(MIN_WORKERS_FLOOR, MIN_WORKERS_CEILING);
		
		System.out.println("Please enter the maximum number of \"light\" workers:");
		int light = InputUtils.getIntInput(MAX_WORKERS_FLOOR, MAX_WORKERS_CEILING);
		
		System.out.println("Please enter the maximum number of \"medium\" workers:");
		int medium = InputUtils.getIntInput(MAX_WORKERS_FLOOR, MAX_WORKERS_CEILING);
		
		System.out.println("Please enter the maximum number of \"heavy\" workers:");
		int heavy = InputUtils.getIntInput(MAX_WORKERS_FLOOR, MAX_WORKERS_CEILING);
		
		System.out.println("Please enter a description for the job:");
		String description = InputUtils.getStringInput();
		
		System.out.println("Please enter the park name the job takes place in:");
		String parkName = InputUtils.getStringInput();
		
		System.out.println("Please enter the location of the job:");
		String location = InputUtils.getStringInput();
		
		Job newJob = new Job(description, startDate, endDate, parkName, location, light, medium, 
							 heavy, minimumVolunteers);
		parkManager.createNewJob(newJob, jobCollection);
		System.out.println("Your job has been created!");
		showParkManagerMenu();
	}
}