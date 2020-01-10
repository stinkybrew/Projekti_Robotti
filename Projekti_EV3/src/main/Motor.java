package main;

import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

/**
 * 
 * @author Ryhmä 9
 *
 */

/**Motor-class contains the variables and methods needed to operate with the motors */

public class Motor {
	private static RegulatedMotor motorRight;
	private static RegulatedMotor motorLeft;
	private static RegulatedMotor motorSteer;
	
	
	/**Constructor assigns the motors to variables by using try...catch- block.
	If the assignment fails (i.e the motors are not connected), the user is informed.*/
	public Motor() {
	
			motorRight = new EV3LargeRegulatedMotor(MotorPort.D);
			System.out.println("Right motor assigned");
			motorLeft = new EV3LargeRegulatedMotor(MotorPort.A);
			System.out.println("Left motor assigned");
			motorSteer = new EV3LargeRegulatedMotor(MotorPort.B);
			System.out.println("Steer motor assigned");
			motorSteer.setSpeed(900);
			System.out.println("Motor assigned");
	
	}
	
	/** setSpeed()-method takes the speed as a parameter and sets it for both thrust motors. */
	public static void setSpeed(int speed) {
		RegulatedMotor[] synchronizationList = {motorLeft};
		motorRight.synchronizeWith(synchronizationList);
		motorRight.startSynchronization();
		motorRight.setSpeed(speed);
		motorLeft.setSpeed(speed);
		motorRight.endSynchronization();
	}
	
	
	
	/**move()-method acts as a switch that determines the direction (forward/backward) of the car.
	The intensity-parameter is passed by the left joystick which returns the value of the Y-axis of the left joystick as an integer. */
	public static void move(int intensity) {
		if (intensity > 0) {
			//If the value is greater than 0, move backward.
			backward(intensity);
		} else if (intensity < 0) {
			//If the value is less than 0, move forward.
			forward(intensity);
		} else {
			//If the value is anything else, stop the motors.
			motorRight.stop();
			motorLeft.stop();
		}
	}
	
	/** turn()-method acts as a switch that determines the direction to which the front tires of the car will turn. */
	public static void turn(int intensity){
		if (intensity > 0) {
			//If the value is greater than 10, turn right.
			motorSteer.rotateTo(90);
		} else if (intensity < 0) {
			//If the value is less than -10, turn left.
			motorSteer.rotateTo(-90);
		} else {
			//If the value is anything else, reset the angle of the tires.
			motorSteer.rotateTo(0);
		}
	}
	
	
	/** Go forward. The greater the parameter 'intensity' the faster the car will move. */
	public static void forward(int intensity) {
		if (intensity <= -80) {
			setSpeed(900);
			motorRight.backward();
			motorLeft.backward();
		} else if (intensity <= -60) {
			setSpeed(600);
			motorRight.backward();
			motorLeft.backward();
		} else if (intensity <= -40) {
			setSpeed(300);
			motorRight.backward();
			motorLeft.backward();
		} else if (intensity < 0) {
			setSpeed(100);
			motorRight.backward();
			motorLeft.backward();
		} else {
			motorRight.stop();
			motorLeft.stop();
		}
	}
	
	/** Go backward. The greater the parameter 'intensity' the faster the car will move. */
	public static void backward(int intensity) {
		if (intensity >= 80) {
			setSpeed(900);
			motorRight.forward();
			motorLeft.forward();
		} else if (intensity >= 60) {
			setSpeed(600);
			motorRight.forward();
			motorLeft.forward();
		} else if (intensity >= 40) {
			setSpeed(300);
			motorRight.forward();
			motorLeft.forward();
		} else if (intensity > 0) {
			setSpeed(100);
			motorRight.forward();
			motorLeft.forward();
		} else {
			motorRight.stop();
			motorLeft.stop();
		}
	}
	
	/** Stop both thrusting motors */
	public static void stop() {
		motorRight.stop();
		motorLeft.stop();
	}
	
	/** Reverse and wait for 500 ms */
	public static void emergencyReverse() {
		motorLeft.forward();
		motorRight.forward();
		Delay.msDelay(500);
	}
	
	/**Returns right motor */
	public static RegulatedMotor getMotorRight() {
		return motorRight;
	}
	
	/**Returns left motor */
	public RegulatedMotor getMotorLeft() {
		return motorLeft;
	}
	
	/**Returns steering motor */
	public static RegulatedMotor getSteeringMotor() {
		return motorSteer;
	}
}
