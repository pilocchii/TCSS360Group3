package urbanparks.view;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import urbanparks.model.Job;
import urbanparks.model.JobCollection;
import urbanparks.model.ParkManager;
import urbanparks.model.UrbanParksStaff;
import urbanparks.model.User;
import urbanparks.model.Volunteer;
import urbanparks.view.MainMenuPane.BackButtonEventHandler;
import urbanparks.model.UserCollection;

public class VolunteerPane extends GridPane {

    private BorderPane root;
    private UserCollection userCollection;
    private JobCollection jobCollection;
    private MainMenuPane back;
    private Button backButton;
    private Volunteer volunteer;
    
    public VolunteerPane(BorderPane root, UserCollection userCollection, JobCollection jobCollection, MainMenuPane back, Button backButton, Volunteer volunteer) {
        super();

        this.root = root;
        this.userCollection = userCollection;
        this.jobCollection = jobCollection;
        this.back = back;
        this.backButton = backButton;
        this.volunteer = volunteer;
 
        show();
    }

    public void show() {
        backButton.setText("Sign out");
        backButton.setOnAction(new BackButtonEventHandler());

    	// Login pane styles
        setAlignment(Pos.CENTER);
        setPadding(new Insets(5, 5, 5, 5));
        setHgap(5);
        setVgap(5);
        
        Button viewPendingButton = new Button("View your pending jobs");
        viewPendingButton.setOnAction(new viewPendingEventHandler());
        viewPendingButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        Button viewAvailableButton = new Button("View jobs available for signup");
        viewAvailableButton.setOnAction(new viewAvailableEventHandler());
        viewAvailableButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        add(viewPendingButton, 0, 0);
        add(viewAvailableButton, 0, 1);
    }
    
    
    public class viewPendingEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	
        }
    }
    
    
    public class viewAvailableEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	
            ArrayList<Job> jobsToShow = new ArrayList<Job>();
            Job testjob1 = new Job("This job starts on 1/20/2018.", LocalDateTime.of(2018, Month.JULY, 29, 17, 30), LocalDateTime.now(), "Park Name", "Park Location", 3, 4, 5, 20);
            Job testjob2 = new Job("This job starts on 1/20/2018.", LocalDateTime.of(2018, Month.JULY, 27, 19, 30), LocalDateTime.now(), "Park Name", "Park Location", 3, 4, 5, 20);
            Job testjob3 = new Job("This job starts on 1/20/2001.", LocalDateTime.of(2015, Month.JULY, 4, 12, 30), LocalDateTime.now(), "Park Name", "Park Location", 3, 4, 5, 20);
            Job testjob4 = new Job("This job starts on 1/20/2019.", LocalDateTime.of(2015, Month.JULY, 5, 2, 30), LocalDateTime.now(), "Park Name", "Park Location", 3, 4, 5, 20);
        	
            jobsToShow.add(testjob1);
            jobsToShow.add(testjob2);
            jobsToShow.add(testjob3);
            jobsToShow.add(testjob4);
            
        	root.setCenter(new JobDisplay().make(jobCollection, jobsToShow, true, true, null));
        }
    }

    
    public class BackButtonEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	root.setCenter(back);
        	back.show();
        }
    }
}