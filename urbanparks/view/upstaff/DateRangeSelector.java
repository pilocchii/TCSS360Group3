package urbanparks.view.upstaff;

import static urbanparks.view.ViewConstants.INVALID_DATE_STYLE;
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
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import urbanparks.model.UrbanParksStaff;
import urbanparks.view.AlertUtils;
import urbanparks.view.MainApplication;

/**
 * Grid pane for showing the date range selector prompt 
 * for Urban Parks staff to view all jobs in a range.
 * invariants: all fields non-null
 */
public class DateRangeSelector extends GridPane {

    /**
     * instance of this to retain its state.
     * This makes it easier to rapidly pick different date ranges.
     */
    private DateRangeSelector dateRangeSelector;
	
	private MainApplication root;
    private UrbanParksStaff urbanParksStaff;
    private Button backButton;

    // date pickers
    private DatePicker lowerBoundDatePicker;
    private DatePicker upperBoundDatePicker;
	
    // fields satisfied flags
	private boolean startDateSatisfied;
	private boolean endDateSatisfied;

	/**
	 * Constructor for DateRangeSelector
	 * 
	 * @param root Reference to the root application. Constructs the pane and shows itself.
	 * @param urbanParksStaff The Urban Parks staff member this menu is for.
	 */
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
    
    /**
     * Sets this gridPane's back button to its default state.
     */
    public void setBackButton() {
        backButton.setText("Back to Urban Parks staff menu");
        backButton.setOnAction(new BackButtonEventHandler());
    }
    
    /**
     * Shows the date range selector prompt 
     * for Urban Parks staff to view all jobs in a range.
     */
    private void show() {
    	setBackButton();
    	
    	// create the date pickers
        lowerBoundDatePicker = new DatePicker();
        upperBoundDatePicker = new DatePicker();
        lowerBoundDatePicker.setValue(LocalDate.now());
        upperBoundDatePicker.setValue(LocalDate.now());
        // validate them after they're created
        validateDates(lowerBoundDatePicker, upperBoundDatePicker);

        // add listener to lower bound picker
        lowerBoundDatePicker.valueProperty().addListener((arg0, oldValue, newValue) -> {
        	validateDates(lowerBoundDatePicker, upperBoundDatePicker);
        });
        
        // add listener and selected days limiter to upper bound
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
        				/**
        				 * disables the ability to select upper bound dates 
        				 * in the calendar that violate business rules.
        				 */
        				if (!areDatesValid(lowerBoundDatePicker.getValue(), tempEnd)) {
        					setDisable(true);
        				}
        			}
        		};
        	}
        };
        upperBoundDatePicker.setDayCellFactory(endDateCallback);
        
        // create a button that shows jobs in the selected range
        Button showJobsButton = new Button("Show all jobs between dates");
        showJobsButton.setOnAction(new ViewJobsEventHandler());
        // Allows it to grow in size to match their container
        showJobsButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        // add elements to this grid pane
        add(new Label("Lower bound of job dates"), 0, 0);
        add(lowerBoundDatePicker, 0, 1);
        add(new Label("Upper bound of job dates"), 0, 2);
        add(upperBoundDatePicker, 0, 3);
        add(new Separator(), 0, 4);
        add(showJobsButton, 0, 5);
 
        // set pane styles
        setAlignment(Pos.CENTER);
        setPadding(new Insets(5, 5, 5, 5));
        setHgap(5);
        setVgap(5);
        
        root.setTitle("Select which jobs to show - " + urbanParksStaff.getEmail());
    }

    /**
     * Validates the new user-entered start and end dates for job display
     * by setting the date picker styles and date values satisfied flag appropriately.
     * precondition: startDatePicker != null
     * precondition: endDatePicker != null
     */
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
    
    /**
     * Determines if the start and end dates picked by the user are valid
     * (The picked end date is equal to or before the end date)
     * precondition: startDatePicker != null
     * precondition: endDatePicker != null
     * 
     * @param startDatePicker The picked lower bound for the list of jobs to show.
     * @param endDatePicker The picked upper bound for the list of jobs to show.
     * @return True if the selected upper bound is equal to or after the lower bound.
     */
    private boolean areDatesValid(LocalDate startDatePicker, LocalDate endDatePicker) {
    	LocalDateTime startDate = startDatePicker.atStartOfDay();
    	LocalDateTime endDate = endDatePicker.atStartOfDay();
    	// start date can be before or equal to the end date
    	return startDate.isBefore(endDate) || startDate.isEqual(endDate);
    }

    /**
     * Event handler for the view jobs in selected range button.
     * If the selected upper bound is equal to or after the lower bound, 
     * the user will be shown a list of jobs in that date range.
     */
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
    
    /**
     * Event handler for the back button. If pressed, 
     * it will send the urban parks staff member to the main urban parks staff menu.
     */
    private class BackButtonEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	root.setCenter(new UPStaffMenu(root, urbanParksStaff));
        }
    }
}