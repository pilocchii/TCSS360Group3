package urbanparks.view;

import urbanparks.model.Job;
import urbanparks.model.JobCollection;

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
	
	private Job selectedJob;

	public Node make(JobCollection jobCollection, ArrayList<Job> jobsToShow, boolean showCancelled, boolean showAvailable, Button action1) {
		
        Label tableTitle = new Label("\t\t\t\tAvailable Jobs");
        tableTitle.setFont(new Font("Arial", 18));
		
        Button action = new Button();
        action.setText("sign up for this job");
        action.setOnAction(new SignupJobButtonHandler());
		action.setDisable(true);
		
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
                        action.setDisable(false);
                        selectedJob = newValue;
                    }
                });
        jobsTable.getColumns().add(startTimeColumn);
        jobsTable.getColumns().add(parkNameColumn);
        jobsTable.getColumns().add(descriptionColumn);

        // add jobs to table
        for (Job j : jobsToShow) {
        	jobsTable.getItems().add(j);
        }
        
        // create up VBox for all components
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().add(tableTitle);
        vbox.getChildren().add(jobsTable);
        vbox.getChildren().add(jobInfoBox);
        vbox.getChildren().add(action);
        return vbox;
	}
	
	
    public class SignupJobButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	if (selectedJob != null) {
        		System.out.println("YOU ARE SIGNED UP FOR JOB " + selectedJob.getStartDateFormatted());
        	}
        }
    }
}