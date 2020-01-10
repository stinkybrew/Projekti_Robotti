package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import controller.DataController;
import lejos.robotics.navigation.Pose;
import view.MainAppLayoutController;

/**
 * Class to fetch data from the Lejos EV3 robot.
 * @author ryhmä 9
 *
 */
public class DataFetcher extends Thread {
    private DataController dataController;
	private Socket socket = null;
	private DataSampleModel dataSampleModel;
	private DataOutputStream out = null;
	private ObjectInputStream in = null;
	private Boolean running = true;
    private static final long serialVersionUID = 473847339218742321L;
    private MainAppLayoutController mainAppLayoutController;

    /**
     * Constructor with a set dataController
     * @param dataController
     */
    public DataFetcher(DataController dataController) {
        this.dataController = dataController;
    }

	@Override
	public void run() {
        this.dataSampleModel = new DataSampleModel("Acceleration", 0);
        this.running = true;
        DataSampleModel sample = new DataSampleModel("Acceleration", 0);
        try {
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	while (running) {
            try {
                try {
                    sample = (DataSampleModel)in.readObject();
                    System.out.println(sample.getAccelerationList()[0]);
                } catch (EOFException e) {
                    e.printStackTrace();
                    break;
                }
                if (sample.getKey().equals("Acceleration")) {
                    this.dataController.getDataModel()
                        .setAcceleration(sample.getAccelerationList());
                    this.dataSampleModel.setAccelerationList(sample.getAccelerationList());
                    System.out.println(this.dataSampleModel.getAccelerationList());
                } else if (sample.getKey().equals("Tyre angle")) {
                    this.dataController.getDataModel().setTyreAngle(sample.getValue());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * Set controlling boolean to false to stop the thread.
     */
    public void stopThread() {
        this.running = false;
    }

    /**
     * @return The list of acceleration values.
     */
    public double[] getAcceleration() {
        return this.dataSampleModel.getAccelerationList();
    }
	
	/**
	 * Empty constructor.
	 */
	public DataFetcher() {}
	
	/**
	 * Constructor with a set socket
	 * @param socket
	 */
	public DataFetcher(Socket socket) {
		this.socket = socket;
	}


	/**
	 * @return Current socket.
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * Set a new socket for data transmission.
	 * @param address IP-address to connect to.
	 * @param port Port to connect to.
	 */
	public void setSocket(String address, int port) {
        Socket socket;
		try {
			socket = new Socket(address, port);
		    this.socket = socket;
		} catch (UnknownHostException e) {
            // TODO: Add Alert window for the error
			System.out.println("Socket connection failed: unknown host");
			e.printStackTrace();
		} catch (IOException e) {
            // TODO: Add Alert window for the error
			System.out.println("Socket connection failed: IO error");
			e.printStackTrace();
		}
	}
}
