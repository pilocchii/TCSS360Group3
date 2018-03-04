package urbanparks.view;

import javafx.scene.paint.Color;

public class ViewConstants {
	
	// for alerts
	public static final String CONFIRMATION_DIALOG_TITLE = "Confirmation";
	public static final String SUCCESS_DIALOG_TITLE = "Success";
	public static final String ERROR_DIALOG_TITLE = "Error";
	
	// for job display
	public static final Color AVAILABLE_JOB_COLOR = Color.DARKGREEN;
	public static final Color UNAVAILABLE_JOB_COLOR = Color.RED;
	
	// for main application
	public static final double DEFAULT_WINDOW_WIDTH = 550;
	public static final double DEFAULT_WINDOW_HEIGHT = 500;
	
	// for signup menu
	// phone regex source: https://howtodoinjava.com/regex/java-regex-validate-and-format-north-american-phone-numbers/
	public static final String PHONE_INPUT_REGEX = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
	public static final String PHONE_DISPLAY_REGEX = "($1) $2-$3";
	public static final String EMAIL_REGEX = ".+\\@.+\\..+";

	// for job creation menu
	public static final String TIME_INPUT_REGEX = "([01][0-9]|2[0-3]):[0-5][0-9]";
//	public static final String HOUR_PARSE_REGEX = "$1\\S\\S\\S";
//	public static final String MINUTE_PARSE_REGEX = ":$2";
	public static final String VALID_DATE_STYLE = "-fx-background-color: green;";
	public static final String INVALID_DATE_STYLE = "-fx-background-color: red;";
	
	// for input validation
	public static final String STYLE_FIELD_VALID = "-fx-text-fill: green;";
	public static final String STYLE_FIELD_INVALID = "-fx-text-fill: red;";
	public static final String STYLE_FIELD_EDIT = "-fx-text-fill: black;";
}
