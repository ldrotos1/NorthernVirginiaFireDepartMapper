package com.fire.model.data_processor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.postgis.Point;

import com.fire.exceptions.DirectionsServiceException;
import com.fire.exceptions.InvalidClientInputException;
import com.fire.model.database.DatastoreAccess;
import com.fire.model.dispatcher.Dispatcher;
import com.fire.model.dispatcher.IncidentResponse;
import com.fire.model.entities.Apparatus;
import com.google.gson.Gson;

/**
 * This class provides methods that are used to carry out 
 * business logic in support of the application. 
 * 
 * @author Louis Drotos - 15 March 2015
 *
 */
public class DataProcessor {
	
	Gson gson;
	DatastoreAccess datastore;
	Validator validator;
	
	/**
	 * Constructor
	 */
	public DataProcessor() {
		this.gson = new Gson();
		this.datastore = new DatastoreAccess();
		this.validator = new Validator();
	}
	
	/**
	 * Creates an incident response object for a incident at a specified location
	 * and of a specified number of alarms the is returned as a JSON string.
	 *  
	 * @param alarms The number of alarms
	 * @param lat The latitude of the incident
	 * @param lon The longitude of the incident
	 * @param apiKey An API key for the The MapQuest Directions Services
	 * @param dbConn The database connection
	 * @return The response object as a JSON string
	 * @throws InvalidClientInputException 
	 * @throws SQLException 
	 * @throws DirectionsServiceException 
	 */
	public String createIncidentResponse(String alarms, String lat, String lon, String apiKey, Connection dbConn) throws InvalidClientInputException, SQLException, DirectionsServiceException {
		
		Dispatcher dispatcher;
		IncidentResponse response;
		Point location;
		int intAlarms;
		double dblLat;
		double dblLon;
		
		// Validates parameter inputs
		if (!validator.ValidateAlarmNumber(alarms) || !validator.ValidateCoordinate(lat, lon)) {
						
			throw new InvalidClientInputException( "Alarm number and/or incident location is invalid." );
		}
					
		// Converts parameter values
		intAlarms = Integer.parseInt(alarms);
		dblLat = Double.parseDouble(lat);
		dblLon = Double.parseDouble(lon);
		location = new Point(dblLon, dblLat);
					
		// Validates incident location
		if (!validator.ValidateLocation(location, dbConn)) {
						
			throw new InvalidClientInputException( "Incident location is outside response area." );
		}
					
		// Creates and returns the incident response
		dispatcher = new Dispatcher(apiKey, dbConn);
		response = dispatcher.buildIncidentResponse(location, intAlarms);
		return gson.toJson(response);
	}
	
	/**
	 * This method returns a list of apparatus assigned to a
	 * particular station as a JSON string
	 * 
	 * @param station The target station's ID
	 * @param dbConn The connection to the database
	 * @return The list of apparatus
	 * @throws InvalidClientInputException 
	 * @throws SQLException 
	 */
	public String assignedUnitQuery(String station, Connection dbConn) throws InvalidClientInputException, SQLException {
		
		List<Apparatus> queryResult;
		
		// Validates input
		if (this.validator.ValidateStationId(station, dbConn) ==  false) {

			throw new InvalidClientInputException( "Station ID is invalid." );			
		}
					
		// Queries the database and converts result into JSON
		queryResult = datastore.getApparatus(station, dbConn);
		Collections.sort(queryResult);
		return gson.toJson(queryResult);
	} 
	
	/**
	 * This method returns a JSON representation of a set of stations IDs
	 * of stations that have at least one unit of a specified type assigned 
	 * to it. All parameters must not be NULL.
	 * 
	 * @param unitType The unit type
	 * @param dbConn The database connection
	 * @return The list in JSON format 
	 * @throws InvalidClientInputException 
	 * @throws SQLException 
	 */
	public String unitQuery(String unitType, Connection dbConn) throws InvalidClientInputException, SQLException {
		
		List<String> queryResult;
		
		// Validate inputs
		if (this.validator.ValidateUnitType(unitType, dbConn) == false) {
			
			throw new InvalidClientInputException( "Invalid unit type." );
		}
					
		// Queries the database and converts result into JSON
		queryResult = datastore.getStations(unitType, dbConn);
		return gson.toJson(queryResult);
	}
}
