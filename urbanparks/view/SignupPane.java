package urbanparks.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import urbanparks.model.UserCollection;
import urbanparks.model.Volunteer;
import urbanparks.view.parkmanager.ParkManagerMenu;
import urbanparks.view.upstaff.UPStaffMenu;
import urbanparks.view.volunteer.VolunteerMenu;
import urbanparks.model.ParkManager;
import urbanparks.model.UrbanParksStaff;
import static urbanparks.view.ViewConstants.*;

/***
 * Signup GUI component.
 * This is a splash page that enables the user to sign up for a new account.
 */
public class SignupPane extends GridPane {
	
	private MainApplication root;
	private Button backButton;
	private UserCollection userCollection;
	
	// text fields
	private TextField emailTextField;
	private TextField firstNameTextField;
	private TextField lastNameTextField;
	private TextField phoneNumberTextField;
	
	// radio buttons/group
	private ToggleGroup accountTypeGroup;
	private RadioButton volunteerRadioButton;
	private RadioButton parkManagerRadioButton;
	private RadioButton staffRadioButton;
	
	// form requirements satisfied flags
	private boolean emailSatisfied;
	private boolean firstNameSatisfied;
	private boolean lastNameSatisfied;
	private boolean phoneSatisfied;

	/**
	 * Constructor for SignupPane. Constructs the pane and shows itself.
	 * 
	 * @param root Reference to the root application.
	 */
    public SignupPane(MainApplication root) {
        super();

        this.root = root;
        this.backButton = root.getBackButton();
        this.userCollection = root.getUserCollection();
        
        emailSatisfied = false;
        firstNameSatisfied = false;
        lastNameSatisfied = false;
        phoneSatisfied = false;
        
        show();
    }

    /**
     * Shows this pane, a user signup form.
     */
    private void show() {
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
            			AlertUtils.showEmailAlreadyRegistered();
            		}
                } else {
                	emailTextField.setStyle(STYLE_FIELD_INVALID);
                	emailSatisfied = false;
                }
            }
        });
        
        // set up first name text field
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
        
        // set up first last text field
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
        
        // set up phone number text field
        phoneNumberTextField = new TextField();
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
                	phoneSatisfied = false;
                }
            }
        });
        
        // Create a button group for account type
        accountTypeGroup = new ToggleGroup();
        
        // create radio buttons for the user types
        volunteerRadioButton = new RadioButton("Volunteer");
        volunteerRadioButton.setToggleGroup(accountTypeGroup);
        volunteerRadioButton.setSelected(true);
        
        parkManagerRadioButton = new RadioButton("Park Manager");
        parkManagerRadioButton.setToggleGroup(accountTypeGroup);
        
        staffRadioButton = new RadioButton("Urban Parks Staff");
        staffRadioButton.setToggleGroup(accountTypeGroup);
        
        // create the sign up button
        Button signupButton = new Button("Sign up");
        signupButton.setOnAction(new SignupEventHandler());
        // Allows it to grow in size to match their container
        signupButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
      
        // add all buttons to this pane
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
        
        // Set pane styles
        setAlignment(Pos.CENTER);
        setPadding(new Insets(5, 5, 5, 5));
        setHgap(5);
        setVgap(5);
        
        root.setTitle("Sign Up");
    }


    /**
     * Event handler for the "Back" button in the context of the Signup Pane.
     * This sets the center element of the application and shows it.
     */
    public class BackButtonEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	root.setCenter(new MainMenuPane(root));
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
        		
        		// fetch the user info from the text fields
        		String firstName = firstNameTextField.getText();
        		String lastName = lastNameTextField.getText();
        		String email = emailTextField.getText();
        		String phone = phoneNumberTextField.getText();
        		
        		// creates a new User object based on the user type selected
        		RadioButton selectedRadioButton = (RadioButton) accountTypeGroup.getSelectedToggle();
        		if (selectedRadioButton == volunteerRadioButton) {
    				Volunteer newVolunteer = new Volunteer(firstName, lastName, email, phone);
    				userCollection.addUser(newVolunteer);
    				AlertUtils.showRegisterSuccess("a volunteer", firstName);
    				root.setCenter(new VolunteerMenu(root, newVolunteer));
    				
        		} else if (selectedRadioButton == parkManagerRadioButton) {
        			ParkManager newParkManager = new ParkManager(firstName, lastName, email, phone);
        			userCollection.addUser(newParkManager);
        			AlertUtils.showRegisterSuccess("a park manager", firstName);
        			root.setCenter(new ParkManagerMenu(root, newParkManager));
        			
        		} else if (selectedRadioButton == staffRadioButton) {
        			UrbanParksStaff newUrbanParksStaff = new UrbanParksStaff(firstName, lastName, email, phone);
        			userCollection.addUser(newUrbanParksStaff);
        			AlertUtils.showRegisterSuccess("an Urban Parks staff", firstName);
        			root.setCenter(new UPStaffMenu(root, newUrbanParksStaff));
        		}
        	} else {
        		AlertUtils.showInvalidOptions();
        	}
        }
    }
}