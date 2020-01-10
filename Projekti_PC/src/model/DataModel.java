package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Data model to update GUI values with.
 * @author ryhmä 9
 *
 */
public class DataModel {

    private final DoubleProperty tyreAngle;
    private final DoubleProperty xAcceleration;
    private final DoubleProperty yAcceleration;
    private final DoubleProperty zAcceleration;
    private final DoubleProperty objectCount;
    private final DoubleProperty distance;

    /**
     * Initialize all values with 0.
     */
    public DataModel() {
        this.tyreAngle = new SimpleDoubleProperty(0);
        this.xAcceleration = new SimpleDoubleProperty(0);
        this.yAcceleration = new SimpleDoubleProperty(0);
        this.zAcceleration = new SimpleDoubleProperty(0);
        this.objectCount = new SimpleDoubleProperty(0);
        this.distance = new SimpleDoubleProperty(0);
    }

	/**
	 * @return The angle value for robot car's tyres.
	 */
	public DoubleProperty getTyreAngle() {
		return tyreAngle;
	}

    /**
     * @param angle Set a new angle values.
     */
    public void setTyreAngle(Double angle) {
        this.tyreAngle.set(angle);
    }

    /**
     * @return The X acceleration value.
     */
    public double getXAcceleration() {
        return xAcceleration.doubleValue();
    }

    /**
     * @return The Y acceleration value.
     */
    public double getYAcceleration() {
        return yAcceleration.doubleValue();
    }

    /**
     * @return The Z acceleration value.
     */
    public double getZAcceleration() {
        return zAcceleration.doubleValue();
    }

    /**
     * Set new acceleration values from a list containing all of them in the order x, y z.
     * @param acceleration
     */
    public void setAcceleration(double[] acceleration) {
        this.xAcceleration.set(acceleration[0]);
        this.yAcceleration.set(acceleration[1]);
        this.zAcceleration.set(acceleration[2]);
    }

    /**
     * @return Return the number of objects seen.
     */
    public DoubleProperty getObjectCount() {
        return objectCount;
    }
    
    /**
     * @return Return current distance from an object.
     */
    public DoubleProperty getDistance() {
        return distance;
    }
}
