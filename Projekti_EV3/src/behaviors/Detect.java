package behaviors;

import java.beans.Expression;
import java.util.Random;

import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import main.Motor;
import main.Sensors;

/**
 * 
 * @author Ryhmä 9
 *
 */

/** Behavior-type class for reading values with the sensors*/
public class Detect implements Behavior{
	
	
	private volatile boolean suppress = false;
	private Sensors sensorDistance = new Sensors();
	public static int direction; 
	
	@Override
	public boolean takeControl() {
		
	/**Take action if either of the sensors detect an object that is closer than allowed*/
		if (Sensors.checkUltraDistance(0.4, 0) || Sensors.checkUltraDistance(0.4, 1)){return true;}
		else {return false;}
		
	}
	
	@Override
	public void action() {
		
	//Action = Turn left or right, or stop

		suppress = false;
		
		/*
		 * SET TO 0 FOR TESTING
		 */
			
		///////////////////////
		int joystickRightX = 0;
		///////////////////////
			
	//If turning already ->
			
		//Emergency stop ->
		Random rand = new Random();
		int max=1;
		int min=-1;
		if(joystickRightX>50 || joystickRightX<-50) {
			if (Sensors.checkUltraDistance(0.3, 0) && Sensors.checkUltraDistance(0.3, 1)) {
				Motor.move(40);
				if(direction==1) {
					Motor.turn(-1);
					direction=2;
				}else if(direction==2) {
				    Motor.turn(1);
				    direction=1;
				}else {
					Motor.turn(rand.nextInt((max -min)+1)+min);
				}
				Delay.msDelay(4000);
			}
		}
		
	//If not turning ->
		if (!(joystickRightX>50) && !(joystickRightX<-50)) {
			
			//Emergency stop ->
			if (Sensors.checkUltraDistance(0.4, 0) && Sensors.checkUltraDistance(0.4, 1)) {
				
				Motor.move(40);
				if(direction==1) {
					Motor.turn(-1);
				}else if(direction==2) {
				    Motor.turn(1);
				}else {
					Motor.turn(rand.nextInt((max -min)+1)+min);
				}
			}
				else if (Sensors.checkUltraDistance(0.2, 0) || Sensors.checkUltraDistance(0.2, 1)) {
					
					Motor.move(40);
					if(direction==1) {
						Motor.turn(-1);
					}else if(direction==2) {
					    Motor.turn(1);
					}else {
						Motor.turn(rand.nextInt((max -min)+1)+min);
					}
			
			//If sensor 1 detects an obstacle ->
			}else if(Sensors.checkUltraDistance(0.5, 0) && !Sensors.checkUltraDistance(0.2, 1) && direction!=1){
				
				 //Turn left
				 Motor.turn(11);
				 direction=1;
				
			//If sensor 2 detects an object ->	 
			}else if(Sensors.checkUltraDistance(0.5, 1) && !Sensors.checkUltraDistance(0.2, 0) && direction!=2){
				
				//Turn right
				Motor.turn(-11);
				direction=2;
				
			}
	}	
	}
	
	@Override
	public void suppress() {
		this.suppress = true;
		
	}

}
