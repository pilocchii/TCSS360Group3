package urbanparks.view;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import urbanparks.model.DateUtils;
import urbanparks.model.Job;
import urbanparks.model.JobCollection;
import urbanparks.model.ParkManager;
import urbanparks.model.UrbanParksStaff;
import urbanparks.model.UserCollection;
import urbanparks.model.Volunteer;
import urbanparks.view.SignupPane.BackButtonEventHandler;
import urbanparks.view.SignupPane.SignupEventHandler;

import static urbanparks.model.Constants.*;

public class CreateJobPane extends GridPane {

	private static final String INVALID_DATE_STYLE = "-fx-background-color: red;";
	private static final String VALID_DATE_STYLE = "-fx-background-color: green;";
	private static final String STYLE_FIELD_EDIT = "-fx-text-fill: black;";
	private static final String STYLE_FIELD_VALID = "-fx-text-fill: green;";
	private static final String STYLE_FIELD_INVALID = "-fx-text-fill: red;";
	
    private MainApplication root;
    private Button backButton;
    private MainMenuPane back;
    private ParkManager parkManager;
    private UserCollection userCollection;
    private JobCollection jobCollection;
    
    private boolean descriptionSatisfied;
    private boolean parkNameSatisfied;
    private boolean jobLocationSatisfied;
    
    //fields
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private TextField descriptionField;
    private TextField parkNameField;
    private TextField jobLocationField;

    public CreateJobPane(MainApplication root, MainMenuPane back, ParkManager parkManager) {
        super();

        this.root = root;
        this.backButton = root.getBackButton();
        this.back = back;
        this.parkManager = parkManager;
        this.userCollection = root.getUserCollection();
        this.jobCollection = root.getJobCollection();
        
        descriptionSatisfied = false;
        parkNameSatisfied = false;
        jobLocationSatisfied = false;
        
        show();
    }
    
