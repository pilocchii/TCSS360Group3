package urbanparks.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import urbanparks.model.ModelConstants;
import urbanparks.model.JobCollection;
import urbanparks.model.UserCollection;
import static urbanparks.view.ViewConstants.*;
import javafx.scene.layout.BorderPane;

/**
 * Main entry point (Application) for Urban Parks application GUI
 * invariants: all fields non-null
 */
public class MainApplication extends Application {

    private MainApplication root = this;
    private Button backButton;
    private BorderPane centerPane;
    private JobCollection jobCollection;
	private UserCollection userCollection;
	private Stage primaryStage;

	/**
	 * Default constructor for MainApplication. Should not be called.
	 */
	public MainApplication() {
	}
	
	/**
	 * Non-default constructor for MainApplication.
	 * Launches the application from here.
	 * 
	 * @param args Command line arguments for the Javafx Application.
	 */
    public MainApplication(String[] args) {
        launch(args);
    }

    /**
     * Creates the primary stage context on which the GUI is built.
     * Adds the static components, such as menu bar and back button.
     * Also loads the persistent data from the files.
     * 
     * @param primaryStage The primary stage of the application.
     */
    @Override
    public void start(Stage primaryStage) {
    	this.primaryStage = primaryStage;
    	
    	loadPersistentData();

        // Stage init
        centerPane = new BorderPane();
        MenuBar menuBar = constructMenuBar();
        backButton = new Button();

        centerPane.setTop(new BorderPane(null, menuBar, null, backButton, null));
        centerPane.setCenter(new MainMenuPane(root));
        primaryStage.setScene(new Scene(centerPane, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT));

        primaryStage.show();
    }
    
    /**
     * Loads the data for users, jobs, and settings,
     * alerting the user if something went wrong.
     * postcondition: jobCollection and userCollection are non-empty.
     */
    private void loadPersistentData() {
    	// try to load user and job data
		jobCollection = new JobCollection();
		userCollection = new UserCollection();
		try {
			jobCollection.loadData();
			userCollection.loadData();
		} catch (Exception e) {
			AlertUtils.showJobUserDataLoadError(e);
			AlertUtils.showEmptyJobUserDataUsed();
		}
		
		// try to load settings data
		try {
			ModelConstants.loadSettingsData();
		} catch (FileNotFoundException e) {
			AlertUtils.showSettingsLoadError(e);
			AlertUtils.showDefaultSettingsUsed();
		}
    }

    /**
     * Creates the menu barf or the gui.
     * @return MenuBar the menu bar for the gui.
     */
    private MenuBar constructMenuBar() {
        final MenuBar menuBar = new MenuBar();

        // create file menu components
        final Menu fileMenu = new Menu("File");
        final MenuItem saveMenuItem = new MenuItem("Save");
        saveMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
				try {
					userCollection.saveData();
					jobCollection.saveData();
					ModelConstants.saveSettingsData();
					AlertUtils.showDataSaveSuccess();
				} catch (IOException e) {
					AlertUtils.showDataSaveError(e);
				}
            }
        }); 
        
        final MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	System.exit(0);
            }
        }); 

        // construct menu bar
        fileMenu.getItems().addAll(saveMenuItem, new SeparatorMenuItem(), exitMenuItem);
        menuBar.getMenus().addAll(fileMenu);
        return menuBar;
    }

    /**
     * Returns a reference to this application's back button.
     * The back button's behavior should be changed at each state change.
     * @return Button a reference to the application's back button.
     */
    public Button getBackButton() {
        return backButton;
    }

    /**
     * Returns a reference to this application's job collection.
     * @return JobCollection a reference to the application's job collection.
     */
    public JobCollection getJobCollection() {
        return jobCollection;
    }

    /**
     * Returns a reference to this application's user collection.
     * @return UserCollection a reference to the application's user collection.
     */
    public UserCollection getUserCollection() {
        return userCollection;
    }

    /**
     * Returns a reference to this application's central border pane.
     * This allows the application's context to be changed.
     * @return BorderPane a reference to the application's central border pane.
     */
    public BorderPane getCenterPane() {
        return centerPane;
    }

    /**
     * Sets the center pane of this application to the provided Pane.
     * precondition: pane != null
     * @param pane the pane with to replace the current pane with.
     */
    public void setCenter(Pane pane) {
        centerPane.setCenter(pane);
    }
    
    /**
     * Updates the title of the gui to the provided title.
     * precondition: newTitle != null
     * @param newTitle The new title of the gui.
     */
    public void setTitle(String newTitle) {
    	primaryStage.setTitle(newTitle);
    }
    
}
