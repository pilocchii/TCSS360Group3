package urbanparks.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import urbanparks.model.UserCollection;
import urbanparks.model.Volunteer;
import urbanparks.model.ParkManager;
import urbanparks.model.UrbanParksStaff;
import urbanparks.view.LoginPane.BackButtonEventHandler;

/***
 * Signup GUI component.
 * This is a splash page that enables the user to sign up for a new account.
 *
 * @author rrki@uw.edu
 * @version 2/26/18
 *
 */
public class SignupPane extends GridPane {
	
	private static final String EMAIL_REGEX = ".+\\@.+\\..+";
	//https://howtodoinjava.com/regex/java-regex-validate-and-format-north-american-phone-numbers/
	private static final String PHONE_INPUT_REGEX = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
	private static final String PHONE_DISPLAY_REGEX = "($1) $2-$3";
	
	private static final String STYLE_FIELD_EDIT = "-fx-text-fill: black;";
	private static final String STYLE_FIELD_VALID = "-fx-text-fill: green;";
	private static final String STYLE_FIELD_INVALID = "-fx-text-fill: red;";
	
	// text fields
	private TextField emailTextField;
	private TextField firstNameTextField;
	private TextField lastNameTextField;
	private TextField phoneNumberTextField;
	
	// radio buttons
	RadioButton volunteerRadioButton;
	RadioButton parkManagerRadioButton;
	RadioButton staffRadioButton;
	ToggleGroup accountTypeGroup;
	
	// meets requirements flags
	private boolean emailSatisfied;
	private boolean firstNameSatisfied;
	private boolean lastNameSatisfied;
	private boolean phoneSatisfied;

    private BorderPane root;
    private Button backButton;
    private MainMenuPane back;
    private UserCollection userCollection;

    public SignupPane(BorderPane root, Button backButton, MainMenuPane back, UserCollection userCollection) {
        super();

        this.root = root;
        this.backButton = backButton;
        this.back = back;
        this.userCollection = userCollection;
        
        emailSatisfied = false;
        firstNameSatisfied = false;
        lastNameSatisfied = false;
        phoneSatisfied = false;
        
        show();
    }
    
