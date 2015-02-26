package com.fire.model;

import java.nio.file.Path;
import java.util.List;

public class FullStationInfo extends BasicStationInfo {
	
	private String department;
	private String address;
	private String city;
	private String state;
	private String zipCode;
	private Path imageUrl;
	private List<Apparatus> units;
	
	public FullStationInfo() {
		super();
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Path getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(Path imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<Apparatus> getUnits() {
		return units;
	}

	public void setUnits(List<Apparatus> units) {
		this.units = units;
	}
	
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
