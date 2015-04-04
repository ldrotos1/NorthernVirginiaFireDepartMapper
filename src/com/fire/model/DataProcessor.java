package com.fire.model;

import java.sql.Connection;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

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
	 * This method queries each station in a database for the number
	 * of apparatus assign to that station. The query can be for the
	 * count of all apparatus at each station or all apparatus of a
	 * particular type. The method will return a set of station/count
	 * pairs in a JSON format. All parameters must not be NULL.
	 * 
	 * @param unitType The unit type to be queried or 'All' if 
	 * all types are to be queried
	 * @param dbConn The database connection
	 * @return The result of the query in JSON format 
	 */
	public String unitQuery(String unitType, Connection dbConn) {
		
		Map<String, Integer> queryResult;
		
		try {
			
			// Runs the appropriate query type on the database
			if (unitType.equals("All Types")) {
				queryResult = datastore.getUnitCountByStation(dbConn);
			}
			else {
				queryResult = datastore.getUnitCountByStation(unitType, dbConn);
			}
			
			// Converts the query result to JSON and returns
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
