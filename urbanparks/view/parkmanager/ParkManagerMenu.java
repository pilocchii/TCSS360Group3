package urbanparks.view.parkmanager;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import urbanparks.model.ParkManager;
import urbanparks.view.AlertUtils;
import urbanparks.view.MainApplication;
import urbanparks.view.MainMenuPane;

/**
 * GridPane that displays the main park manager menu.
 */
public class ParkManagerMenu extends GridPane {
	
    private MainApplication root;
    private Button backButton;
    private ParkManager parkManager;
    
    /**
     * Constructor for ParkManagerMenu. Constructs the pane and shows itself.
     *  
     * @param root Reference to the root application.
     * @param parkManager The park manager to show the menu for.
     */
    public ParkManagerMenu(MainApplication root, ParkManager parkManager) {
        super();

        this.root = root;
        this.backButton = root.getBackButton();
        this.parkManager = parkManager;
 
        show();
    }
    
    /**
     * Shows the main park manager menu.
     */
    private void show() {
        backButton.setText("Sign out");
        backButton.setOnAction(new BackButtonEventHandler());

    	// set pane styles
        setAlignment(Pos.CENTER);
        setPadding(new Insets(5, 5, 5, 5));
        setHgap(5);
        setVgap(5);
        
        // create view submitted jobs button
        Button viewSubmittedButton = new Button("View your submitted jobs");
        viewSubmittedButton.setOnAction(new viewSubmittedEventHandler());
        viewSubmittedButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        // make the create job button
        Button createJobButton = new Button("Create a new job");
        createJobButton.setOnAction(new createJobEventHandler());
        createJobButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        // add buttons to this gridpane
        add(viewSubmittedButton, 0, 0);
        add(createJobButton, 0, 1);
        
        root.setTitle("Park Manager Menu - " + parkManager.getEmail());
    }
    
    /**
     * Button event handler for viewing submitted jobs.
     */
    private class viewSubmittedEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	JobsDisplay parkManagerJobDisplay = new JobsDisplay(root, parkManager);
        	parkManagerJobDisplay.showParkManagerCreatedJobs();
        }
    }
    
    /**
     * Button event handler for creating a new job. 
     * If the current number of pending jobs isn't at capacity, 
     * it will take the park manager to the job creation window.
     */
    private class createJobEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            // don't let park manager create jobs if system is at capacity.
        	if (root.getJobCollection().isNumJobsAtMaximum()) {
        		AlertUtils.numberJobsAtCapacity();
        	} else {
        		root.setCenter(new CreateJobPane(root, parkManager));
        	}
        }
    }
    
    /**
     * Event handler for the back button. If pressed, 
     * it will send the park manager to the main menu.
     */
    private class BackButtonEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	root.setCenter(new MainMenuPane(root));
        }
    }
}