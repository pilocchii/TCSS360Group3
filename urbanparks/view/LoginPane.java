package urbanparks.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * Login and Signup prompt pane.
 * This is a splash page that enables the user to either login, or
 * sign up if they do not have an account.
 */
public class LoginPane extends GridPane {

    BorderPane root;

    public LoginPane(BorderPane root) {
        super();

        this.root = root;

        // Login pane styles
        setAlignment(Pos.CENTER);
        setPadding(new Insets(5, 5, 5, 5));
        setHgap(5);
        setVgap(5);

        // Components
        Button loginButton = new Button("Log in");
        Button signupButton = new Button("Sign up");
        TextField userNameTextField = new TextField();

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
    }


    /**
     * Event handler for the "Sign in" button.
     * This will take the username that has been inputted and set the stage to
     * the appropriate menu page depending on the user's type
     */
    public class LoginEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            Object eventSource = event.getSource();
            System.out.println("placeholder text for login event");
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
            Object eventSource = event.getSource();
            root.setCenter(new SignupPane());
        }
    }

}