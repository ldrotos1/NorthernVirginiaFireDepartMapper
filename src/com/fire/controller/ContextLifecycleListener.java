package com.fire.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.fire.model.BasicStationInfo;
import com.fire.model.DatastoreAccess;

@WebListener
public class ContextLifecycleListener implements ServletContextListener {

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

    public void contextInitialized(ServletContextEvent evt)  { 
    	
    	// Declares objects
    	Connection connection = null;
    	Set<BasicStationInfo> stations;
    	Set<String> stationNames; 
    	Set<String> departments;
    	Set<String> unitTypes;
    	ServletContext context;
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
    		stations = datastore.getAllBasicStationInfo(connection);
    		stationNames = datastore.getAllStationNames(connection);
    		departments = datastore.getAllDepartmentNames(connection);
    		unitTypes = datastore.getAllUnitTypes(connection);
    		
    		// Adds the attributes to the context
    		context.setAttribute("stations", stations);
    		context.setAttribute("stationNames", stationNames);
    		context.setAttribute("departments", departments);
    		context.setAttribute("unitTypes", unitTypes);
    		
    	}
    	catch (Exception e) {
    				
    		// Logs the exception 
    		logger = Logger.getLogger("fire_app");
    		logger.error("Error on servlet setup\n" + e.getMessage() + "\n" + ExceptionUtils.getStackTrace(e));
    	}	
    }
}
