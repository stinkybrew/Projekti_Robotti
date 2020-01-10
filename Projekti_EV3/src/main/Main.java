package main;

import behaviors.Detect;
import behaviors.Emergency;
import behaviors.Move;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

/** 
 * 
 * @author Ryhmä 9
 *
 */


/**Main-class contains the variables and functions needed to start the program as well as switch between user- and autopilotmodes */
public class Main {
	
	/**Visible variable for current usermode*/
	public static boolean userMode=true;
	public static Arbitrator arb;

	public static void main(String[] args) {
		
		DataDispatcher dataDispatcher = new DataDispatcher();
		dataDispatcher.start();		
		Motor motor = new Motor();
		
		Controller controller = new Controller();
		
		/**Create behaviors*/
		Behavior detects = new Detect();
		
		Behavior emergency = new Emergency();
		
		Behavior move = new Move();
		
		/**Add behaviors to a list*/
		Behavior[] behaviorList ={move,detects,emergency};

		/**Create an arbitrator and set the behavior list as a parameter*/
		arb = new Arbitrator(behaviorList);
		
		/**This simple function acts as a switch between modes by reading the global usermode-variable.*/
		while (true) {
			if (controller.getUserMode() == true) {
				controller.control();
			} else {
			/**Start the arbitrator.*/
			System.out.println("Arbitrator activated");
			arb.go();
			}
		}
	}
}
