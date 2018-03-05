package urbanparks.view;

import static urbanparks.view.ViewConstants.*;
import urbanparks.model.ModelConstants;
import urbanparks.model.DateUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import javafx.scene.control.TextArea;

/**
 * Utility class to hold all of the methods that show all of the alerts in the program.
 */
public class AlertUtils {
	
	// ERROR DIALOGS ------------------------------------------------------------------------------------------------------
	/**
	 * Displays a general error message for when the user ignores 
	 * warnings and presses a submit button in a form with an invalid state.
	 */
	public static void showInvalidOptions() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ERROR_DIALOG_TITLE);
        alert.setHeaderText("Invalid input!");
        alert.setContentText("Please ensure all your input is valid.");
        alert.showAndWait();
	}
	
	/**
     * Displays the details of an error when saving all data.
     * precondition: ex != null
     * @param ex The exception thrown when saving data.
     */
    public static void showDataSaveError(Exception ex) {
    	String header = "Job, user, and settings data could not be saved to disk!";
    	String content = "Data could not be saved to " 
        		+ ModelConstants.JOB_DATA_FILE 
        		+ ", " + ModelConstants.USER_DATA_FILE 
        		+ ", and " + ModelConstants.SETTINGS_DATA_FILE;
    	showExceptionAlert(ex, header, content);
    }
    
    /**
     * Displays the details of an error when loading job and user data.
     * precondition: ex != null
     * @param ex The exception thrown when loading job/user data.
     */
    public static void showJobUserDataLoadError(Exception ex) {	
    	String header = "Could not load job and user data from disk!";
    	String content = "Job and user data could not be loaded from " 
        		+ ModelConstants.JOB_DATA_FILE + " and " + ModelConstants.USER_DATA_FILE;
    	showExceptionAlert(ex, header, content);
    }
    
    /**
     * Displays the details of an error when loading settings data.
     * precondition: ex != null
     * @param ex The exception thrown when loading settings data.
     */
    public static void showSettingsLoadError(Exception ex) {
    	String header = "Could not load settings data from disk!";
    	String content = "Could not be loaded from " + ModelConstants.SETTINGS_DATA_FILE;
    	showExceptionAlert(ex, header, content);
    }
    
    /**
     * Displays an error for trying to register with an existing email address.
     */
    public static void showEmailAlreadyRegistered() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ERROR_DIALOG_TITLE);
        alert.setHeaderText("That email is already in use!");
        alert.setContentText("Please register using a different email.");
        alert.showAndWait();
    }
    
    /**
     * Displays an error for trying to sign in with an unregistered email address.
     * precondition: email != null
     * @param email The email address entered.
     */
    public static void emailNotExist(String email) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ERROR_DIALOG_TITLE);
        alert.setHeaderText("There is no account registered for the email " + email + "!");
        alert.setContentText("If you do not have an account, please create one.");
        alert.showAndWait();
    }
    
    /**
     * Displays an error when a Urban Parks staff tries to set a 
     * max pending jobs value smaller than the current number of pending jobs.
     * @param newValue The new max pending jobs value submitted.
     * @param pendingJobs The current number of pending jobs.
     */
    public static void maxPendingJobsTooLow(int newValue, int pendingJobs) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ERROR_DIALOG_TITLE);
        alert.setHeaderText("The new maximum pending jobs value (" + newValue 
        		+ ") is lower than the current number of pending jobs (" + pendingJobs + ").");
        alert.setContentText("Please try a different value.");
        alert.showAndWait();
    }
    
    /**
     * Displays an error when a park manager tries to create
     * a new job when the number of jobs is at the maximum value.
     */
    public static void numberJobsAtCapacity() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ERROR_DIALOG_TITLE);
        alert.setHeaderText("You cannot create a job because\n"
        		+ "the maximum number of pending jobs has been reached.");
        alert.setContentText("Please unsubmit a job or wait for a job to end.");
        alert.showAndWait();
    }
    
	/**
	 * Generates and displays an error alert with an expandable view of an exception.
	 * precondition: ex != null
	 * precondition: header != null
	 * precondition: content != null
	 * @param ex The exception thrown to cause the alert.
	 * @param header The header to show here.
	 * @param content The content to show here.
	 */
	private static void showExceptionAlert(Exception ex, String header, String content) {
		// put exception into string
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String exceptionString = sw.toString();
		// create text area for exception text
		TextArea textArea = new TextArea(exceptionString);
		textArea.setEditable(false);
		// create alert
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(ERROR_DIALOG_TITLE);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.getDialogPane().setExpandableContent(textArea);
		alert.showAndWait();
	}
	
	
	// SUCCESS DIALOGS ------------------------------------------------------------------------------------------------------
    /**
     * Displays when user, job, and settings data was saved successfully.
     */
    public static void showDataSaveSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(SUCCESS_DIALOG_TITLE);
        alert.setHeaderText("All job, user, and settings data is now saved to disk.");
        alert.setContentText("Data saved to " + ModelConstants.JOB_DATA_FILE 
        		+ ", " + ModelConstants.USER_DATA_FILE 
        		+ " and " + ModelConstants.SETTINGS_DATA_FILE);
        alert.showAndWait();
    }
    
    /**
     * Displays when a new user was registered successfully.
     * precondition: userType != null
     * precondition: firstName != null
     * @param userType The type of the new user.
     * @param firstName The first name of the new user.
     */
    public static void showRegisterSuccess(String userType, String firstName) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(SUCCESS_DIALOG_TITLE);
        alert.setHeaderText("You are now registered as " + userType + ", " + firstName + "!");
        alert.setContentText("You will now be signed in.");
        alert.showAndWait();
    }
    
    /**
     * Displays when a new job was submitted successfully.
     * precondition: start != null
     * precondition: end != null
     * @param start The start date and time of the new job.
     * @param end The end date and time of the new job.
     */
    public static void showJobSubmitSuccess(LocalDateTime start, LocalDateTime end) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(SUCCESS_DIALOG_TITLE);
        alert.setHeaderText("The job has been created!"
        		+ "\nStart: " + DateUtils.formatDateTime(start)
        		+ "\nEnd: " + DateUtils.formatDateTime(end));
        alert.setContentText("You will now be taken to the park manager menu.");
        alert.showAndWait();
    }
    
    /**
     * Displays when a park manager successfully unsubmits a job.
     */
    public static void showJobUnsubmitSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(SUCCESS_DIALOG_TITLE);
        alert.setHeaderText("The job has been unsubmitted.");
        alert.setContentText("You will now be taken to the park manager menu.");
        alert.showAndWait();
    }
    
    /**
     * Displays when a volunteer has signed up for a job successfully.
     */
    public static void showJobSignupSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(SUCCESS_DIALOG_TITLE);
        alert.setHeaderText("You are signed up for the job!");
        alert.setContentText("You will now be taken to the volunteer menu.");
        alert.showAndWait();
    }
    
    /**
     * Displays when a volunteer has unvolunteered from a job successfully.
     */
    public static void showJobUnvolunteerSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(SUCCESS_DIALOG_TITLE);
        alert.setHeaderText("You are unvolunteered from the job!");
        alert.setContentText("You will now be taken to the volunteer menu.");
        alert.showAndWait();
    }
    
    /**
     * Shows upon loading empty job and user data into the system.
     */
    public static void showEmptyJobUserDataUsed() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Empty Data Used");
        alert.setHeaderText("An empty data set (users and jobs) has been loaded.");
        alert.showAndWait();
    }
    
    /**
     * Shows upon loading default settings into the system.
     */
    public static void showDefaultSettingsUsed() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Default Settings Used");
        alert.setHeaderText("Maximum pending jobs was set to its default value " 
        		+ ModelConstants.DEFAULT_MAX_PENDING_JOBS + ".");
        alert.showAndWait();
    }
    
    /**
     * Displays when the maximum number of pending jobs setting was successfully updated.
     * @param newValue The new max pending jobs limit.
     */
    public static void showChangedMaxPendingJobs(int newValue) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(SUCCESS_DIALOG_TITLE);
        alert.setHeaderText("Successfully changed the maximum number of pending jobs to " 
        		+ newValue + ".");
        alert.showAndWait();
    }
	
    
	// CONFIRMATION DIALOGS  ------------------------------------------------------------------------------------------------------
    /**
     * Asks for confirmation before creating a new job.
     * precondition: description != null
     * @param description The description of the new job.
     * @return True if the user selected OK, false otherwise.
     */
    public static boolean askJobSubmit(String description) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(CONFIRMATION_DIALOG_TITLE);
        alert.setHeaderText("Are you sure you want to create the job \"" + description + "\"");
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    	    return true;
    	} else {
    	    return false;
    	}
    }
    
    /**
     * Asks for confirmation before unsubmitting an existing job.
     * precondition: description != null
     * @param description The description of the job t unsubmit.
     * @return True if the user selected OK, false otherwise.
     */
    public static boolean askJobUnsubmit(String description) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(CONFIRMATION_DIALOG_TITLE);
        alert.setHeaderText("Are you sure you want to unsubmit (cancel) the job \""
        		+ description + "\"");
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    	    return true;
    	} else {
    	    return false;
    	}
    }
    
    /**
     * Asks for confirmation before signing up for a job.
     * description != null
     * @param description The description of the job to sign up for.
     * @return True if the user selected OK, false otherwise.
     */
    public static boolean askJobSignup(String description) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(CONFIRMATION_DIALOG_TITLE);
        alert.setHeaderText("Are you sure you want to sign up for the job \"" 
        		+ description + "\"");
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    	    return true;
    	} else {
    	    return false;
    	}
    }
    
    /**
     * Asks volunteer for confirmation before unvolunteering from a job
     * they are volunteered for.
     * description != null
     * @param description The description of the job to unvolunteer from.
     * @return True if the user selected OK, false otherwise.
     */
    public static boolean askJobUnvolunteer(String description) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(CONFIRMATION_DIALOG_TITLE);
        alert.setHeaderText("Are you sure you want to unvolunteer from job \"" 
        		+ description + "\"");
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    	    return true;
    	} else {
    	    return false;
    	}
    }
}
