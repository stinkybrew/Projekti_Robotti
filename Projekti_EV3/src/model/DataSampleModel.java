package model;

import java.io.Serializable;

/**
 * Custom class for data transmission between the EV3-robot and another device
 * @author ryhmä 9
 *
 */
public class DataSampleModel implements Serializable{
    private static final long serialVersionUID = 12984576L;
    
	/**
	 * List of the three acceleration values; x, y and z
	 */
	private double[] accelerationList;
    /**
     * General value for integer data
     */
    private double value;
    /**
     * Signifies the data type for this object
     */
    private String key;

    /**
     * Constructor for acceleration
     * @param key
     * @param accelerationList
     */
    public DataSampleModel(String key, double[] accelerationList) {
        this.setKey(key);
        this.value = 0;
        this.accelerationList = accelerationList;
    }

    /**
     * Constructor for general int values
     * @param key
     * @param value
     */
    public DataSampleModel(String key, double value) {
        this.setKey(key);
        this.value = value;
        this.accelerationList = new double[] {0,0,0};
    }

    /**
     * Constructor for acceleration with default key
     * @param accelerationList
     */
    public DataSampleModel(double[] accelerationList) {
        this.setKey("Acceleration");
        this.value = 0;
        this.accelerationList = accelerationList;
    }

    /**
     * @return The generic int value
     */
    public double getValue() {
		return value;
	}

	/**
	 * Set a new generic int value
	 * @param value
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/**
	 * @return key identifying what type of data this object has
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Set a new key for the object
	 * @param key
	 */
	public void setKey(String key) {
		this.key = key;
	}

    /**
     * @return The list containing x, y and z acceleration values
     */
    public double[] getAccelerationList() {
        return accelerationList;
    }

    /**
     * Set new values for the list of acceleration values
     * @param accelerationList
     */
    public void setAccelerationList(double[] accelerationList) {
        this.accelerationList = accelerationList;
    }
}
