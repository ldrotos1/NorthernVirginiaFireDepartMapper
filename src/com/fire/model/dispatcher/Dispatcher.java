package com.fire.model.dispatcher;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fire.exceptions.DirectionsServiceException;
import com.fire.model.entities.Apparatus;
import com.fire.model.entities.BasicStation;
import com.fire.model.database.DatastoreAccess;

import org.postgis.Point;

/**
 * This class is used to generate IncidentResponse objects. Client code
 * provides a location and alarm number for a incident. This class
 * then determines how many units, what type of units and which
 * specific units should response to the incident. The class also
 * determines the routes that the units should take from their assigned
 * stations to the incident and estimates the travel time and distance 
 * of the routes. All of this information is packaged in a IncidentResponse
 * object, which is provided to the client code.
 * 
 * @author Louis Drotos
 *
 */
public class Dispatcher {
	
	private Connection dbConn;
	private DatastoreAccess datastore;
	private MapQuestRequest directionsService;
	
	/**
	 * Constructor
	 * @param apiKey An API key for the MapQuest Directions API
	 * @param dbConn The database connection
	 */
	public Dispatcher(String apiKey, Connection dbConn) {
		
		this.dbConn = dbConn;
		this.datastore = new DatastoreAccess();
		this.directionsService = new MapQuestRequest(apiKey);
	}
	
	/**
	 * This method builds and returns a Incident Response object
	 * corresponding to an incident at a specified location and
	 * of a specified alarm number (fire severity) 
	 * 
	 * @param incidentLoc The location of the incident
	 * @param alarmNumber The alarm number of the incident
	 * @return The IncidentResponse object
	 * @throws SQLException
	 * @throws DirectionsServiceException
	 */
	public IncidentResponse buildIncidentResponse(Point incidentLoc, int alarmNumber) throws SQLException, DirectionsServiceException {
		
		IncidentResponse responsePackage;
		List<RespondingStation> respStationsList;
				
		// Gets a list of closest stations by travel time.
		respStationsList = getRespondingStationsList(incidentLoc, alarmNumber);
		
		// Builds the dispatch response and returns it
		responsePackage = buildResponsePackage(respStationsList, alarmNumber); 
		return responsePackage; 
	}
	
	/**
	 * This method returns a list of closest stations to an incident. The
	 * list is sorted in ascending order by travel time to from the station
	 * to the incident. The number of stations returned will be based on the 
	 * alarm number. A higher alarm number the more stations returned. The
	 * list of stations returned is guaranteed to have the required number
	 * and combination of units to response to the incident 
	 *  
	 * @param loc The location of the incident
	 * @param alarms The number of alarms of the incident
	 * @return The list of stations
	 * @throws DirectionsServiceException
	 * @throws SQLException
	 */
	private List<RespondingStation> getRespondingStationsList(Point loc, int alarms) throws DirectionsServiceException, SQLException {
		
		// Declares objects and variables
		List<RespondingStation> respStationsList;
		List<BasicStation> statDistList;
		List<BasicStation> cloestStations;
		RespondingStation respStation;
		BasicStation basicStation;
		int stationCount;
		 
		// Gets a list of all stations sorted by distance to the incident
		statDistList = datastore.getStationsByDistance(dbConn, loc);
		
		// Gets the set of closest stations  
		stationCount = 6 + 5 * (alarms - 1);
		cloestStations = statDistList.subList(0, stationCount);
		
		// Ensues the set of closest stations has required number and mix of units
		// to response to the incident
		while (requiredUnitsAvailable(alarms, cloestStations) == false) {
			stationCount += 2;
			cloestStations = statDistList.subList(0, stationCount - 1);
		}
		
		// Builds the list of Station Response objects
		respStationsList = new LinkedList<RespondingStation>();
		while(stationCount > 0) {
			
			// Builds the RespondingStation object
			basicStation = statDistList.remove(0);
			respStation = new RespondingStation(basicStation, loc);
			
			// Calls the MapQuest Directions API
			if (directionsService.makeTravelTimeRequest(respStation) == true) {
				respStationsList.add(respStation);
			}
			else {
				throw new DirectionsServiceException();
			}
			stationCount--;
		}
		
		// Sorts and returns the list
		Collections.sort(respStationsList);
		return respStationsList;
	}
	
