package urbanparks.view.parkmanager;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;
import urbanparks.model.JobAvailability;
import urbanparks.model.ParkManager;
import urbanparks.view.AlertUtils;
import urbanparks.view.JobsTableView;
import urbanparks.view.MainApplication;

/**
 * JobsTableView that shows jobs for a park manager.
 * invariants: all fields non-null
 */
public class JobsDisplay extends JobsTableView {

	private ParkManager parkManager;
	
	/**
	 * Constructor for JobsDisplay
	 * 
	 * @param root Reference to the root application.
	 * @param parkManager The park manager this menu is for.
	 */
	public JobsDisplay(MainApplication root, ParkManager parkManager) {
		this.root = root;
		this.parkManager = parkManager;
		
		Button backButton = root.getBackButton();
		backButton.setText("Back to park manager menu");
		backButton.setOnAction(new BackButton_ParkManager_Handler());
	}
    
	/**
	 * Displays the park manager's created jobs in a jobs table.
	 */
	public void showParkManagerCreatedJobs() {
		String tableTitle = "\t\t\t\tJobs You Created";
        ArrayList<JobAvailability> jobsToShow = root.getJobCollection().getAvailableForUnsubmit(parkManager);
		
        Button uncreateButton = new Button();
        uncreateButton.setText("Unsubmit this job");
        uncreateButton.setOnAction(new UnsubmitJobButtonHandler());
		uncreateButton.setDisable(true);
		
        TableColumn<JobAvailability, String> canUncreate = new TableColumn<JobAvailability, String>("Can unsubmit");
        canUncreate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsAvailableFormatted()));
		
		VBox vbox = makeJobsTable(jobsToShow, tableTitle, canUncreate, true, uncreateButton, true);
		vbox.getChildren().add(uncreateButton);
		
		root.setCenter(vbox);
		root.setTitle("View Submitted Jobs - " + parkManager.getEmail());
	}

	/**
	 * Event handler for the unsubmit job button. If the business rules are met, 
	 * pressing this will prompt the park manager to unsubmit the selected job.
	 */
    public class UnsubmitJobButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	if (selectedJob != null && selectedJob.getIsAvailable()) {
        		if (AlertUtils.askJobUnsubmit(selectedJob.getJob().getDescription())) {
            		parkManager.unSubmitJob(selectedJob.getJob());
            		AlertUtils.showJobUnsubmitSuccess();
            		root.setCenter(new ParkManagerMenu(root, parkManager));
        		}
        	} else {
        		AlertUtils.showInvalidOptions();
        	}
        }
    }
    
    /**
     * Event handler for the back button. If pressed, 
     * it will send the user to the park manager menu.
     */
    private class BackButton_ParkManager_Handler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	root.setCenter(new ParkManagerMenu(root, parkManager));
        }
    }
}
