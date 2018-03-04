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
 * Main entry point for Urban Parks application GUI
 */
public class MainApplication extends Application {

    private MainApplication root = this;
    private Button backButton;
    private BorderPane centerPane;
    private JobCollection jobCollection;
	private UserCollection userCollection;
	private Stage primaryStage;

	/**
	 * 
	 */
	public MainApplication() {
		// shouldn't be called
	}
	
	/**
	 * 
	 * @param args
	 */
    public MainApplication(String[] args) {
    	//root = this;
        launch(args);
    }

    /**
     * Creates the primary stage context on which the GUI is built.
     * Adds the static components, such as menu bar and back button.
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
    	this.primaryStage = primaryStage;
    	
		jobCollection = new JobCollection();
		userCollection = new UserCollection();
		try {
			jobCollection.loadData();
			userCollection.loadData();
		} catch (Exception e) {
			AlertUtils.showJobUserDataLoadError(e);
			AlertUtils.showEmptyJobUserDataUsed();
		}
		
		try {
			ModelConstants.loadSettingsData();
		} catch (FileNotFoundException e) {
			AlertUtils.showSettingsLoadError(e);
			AlertUtils.showDefaultSettingsUsed();
		}

        // Stage init
        centerPane = new BorderPane();
        MenuBar menuBar = constructMenuBar();
        backButton = new Button();

        centerPane.setTop(new BorderPane(null, menuBar, null, backButton, null));
        centerPane.setCenter(new MainMenuPane(root));
        primaryStage.setScene(new Scene(centerPane, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT));

        primaryStage.show();
    }
    
    public void setTitle(String newTitle) {
    	primaryStage.setTitle(newTitle);
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
     * @return Button a reference to the application's back button
     */
    public Button getBackButton() {
        return backButton;
    }

    /**
     * Returns a reference to this application's job collection.
     * @return JobCollection a reference to the application's job collection
     */
    public JobCollection getJobCollection() {
        return jobCollection;
    }

    /**
     * Returns a reference to this application's user collection.
     * @return UserCollection a reference to the application's user collection
     */
    public UserCollection getUserCollection() {
        return userCollection;
    }

    /**
     * Returns a reference to this application's central border pane.
     * This allows the application's context to be changed.
     * @return BorderPane a reference to the application's central border pane
     */
    public BorderPane getCenterPane() {
        return centerPane;
    }

    /**
     * Sets the center pane of this application to the provided Pane.
     * @param pane the pane with to replace the current pane with
     */
    public void setCenter(Pane pane) {
        centerPane.setCenter(pane);
    }
    
}
