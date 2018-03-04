package urbanparks.view.upstaff;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;
import urbanparks.model.DateUtils;
import urbanparks.model.Job;
import urbanparks.model.UrbanParksStaff;
import urbanparks.view.JobsTableView;
import urbanparks.view.MainApplication;

public class JobsDisplay extends JobsTableView{
	
	private UrbanParksStaff UPStaff;
	private DateRangeSelector dateRangeSelector;
	
	public JobsDisplay(MainApplication root) {
		this.root = root;
		
		Button backButton = root.getBackButton();
		backButton.setText("Back to date range selector");
		backButton.setOnAction(new BackButton_UPStaff_Handler());
	}

	public void showStaffJobsBetweenDates(UrbanParksStaff UPStaff, DateRangeSelector dateRangeSelector, LocalDateTime startDate, LocalDateTime endDate) {
		this.UPStaff = UPStaff;
		this.dateRangeSelector = dateRangeSelector;
		String startDateString = DateUtils.formatDateTime(startDate);
		String endDateString = DateUtils.formatDateTime(endDate);
		String tableTitle = "Jobs starting or ending from " + startDateString + " to " + endDateString;
        ArrayList<Job> jobsToShow = root.getJobCollection().getJobsBetweenDates(startDate, endDate);
		
        TableColumn<Job, String> canUncreate = new TableColumn<Job, String>("Can unsubmit");
        canUncreate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsAvailableFormatted()));
		
		VBox vbox = makeJobsTable(jobsToShow, tableTitle, canUncreate, false, null, false);
		
		root.setCenter(vbox);
		root.setTitle("View Jobs - " + UPStaff.getEmail());
	} 

    private class BackButton_UPStaff_Handler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	dateRangeSelector.setBackButton();
        	root.setCenter(dateRangeSelector);
        }
    }
}
