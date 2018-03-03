package urbanparks.view.volunteer;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;
import urbanparks.model.Job;
import urbanparks.model.Volunteer;
import urbanparks.view.AlertUtils;
import urbanparks.view.JobsTableView;
import urbanparks.view.MainApplication;

public class JobsDisplay extends JobsTableView {
	
	private VolunteerPane back;
	private Volunteer volunteer;
	
	public JobsDisplay(MainApplication root, VolunteerPane back) {
		this.root = root;
		this.back = back;
		
		Button backButton = root.getBackButton();
		backButton.setText("Back to volunteer menu");
		backButton.setOnAction(new BackButton_Volunteer_Handler());
	}
	
	
	public void showVolunteerAvailJobs(Volunteer volunteer) {
		this.volunteer = volunteer;
		String tableTitle = "\t\t\t\tAvailable Jobs";
        ArrayList<Job> jobsToShow = root.getJobCollection().getAvailableForSignup(volunteer);
		
        Button signUpButton = new Button();
        signUpButton.setText("sign up for this job");
        signUpButton.setOnAction(new SignupJobButtonHandler());
		signUpButton.setDisable(true);
		
        TableColumn<Job, String> canSignUp = new TableColumn<Job, String>("Can sign up");
        canSignUp.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsAvailableFormatted()));
		
		VBox vbox = makeJobsTable(jobsToShow, tableTitle, canSignUp, true, signUpButton, true);
		vbox.getChildren().add(signUpButton);
		
		root.setCenter(vbox);
	}
	
	public void showVolunteerPendingJobs(Volunteer volunteer) {
		this.volunteer = volunteer;
		String tableTitle = "\t\t\t\tYour Pending Jobs";
        ArrayList<Job> jobsToShow = volunteer.getSignedUpJobs(root.getJobCollection());
		
        Button unvolunteerButton = new Button();
        unvolunteerButton.setText("unvolunteer from this job");
        unvolunteerButton.setOnAction(new UnvolunteerButtonHandler());
		unvolunteerButton.setDisable(true);
		
        TableColumn<Job, String> canUnvolunteer = new TableColumn<Job, String>("Can unvolunteer");
        canUnvolunteer.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsAvailableFormatted()));
		
		VBox vbox = makeJobsTable(jobsToShow, tableTitle, canUnvolunteer, true, unvolunteerButton, true);
		vbox.getChildren().add(unvolunteerButton);
		
		root.setCenter(vbox);
	}

    
    public class SignupJobButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	if (selectedJob != null && selectedJob.getIsAvailable()) {
        		volunteer.signUpForJob(selectedJob);
        		AlertUtils.showSignupUpForJob(selectedJob.getDescription());
        		root.setCenter(back);
        	}
        }
    }
    public class UnvolunteerButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	if (selectedJob != null && selectedJob.getIsAvailable()) {
        		volunteer.unVolunteerFromJob(selectedJob);
        		AlertUtils.showUnvolunteered(selectedJob.getDescription());
        		root.setCenter(back);
        	}
        }
    }
    private class BackButton_Volunteer_Handler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	root.setCenter(back);
        	back.show();
        }
    }
}