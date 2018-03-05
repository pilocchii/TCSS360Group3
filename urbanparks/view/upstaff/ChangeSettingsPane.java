package urbanparks.view.upstaff;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import urbanparks.model.ModelConstants;
import urbanparks.model.JobCollection;
import urbanparks.model.UrbanParksStaff;
import urbanparks.view.AlertUtils;
import urbanparks.view.MainApplication;
import static urbanparks.model.ModelConstants.*;
import static urbanparks.view.ViewConstants.STYLE_FIELD_EDIT;
import static urbanparks.view.ViewConstants.STYLE_FIELD_INVALID;
import static urbanparks.view.ViewConstants.STYLE_FIELD_VALID;

/**
 * Gridpane for changing system settings as an Urban Parks staff member.
 */
public class ChangeSettingsPane extends GridPane {

    private MainApplication root;
    private UrbanParksStaff urbanParksStaff;
    private JobCollection jobCollection;
    
    private Button backButton; 
    private TextField newMaxJobsTextField;
    private boolean newMaxJobsValueSatisfied;

    /**
     * Constructor for ChangeSettingsPane
     * 
     * @param root Reference to the root application. Constructs the pane and shows itself.
     * @param urbanParksStaff The staff member this menu is for.
     */
    public ChangeSettingsPane(MainApplication root, UrbanParksStaff urbanParksStaff) {
        super();
        this.root = root;
        this.urbanParksStaff = urbanParksStaff;
        this.jobCollection = root.getJobCollection();
        this.backButton = root.getBackButton();
        newMaxJobsValueSatisfied = false;
        show();
    }
    
    /**
     * Shows the Urban Parks Staff member a prompt for 
     * changing the max number of pending jobs
     */
    private void show() {
        backButton.setText("Back to Urban Parks staff menu");
        backButton.setOnAction(new BackButtonEventHandler());
    	
        // Login pane styles
        setAlignment(Pos.CENTER);
        setPadding(new Insets(5, 5, 5, 5));
        setHgap(5);
        setVgap(5);

        // Create the buttons
        Button changeMaxJobsButton = new Button("Update maximum pending jobs in system");
        changeMaxJobsButton.setOnAction(new ChangeSettingEventHandler());
        Button setDefaultMaxJobsButton = new Button("Set to default value (" + DEFAULT_MAX_PENDING_JOBS + ")");
        setDefaultMaxJobsButton.setOnAction(new SetDefaultMaxJobsEventHandler());
        
        newMaxJobsTextField = new TextField();
        // This adds prompt text to the field and makes it not focused by default
        newMaxJobsTextField.setPromptText("Current: " + getMaxPendingJobs());
        newMaxJobsTextField.setText(new Integer(getMaxPendingJobs()).toString());
        newMaxJobsTextField.setFocusTraversable(false);
        validateNewMaxJobs();
        newMaxJobsTextField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
        	// focus gained
        	if (newValue) {
        		newMaxJobsTextField.setStyle(STYLE_FIELD_EDIT);
        		newMaxJobsValueSatisfied = false;
        	// focus lost
        	} else {
        		validateNewMaxJobs();
            }
        });
        
        // This allows the buttons to grow in size to match their container
        changeMaxJobsButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        setDefaultMaxJobsButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // adds the buttons and text field to this gridpane
        add(newMaxJobsTextField, 0, 1);
        add(changeMaxJobsButton, 0, 2, 2, 1);
        add(setDefaultMaxJobsButton, 0, 3, 2, 1);
        
        root.setTitle("Change settings - " + urbanParksStaff.getEmail());
    }
    
    /**
     * Validates the new user-entered value for the max pending jobs
     * by setting the text style and value satisfied flag appropriately.
     */
    private void validateNewMaxJobs() {
		if (isNewMaxJobsValid(newMaxJobsTextField.getText())) {
    		newMaxJobsTextField.setStyle(STYLE_FIELD_VALID);
    		newMaxJobsValueSatisfied = true;
		} else {
    		newMaxJobsTextField.setStyle(STYLE_FIELD_INVALID);
    		newMaxJobsValueSatisfied = false;
		}
    }
    
    /**
     * Checks if the string entered is an int and
     * if it is not below the minimum allowed value.
     * precondition: input != null
     * 
     * @param input The text field value
     * @return True if the text field value is an int and if it 
     * is not below the minimum allowed value for max pending jobs
     */
    private boolean isNewMaxJobsValid(String input) {
    	if (input.length() != 0) {
	        try {
	        	int newMaxJobs = Integer.parseInt(input);
	        	/*
	        	 * The maximum number of pending park jobs must be a positive integer.
	        	 */
	        	if (newMaxJobs >= MIN_VALUE_OF_MAX_PENDING_JOBS) {
	        		/*
	        		 * The new maximum can't be less than the current number of pending jobs
	        		 */
	        		if (newMaxJobs >= jobCollection.getPendingJobsCount()) {
	        			return true;
	        		} else {
	        			AlertUtils.maxPendingJobsTooLow(newMaxJobs, jobCollection.getPendingJobsCount());
	        		}
	        	}
	        } catch (NumberFormatException nfe) {
	        	nfe.printStackTrace();
	        }
    	}
        return false;
    }
    
    /**
     * Event handler for changing the entered max pending jobs setting.
     * Updates the setting if the input is valid, shows an error otherwise.
     */
    private class ChangeSettingEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	if (newMaxJobsValueSatisfied) {
        		int newValue = Integer.parseInt(newMaxJobsTextField.getText());
        		AlertUtils.showChangedMaxPendingJobs(newValue);
        		ModelConstants.setMaxPendingJobs(newValue);
        		root.setCenter(new ChangeSettingsPane(root, urbanParksStaff));
        	} else {
        		AlertUtils.showInvalidOptions();
        	}
        }
    }

    /**
     * Event handler for setting the max pending jobs to its default setting.
     */
    private class SetDefaultMaxJobsEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	ModelConstants.setDefaultMaxPendingJobs();
        	newMaxJobsTextField.setPromptText("Current: " + getMaxPendingJobs());
        	newMaxJobsTextField.setText(new Integer(getMaxPendingJobs()).toString());
        	validateNewMaxJobs();
        }
    }
    
    /**
     * Event handler for the back button. If pressed, 
     * it will send the urban parks staff member to the main urban parks staff menu.
     */
    private class BackButtonEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	root.setCenter(new UPStaffMenu(root, urbanParksStaff));
        }
    }
}