package com.fire.model;
import org.postgis.Point;

/**
 * This entity class provides a basic representation of a fire station
 * 
 * @author Louis Drotos - 22 January 2015
 *
 */
public class BasicStationInfo {
	
	private String stationId;
	private Point location;
	
	/**
	 * Constructor
	 */
	public BasicStationInfo() {
		super();
	}

	/**
	 * Getter - Station ID
	 * 
	 * @return The station ID of this station
	 */
	public String getStationId() {
		return stationId;
	}

	/**
	 * Setter - Station ID
	 * 
	 * @param stationId The station ID of this station
	 */
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	/**
	 * Getter - Station Location
	 * 
	 * @return The location of this station
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * Setter - Station Location
	 * 
	 * @param location The location of this station
	 */
	public void setLocation(Point location) {
		this.location = location;
	}
	
	/**
	 * This method returns a string representation of this station object
	 * 
	 * @return The string representation
	 */
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		builder.append("Station ID : " + this.stationId);
		builder.append("\nLat\\Long : " + String.valueOf(this.location.y) + "\\" + String.valueOf(this.location.x));
		return builder.toString().trim();
	}
}
