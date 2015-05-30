package com.fire.model.data_processor;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.postgis.Point;

import com.fire.exceptions.Error;
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
	Logger logger;
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
	 */
	public String createIncidentResponse(String alarms, String lat, String lon, String apiKey, Connection dbConn) {
		
		Dispatcher dispatcher;
		IncidentResponse response;
		Point location;
		int intAlarms;
		double dblLat;
		double dblLon;
		Error error;
		
		try
		{
			// Validates parameter inputs
			if (!validator.ValidateAlarmNumber(alarms) || !validator.ValidateCoordinate(lat, lon)) {
				
				error = new Error("Alarm number and/or incident location is invalid.");
				return gson.toJson(error);
			}
			
			// Converts parameter values
			intAlarms = Integer.parseInt(alarms);
			dblLat = Double.parseDouble(lat);
			dblLon = Double.parseDouble(lon);
			location = new Point(dblLon, dblLat);
			
			// Creates and returns the incident response
			dispatcher = new Dispatcher(apiKey, dbConn);
			response = dispatcher.buildIncidentResponse(location, intAlarms);
			return gson.toJson(response);
		}
		catch (Exception e) {
			
			// Logs the error
			logger = Logger.getLogger("fire_app");
			logger.error(e.getMessage() + "\n" + ExceptionUtils.getStackTrace(e)); 
						
			// Returns error
			error = new Error("Unable to process request at this time.");
			return gson.toJson(error);
		}
	}
	
	/**
	 * This method returns a list of apparatus assigned to a
	 * particular station as a JSON string
	 * 
	 * @param station The target station's ID
	 * @param dbConn The connection to the database
	 * @return The list of apparatus
	 */
	public String assignedUnitQuery(String station, Connection dbConn) {
		
		List<Apparatus> queryResult;
		Error error;
		
		try {
			
			// Validates input
			if (this.validator.ValidateStationId(station, dbConn) ==  false) {
				error = new Error("Station ID is invalid.");
				return gson.toJson(error);
			}
			
			// Queries the database and converts result into JSON
			queryResult = datastore.getApparatus(station, dbConn);
			Collections.sort(queryResult);
			return gson.toJson(queryResult);
		}
		catch (Exception e) {
			
			// Logs the error
			logger = Logger.getLogger("fire_app");
    		logger.error(e.getMessage() + "\n" + ExceptionUtils.getStackTrace(e)); 
			
    		// Returns error
    		error = new Error("Unable to process request at this time.");
    		return gson.toJson(error);
		}
	} 
	
	/**
	 * This method returns a JSON representation of a set of stations IDs
	 * of stations that have at least one unit of a specified type assigned 
	 * to it. All parameters must not be NULL.
	 * 
	 * @param unitType The unit type
	 * @param dbConn The database connection
	 * @return The list in JSON format 
	 */
	public String unitQuery(String unitType, Connection dbConn) {
		
		List<String> queryResult;
		Error error;
		
		try {
			
			// Validate inputs
			if (this.validator.ValidateUnitType(unitType, dbConn) == false) {
				error = new Error("Invalid unit type;");
				return gson.toJson(error);
			}
			
			// Queries the database and converts result into JSON
			queryResult = datastore.getStations(unitType, dbConn);
			return gson.toJson(queryResult);
		}
		catch (Exception e) {
			
			// Logs the error
			logger = Logger.getLogger("fire_app");
    		logger.error(e.getMessage() + "\n" + ExceptionUtils.getStackTrace(e)); 
			
    		// Returns error
    		error = new Error("Unable to process request at this time.");
    		return gson.toJson(error);
		}
	}
}
