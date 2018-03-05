package urbanparks.view.upstaff;

import static urbanparks.model.ModelConstants.MAX_DAYS_BEFORE_JOB_ENDS;
import static urbanparks.model.ModelConstants.MAX_JOB_LENGTH;
import static urbanparks.view.ViewConstants.INVALID_DATE_STYLE;
import static urbanparks.view.ViewConstants.STYLE_FIELD_EDIT;
import static urbanparks.view.ViewConstants.STYLE_FIELD_VALID;
import static urbanparks.view.ViewConstants.VALID_DATE_STYLE;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import urbanparks.model.UrbanParksStaff;
import urbanparks.view.AlertUtils;
import urbanparks.view.MainApplication;

public class DateRangeSelector extends GridPane {

    // instance of this to retain its state
    private DateRangeSelector dateRangeSelector;
	
	private MainApplication root;
    private UrbanParksStaff urbanParksStaff;
    private Button backButton;

    //fields
    private DatePicker lowerBoundDatePicker;
    private DatePicker upperBoundDatePicker;
	
    // fields satisfied flags
	private boolean startDateSatisfied;
	private boolean endDateSatisfied;

    public DateRangeSelector(MainApplication root, UrbanParksStaff urbanParksStaff) {
        super();
        
        dateRangeSelector = this;
        this.root = root;
        this.urbanParksStaff = urbanParksStaff;
        this.backButton = root.getBackButton();

        startDateSatisfied = false;
        endDateSatisfied = false;
        
        show();
    }
    
    public void setBackButton() {
        backButton.setText("Back to Urban Parks staff menu");
        backButton.setOnAction(new BackButtonEventHandler());
    }
    
    private void show() {
    	setBackButton();
        lowerBoundDatePicker = new DatePicker();
        upperBoundDatePicker = new DatePicker();
        lowerBoundDatePicker.setValue(LocalDate.now());
        upperBoundDatePicker.setValue(LocalDate.now());
        validateDates(lowerBoundDatePicker, upperBoundDatePicker);

        lowerBoundDatePicker.valueProperty().addListener((arg0, oldValue, newValue) -> {
        	validateDates(lowerBoundDatePicker, upperBoundDatePicker);
        });
        
        upperBoundDatePicker.valueProperty().addListener((arg0, oldValue, newValue) -> {
        	validateDates(lowerBoundDatePicker, upperBoundDatePicker);
        });
        Callback<DatePicker, DateCell> endDateCallback = new Callback<DatePicker, DateCell>() {
        	@Override
        	public DateCell call(DatePicker datePicker) {
        		return new DateCell() {
        			@Override
        			public void updateItem(LocalDate tempEnd, boolean empty) {
        				super.updateItem(tempEnd, empty);
        				if (!areDatesValid(lowerBoundDatePicker.getValue(), tempEnd)) {
        					setDisable(true);
        				}
        			}
        		};
        	}
        };
        upperBoundDatePicker.setDayCellFactory(endDateCallback);
        
        
        Button showJobsButton = new Button("Show all jobs between dates");
        showJobsButton.setOnAction(new ViewJobsEventHandler());
        // Allows it to grow in size to match their container
        showJobsButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        add(new Label("Lower bound of job dates"), 0, 0);
        add(lowerBoundDatePicker, 0, 1);
        add(new Label("Upper bound of job dates"), 0, 2);
        add(upperBoundDatePicker, 0, 3);
        add(new Separator(), 0, 4);
        add(showJobsButton, 0, 5);
 
        // styles
        setAlignment(Pos.CENTER);
        setPadding(new Insets(5, 5, 5, 5));
        setHgap(5);
        setVgap(5);
        
        root.setTitle("Select which jobs to show - " + urbanParksStaff.getEmail());
    }

    private void validateDates(DatePicker startDatePicker, DatePicker endDatePicker) {
    	if (areDatesValid(startDatePicker.getValue(), endDatePicker.getValue())) {
    		startDatePicker.setStyle(VALID_DATE_STYLE);
    		endDatePicker.setStyle(VALID_DATE_STYLE);
    		startDateSatisfied = true;
    		endDateSatisfied = true;
    	} else {
    		startDatePicker.setStyle(INVALID_DATE_STYLE);
    		endDatePicker.setStyle(INVALID_DATE_STYLE);
    		startDateSatisfied = false;
    		endDateSatisfied = false;
    	}
    }
    
    private boolean areDatesValid(LocalDate startDatePicker, LocalDate endDatePicker) {
    	LocalDateTime startDate = startDatePicker.atStartOfDay();
    	LocalDateTime endDate = endDatePicker.atStartOfDay();
    	// start date can be before or equal to the end date
    	return startDate.isBefore(endDate) || startDate.isEqual(endDate);
    }

    public class ViewJobsEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	if (startDateSatisfied && endDateSatisfied) {
                JobsDisplay jobsDisplay = new JobsDisplay(root);
                LocalDateTime startOfLowerBoundDate = lowerBoundDatePicker.getValue().atStartOfDay();
                LocalDateTime endOfUpperBoundDate = upperBoundDatePicker.getValue().atTime(23, 59);
                
                jobsDisplay.showStaffJobsBetweenDates(urbanParksStaff, dateRangeSelector, startOfLowerBoundDate, endOfUpperBoundDate);
        	} else {
        		AlertUtils.showInvalidOptions();
        	}
        }
    }
    
    private class BackButtonEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	root.setCenter(new UPStaffMenu(root, urbanParksStaff));
        }
    }
}