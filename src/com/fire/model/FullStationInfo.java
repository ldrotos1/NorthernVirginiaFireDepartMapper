package com.fire.model;

import java.nio.file.Path;
import java.util.List;

/**
 * This entity class provides a full representation of a fire station
 * 
 * @author Louis Drotos - 22 January 2015
 *
 */
public class FullStationInfo extends BasicStationInfo {
	
	private String stationName;
	private String stationNumber;
	private String department;
	private String address;
	private String city;
	private String state;
	private String zipCode;
	private Path imageUrl;
	private List<Apparatus> units;
	
	/**
	 * Constructor
	 */
	public FullStationInfo() {
		super();
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
	 * Getter - Address
	 * 
	 * @return This station's address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Setter - Address
	 * 
	 * @param address This station's address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Getter - City
	 * 
	 * @return This station's city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Setter - City
	 * 
	 * @param city This station's city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Getter - State
	 * 
	 * @return This station's state
	 */
	public String getState() {
		return state;
	}

	/**
	 * Setter - State
	 * 
	 * @param state This station's state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Getter - Zip Code
	 * 
	 * @return This station's zip code
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * Setter - Zip Code
	 * 
	 * @param zipCode This station's zip code 
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * Getter - Image URL
	 * 
	 * @return The URL of this station's image
	 */
	public Path getImageUrl() {
		return imageUrl;
	}

	/**
	 * Setter - Image URL
	 * 
	 * @param imageUrl The URL of this station's image
	 */
	public void setImageUrl(Path imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * Getter - Units
	 * 
	 * @return The list of the units assigned to this station
	 */
	public List<Apparatus> getUnits() {
		return units;
	}

	/**
	 * Setter - Units
	 * 
	 * @param units The list of the units assigned to this station
	 */
	public void setUnits(List<Apparatus> units) {
		this.units = units;
	}
	
	/**
	 * Returns a string representation of this object
	 * 
	 * @return The string representation
	 */
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		builder.append("Station ID : " + this.getStationId());
		builder.append("\nStation Number : " + this.getStationNumber());
		builder.append("\nStation Name : " + this.getStationName());
		builder.append("\nDepartment : " + this.department);
		builder.append("\nAddress : " + this.address + " " + this.city + ", " + this.state + " " + this.zipCode);
		builder.append("\nLat\\Long : " + String.valueOf(this.getLocation().y) + "\\" + String.valueOf(this.getLocation().x));
		builder.append("\nStation Image : " + this.imageUrl.toString());
		builder.append("\nUnits : ");
		
		for(Apparatus unit : units) {
			builder.append(unit.getUnitDesignator() + ", ");
		}
		builder.deleteCharAt(builder.lastIndexOf(","));
		
		return builder.toString().trim();	
	}
}
