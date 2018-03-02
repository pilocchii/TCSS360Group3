package urbanparks.view;

import javafx.scene.control.Alert;
import urbanparks.model.Constants;
import javafx.embed.swing.JFXPanel;

public class MessageBoxUtils {

    /**
     * Shows upon successful data save.
     */
    public static void showDataSaveSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("All data is now saved to disk.");
        alert.setContentText("Job and user data saved to " 
        		+ Constants.JOB_DATA_FILE + " and " + Constants.USER_DATA_FILE + ".");
        alert.showAndWait();
    }
    
    /**
     * Shows upon error saving data.
     * @param error
     */
    public static void showDataSaveError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("User and job data could not be saved to disk!");
        alert.setContentText("Job and user data could not be saved to " 
        		+ Constants.JOB_DATA_FILE + " and " + Constants.USER_DATA_FILE + " (" + error + ")");
        alert.showAndWait();
    }
    
    /**
     * Shows upon error saving data.
     * @param error
     */
    public static void showDataLoadError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Could not load job and user data from disk!");
        alert.setContentText("Job and user data could not be loaded from " 
        		+ Constants.JOB_DATA_FILE + " and " + Constants.USER_DATA_FILE + " (" + error + ")");
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
    
    public static void showEmailAlreadyRegistered() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("That email is already in use!");
        alert.setContentText("Please register using a different email.");
        alert.showAndWait();
    }
    
    
    public static void newUserRegistered(String userType, String firstName) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("You are now registered as " + userType + ", " + firstName + "!");
        alert.setContentText("You will now be signed in.");
        alert.showAndWait();
    }
    
    
    public static void emailNotExist(String email) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Email not valid");
        alert.setHeaderText("There is not account registered for the email " + email + "!");
        alert.setContentText("If you do not have an account, please create one.");
        alert.showAndWait();
    }
    
    public static void showJobCreated(String description) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Your job has been created: \"" + description + "\"");
        alert.setContentText("You will now be taken to the park manager menu.");
        alert.showAndWait();
    }
    
    
    public static void showSignupUpForJob(String description) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("You are now signed up for the job: \"" + description + "\"");
        alert.setContentText("You will now be taken to the volunteer menu.");
        alert.showAndWait();
    }
    
}
