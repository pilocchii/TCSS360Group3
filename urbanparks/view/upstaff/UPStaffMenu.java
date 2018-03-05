package urbanparks.view.upstaff;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import urbanparks.model.ParkManager;
import urbanparks.model.UrbanParksStaff;
import urbanparks.model.User;
import urbanparks.model.UserCollection;
import urbanparks.model.Volunteer;
import urbanparks.view.AlertUtils;
import urbanparks.view.MainApplication;
import urbanparks.view.MainMenuPane;
import urbanparks.view.SignupPane;

public class UPStaffMenu extends GridPane {

    private MainApplication root;
    private Button backButton;
    private UrbanParksStaff urbanParksStaff;
    
    public UPStaffMenu(MainApplication root, UrbanParksStaff urbanParksStaff) {
        super();

        this.root = root;
        this.backButton = root.getBackButton();
        this.urbanParksStaff = urbanParksStaff;
 
        show();
    }

    private void show() {
        backButton.setText("Sign out");
        backButton.setOnAction(new BackButtonEventHandler());

    	// Login pane styles
        setAlignment(Pos.CENTER);
        setPadding(new Insets(5, 5, 5, 5));
        setHgap(5);
        setVgap(5);
        
        Button viewJobsButton = new Button("View all jobs in the system");
        viewJobsButton.setOnAction(new ViewJobsEventHandler());
        viewJobsButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        Button changeSettingsButton = new Button("Change settings for Urban Parks");
        changeSettingsButton.setOnAction(new ChangeSettingsEventHandler());
        changeSettingsButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        add(viewJobsButton, 0, 0);
        add(changeSettingsButton, 0, 1);
        
        root.setTitle("Urban Parks Staff Menu - " + urbanParksStaff.getEmail());
    }
    
    private class ViewJobsEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	root.setCenter(new DateRangeSelector(root, urbanParksStaff));
        }
    }
    
    private class ChangeSettingsEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	root.setCenter(new ChangeSettingsPane(root, urbanParksStaff));
        }
    }

    private class BackButtonEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	root.setCenter(new MainMenuPane(root));
        }
    }
}