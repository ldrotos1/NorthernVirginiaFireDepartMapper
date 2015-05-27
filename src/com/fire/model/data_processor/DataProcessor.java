package com.fire.model.data_processor;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.fire.model.database.DatastoreAccess;
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
	
	/**
	 * Constructor
	 */
	public DataProcessor() {	
		this.gson = new Gson();
		this.datastore = new DatastoreAccess();
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
		
		try {
			
			// Queries the database and converts result into JSON
			queryResult = datastore.getApparatus(station, dbConn);
			Collections.sort(queryResult);
			return gson.toJson(queryResult);
		}
		catch (Exception e) {
			
			// Logs the error
			logger = Logger.getLogger("fire_app");
    		logger.error("Error on servlet setup\n" + e.getMessage() + "\n" + ExceptionUtils.getStackTrace(e)); 
			
    		// Returns error
    		return gson.toJson("Error");
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
		
		Set<String> queryResult;
		
		try {
			
			// Queries the database and converts result into JSON
			queryResult = datastore.getStations(unitType, dbConn);
			return gson.toJson(queryResult);
		}
		catch (Exception e) {
			
			// Logs the error
			logger = Logger.getLogger("fire_app");
    		logger.error("Error on servlet setup\n" + e.getMessage() + "\n" + ExceptionUtils.getStackTrace(e)); 
			
    		// Returns error
    		return gson.toJson("Error");
		}
	}
}
