package com.fire.model.dispatcher;

import java.util.List;
import java.util.Map;

import org.postgis.Point;

/**
 * This class represents a fire department response to an emergency incident. It 
 * includes fields providing general information about the response including the 
 * number of responding units, the number of stations contributing units to the 
 * response and the time till arrival of the first and last units. The class also
 * provides information on all units responding to the incident and the travel route
 * from each station to the incident.
 *     
 * @author Louis Drotos
 */
public class IncidentResponse {
	
	private int unitCount;
	private int stationCount;
	private int firstArrivalMin;
	private int lastArrivalMin;
	private List<RespondingApparatus> units;
	private Map<String,List<Point>> travelRoutes;
	
	/**
	 * Constructor
	 * @param units The list of units responding to the incident
	 * @param travelRoutes A map of travel routes from the stations 
	 * to the incident. Keys should be station IDs and values a set
	 * of points defining the route from the station to the incident  
	 */
	public IncidentResponse(List<RespondingApparatus> units, Map<String,List<Point>> travelRoutes) {
		
		this.unitCount = units.size();
		this.stationCount = travelRoutes.size();
		this.units = units;
		this.travelRoutes = travelRoutes;
		
		if (units.size() > 0) {
			
			// Iterates through the units to determine the arrival times of the
			// first and last units
			int currentTime;
			this.firstArrivalMin = units.get(0).getTravelTime();
			this.lastArrivalMin = units.get(0).getTravelTime();
			
			for(int x = 1; x < units.size(); x++) {
				
				currentTime = units.get(x).getTravelTime();
				
				if (currentTime < this.firstArrivalMin) {
					this.firstArrivalMin = currentTime;
				}
				
				if (currentTime > this.lastArrivalMin) {
					this.lastArrivalMin = currentTime;
				}
			}
		}
	}

	/**
	 * Getter - Total Unit Count
	 * @return The total number of units responding to the incident
	 */
	public int getUnitCount() {
		return unitCount;
	}
	
	/**
	 * Getter - Total Number of Stations
	 * @return The total number of stations contributing units to the incident
	 */
	public int getStationCount() {
		return stationCount;
	}

	/**
	 * Getter - First Unit Arrival Time
	 * @return Time till the arrival of the first unit at the incident in minutes
	 */
	public double getFirstArrivalMin() {
		return firstArrivalMin;
	}

	/**
	 * Getter - Last Unit Arrival Time
	 * @return Time till the arrival of the last unit at the incident in minutes
	 */
	public double getLastArrivalMin() {
		return lastArrivalMin;
	}

	/**
	 * Getter - Responding Units
	 * @return The list of responding units
	 */
	public List<RespondingApparatus> getUnits() {
		return units;
	}

	/**
	 * Getter - Travel Routes
	 * @return The map of travel routes from the stations to the incident 
	 */
	public Map<String, List<Point>> getTravelRoutes() {
		return travelRoutes;
	}
}
