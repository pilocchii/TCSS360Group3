package urbanparks.view;

import javafx.scene.control.Alert;
import urbanparks.model.Constants;

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
}
