package com.fire.model.data_processor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.xml.bind.ValidationException;

import com.fire.model.database.DatastoreAccess;

/**
 * This class is used for validating request parameters. 
 * 
 * @author Louis Drotos
 *
 */
public class Validator {
	
	private DatastoreAccess dbAccess;
	
	/**
	 * Constructor
	 */
	public Validator() {
		this.dbAccess = new DatastoreAccess();
	}
	
	/**
	 * Determines if a unit type is valid by checking to see if
	 * that unit type is found within the database.
	 * @param type The unit type to be tested
	 * @param dbConn The database connection
	 * @return True if valid, otherwise false
	 * @throws ValidationException 
	 * @throws SQLException 
	 */
	public boolean ValidateUnitType(String type, Connection dbConn) throws ValidationException {
		
		List<String> validTypes;
		
		try {
			validTypes = dbAccess.getAllUnitTypes(dbConn);
			
			if (validTypes.contains(type)) {
				return true;
			}
			return false;
		}
		catch (SQLException e) {
			throw new ValidationException(e.getMessage());	
		}
	}
	
	/**
	 * Determines if a station ID is valid by checking to see if
	 * that station ID belongs to a station in the database
	 * @param stationId The station ID to be tested
	 * @param dbConn The database connection
	 * @return True if valid, otherwise false
	 * @throws ValidationException 
	 */
	public boolean ValidateStationId(String stationId, Connection dbConn) throws ValidationException {
	
		List<String> validIds;
		
		try {
			validIds = dbAccess.getAllUnitTypes(dbConn);
			
			if (validIds.contains(stationId)) {
				return true;
			}
			return false;
		}
		catch (SQLException e) {
			throw new ValidationException(e.getMessage());	
		}
	}
	
	/**
	 * Determines if an alarm number is an integer within the
	 * valid range (1 - 6)
	 * @param alarmNum The alarm number
	 * @return True if valid, otherwise false
	 */
	public boolean ValidateAlarmNumber(String alarmNum) {
	
		try {
			int number = Integer.parseInt(alarmNum);
			
			if (number > 0 && number <= 6) {
				return true;
			}
			return false;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * Determines if a set of coordinates valid. Coordinates are
	 * considered valid if they can be parsed as doubles.
	 * @param lat The latitude coordinate
	 * @param lon The longitude coordinate
	 * @return True if valid, otherwise false 
	 */
	public boolean ValidateCoordinate(String lat, String lon) {
		
		try {
			
			 Double.parseDouble(lat);
			 Double.parseDouble(lon);
			 return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}
}
