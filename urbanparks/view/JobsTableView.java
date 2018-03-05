package urbanparks.view;

import static urbanparks.view.ViewConstants.*;
import urbanparks.model.Job;
import urbanparks.model.JobAvailability;

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
import javafx.util.Callback;

/**
 * Holds the method for creating a jobs table.
 */
public class JobsTableView extends GridPane {
	
	protected MainApplication root;
	protected JobAvailability selectedJob;

	/**
	 * Creates a VBox node that holds a jobs table, its title, and a job info area.
	 * 
	 * @param jobsToShow The jobs the to show in the table.
	 * @param tableTitle The title of the jobs table.
	 * @param lastColumn The last column of the table.
	 * @param showLastColumn Flag to determine whether to show the column (lastColumn).
	 * @param mainButton The button under the table that does an action of a selected job.
	 * @param showMainButton Flag to determine whether to show the button (mainButton).
	 * @return A VBox that holds a jobs table containing the supplied,
	 * a supplied title, and a job info area.
	 */
	public VBox makeJobsTable(ArrayList<JobAvailability> jobsToShow, String tableTitle, 
			TableColumn<JobAvailability, String> lastColumn, boolean showLastColumn, 
			Button mainButton, boolean showMainButton) {
        
		Label titleLabel = new Label(tableTitle);
        titleLabel.setFont(new Font("Arial", 18));

        // set text color of cell in "is available" column to green if the job is available, red otherwise
        if (showLastColumn) {
	        lastColumn.setCellFactory(new Callback<TableColumn<JobAvailability, String>, 
	        		TableCell<JobAvailability, String>>() {
	            @SuppressWarnings("rawtypes")
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
	            					} else {
	                                    this.setTextFill(UNAVAILABLE_JOB_COLOR);
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
        TableColumn<JobAvailability, String> startTimeColumn = new TableColumn<JobAvailability, String>("Start time");
        startTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getJob().getStartDateFormatted()));
        
        TableColumn<JobAvailability, String> parkNameColumn = new TableColumn<JobAvailability, String>("Park name");
        parkNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getJob().getParkName()));
        
        TableColumn<JobAvailability, String> descriptionColumn = new TableColumn<JobAvailability, String>("Description");
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getJob().getDescription()));
        
        TableColumn<JobAvailability, String> cancelledColumn = new TableColumn<JobAvailability, String>("Cancelled");
        cancelledColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getJob().getIsCancelledFormatted()));

        // create job table
        TableView<JobAvailability> jobsTable = new TableView<JobAvailability>();
        jobsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<JobAvailability>() {
                    @Override
                    public void changed(ObservableValue<? extends JobAvailability> observable, JobAvailability oldValue, JobAvailability newValue ) {
                        jobInfoBody.setText("\nStart time:\t" + newValue.getJob().getStartDateFormatted()
                        	+ "\nEnd time:\t\t" + newValue.getJob().getEndDateFormatted()
                        	+ "\nDescription:\t" + newValue.getJob().getDescription()
                        	+ "\nPark name:\t" + newValue.getJob().getParkName()
                        	+ "\nLocation:\t\t" + newValue.getJob().getLocation()
                        	+ "\nVolunteers registered: " + newValue.getJob().getVolunteerCount() );
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

        // add all showable jobs to table
        for (JobAvailability j : jobsToShow) {
        	jobsTable.getItems().add(j);
        }
        
        jobsTable.setMaxHeight(MAX_JOBS_TABLE_HEIGHT);
        
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