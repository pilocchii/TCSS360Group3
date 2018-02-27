package urbanparks.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class InputUtils {
	
	/**
	 * Gets the first non-null, nonempty string the user types
	 * 
	 * @return the first non-null, nonempty string the user types
	 */
	public static String getStringInput() {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		while (line == null || line.isEmpty()) {
			try {
				line = bufferedReader.readLine();
			} catch (IOException e) {
				System.out.println("Error receiving input!");
			}
		}
		return line;
	}
	
	/**
	 * Gets the first int the user types
	 * 
	 * @return the first int the user types
	 */
	public static int getIntInput() {
		Integer i = null;
		while (i == null) {
			try {
				i = Integer.parseInt(getStringInput());
			} catch (java.lang.NumberFormatException e) {
				System.out.println("You didn't enter a number! Try again.");
			}
		}
		return i;
	}
	
	/**
	 * Gets a number from the user in a specified range
	 * 
	 * @param min The lowest the entered number can be
	 * @param max The highest the entered number can be
	 * @return An entered number in the specified range
	 */
	public static int getIntInput(int min, int max) {
		int i = getIntInput();
		while (i < min || i > max) {
			if (i < min) {
				System.err.println("Your number " + i 
						+ " is smaller than the minimum allowed value " + min + "! Try again.");
			} else {
				System.err.println("Your number " + i 
						+ " is greater than the maximum allowed value " + max + "! Try again.");
			}
			i = getIntInput();
		}
		return i;
	}

	/**
	 * Gets a date from the user
	 * 
	 * @return Valid Calendar object the user typed
	 */
	public static Calendar getCalendarInput() {
		String calendarString = getStringInput();
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);
		try {
			calendar.setTime(sdf.parse(calendarString));
		} catch (ParseException e) {
			System.out.println("Wrong date format! Please try again.");
			getCalendarInput();
		}
		return calendar;
	}
	
	/**
	 * Shows the user a list of options and gets their choice
	 * 
	 * @param A list of choices the user can select
	 * @return The index of the choice the user selected
	 */
	public static int getChoice(ArrayList<String> choices) {
		for (int i = 0; i < choices.size(); i++) {
			System.out.println(i + ": " + choices.get(i));
		}
		int i = getIntInput(0, choices.size()-1);
		return i;
	}

}
