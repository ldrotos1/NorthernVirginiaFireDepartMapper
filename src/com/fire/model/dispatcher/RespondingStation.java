package com.fire.model.dispatcher;

import java.util.List;

import org.postgis.Point;

import com.fire.model.entities.BasicStation;

/**
 * This class provides an object that contains information regarding a fire station's
 * response to an incident. The information includes the basic information about the 
 * station, the location of the incident, the travel distance to the incident, the 
 * travel time to the incident and the coordinates of the route from the station to 
 * the incident.  
 * @author Louis Drotos
 *
 */
public class RespondingStation extends BasicStation implements Comparable<RespondingStation> {

	private Point incidentLocation;
	private int travelTimeSec;
	private double travelDistMiles;
	private List<Point> shapePoints;
	
	/**
	 * Constructor
	 * @param id The station ID
	 * @param name The station name
	 * @param stationLoc The station location
	 * @param incidentLoc The incident location
	 */
	protected RespondingStation(BasicStation station, Point incidentLoc) {
		
		super.setStationId(station.getStationId());
		super.setStationName(station.getStationName());
		super.setStationNumber(station.getStationNumber());
		super.setDepartment(station.getDepartment());
		super.setLocation(station.getLocation());
		this.incidentLocation = incidentLoc;
		this.travelTimeSec = 0;
		this.travelDistMiles = 0;
		this.shapePoints = null;
	}
	
	/**
	 * Get the travel time from the station to the incident in seconds.
	 * @return The travel time
	 */
	protected int getTravelTimeSec() {
		return travelTimeSec;
	}
	
	/**
	 * Sets the travel time from the station to the incident in seconds.
	 * @param travelTimeSec The travel time
	 */
	protected void setTravelTimeSec(int travelTimeSec) {
		this.travelTimeSec = travelTimeSec;
	}
	
	/**
	 * Get the travel distance in miles from the station to the incident.
	 * @return
	 */
	protected double getTravelDistMiles() {
		return travelDistMiles;
	}
	
	/**
	 * Sets the travel distance in miles from the station to the incident.
	 * @param travelDistMiles The travel distance
	 */
	protected void setTravelDistMiles(double travelDistMiles) {
		this.travelDistMiles = travelDistMiles;
	}
	
	/**
	 * Gets a list of coordinates representing the route from the station
	 * to the incident
	 * @return The list of coordinates
	 */
	protected List<Point> getShapePoints() {
		return shapePoints;
	}
	
	/**
	  * Gets a list of coordinates representing the route from the station
	 * to the incident
	 * @param shapePoints The list of coordinates
	 */
	protected void setShapePoints(List<Point> shapePoints) {
		this.shapePoints = shapePoints;
	}
	
	/**
	 * Gets the incident location
	 * @return The incident location
	 */
	protected Point getIncidentLocation() {
		return incidentLocation;
	}
	
	/**
	 * Sets the incident location
	 * @param location The incident location
	 */
	protected void setIncidentLocation(Point location) {
		this.incidentLocation = location;
	}

	@Override
	public int compareTo(RespondingStation otherStation) {
		
		if (this.travelTimeSec < otherStation.travelTimeSec) {
			return -1;
		}
		else if (this.travelTimeSec > otherStation.travelTimeSec) {
			return 1;
		}
		else {
			return 0;
		}
	}	
}
