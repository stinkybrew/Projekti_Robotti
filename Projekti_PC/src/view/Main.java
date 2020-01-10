package view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import java.util.HashMap;

/**
 * Main class for the GUI application.
 * @author ryhmä 9
 *
 */
public class Main extends Application{
    private Stage primaryStage;
    private BorderPane mainAppLayout;

	@Override
	public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Lejos-projekti PC");

        initMainAppLayout();
	}

    /**
     * Initialize the main GUI layout and set controllers.
     */
    public void initMainAppLayout() {
        // Load the main layout file
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("MainAppLayout.fxml"));
            this.mainAppLayout = (BorderPane) loader.load();

            // Apply and show the layout
            Scene scene = new Scene(this.mainAppLayout);
            this.primaryStage.setScene(scene);
            this.primaryStage.show();

            // Give layout controller a reference to this Main class
            MainAppLayoutController controller = loader.getController();
            controller.setMain(this);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Loading MainAppLayout.fxml failed.");
        }
    }

	/**
	 * Launch application.
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
