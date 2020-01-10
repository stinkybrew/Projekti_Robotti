package controller;

import model.DataFetcher;
import model.DataModel;
import model.DataSampleModel;
import view.MainAppLayoutController;

/**
 * Controls the data flow from fetches to the GUI.
 * @author ryhmä 9
 *
 */
public class DataController {

    private DataSampleModel dataSampleModel;
    private boolean threadRunning = false;
    private DataFetcher dataFetcher;
    private DataModel dataModel = new DataModel();
    private MainAppLayoutController mainAppLayoutController;

    /**
     * Constructor which takes the GUI controller as parameter.
     * @param mainAppLayoutController
     */
    public DataController(MainAppLayoutController mainAppLayoutController) {
        this.dataSampleModel = new DataSampleModel("Acceleration", 0);
        this.mainAppLayoutController = mainAppLayoutController;
    }

    /**
     * Connect the DataFetcher object and start the thread.
     */
    public void startThread() {
        this.dataFetcher = new DataFetcher(this);
        this.dataFetcher.setSocket("10.0.1.1", 1111);
        this.dataFetcher.start();
        threadRunning = true;
    }

    /**
     * Necessary procedures to stop the thread.
     */
    public void stopThread() {
        threadRunning = false;
        this.dataFetcher.stopThread();
    }

    /**
     * @return DataModel.
     */
    public DataModel getDataModel() {
        return dataModel;
    }
}
