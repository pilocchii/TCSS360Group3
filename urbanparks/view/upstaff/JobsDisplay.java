package urbanparks.view.upstaff;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;
import urbanparks.model.Job;
import urbanparks.model.UrbanParksStaff;
import urbanparks.view.JobsTableView;
import urbanparks.view.MainApplication;

public class JobsDisplay extends JobsTableView{
	
	private UPStaffMenu back;
	private UrbanParksStaff UPStaff;
	
	public JobsDisplay(MainApplication root, UPStaffMenu back) {
		this.root = root;
		this.back = back;
		
		Button backButton = root.getBackButton();
		backButton.setText("Back to Urban Parks Staff menu");
		backButton.setOnAction(new BackButton_UPStaff_Handler());
	}

	public void showStaffJobsBetweenDates(UrbanParksStaff UPStaff) {
		this.UPStaff = UPStaff;
		String tableTitle = "\t\t\t\tJobs Between X & Y";
        ArrayList<Job> jobsToShow = createsometestjobs();
		
        TableColumn<Job, String> canUncreate = new TableColumn<Job, String>("Can unsubmit");
        canUncreate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsAvailableFormatted()));
		
		VBox vbox = makeJobsTable(jobsToShow, tableTitle, canUncreate, false, null, false);
		
		root.setCenter(vbox);
	}
	
	public static ArrayList<Job> createsometestjobs() {
        ArrayList<Job> jobsToShow = new ArrayList<Job>();
        Job testjob1 = new Job("This job starts on 1/20/2018.", LocalDateTime.of(2018, Month.JULY, 29, 17, 30), LocalDateTime.now(), "Park Name", "Park Location");
        Job testjob2 = new Job("This job starts on 1/20/2018.", LocalDateTime.of(2018, Month.JULY, 27, 19, 30), LocalDateTime.now(), "Park Name", "Park Location");
        Job testjob3 = new Job("This job starts on 1/20/2001.", LocalDateTime.of(2015, Month.JULY, 4, 12, 30), LocalDateTime.now(), "Park Name", "Park Location");
        Job testjob4 = new Job("This job starts on 1/20/2019.", LocalDateTime.of(2015, Month.JULY, 5, 2, 30), LocalDateTime.now(), "Park Name", "Park Location");
        jobsToShow.add(testjob1);
        jobsToShow.add(testjob2);
        jobsToShow.add(testjob3);
        jobsToShow.add(testjob4);
        return jobsToShow;
	}
	  

    private class BackButton_UPStaff_Handler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	//root.setCenter(back_UPStaff);
        	//back_UPStaff.show();
        }
    }
}
