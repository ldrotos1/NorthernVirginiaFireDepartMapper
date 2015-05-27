package com.fire.model.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.postgis.PGgeometry;
import org.postgis.Point;

import com.fire.model.dispatcher.RespondingStation;
import com.fire.model.entities.*;

/**
 * This class provides methods used to make database queries. All methods require a
 * database connection object be passed as a parameter. All methods are thread safe
 * on the database connection.  
 * 
 * @author Louis Drotos - 27 January 2015
 *
 */
public class DatastoreAccess {

	/**
	 * Returns a set containing an entity station object for each station in the database.
	 * 
	 * @param imageDirectory The directory containing the images of the stations.
	 * @param conn The database connection.
	 * @return The set of stations.
	 * @throws SQLException
	 */
	public Set<FullStation> getAllStations(String imageDirectory, Connection conn) throws SQLException {

		// Declares objects
		Set<FullStation> stations;
		FullStation station;
		ResultSet results;
		String sql;
		PGgeometry geom;
		
		// Runs the query
		sql = "SELECT station_id, station_name, station_number, geom, department, address, city, state, zipcode, phone_number, fax_number FROM station";
		results = queryDatabase(conn, sql);
		
		// Adds the query results to the set
		stations = new HashSet<FullStation>();
		while (results.next() == true) {
			
			// Builds the station info object
			station = new FullStation();
			geom = (PGgeometry)results.getObject("geom");
			station.setLocation((Point)geom.getGeometry());
			station.setStationId(results.getString("station_id").trim());
			station.setStationNumber(results.getString("station_number").trim());
			station.setStationName(results.getString("station_name").trim());
			station.setDepartment(results.getString("department").trim());
			station.setAddress(results.getString("address"));
			station.setCity(results.getString("city"));
			station.setState(results.getString("state"));
			station.setZipCode(results.getString("zipcode"));
			station.setPhoneNumber(results.getString("phone_number"));
			station.setFaxNumber(results.getString("fax_number"));
			station.setImageUrl(imageDirectory + station.getStationId());
			
			// Adds the station info object to the set
			stations.add(station);
		}
		
		// Returns the set
		return stations;
	}
	
	/**
	 * Returns a set of all unique unit types found in the database.
	 * 
	 * @param conn The database connection
	 * @return The set of all units  
	 * @throws SQLException
	 */
	public List<String> getAllUnitTypes(Connection conn) throws SQLException {
		
		// Declares objects
		List<String> unitTypes;
		ResultSet results;
		String sql;
		
		// Runs the query
		sql = "SELECT DISTINCT unit_type FROM apparatus";
		results = queryDatabase(conn, sql);
		
		// Adds the query results to the set
		unitTypes = new LinkedList<String>();
		while (results.next() == true) {
			unitTypes.add(results.getString("unit_type"));
		}
		
		// Sort and returns the list
		Collections.sort(unitTypes);
		return unitTypes;
	}
	
	/**
	 * Returns a set of all unique department names found in the database.
	 * 
	 * @param conn The database connection
	 * @return The set of all department names
	 * @throws SQLException
	 */
	public List<String> getAllDepartmentNames(Connection conn) throws SQLException {
		
		// Declares objects
		List<String> departments;
		ResultSet results;
		String sql;
				
		// Runs the query
		sql = "SELECT DISTINCT department FROM station";
		results = queryDatabase(conn, sql);
				
		// Adds the query results to the set
		departments = new LinkedList<String>();
		while (results.next() == true) {
			departments.add(results.getString("department"));
		}
				
		// Sorts and returns the list
		Collections.sort(departments);
		return departments;
	}
	
	/**
	 * Returns a set of all unique station names found in the database
	 * 
	 * @param conn The database connection
	 * @return The set of all station names
	 * @throws SQLException
	 */
	public List<String> getAllStationNames(Connection conn) throws SQLException {
	
		// Declares objects
		List<String> stationNames;
		ResultSet results;
		String sql;
						
		// Runs the query
		sql = "SELECT DISTINCT station_name FROM station";
		results = queryDatabase(conn, sql);
						
		// Adds the query results to the set
		stationNames = new LinkedList<String>();
		while (results.next() == true) {
			stationNames.add(results.getString("station_name"));
		}
						
		// Sorts and returns the list
		Collections.sort(stationNames);
		return stationNames;	
	}

	/**
	 * Queries the database for the set of station IDs of stations that have 
	 * at least one unit assigned to it of a specified unit type. Parameters must 
	 * not be NULL.      
	 * 
	 * @param unitType The unit type.
	 * @param conn The database connection.
	 * @return The set of stations IDs.
	 * @throws SQLException 
	 */
	public Set<String> getStations(String unitType, Connection conn) throws SQLException {
		
		// Declares objects
		Set<String> stations;
		ResultSet results;
		StringBuilder sql;
										
		// Runs the query
		sql = new StringBuilder();
		sql.append("SELECT DISTINCT station_id FROM apparatus ");
		sql.append("WHERE unit_type = \'" + unitType.trim() + "\'");
		results = queryDatabase(conn, sql.toString());
										
		// Adds the query results to the set
		stations = new HashSet<String>();
		while (results.next() == true) {
			stations.add(results.getString("station_id"));
		}
										
		// Returns the list
		return stations;
	}
	
