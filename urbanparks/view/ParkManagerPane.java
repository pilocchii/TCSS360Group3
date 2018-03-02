package urbanparks.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import urbanparks.model.JobCollection;
import urbanparks.model.ParkManager;
import urbanparks.model.UserCollection;
import urbanparks.model.Volunteer;

public class ParkManagerPane extends GridPane {
	
	private ParkManagerPane parkManagerPane = this;
    private MainApplication root;
    private UserCollection userCollection;
    private JobCollection jobCollection;
    private MainMenuPane back;
    private Button backButton;
    ParkManager parkManager;
    
    public ParkManagerPane(MainApplication root, MainMenuPane back, ParkManager parkManager) {
        super();

        this.root = root;
        this.userCollection = root.getUserCollection();
        this.jobCollection = root.getJobCollection();
        this.back = back;
        this.backButton = root.getBackButton();
        this.parkManager = parkManager;
 
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
        
        Button viewSubmittedButton = new Button("View your submitted jobs");
        viewSubmittedButton.setOnAction(new viewSubmittedEventHandler());
        viewSubmittedButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        Button createJobButton = new Button("Create a new job");
        createJobButton.setOnAction(new createJobEventHandler());
        createJobButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        add(viewSubmittedButton, 0, 0);
        add(createJobButton, 0, 1);
    }
    
    
    private class viewSubmittedEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	JobDisplay jobDisplay = new JobDisplay(root, parkManagerPane);
        	jobDisplay.showParkManagerCreatedJobs(parkManager);
        }
    }
    
    
    private class createJobEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	 root.setCenter(new CreateJobPane(root, back, parkManager));
        }
    }
    
    private class BackButtonEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	root.setCenter(back);
        	back.show();
        }
    }
}
