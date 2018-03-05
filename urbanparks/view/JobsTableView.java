package urbanparks.view;

import static urbanparks.view.ViewConstants.*;
import urbanparks.model.Job;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class JobsTableView extends GridPane {
	
	protected MainApplication root;
	protected Job selectedJob;

	public VBox makeJobsTable(ArrayList<Job> jobsToShow, String tableTitle, TableColumn<Job, String> lastColumn, 
			boolean showLastColumn, Button mainButton, boolean showMainButton) {
        
		Label titleLabel = new Label(tableTitle);
        titleLabel.setFont(new Font("Arial", 18));

        // set row background color to green if the job is available, grey otherwise
        if (showLastColumn) {
	        lastColumn.setCellFactory(new Callback<TableColumn<Job, String>, TableCell<Job, String>>() {
	            public TableCell call(TableColumn arg0) {
	                return new TableCell<Job, String>() {	
	                	@Override
	                    public void updateItem(String isAvail, boolean empty) {
	                        super.updateItem(isAvail, empty);
	                        if (!isEmpty()) {
	                            @SuppressWarnings("unchecked")
								TableRow<Job> currentRow = getTableRow();
	                            if (currentRow != null) {
	                                if(isAvail.equals("Yes")) {
	                                    this.setTextFill(AVAILABLE_JOB_COLOR);   
	                                    //currentRow.setStyle("-fx-background-color: DARKGREEN");
	            					} else {
	                                    this.setTextFill(UNAVAILABLE_JOB_COLOR);
	                                    //currentRow.setStyle("-fx-background-color: LIGHTGREY");
	            					}
	                            }
	                            setText(isAvail);
	                        }
	                    }
	                };
	            }
	        });
        }
		
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
                        	+ "\nLocation:\t\t" + newValue.getLocation()
                        	+ "\nVolunteers registered: " + newValue.getVolunteerCount() );
                        if (showMainButton) {
                        	// only enable button if job is available
                        	if (newValue.getIsAvailable()) {
                        		mainButton.setDisable(false);
                        	} else {
                        		mainButton.setDisable(true);
                        	}
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

        // add all showable job to table
        for (Job j : jobsToShow) {
        	jobsTable.getItems().add(j);
        }
        
        jobsTable.setMaxHeight(200);
        
        // create up VBox for all components
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().add(titleLabel);
        vbox.getChildren().add(jobsTable);
        vbox.getChildren().add(jobInfoBox);
        return vbox;
	}

}