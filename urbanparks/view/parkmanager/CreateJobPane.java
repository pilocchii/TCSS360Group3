package urbanparks.view.parkmanager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import urbanparks.view.AlertUtils;
import urbanparks.view.MainApplication;
import urbanparks.view.MainMenuPane;
import urbanparks.view.SignupPane.BackButtonEventHandler;
import urbanparks.view.SignupPane.SignupEventHandler;
import static urbanparks.view.ViewConstants.*;

import static urbanparks.model.ModelConstants.*;

public class CreateJobPane extends GridPane {
	
    private MainApplication root;
    private Button backButton;
    private ParkManager parkManager;
    private JobCollection jobCollection;
    
    // fields satisfied flags
	private boolean datesAndTimesSatisfied;
    private boolean descriptionSatisfied;
    private boolean parkNameSatisfied;
    private boolean jobLocationSatisfied;
    
    //fields
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private TextField startTimeTextField;
    private TextField endTimeTextField;
    private TextField descriptionField;
    private TextField parkNameField;
    private TextField jobLocationField;

    public CreateJobPane(MainApplication root, ParkManager parkManager) {
        super();

        this.root = root;
        this.backButton = root.getBackButton();
        this.parkManager = parkManager;
        this.jobCollection = root.getJobCollection();
        
        datesAndTimesSatisfied = false;
        descriptionSatisfied = false;
        parkNameSatisfied = false;
        jobLocationSatisfied = false;
        
        show();
    }
    
    public void show() {
        backButton.setText("Back to park manager menu");
        backButton.setOnAction(new BackButtonEventHandler());
        
        startDatePicker = new DatePicker();
        endDatePicker = new DatePicker();
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());

        // start date picker
        startDatePicker.valueProperty().addListener((arg0, oldValue, newValue) -> {
        	validateDatesAndTimes();
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
        endDatePicker.valueProperty().addListener((arg0, oldValue, newValue) -> {
        	validateDatesAndTimes();
        });
        Callback<DatePicker, DateCell> endDateCallback = new Callback<DatePicker, DateCell>() {
        	@Override
        	public DateCell call(DatePicker datePicker) {
        		return new DateCell() {
        			@Override
        			public void updateItem(LocalDate tempEnd, boolean empty) {
        				super.updateItem(tempEnd, empty);
        				if (!areDatesValid(startDatePicker.getValue(), tempEnd)) {
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
        
        
        startTimeTextField = new TextField();
        startTimeTextField.setPromptText("Start time");
        startTimeTextField.setText("00:00");
        startTimeTextField.setFocusTraversable(false);
        startTimeTextField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
        	// focus gained
        	if (newValue) {
        		startTimeTextField.setStyle(STYLE_FIELD_EDIT);
        	// focus lost
        	} else {
        		validateDatesAndTimes();
            }
        });
        
        endTimeTextField = new TextField();
        endTimeTextField.setPromptText("End time");
        endTimeTextField.setText("00:01");
        endTimeTextField.setFocusTraversable(false);
        endTimeTextField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
        	// focus gained
        	if (newValue) {
        		endTimeTextField.setStyle(STYLE_FIELD_EDIT);
        	// focus lost
        	} else {
        		validateDatesAndTimes();
            }
        });
        validateDatesAndTimes();

        Button createJobButton = new Button("Create Job");
        createJobButton.setOnAction(new CreateJobButtonHandler());
        // Allows it to grow in size to match their container
        createJobButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        // column 1
        add(new Label("Job Start Date"), 0, 0);
        add(startDatePicker, 0, 1);
        add(new Label("Job End Date"), 0, 2);
        add(endDatePicker, 0, 3);
        add(new Separator(), 0, 4);
        add(descriptionField, 0, 5);
        add(parkNameField, 0, 6);
        add(jobLocationField, 0, 7);
        add(new Separator(), 0, 8);
        add(createJobButton, 0, 9);
        
        // column 2
        add(new Label("Job start in military time (MM:SS)"), 1, 0);
        add(startTimeTextField, 1, 1);
        add(new Label("Job end in military time (MM::SS)"), 1, 2);
        add(endTimeTextField, 1, 3);
 
        // styles
        setAlignment(Pos.CENTER);
        setPadding(new Insets(5, 5, 5, 5));
        setHgap(5);
        setVgap(5);
        
        root.setTitle("Create A New Job - " + parkManager.getEmail());
    }
    
    private boolean isTimeStringParsable(String input) {
    	try {
    		LocalTime.parse(input);
    		return true;
    	} catch (DateTimeParseException dtpe) {
    	}
    	return false;
    }
    
    public class CreateJobButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	if (datesAndTimesSatisfied && descriptionSatisfied 
        			&& parkNameSatisfied && jobLocationSatisfied) {
        		
        		// parse and add together dates + times
        		LocalTime startTime = LocalTime.parse(startTimeTextField.getText());
        		LocalTime endTime = LocalTime.parse(endTimeTextField.getText());
        		LocalDateTime startDateTime = startDatePicker.getValue().atTime(startTime);
        		LocalDateTime endDateTime = endDatePicker.getValue().atTime(endTime);
        		
        	    String description = descriptionField.getText();
        	    String parkName = parkNameField.getText();
        	    String location = jobLocationField.getText();
        		
				if (AlertUtils.askJobSubmit(description)) {
	        		Job newJob = new Job(description, startDateTime, endDateTime, parkName, location);
					parkManager.createNewJob(newJob.getJobId());
					jobCollection.addJob(newJob);
					AlertUtils.showJobSubmitSuccess(startDateTime, endDateTime);
					root.setCenter(new ParkManagerMenu(root, parkManager));
				}
        	} else {
        		AlertUtils.showInvalidOptions();
        	}
        }
    }
    
