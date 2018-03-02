package urbanparks.view;

import urbanparks.model.Job;
import urbanparks.model.JobCollection;
import urbanparks.model.ParkManager;
import urbanparks.model.UrbanParksStaff;
import urbanparks.model.User;
import urbanparks.model.Volunteer;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;

public class JobDisplay extends GridPane {
	
	private BorderPane root;
	
	private Button backButton_Volunteer;
	private Button backButton_parkManager;
	private Button backButton_UPStaff;

	private VolunteerPane back_Volunteer;
	private ParkManagerPane back_parkManager;
	private UrbanParksStaffPane back_UPStaff;

	private Job selectedJob;
	private User currentUser;
	
	
	public JobDisplay(BorderPane root, Button backButton_Volunteer, VolunteerPane back_Volunteer) {
		this.root = root;
		this.backButton_Volunteer = backButton_Volunteer;
		this.back_Volunteer = back_Volunteer;
		this.backButton_Volunteer.setText("Back to volunteer menu");
		this.backButton_Volunteer.setOnAction(new BackButton_Volunteer_Handler());
	}
	
	public JobDisplay(BorderPane root, Button backButton_parkManager, ParkManagerPane back_parkManager) {
		this.root = root;
		this.backButton_parkManager = backButton_parkManager;
		this.back_parkManager = back_parkManager;
		this.backButton_parkManager.setText("Back to park manager menu");
		this.backButton_parkManager.setOnAction(new BackButton_ParkManager_Handler());
	}
	
	public JobDisplay(BorderPane root, Button backButton_UPStaff, UrbanParksStaffPane back_UPStaff) {
		this.root = root;
		this.backButton_UPStaff = backButton_UPStaff;
		this.back_UPStaff = back_UPStaff;
		this.backButton_UPStaff.setText("Back to Urban Parks Staff menu");
		this.backButton_UPStaff.setOnAction(new BackButton_UPStaff_Handler());
	}
	
