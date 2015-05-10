package com.fire.model;

import java.util.List;

import org.postgis.Point;

/**
 * This class provides an object that contains information regarding a fire station's
 * response to an incident. The information includes the basic information about the 
 * station, the location of the incident, the travel distance to the incident, the 
 * travel time to the incident and the coordinates of the route from the station to 
 * the incident.  
 * @author Louis Drotos
 *
 */
public class StationDispatchResponse {

	private String stationId;
	private String stationName;
	private Point stationLocation;
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
	public StationDispatchResponse(String id, String name, Point stationLoc, Point incidentLoc) {
		
		this.stationId = id;
		this.stationName = name;
		this.stationLocation = stationLoc;
		this.incidentLocation = incidentLoc;
		this.travelTimeSec = 0;
		this.travelDistMiles = 0;
		this.shapePoints = null;
	}
	
	/**
	 * Get the travel time from the station to the incident in seconds.
	 * @return The travel time
	 */
	public int getTravelTimeSec() {
		return travelTimeSec;
	}
	
	/**
	 * Sets the travel time from the station to the incident in seconds.
	 * @param travelTimeSec The travel time
	 */
	public void setTravelTimeSec(int travelTimeSec) {
		this.travelTimeSec = travelTimeSec;
	}
	
	/**
	 * Get the travel distance in miles from the station to the incident.
	 * @return
	 */
	public double getTravelDistMiles() {
		return travelDistMiles;
	}
	
	/**
	 * Sets the travel distance in miles from the station to the incident.
	 * @param travelDistMiles The travel distance
	 */
	public void setTravelDistMiles(double travelDistMiles) {
		this.travelDistMiles = travelDistMiles;
	}
	
	/**
	 * Gets a list of coordinates representing the route from the station
	 * to the incident
	 * @return The list of coordinates
	 */
	public List<Point> getShapePoints() {
		return shapePoints;
	}
	
	/**
	  * Gets a list of coordinates representing the route from the station
	 * to the incident
	 * @param shapePoints The list of coordinates
	 */
	public void setShapePoints(List<Point> shapePoints) {
		this.shapePoints = shapePoints;
	}
	
	/**
	 * Gets the station ID
	 * @return The station ID
	 */
	public String getStationId() {
		return stationId;
	}
	
	/**
	 * Gets the station name
	 * @return The station name
	 */
	public String getStationName() {
		return stationName;
	}
	
	/**
	 * Gets the station location
	 * @return The station location
	 */
	public Point getStationLocation() {
		return stationLocation;
	}

	/**
	 * Gets the incident location
	 * @return The incident location
	 */
	public Point getIncidentLocation() {
		return incidentLocation;
	}
}