    public void show() {
    	// set up back button
        backButton.setText("Back (to main menu)");
        backButton.setOnAction(new BackButtonEventHandler());
        
        // set up email text field
        emailTextField = new TextField();
        emailTextField.setPromptText("Email");
        emailTextField.setFocusTraversable(false);
        emailTextField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
        	// focus gained
        	if (newValue) {
        		emailTextField.setStyle(STYLE_FIELD_EDIT);
        		emailSatisfied = false;
        	// focus lost
        	} else {
                if(emailTextField.getText().matches(EMAIL_REGEX)) {
            		// email doesn't exit in system
            		if (userCollection.getUser(emailTextField.getText()) == null) {
                    	emailTextField.setStyle(STYLE_FIELD_VALID);
                    	emailSatisfied = true;
            		} else {
            			emailTextField.setStyle(STYLE_FIELD_INVALID);
            			MessageBoxUtils.showEmailAlreadyRegistered();
            		}
                } else {
                	emailTextField.setStyle(STYLE_FIELD_INVALID);
                }
            }
        });
        
        firstNameTextField = new TextField();
        firstNameTextField.setPromptText("First name");
        firstNameTextField.setFocusTraversable(false);
        firstNameTextField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
        	// focus gained
        	if (newValue) {
        		firstNameTextField.setStyle(STYLE_FIELD_EDIT);
        		firstNameSatisfied = false;
        	// focus lost
        	} else {
        		// any non-empty name is ok
        		firstNameTextField.setStyle(STYLE_FIELD_VALID);
        		firstNameSatisfied = true;

            }
        });
        
        lastNameTextField = new TextField();
        lastNameTextField.setPromptText("Last name");
        lastNameTextField.setFocusTraversable(false);
        lastNameTextField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
        	// focus gained
        	if (newValue) {
        		lastNameTextField.setStyle(STYLE_FIELD_EDIT);
        		lastNameSatisfied = false;
        	// focus lost
        	} else {
        		// any non-empty name is ok
        		lastNameTextField.setStyle(STYLE_FIELD_VALID);
        		lastNameSatisfied = true;

            }
        });
        
        phoneNumberTextField = new TextField();
        // This adds prompt text to the fields and makes them not focused by default
        phoneNumberTextField.setPromptText("Phone Number (USA)");
        phoneNumberTextField.setFocusTraversable(false);
        phoneNumberTextField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
        	// focus gained
        	if (newValue) {
        		phoneNumberTextField.setStyle(STYLE_FIELD_EDIT);
        		phoneSatisfied = false;
        	// focus lost
        	} else {
            	Pattern pattern = Pattern.compile(PHONE_INPUT_REGEX);
                Matcher matcher = pattern.matcher(phoneNumberTextField.getText());
                if(matcher.matches()) {
                	phoneNumberTextField.setStyle(STYLE_FIELD_VALID);
                	phoneNumberTextField.setText(matcher.replaceFirst(PHONE_DISPLAY_REGEX));
                	phoneSatisfied = true;
                } else {
                	phoneNumberTextField.setStyle(STYLE_FIELD_INVALID);
                }
            }
        });
        
        // Create a button group for account type
        accountTypeGroup = new ToggleGroup();
        
        volunteerRadioButton = new RadioButton("Volunteer");
        volunteerRadioButton.setToggleGroup(accountTypeGroup);
        volunteerRadioButton.setSelected(true);
        
        parkManagerRadioButton = new RadioButton("Park Manager");
        parkManagerRadioButton.setToggleGroup(accountTypeGroup);
        
        staffRadioButton = new RadioButton("Staff");
        staffRadioButton.setToggleGroup(accountTypeGroup);
        
        Button signupButton = new Button("Sign up");
        signupButton.setOnAction(new SignupEventHandler());
        // Allows it to grow in size to match their container
        signupButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
      
        // add all buttons to this
        add(emailTextField, 0, 1);
        add(firstNameTextField, 0, 2);
        add(lastNameTextField, 0, 3);
        add(phoneNumberTextField, 0, 4);
        add(new Separator(), 0, 5);
        add(volunteerRadioButton, 0, 6);
        add(parkManagerRadioButton, 0, 7);
        add(staffRadioButton, 0, 8);
        add(new Separator(), 0, 9);
        add(signupButton, 0, 10);
        
        // Login pane styles
        setAlignment(Pos.CENTER);
        setPadding(new Insets(5, 5, 5, 5));
        setHgap(5);
        setVgap(5);
    }

    public class BackButtonEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	root.setCenter(back);
        	back.show();
        }
    }

    /**
     * Event handler for the "Sign up" button on the sign up page.
     * This will take the input and create a new user, then set the stage to
     * the appropriate.
     */
    public class SignupEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
        	if (emailSatisfied && firstNameSatisfied && lastNameSatisfied && phoneSatisfied) {
        			
        		String firstName = firstNameTextField.getText();
        		String lastName = lastNameTextField.getText();
        		String email = emailTextField.getText();
        		String phone = phoneNumberTextField.getText();
        		
        		RadioButton selectedRadioButton = (RadioButton) accountTypeGroup.getSelectedToggle();
        		if (selectedRadioButton == volunteerRadioButton) {
    				Volunteer newVolunteer = new Volunteer(firstName, lastName, email, phone);
    				userCollection.addUser(newVolunteer);
    				MessageBoxUtils.newUserRegistered("a volunteer", firstName);
    				
        		} else if (selectedRadioButton == parkManagerRadioButton) {
        			ParkManager newParkManager = new ParkManager(firstName, lastName, email, phone);
        			userCollection.addUser(newParkManager);
        			MessageBoxUtils.newUserRegistered("a park manager", firstName);
        			
        		} else if (selectedRadioButton == parkManagerRadioButton) {
        			UrbanParksStaff newUrbanParksStaff = new UrbanParksStaff(firstName, lastName, email, phone);
        			userCollection.addUser(newUrbanParksStaff);
        			MessageBoxUtils.newUserRegistered("an Urban Parks staff", firstName);
        		}
        		// log them in
        	}
        }
    }
}