	public void showVolunteerAvailJobs(Volunteer volunteer, JobCollection jobCollection, BorderPane root) {

		currentUser = volunteer;
		String tableTitle = "\t\t\t\tAvailable Jobs";
        ArrayList<Job> jobsToShow = jobCollection.getAvilableJobs(volunteer);
		
        Button signUpButton = new Button();
        signUpButton.setText("sign up for this job");
        signUpButton.setOnAction(new SignupJobButtonHandler());
		signUpButton.setDisable(true);
		
        TableColumn<Job, String> canSignUp = new TableColumn<Job, String>("Can sign up");
        canSignUp.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsAvailableFormatted()));
		
		VBox vbox = make(jobsToShow, tableTitle, canSignUp, true, signUpButton, true);
		vbox.getChildren().add(signUpButton);
		
		root.setCenter(vbox);
	}
	
	public void showVolunteerPendingJobs(Volunteer volunteer, JobCollection jobCollection, BorderPane root) {
		currentUser = volunteer;
		String tableTitle = "\t\t\t\tYour Pending Jobs";
        ArrayList<Job> jobsToShow = volunteer.getSignedUpJobs(jobCollection);
		
        Button unvolunteerButton = new Button();
        unvolunteerButton.setText("unvolunteer from this job");
        unvolunteerButton.setOnAction(new UnvolunteerButtonHandler());
		unvolunteerButton.setDisable(true);
		
        TableColumn<Job, String> canUnvolunteer = new TableColumn<Job, String>("Can unvolunteer");
        canUnvolunteer.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsAvailableFormatted()));
		
		VBox vbox = make(jobsToShow, tableTitle, canUnvolunteer, true, unvolunteerButton, true);
		vbox.getChildren().add(unvolunteerButton);
		
		root.setCenter(vbox);
	}
	
	public void showParkManagerCreatedJobs(ParkManager parkManager, JobCollection jobCollection, BorderPane root) {
		currentUser = parkManager;
		String tableTitle = "\t\t\t\tJobs You Created";
        ArrayList<Job> jobsToShow = createsometestjobs();
		
        Button uncreateButton = new Button();
        uncreateButton.setText("unvolunteer from this job");
        uncreateButton.setOnAction(new UnsubmitJobButtonHandler());
		uncreateButton.setDisable(true);
		
        TableColumn<Job, String> canUncreate = new TableColumn<Job, String>("Can uncreate");
        canUncreate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsAvailableFormatted()));
		
		VBox vbox = make(jobsToShow, tableTitle, canUncreate, true, uncreateButton, true);
		vbox.getChildren().add(uncreateButton);
		
		root.setCenter(vbox);
	}
	
	public void showStaffJobsBetweenDates(UrbanParksStaff parksStaff, JobCollection jobCollection, BorderPane root) {
		currentUser = parksStaff;
		String tableTitle = "\t\t\t\tJobs Between X & Y";
        ArrayList<Job> jobsToShow = createsometestjobs();
		
        TableColumn<Job, String> canUncreate = new TableColumn<Job, String>("Can uncreate");
        canUncreate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsAvailableFormatted()));
		
		VBox vbox = make(jobsToShow, tableTitle, canUncreate, false, null, false);
		
		root.setCenter(vbox);
	}

	
	public VBox make(ArrayList<Job> jobsToShow, String tableTitle, TableColumn<Job, String> lastColumn, boolean showLastColumn, Button mainButton, boolean showMainButton) {
        Label titleLabel = new Label(tableTitle);
        titleLabel.setFont(new Font("Arial", 18));
		
		// Create job info area
        Label jobInfoHeader = new Label("Job Info:");
        Label jobInfoBody = new Label("\nNo job selected.");
        HBox jobInfoBox = new HBox();
        jobInfoBox.getChildren().addAll(jobInfoHeader, jobInfoBody);

        // Create all columns
        TableColumn<Job, String> startTimeColumn = new TableColumn<Job, String>("Start time");
        startTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartDateFormatted()));
        
        TableColumn<Job, String> parkNameColumn = new TableColumn<Job, String>("Park name");
        parkNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getParkName()));
        
        TableColumn<Job, String> descriptionColumn = new TableColumn<Job, String>("Description");
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        
        TableColumn<Job, String> cancelledColumn = new TableColumn<Job, String>("Cancelled");
        cancelledColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsCancelledFormatted()));

        // create job table
        TableView<Job> jobsTable = new TableView<Job>();
        jobsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Job>() {
                    @Override
                    public void changed(ObservableValue<? extends Job> observable, Job oldValue, Job newValue ) {
                        jobInfoBody.setText("\nStart time:\t" + newValue.getStartDateFormatted()
                        	+ "\nEnd time:\t\t" + newValue.getEndDateFormatted()
                        	+ "\nDescription:\t" + newValue.getDescription()
                        	+ "\nPark name:\t" + newValue.getParkName()
                        	+ "\nLocation:\t\t" + newValue.getLocation() );
                        if (showMainButton) {
                        	mainButton.setDisable(false);
                        }
                        selectedJob = newValue;
                    }
                });
        jobsTable.getColumns().add(startTimeColumn);
        jobsTable.getColumns().add(parkNameColumn);
        jobsTable.getColumns().add(descriptionColumn);
        jobsTable.getColumns().add(cancelledColumn);
        if (showLastColumn ) {
        	jobsTable.getColumns().add(lastColumn);
        }

        // add jobs to table
        for (Job j : jobsToShow) {
        	jobsTable.getItems().add(j);
        }
        
        // create up VBox for all components
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().add(titleLabel);
        vbox.getChildren().add(jobsTable);
        vbox.getChildren().add(jobInfoBox);
        return vbox;
	}
	
	
	public static ArrayList<Job> createsometestjobs() {
        ArrayList<Job> jobsToShow = new ArrayList<Job>();
        Job testjob1 = new Job("This job starts on 1/20/2018.", LocalDateTime.of(2018, Month.JULY, 29, 17, 30), LocalDateTime.now(), "Park Name", "Park Location", 3, 4, 5, 20);
        Job testjob2 = new Job("This job starts on 1/20/2018.", LocalDateTime.of(2018, Month.JULY, 27, 19, 30), LocalDateTime.now(), "Park Name", "Park Location", 3, 4, 5, 20);
        Job testjob3 = new Job("This job starts on 1/20/2001.", LocalDateTime.of(2015, Month.JULY, 4, 12, 30), LocalDateTime.now(), "Park Name", "Park Location", 3, 4, 5, 20);
        Job testjob4 = new Job("This job starts on 1/20/2019.", LocalDateTime.of(2015, Month.JULY, 5, 2, 30), LocalDateTime.now(), "Park Name", "Park Location", 3, 4, 5, 20);
        jobsToShow.add(testjob1);
        jobsToShow.add(testjob2);
        jobsToShow.add(testjob3);
        jobsToShow.add(testjob4);
        return jobsToShow;
	}
	
	
    public class SignupJobButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	if (selectedJob != null && selectedJob.getIsAvailable()) {
        		Volunteer volunteer = (Volunteer)currentUser;
        		volunteer.signUpForJob(selectedJob);
        		MessageBoxUtils.showSignupUpForJob(selectedJob.getDescription());
        	}
        }
    }
    
    public class UnvolunteerButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	if (selectedJob != null) {
        		System.out.println("TODO UNVOLUNTEER " + selectedJob.getStartDateFormatted());
        	}
        }
    }
    
    public class UnsubmitJobButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	if (selectedJob != null) {
        		System.out.println("TODO UNSUBMIT " + selectedJob.getStartDateFormatted());
        	}
        }
    }
    

    // back button handlers
    private class BackButton_Volunteer_Handler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	root.setCenter(back_Volunteer);
        	back_Volunteer.show();
        }
    }    
    private class BackButton_ParkManager_Handler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	root.setCenter(back_parkManager);
        	back_parkManager.show();
        }
    }
    private class BackButton_UPStaff_Handler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	//root.setCenter(back_UPStaff);
        	//back_UPStaff.show();
        }
    }
}