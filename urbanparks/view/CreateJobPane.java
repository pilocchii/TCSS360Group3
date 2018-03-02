package urbanparks.view;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.application.Application;
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
import urbanparks.model.JobCollection;
import urbanparks.model.UserCollection;
import urbanparks.view.SignupPane.BackButtonEventHandler;
import static urbanparks.model.Constants.*;

public class CreateJobPane extends GridPane {

	private static final String INVALID_DATE_STYLE = "-fx-background-color: red;";
	private static final String VALID_DATE_STYLE = "-fx-background-color: green;";
	
    private BorderPane root;
    private Button backButton;
    private MainMenuPane back;
    private UserCollection userCollection;
    private JobCollection jobCollection;

    public CreateJobPane(BorderPane root, Button backButton, MainMenuPane back, UserCollection userCollection, JobCollection jobCollection) {
        super();

        this.root = root;
        this.backButton = backButton;
        this.back = back;
        this.userCollection = userCollection;
        this.jobCollection = jobCollection;
        
        show();
    }
    
    public void show() {
        backButton.setText("Temp...");
        
        DatePicker startDatePicker = new DatePicker();
        DatePicker endDatePicker = new DatePicker();

        // start date picker
        startDatePicker.setValue(LocalDate.now());
        startDatePicker.valueProperty().addListener((arg0, oldValue, newValue) -> {
        	validateDates(startDatePicker, endDatePicker);
        });
//        Callback<DatePicker, DateCell> startDateCallBack = new Callback<DatePicker, DateCell>() {
//        	@Override
//        	public DateCell call(DatePicker datePicker) {
//        		return new DateCell() {
//        			@Override
//        			public void updateItem(LocalDate tempStart, boolean empty) {
//        				super.updateItem(tempStart, empty);
//        				if (doesViolateBizRule(tempStart, endDatePicker.getValue())) {
//        					setDisable(true);
//        				}
//        			}
//        		};
//        	}
//        };
//        startDatePicker.setDayCellFactory(startDateCallBack);
        
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
        
        
        add(new Label("Job Start Date"), 0, 0);
        add(startDatePicker, 0, 1);
        add(new Separator(), 0, 2);
        add(new Label("Job End Date"), 0, 3);
        add(endDatePicker, 0, 4);
        
        // styles
        setAlignment(Pos.CENTER);
        setPadding(new Insets(5, 5, 5, 5));
        setHgap(5);
        setVgap(5);
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