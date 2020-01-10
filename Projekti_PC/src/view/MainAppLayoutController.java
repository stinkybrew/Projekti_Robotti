package view;

import java.net.URL;
import java.util.ResourceBundle;

import controller.DataController;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.DataFetcher;
import model.DataModel;

/**
 * Controller for the main layout to manipulate GUI elements
 * @author ryhmä 9
 *
 */
public class MainAppLayoutController implements Initializable{

    private DataController dataController = new DataController(this);
    private DataFetcher dataFetcher;
    private boolean threadRunning;
    private DataModel dataModel;

    private Service<Void>[] labelUpdaterThreads;
    private Service<Void> labelUpdaterThread;
    private Service<Void> accYUpdaterThread;
    private Service<Void> accZUpdaterThread;
    private Service<Void> tyreUpdaterThread;

    @FXML
    private Rectangle leftTyre;
    @FXML
    private Rectangle rightTyre;
    @FXML
    private Circle accelerationCircle;
    @FXML
    private Label tyreAngleLabel;
    @FXML
    private Label accelerationLabel;
    @FXML
    private Label yaccelerationLabel;
    @FXML
    private Label zaccelerationLabel;
    @FXML
    private Label objectCountLabel;
    @FXML
    private Label distanceLabel;
    @FXML
    private Button startButton;
    @FXML
    private Button stoptButton;
    @FXML
    private Button refreshButton;

    private Main mainApp;

    @FXML
    private void initialize() {}

    /**
     * For passing through the main class.
     * @param mainApp
     */
    public void setMain(Main mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Starts threads necessary to change label texts.
     */
    @FXML
    private void handleStartThread() {
        this.threadRunning = true;
        this.dataController.startThread();
        
        labelUpdaterThread = new Service<Void>() {

			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {

					@Override
					protected Void call() throws Exception {
                        while (threadRunning) {
                            updateMessage(Double.toString(getDataController().getDataModel()
                                    .getXAcceleration()));
                        }
						return null;
					}
				};
			}
        };

        accYUpdaterThread = new Service<Void>() {

			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {

					@Override
					protected Void call() throws Exception {
                        while (threadRunning) {
                            updateMessage(Double.toString(getDataController().getDataModel()
                                    .getYAcceleration()));
                        }
						return null;
					}
				};
			}
        };

        accZUpdaterThread = new Service<Void>() {

			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {

					@Override
					protected Void call() throws Exception {
                        while (threadRunning) {
                            updateMessage(Double.toString(getDataController().getDataModel()
                                    .getZAcceleration()));
                        }
						return null;
					}
				};
			}
        };

        accelerationLabel.textProperty().bind(labelUpdaterThread.messageProperty());
        yaccelerationLabel.textProperty().bind(accYUpdaterThread.messageProperty());
        zaccelerationLabel.textProperty().bind(accZUpdaterThread.messageProperty());

        labelUpdaterThread.restart();
        accYUpdaterThread.restart();
        accZUpdaterThread.restart();
    }

    /**
     * @return dataModel
     */
    @FXML
    private DataModel getDataModel() {
        return dataModel;
    }

    /**
     * @return dataController
     */
    @FXML
    private DataController getDataController() {
        return this.dataController;
    }

    /**
     * Procedures necessary to stop a thread.
     */
    @FXML
    private void handleStopThread() {
        this.threadRunning = false;
        this.dataController.stopThread();
    }

    /**
     * Handling label refreshes. Unused.
     */
    @FXML
    private void handleRefresh() {
        this.accelerationLabel.setText("");
    }

    /**
     * Set the acceleration values in dataModel to update the UI labels.
     * @param acceleration
     */
    public void changeAcceleration(double[] acceleration) {
        this.dataModel.setAcceleration(acceleration);
    }

	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}
