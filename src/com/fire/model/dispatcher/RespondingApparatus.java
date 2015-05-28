package com.fire.model.dispatcher;

import com.fire.model.entities.Apparatus;

/**
 * This class represents a fire department apparatus that is responding
 * to an incident. This class extends the Apparatus class and provides
 * additional fields referring to the station that its responding from
 * and the time and distance to the incident from the station.
 * 
 * @author Louis Drotos
 */
public class RespondingApparatus extends Apparatus {

	private String stationName;
	private String stationNumber;
	private String department;
	private int travelTime;
	private double travelDistance;
	
	/**
	 * Constructor
	 * @param unit The base Apparatus object
	 * @param station The station that this unit is responding from
	 * @param time Travel time in seconds
	 * @param dist Travel distance in miles
	 */
	protected RespondingApparatus(Apparatus unit, RespondingStation station) {
		
		super.setStationId(unit.getStationId());
		super.setUnitDesignator(unit.getUnitDesignator());
		super.setUnitType(unit.getUnitType());
		this.stationName = station.getStationName();
		this.stationNumber = station.getStationNumber();
		this.department = station.getDepartment();
		this.travelTime = station.getTravelTimeSec();
		this.travelDistance = station.getTravelDistMiles();
	}

	/**
	 * Getter - Station Name
	 * @return The name of this apparatus's assigned station
	 */
	protected String getStationName() {
		return stationName;
	}

	/**
	 * Setter - Station Name
	 * @param stationName The name of this apparatus's assigned station
	 */
	protected void setStationName(String stationName) {
		this.stationName = stationName;
	}

	/**
	 * Getter - Station Number 
	 * @return The number of this apparatus's assigned station
	 */
	protected String getStationNumber() {
		return stationNumber;
	}

	/**
	 * Setter - Station Number
	 * @param stationNumber The number of this apparatus's assigned station
	 */
	protected void setStationNumber(String stationNumber) {
		this.stationNumber = stationNumber;
	}

	/**
	 * Getter - Department
	 * @return The department that this apparatus belongs to
	 */
	protected String getDepartment() {
		return department;
	}

	/**
	 * Setter - Department
	 * @param department The department that this apparatus belongs to
	 */
	protected void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * Getter - Travel Time
	 * @return The travel time to the incident in seconds
	 */
	protected int getTravelTime() {
		return travelTime;
	}

	/**
	 * Setter - Travel Time
	 * @param travelTime The travel time to the incident in seconds
	 */
	protected void setTravelTime(int travelTime) {
		this.travelTime = travelTime;
	}

	/**
	 * Getter - Travel Distance
	 * @return The travel distance to the incident in miles
	 */
	protected double getTravelDistance() {
		return travelDistance;
	}

	/**
	 * Setter - Travel Distance
	 * @param travelDistance The travel distance to the incident in miles 
	 */
	protected void setTravelDistance(double travelDistance) {
		this.travelDistance = travelDistance;
	}
}
