package urbanparks.view.volunteer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import urbanparks.model.Volunteer;
import urbanparks.view.MainApplication;
import urbanparks.view.MainMenuPane;

/**
 * Grid pane that shows the main volunteer menu.
 * invariants: all fields non-null
 */
public class VolunteerMenu extends GridPane {

    private MainApplication root;
    private Button backButton;
    private Volunteer volunteer;
    
    /**
     * Constructor for VolunteerMenu.
     * 
     * @param root The root of the application
     * @param volunteer the volunteer to show the menu to
     */
    public VolunteerMenu(MainApplication root, Volunteer volunteer) {
        super();

        this.root = root;
        this.backButton = root.getBackButton();
        this.volunteer = volunteer;
 
        show();
    }
    
    /**
     * Shows the main volunteer screen. Users can navigate to signing up for a job
     * or viewing their currently signed up jobs.
     */
    private void show() {
        backButton.setText("Sign out");
        backButton.setOnAction(new BackButtonEventHandler());

    	// Set pane styles
        setAlignment(Pos.CENTER);
        setPadding(new Insets(5, 5, 5, 5));
        setHgap(5);
        setVgap(5);
        
        // create view pending jobs button
        Button viewPendingButton = new Button("View your pending jobs");
        viewPendingButton.setOnAction(new viewPendingEventHandler());
        viewPendingButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        // create view available jobs butotn
        Button viewAvailableButton = new Button("View jobs available for signup");
        viewAvailableButton.setOnAction(new viewAvailableEventHandler());
        viewAvailableButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        // add buttons to this grid pane
        add(viewPendingButton, 0, 0);
        add(viewAvailableButton, 0, 1);
        
        root.setTitle("Volunteer Menu - " + volunteer.getEmail());
    }
    
    /**
     * Inner class that handles the button to move to the signed up jobs screen.
     */
    private class viewPendingEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	JobsDisplay volunteerJobDisplay = new JobsDisplay(root, volunteer);
        	volunteerJobDisplay.showVolunteerPendingJobs();
        }
    }
    
    /**
     * Inner class that handles the button to move to the available-for-signup jobs screen.
     */
    private class viewAvailableEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	JobsDisplay volunteerJobDisplay = new JobsDisplay(root, volunteer);
        	volunteerJobDisplay.showVolunteerAvailJobs();
        }
    }

    /**
     * An inner class that handles events for clicking the back button. Returns 
     * the user to the previous screen.
     */
    private class BackButtonEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	root.setCenter(new MainMenuPane(root));
        }
    }
}