package com.fire.model.beans;

/**
 * This entity class provides a full representation of a fire station
 * 
 * @author Louis Drotos - 15 March 2015
 */
public class FullStation extends BasicStation {
	
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
	public FullStation() {
		super();
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
		builder.append("Station ID : " + super.getStationId());
		builder.append("\nStation Number : " + super.getStationNumber());
		builder.append("\nStation Name : " + super.getStationName());
		builder.append("\nDepartment : " + super.getDepartment());
		builder.append("\nAddress : " + this.address + " " + this.city + ", " + this.state + " " + this.zipCode);
		builder.append("\nLat\\Long : " + String.valueOf(this.getLocation().y) + "\\" + String.valueOf(this.getLocation().x));
		builder.append("\nPhone Number : " + this.phoneNumber);
		builder.append("\nFax Number : " + this.faxNumber);
		builder.append("\nStation Image : " + this.imageUrl);
		return builder.toString().trim();	
	}
}
