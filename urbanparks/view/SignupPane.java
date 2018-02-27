package urbanparks.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/***
 * Signup GUI component.
 * This is a splash page that enables the user to sign up for a new account.
 *
 * @author rrki@uw.edu
 * @version 2/26/18
 *
 */
public class SignupPane extends GridPane {

    BorderPane root;

    public SignupPane(BorderPane root) {
        super();

        this.root = root;

        // Login pane styles
        setAlignment(Pos.CENTER);
        setPadding(new Insets(5, 5, 5, 5));
        setHgap(5);
        setVgap(5);

        // Components
        Button signupButton = new Button("Sign up");
        TextField firstNameTextField = new TextField();
        TextField lastNameTextField = new TextField();
        TextField emailTextField = new TextField();
        TextField phoneNumberTextField = new TextField();
        ToggleGroup accountTypeGroup = new ToggleGroup();
        RadioButton volunteerRadioButton = new RadioButton("Volunteer");
        RadioButton parkManagerRadioButton = new RadioButton("Park Manager");
        RadioButton staffRadioButton = new RadioButton("Staff");

        // Create a button group for account type
        volunteerRadioButton.setToggleGroup(accountTypeGroup);
        parkManagerRadioButton.setToggleGroup(accountTypeGroup);
        staffRadioButton.setToggleGroup(accountTypeGroup);
        volunteerRadioButton.setSelected(true);

        // This adds prompt text to the fields and makes them not focused by default
        firstNameTextField.setPromptText("First name");
        firstNameTextField.setFocusTraversable(false);
        lastNameTextField.setPromptText("Last name");
        lastNameTextField.setFocusTraversable(false);
        emailTextField.setPromptText("Email");
        emailTextField.setFocusTraversable(false);
        phoneNumberTextField.setPromptText("Phone e.g. 4251234567");
        phoneNumberTextField.setFocusTraversable(false);

        signupButton.setOnAction(new SignupEventHandler());

        // This allows the buttons to grow in size to match their container
        signupButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        add(firstNameTextField, 0, 1);
        add(lastNameTextField, 0, 2);
        add(emailTextField, 0, 3);
        add(phoneNumberTextField, 0, 4);
        add(new Separator(), 0, 5);
        add(volunteerRadioButton, 0, 6);
        add(parkManagerRadioButton, 0, 7);
        add(staffRadioButton, 0, 8);
        add(new Separator(), 0, 9);
        add(signupButton, 0, 10);
    }

    /**
     * Event handler for the "Sign up" button on the sign up page.
     * This will take the input and create a new user, then set the stage to
     * the appropriate.
     */
    public class SignupEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
        }
    }
}