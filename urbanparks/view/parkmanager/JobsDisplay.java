package urbanparks.view.parkmanager;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;
import urbanparks.model.Job;
import urbanparks.model.ParkManager;
import urbanparks.view.AlertUtils;
import urbanparks.view.JobsTableView;
import urbanparks.view.MainApplication;

public class JobsDisplay extends JobsTableView {

	private ParkManagerMenu back;
	private ParkManager parkManager;
	
	public JobsDisplay(MainApplication root, ParkManagerMenu back) {
		this.root = root;
		this.back = back;
		
		Button backButton = root.getBackButton();
		backButton.setText("Back to park manager menu");
		backButton.setOnAction(new BackButton_ParkManager_Handler());
	}
    
	public void showParkManagerCreatedJobs(ParkManager parkManager) {
		this.parkManager = parkManager;
		String tableTitle = "\t\t\t\tJobs You Created";
        ArrayList<Job> jobsToShow = root.getJobCollection().getAvailableForUnsubmit(parkManager);
		
        Button uncreateButton = new Button();
        uncreateButton.setText("Unsubmit this job");
        uncreateButton.setOnAction(new UnsubmitJobButtonHandler());
		uncreateButton.setDisable(true);
		
        TableColumn<Job, String> canUncreate = new TableColumn<Job, String>("Can unsubmit");
        canUncreate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsAvailableFormatted()));
		
		VBox vbox = makeJobsTable(jobsToShow, tableTitle, canUncreate, true, uncreateButton, true);
		vbox.getChildren().add(uncreateButton);
		
		root.setCenter(vbox);
	}
	
    private class BackButton_ParkManager_Handler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	root.setCenter(back);
        	back.show();
        }
    }
    
    public class UnsubmitJobButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	if (selectedJob != null && selectedJob.getIsAvailable()) {
        		parkManager.unSubmitJob(selectedJob);
        		AlertUtils.showJobUnsubmitted(selectedJob.getDescription());
        		root.setCenter(back);
        	}
        }
    }
}
