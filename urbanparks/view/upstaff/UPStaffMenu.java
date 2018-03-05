package urbanparks.view.upstaff;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import urbanparks.model.UrbanParksStaff;
import urbanparks.view.MainApplication;
import urbanparks.view.MainMenuPane;

/**
 * GridPane for showing the main Urban Parks Staff member menu
 * invariants: all fields non-null
 */
public class UPStaffMenu extends GridPane {

    private MainApplication root;
    private Button backButton;
    private UrbanParksStaff urbanParksStaff;
    
    /**
     * Constructor for UPStaffMenu.
     * 
     * @param root Reference to the root application. Constructs the pane and shows itself.
     * @param urbanParksStaff The Urban Parks Staff member this menu is for
     */
    public UPStaffMenu(MainApplication root, UrbanParksStaff urbanParksStaff) {
        super();

        this.root = root;
        this.backButton = root.getBackButton();
        this.urbanParksStaff = urbanParksStaff;
 
        show();
    }

    /**
     * Constructs and shows the main Urban Parks Staff member menu.
     */
    private void show() {
        backButton.setText("Sign out");
        backButton.setOnAction(new BackButtonEventHandler());

    	// Set pane styles
        setAlignment(Pos.CENTER);
        setPadding(new Insets(5, 5, 5, 5));
        setHgap(5);
        setVgap(5);
        
        // create view jobs button
        Button viewJobsButton = new Button("View all jobs in the system");
        viewJobsButton.setOnAction(new ViewJobsEventHandler());
        viewJobsButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        // create change system settings button
        Button changeSettingsButton = new Button("Change settings for Urban Parks");
        changeSettingsButton.setOnAction(new ChangeSettingsEventHandler());
        changeSettingsButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        // add buttons to this pane
        add(viewJobsButton, 0, 0);
        add(changeSettingsButton, 0, 1);
        
        root.setTitle("Urban Parks Staff Menu - " + urbanParksStaff.getEmail());
    }
    
    /**
     * Button event handler for viewing all the jobs in the system.
     */
    private class ViewJobsEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	root.setCenter(new DateRangeSelector(root, urbanParksStaff));
        }
    }
    
    /**
     * Button event handler for changing system settings.
     */
    private class ChangeSettingsEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	root.setCenter(new ChangeSettingsPane(root, urbanParksStaff));
        }
    }

    /**
     * Event handler for the back button. If pressed,
     * it will send the urban parks staff member to the main menu.
     */
    private class BackButtonEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	root.setCenter(new MainMenuPane(root));
        }
    }
}