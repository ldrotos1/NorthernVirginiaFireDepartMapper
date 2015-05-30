package com.fire.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fire.model.data_processor.DataProcessor;

@WebServlet(asyncSupported = true, urlPatterns = { "/data" })
public class DataRequestServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Declares objects
		DataProcessor processor;
		PrintWriter responseWriter;
		String requestType;
		String unitType;
		String stationId;
		String alarms;
		String latitude;
		String longitude;
		String apiKey;
		String json = "";
		Connection dbConn;
		Boolean badRequest = false;
		
		// Determines the request type
		requestType = request.getParameter("RequestFor");
		switch(requestType) {
			
			// Request for a list of all station names 
			case "AllStationNames":
				synchronized (getServletContext()) {
					json = (String)getServletContext().getAttribute("stationNames");
				}
				break;
				
			// Request for all basic station info
			case "AllStations":
				synchronized (getServletContext()) {
					json = (String)getServletContext().getAttribute("stations");
				}
				break;
			
			// Request for units assigned to a station
			case "AssignedUnits":
				
				// Gets parameter info and database connection
				stationId = request.getParameter("stationId");
				dbConn = (Connection)getServletContext().getAttribute("database");
				
				if (stationId != null) {
					processor = new DataProcessor();
					json = processor.assignedUnitQuery(stationId, dbConn);
				}
				else {
					badRequest = true;
				}
				break;
				
			// Request for query by unit type 
			case "QueryUnitType":
				
				// Gets parameter info and database connection
				unitType = request.getParameter("type"); 
				dbConn = (Connection)getServletContext().getAttribute("database"); 
				
				if (unitType != null) {
					processor = new DataProcessor();
					json = processor.unitQuery(unitType, dbConn);
				}
				else {
					badRequest = true;
				}
				break;
				
			case "IncidentResponse":
				
				// Gets parameter info and database connection
				alarms = request.getParameter("alarms");
				latitude = request.getParameter("latitude");
				longitude = request.getParameter("longitude");
				dbConn = (Connection)getServletContext().getAttribute("database");
				apiKey = (String)getServletContext().getAttribute("api_key");
				
				if (alarms != null && latitude != null && longitude != null) {
					processor = new DataProcessor();
					json = processor.createIncidentResponse(alarms, latitude, longitude, apiKey, dbConn);				
				}
				else {
					badRequest = true;
				}
				break;
				
			// Bad request
			default:
					badRequest = true;
				break;
		}
		
		if (badRequest ==  false) {
			
			// Writes the JSON to the response object
			response.setContentType("application/json");
			responseWriter = response.getWriter();
			responseWriter.write(json);
			responseWriter.flush(); 
		}
		else {
			response.sendError(400, "Invalid request type");
		}
	}
}