    private void validateDatesAndTimes() {
    	boolean timesValid = false;
    	
    	if (areDatesValid(startDatePicker.getValue(), endDatePicker.getValue())) {
    		boolean startTimeParsable = isTimeStringParsable(startTimeTextField.getText());
    		boolean endTimeParsable = isTimeStringParsable(endTimeTextField.getText());
    		if (!startTimeParsable) {
    			startTimeTextField.setStyle(STYLE_FIELD_INVALID);
        		datesAndTimesSatisfied = false;
        		return;
    		}
    		if (!endTimeParsable) {
        		endTimeTextField.setStyle(STYLE_FIELD_INVALID);
        		datesAndTimesSatisfied = false;
        		return;
    		}
    		
    		if (startTimeParsable && endTimeParsable) {
        		LocalTime startTime = LocalTime.parse(startTimeTextField.getText());
        		LocalTime endTime = LocalTime.parse(endTimeTextField.getText());
        		LocalDateTime startDateTime = startDatePicker.getValue().atTime(startTime);
        		LocalDateTime endDateTime = endDatePicker.getValue().atTime(endTime);

        		if (startDateTime.isBefore(endDateTime)) {
                	timesValid = true;
        		}
        	}	
    	}
        	
        if (timesValid) {
    		startDatePicker.setStyle(VALID_DATE_STYLE);
    		endDatePicker.setStyle(VALID_DATE_STYLE);
    		datesAndTimesSatisfied = true;
        	startTimeTextField.setStyle(STYLE_FIELD_VALID);
        	endTimeTextField.setStyle(STYLE_FIELD_VALID);
    	} else {
    		startDatePicker.setStyle(INVALID_DATE_STYLE);
    		endDatePicker.setStyle(INVALID_DATE_STYLE);
    		datesAndTimesSatisfied = false;
        	startTimeTextField.setStyle(STYLE_FIELD_INVALID);
        	endTimeTextField.setStyle(STYLE_FIELD_INVALID);
        	
    	}
    }
    
    private boolean areDatesValid(LocalDate startDatePicker, LocalDate endDatePicker) {
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
    	return valid;
    }
    
    private class BackButtonEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	root.setCenter(new ParkManagerMenu(root, parkManager));
        }
    }
}