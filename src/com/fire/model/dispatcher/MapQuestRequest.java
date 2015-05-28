package com.fire.model.dispatcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.postgis.Point;

/**
 * This class is used to make web service calls to the MapQuest Directions API
 * @author Louis Drotos
 *
 */
public class MapQuestRequest {

	private final String baseURI;
	private final String routeURI;
	private final String apiKey;
	private final String shapeParm;
	private JSONObject responseJson;
	private JSONArray shapePointsJson;
	private List<Point> shapePointsList;
	
	/**
	 * Constructor
	 * @param apiKey The API key that will be used to make the requests
	 */
	protected MapQuestRequest(String apiKey) {
		
		this.baseURI = "http://open.mapquestapi.com/";
		this.routeURI = "directions/v2/route";
		this.apiKey = "?key=" + apiKey.trim();
		this.shapeParm = "&fullShape=true";
	}
	
	/**
	 * This method determines the travel time, travel distance and route between a
	 * station and a incident. The station dispatch response object provided as a 
	 * parameter is used to determine the locations of the station and the incident.
	 * The travel time, distance and route are added to the station response object.
	 * @param disResponse The station response object
	 * @return True if the request was successful, otherwise false
	 */
	protected boolean makeTravelTimeRequest(RespondingStation disResponse) {
		
		StringBuilder uri;
		String uriTo;
		String uriFrom;
		String response;
		Point stationLoc;
		Point incidentLoc;
		double y;
		double x;
		
		try
		{
			// Builds the to and from parameters 
			stationLoc = disResponse.getLocation();
			incidentLoc = disResponse.getIncidentLocation();
			uriFrom = "&from=" + stationLoc.y + "," + stationLoc.x;
			uriTo = "&to=" + incidentLoc.y + "," + incidentLoc.x; 
			
			// Builds the URI
			uri = new StringBuilder();
			uri.append(baseURI);
			uri.append(routeURI);
			uri.append(apiKey);
			uri.append(uriFrom);
			uri.append(uriTo);
			uri.append(shapeParm);
			
			// Makes the request
			response = makeRequest(uri.toString());
			if (response != null) {
				
				// Process the JSON response
				responseJson = new JSONObject(response).getJSONObject("route");
				disResponse.setTravelTimeSec(responseJson.getInt("time"));
				disResponse.setTravelDistMiles(responseJson.getDouble("distance"));
				
				// Creates the list of coordinates representing the route shape
				shapePointsJson = responseJson.getJSONObject("shape").getJSONArray("shapePoints"); 
				shapePointsList = new LinkedList<Point>();
				for(int index = 0; index < shapePointsJson.length() - 1; index+=2 ) {
					
					// Gets the next coordinate and adds it to the list
					y = shapePointsJson.getDouble(index);
					x = shapePointsJson.getDouble(index + 1);
					shapePointsList.add(new Point(x, y));
				}
				disResponse.setShapePoints(shapePointsList);
				
				return true;
			}
			return false;
		}
		catch(Exception err) {
			return false;
		}	
	}
	
	/**
	 * This method executes a web service request. This method will return
	 * null if the request fails.
	 * @param requestUri The URI to the web service
	 * @return The text of the request response
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private String makeRequest(String requestUri) throws URISyntaxException, ClientProtocolException, IOException {
		
		URI uri;
		CloseableHttpClient httpClient;
		CloseableHttpResponse httpResponse;
		HttpGet httpRequest;
		StatusLine httpStatusInfo;
		HttpEntity responseEntity;
		ContentType contentType;
		Charset charset;
		Reader reader;
		BufferedReader bReader;
		String line;
		StringBuilder responseString;
		
		// Makes the request
		uri = new URI(requestUri);
		httpRequest = new HttpGet(uri);
		httpClient = HttpClients.createDefault();
		httpResponse = httpClient.execute(httpRequest);
		
		// Process response
		httpStatusInfo = httpResponse.getStatusLine();
		if (httpStatusInfo.getStatusCode() == 200) {
			
			// Sets up the content reader
			responseEntity = httpResponse.getEntity();
			contentType = ContentType.getOrDefault(responseEntity);
			charset = contentType.getCharset();
			reader = new InputStreamReader(responseEntity.getContent(), charset);
	        bReader = new BufferedReader(reader);
	        
	        // Creates a single response string and returns it
	        responseString = new StringBuilder();
	        line = bReader.readLine();
	        
	        while (line != null) {
	        	responseString.append(line);
	        	line = bReader.readLine();
	        }   
	        return responseString.toString();
		}	
		return null;
	}
}






