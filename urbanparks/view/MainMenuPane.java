package urbanparks.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import urbanparks.model.UserCollection;
import urbanparks.model.User;
import urbanparks.model.Volunteer;
import urbanparks.view.parkmanager.ParkManagerMenu;
import urbanparks.view.upstaff.UPStaffMenu;
import urbanparks.view.volunteer.VolunteerMenu;
import urbanparks.model.JobCollection;
import urbanparks.model.ParkManager;
import urbanparks.model.UrbanParksStaff;


/**
 * Login and Signup prompt pane.
 * This is a splash page that enables the user to either login, or
 * sign up if they do not have an account.
 */
public class MainMenuPane extends GridPane {

    MainApplication root;
    Button backButton;
    UserCollection userCollection;    
    
    TextField userNameTextField;

    public MainMenuPane(MainApplication root) {
        super();
        this.root = root;
        this.userCollection = root.getUserCollection();
        this.backButton = root.getBackButton();
        show();
    }
    
    private void show() {
        // Login pane styles
        setAlignment(Pos.CENTER);
        setPadding(new Insets(5, 5, 5, 5));
        setHgap(5);
        setVgap(5);

        // Components
        Button loginButton = new Button("Log in");
        Button signupButton = new Button("Sign up");
        userNameTextField = new TextField();

        // This adds prompt text to the field and makes it not focused by default
        userNameTextField.setPromptText("User name");
        userNameTextField.setFocusTraversable(false);

        loginButton.setOnAction(new LoginEventHandler());
        signupButton.setOnAction(new SignupMenuEventHandler());

        // This allows the buttons to grow in size to match their container
        loginButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        signupButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        add(userNameTextField, 0, 1);
        add(loginButton, 0, 2, 2, 1);
        add(signupButton, 0, 3, 2, 1);
        
        backButton.setText("Back");
        backButton.setDisable(true);
        
        root.setTitle("Urban Parks Main Menu");
    }


    /**
     * Event handler for the "Sign in" button.
     * This will take the username that has been inputted and set the stage to
     * the appropriate menu page depending on the user's type
     */
    public class LoginEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String email = userNameTextField.getText();
            if (!email.isEmpty()) {
            	User user = userCollection.getUser(email);
				if (user == null) {
					AlertUtils.emailNotExist(email);
				} else {
					if (user instanceof Volunteer) {
						root.setCenter(new VolunteerMenu(root, (Volunteer)user));
						backButton.setDisable(false);
					
					} else if (user instanceof ParkManager) {
						root.setCenter(new ParkManagerMenu(root, (ParkManager)user));
						backButton.setDisable(false);
					
					} else if (user instanceof UrbanParksStaff) {
						root.setCenter(new UPStaffMenu(root, (UrbanParksStaff)user));
						backButton.setDisable(false);
					}
				}
            }
        }
    }

    /**
     * Event handler for the "Sign up" button on the login splash page.
     * This will take the username that has been inputted and set the stage to
     * a sign-up form menu.
     */
    public class SignupMenuEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            backButton.setDisable(false);
            root.setCenter(new SignupPane(root));
        }
    }
}