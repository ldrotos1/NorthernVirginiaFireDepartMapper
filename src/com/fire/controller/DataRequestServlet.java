package com.fire.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(asyncSupported = true, urlPatterns = { "/data" })
public class DataRequestServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Declares objects
		PrintWriter responseWriter;
		String requestType;
		String json = "";
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
