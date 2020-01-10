package behaviors;

import lejos.robotics.subsumption.Behavior;
import main.Motor;

/**
 * 
 * @author Ryhmä 9
 *
 */

/** Behavior-type class for movement.*/
public class Move implements Behavior {
	private volatile boolean suppress = false;
	
	@Override
	public boolean takeControl() {
		return true;
	}

	/**Basic functionality for moving*/
	@Override
	public void action() {
		   suppress = false;
		   Motor.turn(0);
		   Detect.direction=0;
		   Motor.move(-40);
		   
	}
	
	@Override
	public void suppress() {
		this.suppress = true;
	}
}
