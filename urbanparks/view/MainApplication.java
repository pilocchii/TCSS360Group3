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
        root.setCenter(new LoginPane(root));
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
}
