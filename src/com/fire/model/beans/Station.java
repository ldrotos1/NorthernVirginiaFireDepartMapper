package com.fire.model.beans;

import org.postgis.Point;

/**
 * This entity class provides a representation of a fire station
 * 
 * @author Louis Drotos - 15 March 2015
 *
 */
public class Station {
	
	private Point location;
	private String stationId;
	private String stationNumber;
	private String stationName;
	private String department;
	private String address;
	private String city;
	private String state;
	private String zipCode;
	private String phoneNumber;
	private String faxNumber;
	private String imageUrl;
	
	/**
	 * Constructor
	 */
	public Station() {
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
	 * Getter - Phone Number
	 * 
	 * @return This station's phone number 
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	/**
	 * Setter - Phone Number
	 * 
	 * @param phoneNumber This station's phone number 
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	/**
	 * Getter - Fax Number
	 * 
	 * @return This station's fax number 
	 */
	public String getFaxNumber() {
		return faxNumber;
	}
	
	/**
	 * Setter - Fax Number
	 * 
	 * @param faxNumber This station's fax number 
	 */
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	
	/**
	 * Getter - Image URL
	 * 
	 * @return The URL of this station's image
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * Setter - Image URL
	 * 
	 * @param imageUrl The URL of this station's image
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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
		builder.append("\nAddress : " + this.address + " " + this.city + ", " + this.state + " " + this.zipCode);
		builder.append("\nLat\\Long : " + String.valueOf(this.getLocation().y) + "\\" + String.valueOf(this.getLocation().x));
		builder.append("\nPhone Number : " + this.phoneNumber);
		builder.append("\nFax Number : " + this.faxNumber);
		builder.append("\nStation Image : " + this.imageUrl);
		return builder.toString().trim();	
	}
}
