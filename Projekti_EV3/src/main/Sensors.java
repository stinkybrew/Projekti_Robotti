package main;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.MindsensorsAbsoluteIMU;
import lejos.hardware.sensor.NXTUltrasonicSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.AccelerometerAdapter;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.robotics.filter.MedianFilter;

/**
 * 
 * @author Ryhmä 9
 *
 */

/** Sensors-class contains the required variables and functions to operate different sensors that are attached to the robot. These sensors include two ultrasonic sensors
 * in front part of the car, as well as an accelerometer and a receiver for the controller.*/
public class Sensors{
	/**Array-variable for ultrasonic sensors*/
	private final static NXTUltrasonicSensor[] ultras= {new NXTUltrasonicSensor(SensorPort.S2),new NXTUltrasonicSensor(SensorPort.S3)};
	/**Variables for accelerometer*/
	public static Port accPort = LocalEV3.get().getPort("S4");
	public static SensorModes accSensor = new MindsensorsAbsoluteIMU(accPort);
	public static SampleProvider accSampleProvider = ((MindsensorsAbsoluteIMU)accSensor).getAccelerationMode();
	public static AccelerometerAdapter accelerometerAdapter = new AccelerometerAdapter(accSampleProvider);
	
    /**Sampleproviders and meanfilters for ultrasonic sensors*/
	private final static SampleProvider[] ultraSamplers = {ultras[0].getDistanceMode(),ultras[1].getDistanceMode()};
	private final static SampleProvider[] ultraMeans = {new MeanFilter(ultraSamplers[0],5),new MeanFilter(ultraSamplers[1],5)};
    private static float[][] ultrasamples = {new float[ultraSamplers[0].sampleSize()],new float[ultraSamplers[1].sampleSize()]};
    

    /**Fetch current accelerometer values for X,Y and Z. Assemble and return an array of values.*/
    public static double[] checkAcceleration() {
    	double[] temp = {
    		new Double(accelerometerAdapter.getXAccel()),
    		new Double(accelerometerAdapter.getYAccel()),
    		new Double(accelerometerAdapter.getZAccel()),
    	};
		return temp;
	}

    
    public Sensors() {
    	
    }
    
    /**Fetch reading from ultrasensor 0 or 1 (left or right*/
    public static float getUltraDistance(int sensor) {
        ultraMeans[sensor].fetchSample(ultrasamples[sensor], 0);
        return ultrasamples[sensor][0];
    }
    
    /**Compare the distance reading to a stopping distance that is passed as a parameter*/
    public static boolean checkUltraDistance(double stopDistance, int sensor) {
        try {
            boolean test = getUltraDistance(sensor) < stopDistance;
            return test;
            
        } catch (Exception e) {
            return true;
        
        }
    }
}