	/**
	 * This method determines if a list of fire stations has the required
	 * mix and number of units to response to an incident of a specified
	 * alarm level.
	 * 
	 * @param alarmCount The alarm level
	 * @param stations The list of stations
	 * @return True if the required units are available, otherwise false.
	 * @throws SQLException
	 */
	private boolean requiredUnitsAvailable(int alarmCount, List<BasicStation> stations) throws SQLException {
		
		Set<String> stationIds;
		Map<String,Integer> unitBreakdown;
		boolean unitsAvailable = true;
		
		// Creates the list of station IDs
		stationIds = new HashSet<String>();
		for (BasicStation station : stations) {
			stationIds.add(station.getStationId());
		}
		
		// Gets the unit break-out and determines if the stations have the
		// required mix of units
		unitBreakdown = datastore.getUnitBreakdown(dbConn, stationIds);
		
		if (unitBreakdown.get("suppression") < alarmCount * 3) {
			unitsAvailable = false;
		}
		
		if (unitBreakdown.get("aerial") < alarmCount) {
			unitsAvailable = false;
		}
		
		if (unitBreakdown.get("rescue") < alarmCount) {
			unitsAvailable = false;
		}
		
		if (unitBreakdown.get("medic") < alarmCount) {
			unitsAvailable = false;
		}
		
		if (unitBreakdown.get("command") < alarmCount) {
			unitsAvailable = false;
		}
		
		return unitsAvailable;
	}
	
	/**
	 * This method builds and returns a IncidentResponse object. The method
	 * ingests a list of stations and an alarm level and selects the units
	 * to respond from those stations. This method assumes that the list
	 * of stations is sorted in ascending order by travel time from the station
	 * to the incident. The method also assumes that the stations in the list
	 * collectively have the right number and mix of units to response to 
	 * the incident at the specified alarm level. The selected units are used
	 * to build the IncidentResponse object.
	 * 
	 * @param stations The list of stations
	 * @param alarms The alarm level
	 * @return The incident response object
	 * @throws SQLException
	 */
	private IncidentResponse buildResponsePackage(List<RespondingStation> stations, int alarms) throws SQLException {
		
		IncidentResponse response;
		RespondingStation station;
		RespondingApparatus respUnit;
		String unitType;
		List<RespondingApparatus> repondingUnits;
		Map<String,List<Point>> routes;
		Map<String,List<Apparatus>> stationUnits;
		List<Apparatus> unitList;
		int suppressionCount;
		int aerialCount;
		int rescueCount;
		int medicCount;
		int commandCount;
		boolean stationSendingUnits;
		
		repondingUnits = new LinkedList<RespondingApparatus>();
		routes = new HashMap<String,List<Point>>();
		
		// Determines the number of units for each category required to respond to the incident
		suppressionCount = alarms * 3;
		aerialCount = alarms;
		rescueCount = alarms;
		medicCount = alarms;
		commandCount = alarms;
		
		// Gets the mapping of station IDs to unit lists
		stationUnits = datastore.getRespondingUnits(dbConn, stations);
		
		// Iterates through the stations until incident response has all required units assigned.
		while(suppressionCount > 0 || aerialCount > 0 || rescueCount > 0 || medicCount > 0 || commandCount > 0) {
			
			// Gets the next closest station and its units
			station = stations.remove(0);
			unitList = stationUnits.get(station.getStationId());
			stationSendingUnits = false;
			
			for(Apparatus unit : unitList) {
				
				// Determines if a unit of this type is needed
				unitType = unit.getUnitType();
				if ((unitType.equals("Engine") || unitType.equals("Tanker")) && suppressionCount > 0 ) {
					
					// Adds this unit to the list of responding units
					respUnit = new RespondingApparatus(unit, station);
					repondingUnits.add(respUnit);
					stationSendingUnits = true;
					suppressionCount--;
				}
				else if ((unitType.equals("Tower") || unitType.equals("Truck")) && aerialCount > 0 ) {
					
					// Adds this unit to the list of responding units
					respUnit = new RespondingApparatus(unit, station);
					repondingUnits.add(respUnit);
					stationSendingUnits = true;
					aerialCount--;
				}
				else if ((unitType.equals("Rescue") || unitType.equals("Technical Rescue")) && rescueCount > 0 ) {
					
					// Adds this unit to the list of responding units
					respUnit = new RespondingApparatus(unit, station);
					repondingUnits.add(respUnit);
					stationSendingUnits = true;
					rescueCount--;
				}
				else if (unitType.equals("Medic") && medicCount > 0) {
				
					// Adds this unit to the list of responding units
					respUnit = new RespondingApparatus(unit, station);
					repondingUnits.add(respUnit);
					stationSendingUnits = true;
					medicCount--;
				}
				else if (unitType.equals("Battalion Chief") && commandCount > 0) {
					
					// Adds this unit to the list of responding units
					respUnit = new RespondingApparatus(unit, station);
					repondingUnits.add(respUnit);
					stationSendingUnits = true;
					commandCount--;
				}
			}
			
			// Adds the travel route to the IncidentResponse if the current
			// station is sending a unit to the incident
			if (stationSendingUnits == true) {
				routes.put(station.getStationId(), station.getShapePoints());
			}
		}
		
		// Builds the IncidentResponse object
		response = new IncidentResponse(repondingUnits, routes);
		return response;
	}
}
