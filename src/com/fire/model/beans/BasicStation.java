package com.fire.model.beans;

import org.postgis.Point;

/**
 * This entity class provides a partial representation of a fire station
 * 
 * @author Louis Drotos - 23 May 2015
 */
public class BasicStation {

	private Point location;
	private String stationId;
	private String stationNumber;
	private String stationName;
	private String department;
	
	/**
	 * Constructor
	 */
	public BasicStation() {
		super();
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
	 * Getter - Station Number
	 * 
	 * @return This station's station number
	 */
	public String getStationNumber() {
		return stationNumber;
	}

	/**
	 * Setter - Station Number
	 * 
	 * @param stationNumber This station's station number
	 */
	public void setStationNumber(String stationNumber) {
		this.stationNumber = stationNumber;
	}

	/**
	 * Getter - Station Name
	 * 
	 * @return The name of this station
	 */
	public String getStationName() {
		return stationName;
	}

	/**
	 * Setter - Station Name
	 * 
	 * @param stationName The name of this station
	 */
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	
	/**
	 * Getter - Department
	 * 
	 * @return This station's department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * Setter - Department
	 * 
	 * @param department This station's department
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	
	/**
	 * Returns a string representation of this object
	 * 
	 * @return The string representation
	 */
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		builder.append("Station ID : " + this.stationId);
		builder.append("\nStation Number : " + this.stationNumber);
		builder.append("\nStation Name : " + this.stationName);
		builder.append("\nDepartment : " + this.department);
		return builder.toString().trim();	
	}
}
