package urbanparks.view.upstaff;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;
import urbanparks.model.DateUtils;
import urbanparks.model.JobAvailability;
import urbanparks.model.UrbanParksStaff;
import urbanparks.view.JobsTableView;
import urbanparks.view.MainApplication;

/**
 * JobsTableView for viewing all jobs in the system starting or ending between 2 dates.
 */
public class JobsDisplay extends JobsTableView{
	
	private DateRangeSelector dateRangeSelector;
	
	/**
	 * Constructor for JobsDisplay.
	 * @param root Reference to the root application.
	 */
	public JobsDisplay(MainApplication root) {
		this.root = root;
		
		Button backButton = root.getBackButton();
		backButton.setText("Back to date range selector");
		backButton.setOnAction(new BackButton_UPStaff_Handler());
	}

	/**
	 * Displays a list of jobs starting or ending between 2 dates.
	 * 
	 * @param UPStaff The Urban Parks staff the display is for.
	 * @param dateRangeSelector The previous DateRangeSelector instance.
	 * @param startDate The lower bound of the job start or end dates to display.
	 * @param endDate The upper bound of the job start or end dates to display.
	 */
	public void showStaffJobsBetweenDates(UrbanParksStaff UPStaff, DateRangeSelector 
			dateRangeSelector, LocalDateTime startDate, LocalDateTime endDate) {
		this.dateRangeSelector = dateRangeSelector;
		String startDateString = DateUtils.formatDateTime(startDate);
		String endDateString = DateUtils.formatDateTime(endDate);
		String tableTitle = "Jobs starting or ending from " + startDateString + " to " + endDateString;
        ArrayList<JobAvailability> jobsToShow = root.getJobCollection().getJobsBetween2DateTimes(startDate, endDate);
		
        // set up a JobsDisplay, without an availability column or an action button
        TableColumn<JobAvailability, String> canUncreate = new TableColumn<JobAvailability, String>("Can unsubmit");
        canUncreate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsAvailableFormatted()));
		
        // show the jobs table
		VBox vbox = makeJobsTable(jobsToShow, tableTitle, canUncreate, false, null, false);
		root.setCenter(vbox);
		root.setTitle("View Jobs - " + UPStaff.getEmail());
	} 

    /**
     * Event handler for the back button. If pressed, 
     * it will send the urban parks staff member to the
     * last visited date range selection screen in its original state.
     */
    private class BackButton_UPStaff_Handler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	dateRangeSelector.setBackButton();
        	root.setCenter(dateRangeSelector);
        }
    }
}
