package urbanparks.view.volunteer;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;
import urbanparks.model.JobAvailability;
import urbanparks.model.Volunteer;
import urbanparks.view.AlertUtils;
import urbanparks.view.JobsTableView;
import urbanparks.view.MainApplication;

/**
 * JobsTableView that shows jobs to a volunteer.
 */
public class JobsDisplay extends JobsTableView {
	
	private Volunteer volunteer;
	
	/**
	 * JobsDisplay constructor.
	 * 
	 * @param root reference to the root application
	 * @param volunteer the volunteer to show the menu for
	 */
	public JobsDisplay(MainApplication root, Volunteer volunteer) {
		this.root = root;
		this.volunteer = volunteer;
		
		Button backButton = root.getBackButton();
		backButton.setText("Back to volunteer menu");
		backButton.setOnAction(new BackButton_Volunteer_Handler());
	}
	
	/**
	 * Shows all available jobs the volunteer can sign up for
	 */
	public void showVolunteerAvailJobs() {
		String tableTitle = "\t\t\t\tAvailable Jobs";
        ArrayList<JobAvailability> jobsToShow = root.getJobCollection().getAvailableForSignup(volunteer);
		
        Button signUpButton = new Button();
        signUpButton.setText("Sign up for this job");
        signUpButton.setOnAction(new SignupJobButtonHandler());
		signUpButton.setDisable(true);
		
        TableColumn<JobAvailability, String> canSignUp = new TableColumn<JobAvailability, String>("Can sign up");
        canSignUp.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsAvailableFormatted()));
		
		VBox vbox = makeJobsTable(jobsToShow, tableTitle, canSignUp, true, signUpButton, true);
		vbox.getChildren().add(signUpButton);
		
		root.setCenter(vbox);
		root.setTitle("View Available Jobs - " + volunteer.getEmail());
	}
	
	/**
	 * Shows all jobs the volunteer is currently signed up for that are currently pending
	 */
	public void showVolunteerPendingJobs() {
		String tableTitle = "\t\t\t\tYour Pending Jobs";
        ArrayList<JobAvailability> jobsToShow = root.getJobCollection().getAvailableForUnvolunteer(volunteer);//volunteer.getSignedUpJobs(root.getJobCollection());
		
        Button unvolunteerButton = new Button();
        unvolunteerButton.setText("unvolunteer from this job");
        unvolunteerButton.setOnAction(new UnvolunteerButtonHandler());
		unvolunteerButton.setDisable(true);
		
        TableColumn<JobAvailability, String> canUnvolunteer = new TableColumn<JobAvailability, String>("Can unvolunteer");
        canUnvolunteer.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsAvailableFormatted()));
		
		VBox vbox = makeJobsTable(jobsToShow, tableTitle, canUnvolunteer, true, unvolunteerButton, true);
		vbox.getChildren().add(unvolunteerButton);
		
		root.setCenter(vbox);
		root.setTitle("View Pending Jobs - " + volunteer.getEmail());
	}

    
	/**
	 * An inner class that handles events for clicking the signup for a job button.
	 */
    public class SignupJobButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	if (selectedJob != null && selectedJob.getIsAvailable()) {
        		if (AlertUtils.askJobSignup(selectedJob.getJob().getDescription())) {
            		volunteer.signUpForJob(selectedJob.getJob().getJobId());
            		selectedJob.getJob().addVolunteer(volunteer.getEmail());
            		AlertUtils.showJobSignupSuccess();
            		root.setCenter(new VolunteerMenu(root, volunteer));
        		}
        	} else {
        		AlertUtils.showInvalidOptions();
        	}
        }
    }
    
	/**
	 * An inner class that handles events for clicking the unvolunteer for a job button.
	 */
    public class UnvolunteerButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	if (selectedJob != null && selectedJob.getIsAvailable()) {
        		if (AlertUtils.askJobUnvolunteer(selectedJob.getJob().getDescription())) {
        			selectedJob.getJob().removeVoluneer(volunteer.getEmail());
        			volunteer.unVolunteerFromJob(selectedJob.getJob().getJobId());
        			AlertUtils.showJobUnvolunteerSuccess();
        			root.setCenter(new VolunteerMenu(root, volunteer));
        		}
        	} else {
        		AlertUtils.showInvalidOptions();
        	}
        }
    }
    /**
     * An inner class that handles events for clicking the back button. Returns 
     * the user to the previous screen.
     */
    private class BackButton_Volunteer_Handler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	root.setCenter(new VolunteerMenu(root, volunteer));
        }
    }
}