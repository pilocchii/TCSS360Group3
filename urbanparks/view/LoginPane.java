package urbanparks.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import urbanparks.model.ParkManager;
import urbanparks.model.UrbanParksStaff;
import urbanparks.model.User;
import urbanparks.model.Volunteer;
import urbanparks.view.MainMenuPane.BackButtonEventHandler;
import urbanparks.model.UserCollection;

public class LoginPane extends GridPane {

    BorderPane root;
    UserCollection userCollection;
    String email;
    MainMenuPane back;
    Button backButton;
    
    public LoginPane(BorderPane root, UserCollection userCollection, String email, MainMenuPane back, Button backButton) {
        super();

        this.root = root;
        this.userCollection = userCollection;
        this.email = email;
        this.back = back;
        this.backButton = backButton;
 
        show();
    }

    
    public void show() {
        
        backButton.setText("Back (to main menu)");
        backButton.setOnAction(new BackButtonEventHandler());
        
        User user = userCollection.getUser(email);
        if (user == null) {
        	// Login pane styles
            setAlignment(Pos.CENTER);
            setPadding(new Insets(5, 5, 5, 5));
            setHgap(5);
            setVgap(5);
            
            Button signupButton = new Button("Not an user. Try again (this is temp UI solution)");
            signupButton.setOnAction(new BackButtonEventHandler());
            signupButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            add(signupButton, 0, 10);
        }
        else {
            if (user instanceof ParkManager) {
            	//new ParkManagerMenu(mainMenu, (ParkManager)user, jobCollection);
            } else if (user instanceof Volunteer) {
            	//new VolunteerMenu(mainMenu, (Volunteer)user, jobCollection);
            } else if (user instanceof UrbanParksStaff) {
            	//new UrbanParksStaffMenu();
            }
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