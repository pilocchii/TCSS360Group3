package urbanparks.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;


/**
 * Main entry point for Urban Parks application GUI
 */
public class MainApplication extends Application {

    private final String title = "Urban Parks";
    BorderPane root;
    Button backButton;


    // Will be moved to or replace view/main for production
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Creates the primary stage context on which the GUI is built.
     * Adds the static components, such as menu bar and back button.
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {

        // Stage init
        primaryStage = primaryStage;
        primaryStage.setTitle(title);
        root = new BorderPane();
        MenuBar menuBar = constructMenuBar();
        backButton = new Button("Go Back");
        root.setTop(new BorderPane(null, menuBar, null, backButton, null));
        root.setCenter(new LoginPane());
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }


    /**
     * Creates the menu bar.
     * @return MenuBar the menu bar
     */
    public MenuBar constructMenuBar() {

        final MenuBar menuBar = new MenuBar();

        // create file menu components
        final Menu fileMenu = new Menu("File");
        final MenuItem saveMenuItem = new MenuItem("Save");
        final MenuItem exitMenuItem = new MenuItem("Exit");

        // construct menu bar
        fileMenu.getItems().addAll(saveMenuItem, new SeparatorMenuItem(), exitMenuItem);
        menuBar.getMenus().addAll(fileMenu);
        return menuBar;
    }


    /**
     * Event handler for the "Sign in" button.
     * This will take the username that has been inputted and set the stage to
     * the appropriate menu page depending on the user's type
     */
    public class LoginEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            Object eventSource = event.getSource();
            System.out.println("placeholder text for login event");
        }
    }


    /**
     * Event handler for the "Sign up" button on the login splash page.
     * This will take the username that has been inputted and set the stage to
     * a sign-up form menu.
     */
    public class SignupMenuEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            Object eventSource = event.getSource();
            root.setCenter(new SignupPane());
        }
    }


    /**
     * Login and Signup prompt page.
     * This is a splash page that enables the user to either login, or
     * sign up if they do not have an account.
     */
    public class LoginPane extends GridPane {

        public LoginPane() {
            super();

            // Login pane styles
            setAlignment(Pos.CENTER);
            setPadding(new Insets(5, 5, 5, 5));
            setHgap(5);
            setVgap(5);

            // Components
            Button loginButton = new Button("Log in");
            Button signupButton = new Button("Sign up");
            TextField userNameTextField = new TextField();

            // This adds prompt text to the field and makes it not focused by default
            userNameTextField.setPromptText("User name");
            userNameTextField.setFocusTraversable(false);

            loginButton.setOnAction(new LoginEventHandler());
            signupButton.setOnAction(new SignupMenuEventHandler());

            // This allows the buttons to grow in size to match their container
            loginButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            signupButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

            add(userNameTextField, 0, 1);
            add(loginButton, 0, 2, 2, 1);
            add(signupButton, 0, 3, 2, 1);
        }

    }



}
