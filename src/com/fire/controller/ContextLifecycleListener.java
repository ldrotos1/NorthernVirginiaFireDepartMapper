package com.fire.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.fire.model.beans.FullStation;
import com.fire.model.database.DatastoreAccess;
import com.google.gson.Gson;

/**
 * This class provides methods that setup the application on startup
 * and shutdown of the web server.
 * 
 * @author Louis Drotos - 2 February 2015
 *
 */
@WebListener
public class ContextLifecycleListener implements ServletContextListener {

	/**
	 * This method disconnects the application from the database 
	 * when the web server is shutdown. 
	 */
	public void contextDestroyed(ServletContextEvent evt)  { 
    	
    	// Declares objects
    	Connection connection = null;
    	ServletContext context;
    	Object value;
    	Logger logger;
    	
    	try {
    		
    		// Gets the database connection.
    		context = evt.getServletContext();
        	value = context.getAttribute("database");
        	
        	// If there is a connection to the database then it is disconnected.
        	if (value != null) {
        		connection = (Connection)value;
        		connection.close();
        	}
    	}
    	catch(Exception e) {
    		
    		// Logs the exception 
    		logger = Logger.getLogger("fire_app");
    		logger.error("Error on servlet shutdown\n" + e.getMessage() + "\n" + ExceptionUtils.getStackTrace(e));
    	}
    }

	/**
	 * This method establishes the database connection and sets the 
	 * values of the context parameters when the web server is started.  
	 */
    public void contextInitialized(ServletContextEvent evt)  { 
    	
    	// Declares objects
    	Connection connection = null;
    	Set<FullStation> stations;
    	List<String> stationNames; 
    	List<String> departments;
    	List<String> unitTypes;
    	ServletContext context;
    	String imageDir;
    	Gson gson;
    	Logger logger;
    	DatastoreAccess datastore;
    	String username;
    	String password;
    	String host;
    	String port;
    	String databaseType;
    	String database;
    	String connectionInfo;
    			
    	try {
    		context = evt.getServletContext();
    				
    		// Gets the connection information needed to connect to the database.
    		database = context.getInitParameter("database_name");
    		databaseType = context.getInitParameter("database_type");
    		host = context.getInitParameter("database_host");
    		port =  context.getInitParameter("database_port");
    		username =  context.getInitParameter("database_username");
    		password = context.getInitParameter("database_password");
    		connectionInfo = "jdbc:" + databaseType + "://" + host + ":" + port + "/" + database;
    				
    		// Connects to the database and adds the connection to the context as a attribute.
    		Class.forName("org.postgresql.Driver");
    		connection = DriverManager.getConnection(connectionInfo, username, password);
    		context.setAttribute("database", connection);
    		
    		// Sets up the data store access object
    		datastore = new DatastoreAccess();
    		
    		// Gets the attributes lists
    		imageDir = context.getInitParameter("imageDirectory"); 
    		stations = datastore.getAllStations(imageDir, connection);
    		stationNames = datastore.getAllStationNames(connection);
    		departments = datastore.getAllDepartmentNames(connection);
    		unitTypes = datastore.getAllUnitTypes(connection);
    		
    		// Adds the attributes to the context
    		context.setAttribute("departments", departments);
    		context.setAttribute("unitTypes", unitTypes);
    		
    		// Converts collections to JSON and adds to the context 
    		gson = new Gson();
    		context.setAttribute("stations", gson.toJson(stations));
    		context.setAttribute("stationNames", gson.toJson(stationNames));
    	}
    	catch (Exception e) {
    				
    		// Logs the exception 
    		logger = Logger.getLogger("fire_app");
    		logger.error("Error on servlet setup\n" + e.getMessage() + "\n" + ExceptionUtils.getStackTrace(e));
    	}	
    }
}
