package behaviors;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import main.Controller;
import main.Main;
import main.Motor;
/**
 * 
 * @author Ryhmä 9
 *
 */

/** Behavior-type class for emergency situations*/
public class Emergency implements Behavior{
	private volatile boolean suppressed = false;
	static boolean hataseisPainettu = false;

	
	/**If the emergency-buttons are pressed, this behavior takes control*/
	@Override
	public boolean takeControl() {
		if (Button.ESCAPE.isDown() || Controller.checkEmergencyButtons() || Controller.setUserMode()) return true;
		return false;
	}

	/**If the emergency-button is pressed while the robot is in autopilot, the system will automatically shut down.
	 * If the robot was controlled by the user, the user gets an option to quit the program or continue driving.*/
	@Override
	public void action(){
		if(Main.userMode=true) {
			Main.arb.stop();
			System.exit(0);
		}else {
		Emergency.hataseisPainettu = true;
		Motor.stop();
		System.out.println("Hataseis painettu");
		System.out.println("Paina ENTER jatkaaksesi\nPaina DOWN lopettaaksesi");
		while (!Button.ENTER.isDown()) {
			Delay.msDelay(5);
			if (Button.DOWN.isDown()) {
				System.exit(0);
			}
			}
		}
		}
	

	//suppress()-metodi lopettaa toiminnan.
	@Override
	public void suppress() {
		this.suppressed = true;
	}

}
