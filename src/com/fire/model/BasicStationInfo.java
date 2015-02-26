package com.fire.model;
import org.postgis.Point;

public class BasicStationInfo {
	
	private String stationId;
	private String stationName;
	private String stationNumber;
	private Point location;
	
	public BasicStationInfo() {
		super();
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getStationNumber() {
		return stationNumber;
	}

	public void setStationNumber(String stationNumber) {
		this.stationNumber = stationNumber;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}
	
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		builder.append("Station ID : " + this.stationId);
		builder.append("\nStation Number : " + this.stationNumber);
		builder.append("\nStation Name : " + this.stationName);
		builder.append("\nLat\\Long : " + String.valueOf(this.location.y) + "\\" + String.valueOf(this.location.x));
		return builder.toString().trim();
	}
}
