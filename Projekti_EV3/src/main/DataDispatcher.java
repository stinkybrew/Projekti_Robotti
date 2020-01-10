package main;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import com.sun.media.jfxmedia.events.NewFrameEvent;

import lejos.utility.Delay;
import model.DataSampleModel;

/**
 * A Thread class to dispatch robot related physical data on to another device
 * @author ryhmä 9
 *
 */
public class DataDispatcher extends Thread{
	
	/**
	 * Socket for sending data.
	 */
	private Socket socket = null;
	/**
	 * Server socket for sending data.
	 */
	private ServerSocket serverSocket = null;
	/**
	 * Output stream for data transmission.
	 */
	private ObjectOutputStream objOut = null;
	/**
	 * Boolean check to notify thread to stop.
	 */
	private Boolean running = true;

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		this.running = true;
		try {
			serverSocket = new ServerSocket(1111);
			socket = serverSocket.accept();
		    objOut = new ObjectOutputStream(socket.getOutputStream());
			while (running) {
				DataSampleModel accDataSample = new DataSampleModel(Sensors.checkAcceleration());
				DataSampleModel tyreAngleSample = new DataSampleModel("Tyre Angle", 23);
				objOut.writeObject(accDataSample);
				objOut.flush();
				objOut.writeObject(tyreAngleSample);
				objOut.flush();
				Delay.msDelay(200);
			}
		} catch (IOException e) {
			stopThread();
			System.out.println("Yhteys katkaistu");
			e.printStackTrace();
		}
	}

	/**
	 * Set a normal socket for the object. Unused.
	 * @param address Address to connect to.
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

    /**
     * Perform necessary procedures to stop the thread.
     */
    public void stopThread() {
    	if (this.serverSocket != null) {
			try {
				this.serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        this.running = false;
    }
}
