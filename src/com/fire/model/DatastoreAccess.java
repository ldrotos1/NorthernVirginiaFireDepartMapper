package com.fire.model;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.postgis.PGgeometry;
import org.postgis.Point;

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
	 * Returns a set containing the basic station information for all stations
	 * 
	 * @param conn The database connection
	 * @return	   The set of basic station info
	 * @throws SQLException
	 */
	public Set<BasicStationInfo> getAllBasicStationInfo(Connection conn) throws SQLException {
		
		// Declares objects
		Set<BasicStationInfo> stationInfo;
		BasicStationInfo info;
		ResultSet results;
		String sql;
		PGgeometry geom;
				
		// Runs the query
		sql = "SELECT station_id, station_name, station_number, geom FROM station";
		results = queryDatabase(conn, sql);
				
		// Adds the query results to the set
		stationInfo = new HashSet<BasicStationInfo>();
		while (results.next() == true) {
			
			// Builds the station info object
			info = new BasicStationInfo();
			geom = (PGgeometry)results.getObject("geom");
			info.setLocation((Point)geom.getGeometry());
			info.setStationId(results.getString("station_id"));
			info.setStationNumber(results.getString("station_number"));
			info.setStationName(results.getString("station_name"));
			
			// Adds the station info object to the set
			stationInfo.add(info);
		}
				
		// Returns the list
		return stationInfo;
	}
	
	/**
	 * Returns a set of all unique unit types found in the database.
	 * 
	 * @param conn The database connection
	 * @return     The set of all units  
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
	 * @return     The set of all department names
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
	 * @return     The set of all station names
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
	 * Gets from the database a set of station IDs associated with a department. If
	 * there are no stations associated with the department then the set will be empty 
	 * 
	 * @param department The department 
	 * @param conn The database connection
	 * @return		The set of station IDs
	 * @throws SQLException 
	 */
	public Set<String> getStationIds(String department, Connection conn) throws SQLException {

		// Declares objects
		Set<String> stationIds;
		ResultSet results;
		String sql;
								
		// Runs the query
		sql = "SELECT station_id FROM station WHERE department = \'" + department.trim() + "\'";
		results = queryDatabase(conn, sql);
								
		// Adds the query results to the set
		stationIds = new HashSet<String>();
		while (results.next() == true) {
			stationIds.add(results.getString("station_id"));
		}
								
		// Returns the list
		return stationIds;
	}
	
	/**
	 * Queries from the database the full set of station apparatus information for a station
	 * based on a station ID. If there is no information for the station ID then this method
	 * will return null.  
	 * 
	 * @param stationID The ID of a station
	 * @param conn The database connection
	 * @return		The station and apparatus information
	 * @throws SQLException 
	 */
	public FullStationInfo getFullStationInfo(String stationId, Path imageDirectory,  Connection conn) throws SQLException {
		
		// Declares objects
		FullStationInfo stationInfo = null;
		Apparatus unit;
		List<Apparatus> units;
		ResultSet results;
		StringBuilder sql;
		PGgeometry geom;
										
		// Runs the query
		sql = new StringBuilder();
		sql.append("SELECT s.geom, s.station_name, s.station_number, s.department, ");
		sql.append("s.address, s.city, s.state, s.zipcode, a.unit_type, a.unit_designator ");
		sql.append("FROM station s INNER JOIN apparatus a ");
		sql.append("ON s.station_id = a.station_id ");
		sql.append("WHERE a.station_id = \'" + stationId + "\'");
		results = queryDatabase(conn, sql.toString());
			
		// Adds the station info to the object
		results.next();
		if (results.isFirst() == true)
		{
			stationInfo = new FullStationInfo();
			geom = (PGgeometry)results.getObject("geom"); 
			stationInfo.setLocation((Point)geom.getGeometry());
			stationInfo.setStationId(stationId);
			stationInfo.setStationNumber(results.getString("station_number"));
			stationInfo.setStationName(results.getString("station_name"));
			stationInfo.setDepartment(results.getString("department"));
			stationInfo.setAddress(results.getString("address"));
			stationInfo.setCity(results.getString("city"));
			stationInfo.setState(results.getString("state"));
			stationInfo.setZipCode(results.getString("zipcode"));
			stationInfo.setImageUrl(imageDirectory.resolve(stationId));
			
			// Adds the units to the station info object
			units = new LinkedList<Apparatus>();
			do {
				unit = new Apparatus();
				unit.setStationId(stationId);
				unit.setUnitDesignator(results.getString("unit_designator"));
				unit.setUnitType(results.getString("unit_type"));
				units.add(unit);
				
			} while (results.next() == true);
			stationInfo.setUnits(units);
		}
		
		// Returns the list
		return stationInfo;
	}
	
	/**
	 * Queries the database for the number of a particular apparatus type assigned to each 
	 * station. The method returns a map collection where the key represents a station ID 
	 * and the value represents the number of apparatus of the target type assigned to that
	 * station. If a station has no apparatus of the target type then that station will not
	 * be included in the map. If there are no apparatus of the target type assigned to any
	 * stations then the returned set will be empty.      
	 * 
	 * @param unitType The target apparatus type
	 * @param conn The database connection
	 * @return		The map collection
	 * @throws SQLException 
	 */
	public Map<String, String> getUnitCountByStation(String unitType, Connection conn) throws SQLException {
		
		// Declares objects
		Map<String, String> unitCount;
		ResultSet results;
		StringBuilder sql;
										
		// Runs the query
		sql = new StringBuilder();
		sql.append("SELECT station_id, COUNT(*) count FROM apparatus ");
		sql.append("WHERE unit_type = \'" + unitType.trim() + "\' GROUP BY station_id");
		results = queryDatabase(conn, sql.toString());
										
		// Adds the query results to the set
		unitCount = new HashMap<String, String>();
		while (results.next() == true) {
			unitCount.put(results.getString("station_id"), results.getString("count"));
		}
										
		// Returns the list
		return unitCount;
	}
	
	/**
	 * This method executes a SQL query against the database. 
	 * 
	 * @param conn The connection to the database
	 * @param sql The SQL query to execute
	 * @return    The result set from the query  
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
