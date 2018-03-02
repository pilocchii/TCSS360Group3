package urbanparks.view;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import urbanparks.model.Constants.*;
//import urbanparks.view.LoginPane.BackButtonEventHandler;
import urbanparks.model.JobCollection;
import urbanparks.model.UserCollection;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;


/**
 * Main entry point for Urban Parks application GUI
 */
public class MainApplication extends Application {
	
	public class Test extends BorderPane {
	    private Button backButton;
		private JobCollection jobCollection;
		private UserCollection userCollection;
		
		public Test(Button backButton, JobCollection jobCollection, UserCollection userCollection) {
			this.backButton = backButton;
			this.jobCollection = jobCollection;
			this.userCollection = userCollection;
		}
	}

    private final String title = "Urban Parks";
    private BorderPane root;
    private Button backButton;
	private static JobCollection jobCollection;
	private static UserCollection userCollection;


    public MainApplication() {
	}

	/**
	 * 
	 * @param args
	 */
    public MainApplication(String[] args) {
        launch(args);
    }

    /**
     * Creates the primary stage context on which the GUI is built.
     * Adds the static components, such as menu bar and back button.
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
    	
		jobCollection = new JobCollection();
		userCollection = new UserCollection();
		try {
			jobCollection.loadData();
			userCollection.loadData();
		} catch (Exception e) {
			MessageBoxUtils.showDataLoadError(e.getMessage());
			MessageBoxUtils.showEmptyDataUsed();
		}

        // Stage init
        primaryStage.setTitle(title);
        root = new BorderPane();
        MenuBar menuBar = constructMenuBar();
        backButton = new Button();
        root.setTop(new BorderPane(null, menuBar, null, backButton, null));
        root.setCenter(new MainMenuPane(root, userCollection, jobCollection, backButton));
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
        saveMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
				try {
					userCollection.saveData();
					jobCollection.saveData();
					MessageBoxUtils.showDataSaveSuccess();
					
				} catch (IOException e) {
					MessageBoxUtils.showDataSaveError(e.getMessage());
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
    
}
