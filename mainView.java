package view


// As a Volunteer I want to sign up for a job

// As a Park Manager I want to submit a new job

// As a User I want to be identified by the system

public class mainView() {

	Scanner myScanner = new Scanner(System.in);
	String myInputPrompt = "Please enter the number corresponding with your menu choice:"
	User myUser;

	// entrypoint
	System.out.println("Welcome to Urban Parks!);
	System.out.println(myInputPrompt);
	System.out.println("1. Sign in");
	System.out.println("2. Create a new user");
	System.out.println("3. Exit this application");
	char choice = scanner.nextChar();

	switch(choice) {

		case '1':
			System.out.println("Please enter your email address:");
			String username = scanner.nextLine();
			myUser = UserCollection.getUser(username);
			showMainMenu();
			break;
		
		case '2':
			showSignupMenu();
			break;

		case '3':
			System.exit(0);
	}


	private static showSubmitNewJobMenu() {

		System.out.println("Please enter a start date and time for this job:");
		Calendar start = new Calendar(scanner.nextLine());


	} // end showSubmitNewJobMenu


	private static showSignupForJobMenu() {



	} // end showSignupForJobMenu


	private static void showMainMenu() {

		// show Park Manager options
		if (myUser instanceof ParkManager) {
			System.out.println("Hi, " + myUser.getFirstName() + "! What would you like to do?");
			System.out.println(myInputPrompt);
			System.out.println("1. Submit a new job");
			String choice = scanner.nextChar();

			switch(choice) {
				case '1':
					showSubmitNewJobMenu();
			}

		} // end Park Manager options

		// show Volunteer options
		else if (myUser instanceof Volunteer) {
			System.out.println("Hi, " + myUser.getFirstName() + "! What would you like to do?");
			System.out.println(myInputPrompt);
			System.out.println("1. Sign up for a job");
			String choice = scanner.nextChar();

			switch(choice) {
				case '1':
					showSignupForJobMenu();
			}

		} // end Volunteer options

	} // end showMainMenu


	private static void showSignupMenu() {

		System.out.println("Please enter the number corresponding with your user type:");
		System.out.println("1. Volunteer");
		System.out.println("2. Park Manager");
		String choice = scanner.nextChar();

		System.out.println("Please enter your email address:");
		String email = scanner.nextLine();

		System.out.println("Please enter your first and last name:");
		String name = scanner.nextLine();
		StringTokenizer st = new StringTokenizer(name);
		String firstName = st.nextToken();
		String lastName = st.nextToken();

		System.out.println("Please enter your phone number:");
		String phone = scanner.nextLine();

		switch(choice) {

			case '1':
				System.out.println("Welcome, volunteer " + firstName + "!");
				Volunteer newVolunteer = Volunteer(email, firstName, lastName, phone);
				myUser = newVolunteer;
				showMainMenu();
				break;

			case '2': 
				System.out.println("Welcome, park manager " + firstName + "!");
				ParkManager newParkManager = ParkManager(email, firstName, lastName, phone);
				myUser = newParkManager;
				showMainMenu();
				break;

		} // end switch

	} // end showSignupMenu

} // end MainView