	/**
	 * This method returns a list of apparatus assigned to a particular station
	 * 
	 * @param stationId The station ID of the station.
	 * @param conn The database connection.
	 * @return The list of apparatus assigned to the station.
	 * @throws SQLException 
	 */
	public List<Apparatus> getApparatus(String stationId, Connection conn) throws SQLException {
		
		// Declare objects
		Apparatus unit;
		List<Apparatus> units;
		ResultSet results;
		String sql;
		
		// Runs the query
		sql = "SELECT unit_designator, unit_type FROM apparatus WHERE station_id = \'" + stationId + "\'";
		results = queryDatabase(conn, sql);
		
		// Cycles through the results and adds the apparatuses to the list
		units = new ArrayList<Apparatus>();
		while (results.next() == true) {
			
			unit = new Apparatus();
			unit.setStationId(stationId);
			unit.setUnitDesignator(results.getString("unit_designator"));
			unit.setUnitType(results.getString("unit_type"));
			units.add(unit);
		}
		
		// Returns the list
		return units;
	}
	
	/**
	 * This method returns a ordered list of all stations. The stations
	 * are listed in ascending order based on their straight line distance 
	 * to a location defined by the toLocation parameter
	 * 
	 * @param conn The database connection.
	 * @param toLocation A location represented as a point geometry
	 * @return The ordered queue of stations.
	 * @throws SQLException 
	 */
	public List<BasicStation> getStationsByDistance(Connection conn, Point toLocation) throws SQLException {
		
		// Declares objects
		StringBuilder sql;
		ResultSet results;
		String stationId;
		String stationName;
		String stationNumber;
		String stationDept;
		BasicStation station;
		PGgeometry geom;
		List<BasicStation> stationList;
		
		// Builds the SQL statement
		sql = new StringBuilder();
		sql.append("SELECT station_id, station_name, station_number, department, geom ");
		sql.append("FROM station ORDER BY ST_Distance(geom::geography, ST_GeomFromText('POINT(");
		sql.append(toLocation.x);
		sql.append(" ");
		sql.append(toLocation.y);
		sql.append(")',4326)::geography)");
		
		// Executes the query
		results = queryDatabase(conn, sql.toString());
		
		// Process result and return list
		stationList = new LinkedList<BasicStation>();
		while (results.next() == true) {
			
			stationId = results.getString("station_id");
			stationName = results.getString("station_name");
			stationNumber = results.getString("station_number");
			stationDept = results.getString("department");
			geom = (PGgeometry)results.getObject("geom");
			
			station = new BasicStation();
			station.setStationId(stationId);
			station.setStationName(stationName);
			station.setStationNumber(stationNumber);
			station.setDepartment(stationDept);
			station.setLocation((Point)geom.getGeometry());
			stationList.add(station);
		}
		return stationList;
	}
	
	/**
	 * This method returns a map collection that holds information about the number
	 * of apparatus by type which are assigned to a set of stations. Keys represent
	 * the apparatus type and values represent the number of apparatus of that type.
	 * The keys are suppression, aerial, rescue, medic and command
	 * 
	 * @param conn The database connection
	 * @param stations The set of station IDs
	 * @return The map collection
	 * @throws SQLException
	 */
	public Map<String,Integer> getUnitBreakdown(Connection conn, Set<String> stations) throws SQLException {
	
		// Declare objects
		StringBuilder sql;
		ResultSet results;
		Map<String, Integer> unitBreakout;
		int suppressionCount;
		int aerialCount;
		int rescueCount;
		int medicCount;
		int commandCount;
		
		// Builds the SQL statement
		sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("SUM(CASE WHEN u.unit_type = 'Engine' or u.unit_type = 'Tanker' THEN u.count ELSE 0 END) suppression, ");
		sql.append("SUM(CASE WHEN u.unit_type = 'Truck' or u.unit_type = 'Tower' THEN u.count ELSE 0 END) aerial, ");
		sql.append("SUM(CASE WHEN u.unit_type = 'Rescue' or u.unit_type = 'Technical Rescue' THEN u.count ELSE 0 END) rescue, ");
		sql.append("SUM(CASE WHEN u.unit_type = 'Medic' THEN u.count ELSE 0 END) medic, ");
		sql.append("SUM(CASE WHEN u.unit_type = 'Battalion Chief' THEN u.count ELSE 0 END) command ");
		sql.append("FROM ( SELECT unit_type, COUNT(*) count FROM apparatus ");
		sql.append("WHERE ");
					
		for(String id : stations) {
			sql.append("station_id = '");
			sql.append(id);
			sql.append("' or ");
		}
		
		sql.delete(sql.lastIndexOf("or"), sql.length() - 1);
		sql.append(" GROUP BY unit_type) u");
		
		// Executes the query
		results = queryDatabase(conn, sql.toString());
		
		// Process result and return the map
		unitBreakout = new HashMap<String,Integer>();
		if(results.next() != false) {
			
			// Get the unit break-out counts
			suppressionCount = results.getInt("suppression");
			aerialCount = results.getInt("aerial");
			rescueCount = results.getInt("rescue");
			medicCount = results.getInt("medic");
			commandCount = results.getInt("command");
			
			// Add the counts to the map
			unitBreakout.put("suppression", suppressionCount);
			unitBreakout.put("aerial", aerialCount);
			unitBreakout.put("rescue", rescueCount);
			unitBreakout.put("medic", medicCount);
			unitBreakout.put("command", commandCount);
		}
		
		return unitBreakout;
	}
	