    public void show() {
        backButton.setText("Temp...");
        
        startDatePicker = new DatePicker();
        endDatePicker = new DatePicker();

        // start date picker
        startDatePicker.setValue(LocalDate.now());
        startDatePicker.valueProperty().addListener((arg0, oldValue, newValue) -> {
        	validateDates(startDatePicker, endDatePicker);
        });
        Callback<DatePicker, DateCell> startDateCallBack = new Callback<DatePicker, DateCell>() {
        	@Override
        	public DateCell call(DatePicker datePicker) {
        		return new DateCell() {
        			@Override
        			public void updateItem(LocalDate tempStart, boolean empty) {
        				super.updateItem(tempStart, empty);
//        				if (doesViolateBizRule(tempStart, endDatePicker.getValue())) {
//        					setDisable(true);
//        				}
        				if (tempStart.isBefore(LocalDate.now())) {
        					setDisable(true);
        				}
        			}
        		};
        	}
        };
        startDatePicker.setDayCellFactory(startDateCallBack);
        
        // end date picker
        endDatePicker.setValue(LocalDate.now());
        endDatePicker.valueProperty().addListener((arg0, oldValue, newValue) -> {
        	validateDates(startDatePicker, endDatePicker);
        });
        Callback<DatePicker, DateCell> endDateCallback = new Callback<DatePicker, DateCell>() {
        	@Override
        	public DateCell call(DatePicker datePicker) {
        		return new DateCell() {
        			@Override
        			public void updateItem(LocalDate tempEnd, boolean empty) {
        				super.updateItem(tempEnd, empty);
        				if (doesViolateBizRule(startDatePicker.getValue(), tempEnd)) {
        					setDisable(true);
        				}
        			}
        		};
        	}
        };
        endDatePicker.setDayCellFactory(endDateCallback);
        
        
        descriptionField = new TextField();
        descriptionField.setPromptText("Job description");
        descriptionField.setFocusTraversable(false);
        descriptionField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
        	// focus gained
        	if (newValue) {
        		descriptionField.setStyle(STYLE_FIELD_EDIT);
        		descriptionSatisfied = false;
        	// focus lost
        	} else {
        		// any non-empty value is ok
        		descriptionField.setStyle(STYLE_FIELD_VALID);
        		descriptionSatisfied = true;
            }
        });
        
        
        parkNameField = new TextField();
        parkNameField.setPromptText("Park name");
        parkNameField.setFocusTraversable(false);
        parkNameField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
        	// focus gained
        	if (newValue) {
        		parkNameField.setStyle(STYLE_FIELD_EDIT);
        		parkNameSatisfied = false;
        	// focus lost
        	} else {
        		// any non-empty value is ok
        		parkNameField.setStyle(STYLE_FIELD_VALID);
        		parkNameSatisfied = true;
            }
        });
        
        jobLocationField = new TextField();
        jobLocationField.setPromptText("Job's location");
        jobLocationField.setFocusTraversable(false);
        jobLocationField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
        	// focus gained
        	if (newValue) {
        		jobLocationField.setStyle(STYLE_FIELD_EDIT);
        		jobLocationSatisfied = false;
        	// focus lost
        	} else {
        		// any non-empty value is ok
        		jobLocationField.setStyle(STYLE_FIELD_VALID);
        		jobLocationSatisfied = true;
            }
        });
        
        
        Button createJobButton = new Button("Create Job");
        createJobButton.setOnAction(new CreateJobButtonHandler());
        // Allows it to grow in size to match their container
        createJobButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        
        add(new Label("Job Start Date"), 0, 0);
        add(startDatePicker, 0, 1);
        add(new Separator(), 0, 2);
        add(new Label("Job End Date"), 0, 3);
        add(endDatePicker, 0, 4);
        add(descriptionField, 0, 5);
        add(parkNameField, 0, 7);
        add(jobLocationField, 0, 9);
        add(new TextField("TODO job times"), 0, 10);
        add(createJobButton, 0, 11);
 
        // styles
        setAlignment(Pos.CENTER);
        setPadding(new Insets(5, 5, 5, 5));
        setHgap(5);
        setVgap(5);
    }
    
    
    
    public class CreateJobButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	if (descriptionSatisfied && parkNameSatisfied && jobLocationSatisfied) {
        			
        		//TODO: add times for dates
        		LocalDateTime startTime = startDatePicker.getValue().atStartOfDay();
        		LocalDateTime endTime = endDatePicker.getValue().atStartOfDay();
        	    String description = descriptionField.getText();
        	    String parkName = parkNameField.getText();
        	    String location = jobLocationField.getText();
        		
        		Job newJob = new Job(description, startTime, endTime, parkName, location, 111111, 11111, 11111, 1111111);
				parkManager.createNewJob(newJob, jobCollection);
				MessageBoxUtils.showJobCreated(description);
				root.setCenter(new ParkManagerPane(root, back, parkManager));
        	}
        }
    }
    
    
    private void validateDates(DatePicker startDatePicker, DatePicker endDatePicker) {
    	if (doesViolateBizRule(startDatePicker.getValue(), endDatePicker.getValue())) {
    		startDatePicker.setStyle(INVALID_DATE_STYLE);
    		endDatePicker.setStyle(INVALID_DATE_STYLE);
    	} else {
    		startDatePicker.setStyle(VALID_DATE_STYLE);
    		endDatePicker.setStyle(VALID_DATE_STYLE);
    	}
    }
    
    
    private boolean doesViolateBizRule(LocalDate startDatePicker, LocalDate endDatePicker) {
    	LocalDateTime startDate = startDatePicker.atStartOfDay();
    	LocalDateTime endDate = endDatePicker.atStartOfDay();
    	LocalDateTime nowDate = LocalDate.now().atStartOfDay();
    	
    	boolean valid = true;
    	if (startDate.isAfter(endDate)) {
    		valid = false;
    	}
    	if (startDate.isBefore(nowDate)) {
    		valid = false;
    	}
    	if (DateUtils.daysBetweenNowAndDate(endDate) > MAX_DAYS_BEFORE_JOB_ENDS) {
    		valid = false;
    	}
    	if (DateUtils.daysBetween2Dates(startDate, endDate) > MAX_JOB_LENGTH) {
    		valid = false;
    	}
    	return !valid;
    }
}