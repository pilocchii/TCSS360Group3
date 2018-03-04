package urbanparks.view;

import static urbanparks.view.ViewConstants.*;
import urbanparks.model.Constants;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.embed.swing.JFXPanel;
import java.util.Optional;
import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.GridPane;

public class AlertUtils {
	
	/**
	 * 
	 * @param ex
	 * @param header
	 * @param content
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
	
	// ERROR DIALOGS ------------------------------------------------------------------------------------------------------
    
	public static void showInvalidOptions() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ERROR_DIALOG_TITLE);
        alert.setHeaderText("Invalid input!");
        alert.setContentText("Please ensure all your input is valid.");
        alert.showAndWait();
	}
	
	/**
     * Shows upon error saving data.
     * @param error
     */
    public static void showDataSaveError(Exception ex) {
    	String header = "User and job data could not be saved to disk!";
    	String content = "Job and user data could not be saved to " 
        		+ Constants.JOB_DATA_FILE + " and " + Constants.USER_DATA_FILE;
    	showExceptionAlert(ex, header, content);
    }
    
    /**
     * Shows upon error saving data.
     * @param error
     */
    public static void showDataLoadError(Exception ex) {	
    	String header = "Could not load job and user data from disk!";
    	String content = "Job and user data could not be loaded from " 
        		+ Constants.JOB_DATA_FILE + " and " + Constants.USER_DATA_FILE;
    	showExceptionAlert(ex, header, content);
    }
    
    public static void showEmailAlreadyRegistered() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ERROR_DIALOG_TITLE);
        alert.setHeaderText("That email is already in use!");
        alert.setContentText("Please register using a different email.");
        alert.showAndWait();
    }
    
    public static void emailNotExist(String email) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ERROR_DIALOG_TITLE);
        alert.setHeaderText("There is not account registered for the email " + email + "!");
        alert.setContentText("If you do not have an account, please create one.");
        alert.showAndWait();
    }
	
	// SUCCESS DIALOGS ------------------------------------------------------------------------------------------------------
    /**
     * Shows upon successful data save.
     */
    public static void showDataSaveSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(SUCCESS_DIALOG_TITLE);
        alert.setHeaderText("All data is now saved to disk.");
        alert.setContentText("Job and user data saved to " 
        		+ Constants.JOB_DATA_FILE + " and " + Constants.USER_DATA_FILE + ".");
        alert.showAndWait();
    }
    
    public static void showRegisterSuccess(String userType, String firstName) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(SUCCESS_DIALOG_TITLE);
        alert.setHeaderText("You are now registered as " + userType + ", " + firstName + "!");
        alert.setContentText("You will now be signed in.");
        alert.showAndWait();
    }
    
    public static void showJobSubmitSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(SUCCESS_DIALOG_TITLE);
        alert.setHeaderText("The job has been created!");
        alert.setContentText("You will now be taken to the park manager menu.");
        alert.showAndWait();
    }
    
    public static void showJobUnsubmitSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(SUCCESS_DIALOG_TITLE);
        alert.setHeaderText("The job has been unsubmitted.");
        alert.setContentText("You will now be taken to the park manager menu.");
        alert.showAndWait();
    }
    
    public static void showJobSignupSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(SUCCESS_DIALOG_TITLE);
        alert.setHeaderText("You are signed up for the job!");
        alert.setContentText("You will now be taken to the volunteer menu.");
        alert.showAndWait();
    }
    
    public static void showJobUnvolunteerSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(SUCCESS_DIALOG_TITLE);
        alert.setHeaderText("You are unvolunteered from the job!");
        alert.setContentText("You will now be taken to the volunteer menu.");
        alert.showAndWait();
    }
    
    /**
     * Shows upon loading empty data into the system 
     * @param error
     */
    public static void showEmptyDataUsed() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Empty Data Used");
        alert.setHeaderText("An empty data set (users and jobs) has been loaded.");
        alert.showAndWait();
    }
	
	// CONFIRMATION DIALOGS  ------------------------------------------------------------------------------------------------------
    
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
    
    public static boolean askJobUnsubmit(String description) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(CONFIRMATION_DIALOG_TITLE);
        alert.setHeaderText("Are you sure you want to unsubmit (cancel) the job \"" + description + "\"");
        //alert.setContentText("You will now be taken to the park manager menu.");
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    	    return true;
    	} else {
    	    return false;
    	}
    }
    
    public static boolean askJobSignup(String description) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(CONFIRMATION_DIALOG_TITLE);
        alert.setHeaderText("Are you sure you want to sign up for the job \"" + description + "\"");
        //alert.setContentText("You will now be taken to the volunteer menu.");
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    	    return true;
    	} else {
    	    return false;
    	}
    }
    
    public static boolean askJobUnvolunteer(String description) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(CONFIRMATION_DIALOG_TITLE);
        alert.setHeaderText("Are you sure you want to unvolunteer from job \"" + description + "\"");
        //alert.setContentText("You will now be taken to the volunteer menu.");
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    	    return true;
    	} else {
    	    return false;
    	}
    }
}