	/**
	 * This method returns a map collection consisting of keys consisting of station 
	 * IDs and values that consist of he list of apparatus assigned to the station.
	 * The apparatus contained in the lists don't include special units. 
	 *  
	 * @param conn The database connection
	 * @param stations The list of station IDs
	 * @return The map collection
	 * @throws SQLException
	 */
	public Map<String,List<Apparatus>> getRespondingUnits(Connection conn, List<RespondingStation> stations) throws SQLException {
	
		// Declare objects
		StringBuilder sql;
		String stationId;
		String unitDsg;
		String unitType;
		Apparatus unit;
		List<Apparatus> unitList;
		ResultSet results;
		Map<String,List<Apparatus>> unitMap;
		
		// Builds the SQL statement
		sql = new StringBuilder();
		sql.append("SELECT a2.unit_designator, a2.station_id, a2.unit_type ");
		sql.append("FROM Apparatus a1 JOIN ( SELECT * FROM Apparatus WHERE");
		
		// Adds the station IDs to the SQL
		for(RespondingStation station : stations) {
			sql.append(" station_id = '");
			sql.append(station.getStationId());
			sql.append("' or");
		}
		sql.delete(sql.lastIndexOf("or"), sql.length());
		
		// Adds the remaining SQL
		sql.append(") a2 ON a1.unit_designator = a2.unit_designator ");
		sql.append("WHERE a2.unit_type = 'Engine' or a2.unit_type = 'Tanker' or a2.unit_type = 'Truck' or ");
		sql.append("a2.unit_type = 'Tower' or a2.unit_type = 'Medic' or a2.unit_type = 'Rescue' or ");
		sql.append(" a2.unit_type = 'Technical Rescue' or a2.unit_type = 'Battalion Chief'");
		
		// Runs the query
		results = queryDatabase(conn, sql.toString());
		
		// Builds the map from the results
		unitMap = new HashMap<String,List<Apparatus>>();
		
		while(results.next() != false) { 
			
			// Extracts the unit info
			stationId = results.getString("station_id");
			unitDsg = results.getString("unit_designator");
			unitType = results.getString("unit_type");
			
			// Builds the Apparatus object
			unit = new Apparatus();
			unit.setStationId(stationId);
			unit.setUnitDesignator(unitDsg);
			unit.setUnitType(unitType);
			
			// Adds the unit to the map
			if (unitMap.containsKey(stationId)) {
				unitMap.get(stationId).add(unit);
			}
			else {
				unitList = new LinkedList<Apparatus>();
				unitList.add(unit);
				unitMap.put(stationId, unitList);
			}
		}
		return unitMap;
	}
	
	/**
	 * This method returns a boolean indicating whether or not a point is located
	 * within the borders polygon
	 *  
	 * @param conn The database connection
	 * @param location The location to be tested
	 * @return True if the location is within the border polygon, otherwise false
	 * @throws SQLException
	 */
	public boolean validateLocation(Connection conn, Point location) throws SQLException {
		
		StringBuilder sql;
		ResultSet results;
		String result;
		
		// Builds the SQL
		sql = new StringBuilder();
		sql.append("SELECT ST_Contains((SELECT geom FROM border), ");
		sql.append("ST_GeomFromText('POINT(");
		sql.append(location.x);
		sql.append(" ");
		sql.append(location.y);
		sql.append(")',4326)) AS result");
		
		// Runs the query and process result
		results = queryDatabase(conn, sql.toString());
		results.next();
		if (results != null) {
			
			result = results.getString("result");
			if (result.equals("t")) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method executes a SQL query against the database. 
	 * 
	 * @param conn The connection to the database
	 * @param sql The SQL query to execute
	 * @return The result set from the query  
	 * @throws SQLException
	 */
	private synchronized ResultSet queryDatabase(Connection conn, String sql) throws SQLException {
		
		// Declares objects
		PreparedStatement statement;
		ResultSet results;
		
		// Carries out query on the database
		statement = conn.prepareStatement(sql);
		results = statement.executeQuery();
		
		// Returns results
		return results;
	}
}
