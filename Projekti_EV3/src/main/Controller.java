package main;

import lejos.hardware.Button;
import lejos.hardware.device.PSPNXController;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;

/**
 * 
 * @author Ryhmä 9
 *
 */

/**Controller-class contains functions to read values from the controller, and call functions that make the robot act accordingly.*/
public class Controller {
	
	private static PSPNXController controller;
	private static int[] buttonList;
	private static boolean emergencyState;

	
	
	/* --------CONTROLS--------------------
	 * LEFT JOYSTICK Y (FORWARD / BACKWARD)
	 * FORWARD = < 0 (MAX -95)
	 * BACKWARD = > 0 (MAX 99
	 *
	 * RIGHT JOYSTICK X (LEFT / RIGHT )
	 * LEFT = < 0 (MAX -100)
	 * RIGHT = > 0 (MAX 99)
	 * 
	 * TRIANGLE BUTTON (getButtons[11]) = EMERGENCY
	 * 
	 * SELECT BUTTON (getButtons[7]) = SWITCH MODE (MANUAL / AUTOMATIC)
	 * ------------------------------------
	*/
	
	/**Constructor  for a new controller*/
	public Controller() {
		try {
		controller = new PSPNXController(SensorPort.S1);
		Main.userMode = true;
		} catch (Exception e) {
			System.out.println("Controller is not connected");
		}
	}
	
	
	/**Control-function acts as a sort of an arbitrator for the controller where the values are read from the controller in a continuous cycle.(While the global mode-variable is true.)*/
	public void control() {
		while (Main.userMode == true) {
			setUserMode();
			checkEmergencyButtons();
			sense();
			Motor.move(getLeftY());
			Motor.turn(getRightX());
		}
	}
	
	
	/**Use ultrasonic sensors to analyze environment*/
	public void sense() {
		double stopDistance = 0.25;
		int sensorLeft = 0;
		int sensorRight = 1;
		
		if (Sensors.checkUltraDistance(stopDistance, sensorLeft)) {
				// Left sensor detects an object on the right, so turn left
				Motor.stop();
				Motor.emergencyReverse();
		} else if (Sensors.checkUltraDistance(stopDistance, sensorRight)) {
				// Right sensor detects an object on the left, to turn right
				Motor.stop();
				Motor.emergencyReverse();
			
		}
		
	}
	
	/**Check if the emergencybuttons are pressed*/
	public static boolean checkEmergencyButtons() {
		buttonList = controller.getButtons();
		if (buttonList[11] == 1 || Button.ENTER.isDown()) {
			emergencyState = true;
			System.out.println("Emergency active\nPress again\nto continue\nPress ESC\nto quit");
			boolean cont = true;
			while(cont == true) {
				Delay.msDelay(1000);
				if (buttonList[11] == 1 || Button.ENTER.isDown()) {
					System.out.println("Resuming...");
					cont = false;
					Delay.msDelay(2000);
			}else if (Button.ESCAPE.isDown()) {
					System.exit(0);
			}
			}
			return emergencyState;
		} else {
			return false;
		}
	}
	
	/**If the usermode-button is pressed, return true. Usermode will now change to autopilot.*/
	public static boolean setUserMode() {
		buttonList = controller.getButtons();
		if (buttonList[7] == 1 || Button.DOWN.isDown()) {
			Main.userMode = !Main.userMode;
			System.out.println("UserMode = " +  String.valueOf(Main.userMode));
			Delay.msDelay(5000);
			return true;
		}else {
			return false;
		}
	}
	
	//getUserMode() returns the global mode-variable from main-class
	public boolean getUserMode() {
		return Main.userMode;
	}
	
	//getEmergencyState() returns the current state of emergencybuttons (checks if they are pressed).
	public boolean getEmergencyState() {
		return emergencyState;
	}
	
	//getLeftY() returns the Y-value of the left joystick on the controller.This is used controlling the speed of the robot.
	public int getLeftY() {
		return controller.getLeftY();
	}
	
	//getRightX() returns the X-value of the right joystick. This is used to steer the robot.
	public int getRightX() {
		return controller.getRightX();
	}
	
	//Returns controller-object.
	public PSPNXController getController() {
		return controller;
	}
}
