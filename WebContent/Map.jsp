<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="http://cdn.leafletjs.com/leaflet-0.7.3/leaflet.css" />
		<link rel="stylesheet" type="text/css" href="css/leaflet.label.css">
		<link rel="stylesheet" type="text/css" href="css/layout.css">
		<link rel="stylesheet" type="text/css" href="css/jquery-ui.css">
		<link rel="stylesheet" type="text/css" href="css/jquery-ui.structure.css">
		<link rel="stylesheet" type="text/css" href="css/jquery-ui.theme.css">
		<link rel="stylesheet" type="text/css" href="css/incident-pane.css">
		<link rel="stylesheet" type="text/css" href="css/queryPane.css">
		<link rel="stylesheet" type="text/css" href="css/controlPane.css">
		<link rel="stylesheet" type="text/css" href="css/paneDesign.css">
		<link rel="stylesheet" type="text/css" href="css/station-label.css">
		<link rel="stylesheet" type="text/css" href="css/station-info-dialog.css">
		<link rel="stylesheet" type="text/css" href="css/response-table.css">
		<link rel="stylesheet" type="text/css" href="css/about-dialog.css">
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
		<script type="text/javascript" src="javascript/jquery-ui.min.js"></script>
		<script src="http://cdn.leafletjs.com/leaflet-0.7.3/leaflet.js"></script>
		<script src="javascript/station-info-dialog.js" type="text/javascript"></script>
		<script src="javascript/leaflet.label.js" type="text/javascript"></script>
		<script src="javascript/globalVars.js" type="text/javascript"></script>
		<script src="javascript/station.js" type="text/javascript"></script>
		<script src="javascript/route.js" type="text/javascript"></script>
		<script src="javascript/map.js" type="text/javascript"></script>
		<script src="javascript/query-pane.js" type="text/javascript"></script>
		<script src="javascript/query.js" type="text/javascript"></script>
		<script src="javascript/incident-pane.js" type="text/javascript"></script>
		<script src="javascript/control-pane.js" type="text/javascript"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Northern Virginia Fire Department Mapper</title>
	</head>

	<body>
		<header class="ui-widget-header">
			<h1 id="title">Northern Virginia Fire Department Mapper</h1>
		</header>
		
		<div id='wrapper'>
			<div id='map'></div>
			
			<!-- The query pane -->
			<div id='queryPane' class='pane'>
				<label id='queryLabel' class='paneMainLabel'>Station Query</label>
					
				<div id='queryControls'>
					
					<!-- Query by unit type -->	
					<div id='unitDiv'>
						<div class='labelDiv'>
							<label class='query-sub-label'>Unit Type:</label><br>
						</div>	
						<select id="unitCombo">
							<option selected="selected">None Selected</option>
							<c:forEach items="${applicationScope.unitTypes}" var="dept" >
								<option>${dept}</option>
							</c:forEach>
						</select>
					</div>
						
					<!-- Query by department -->	
					<div id='deptDiv'>
						<div class='labelDiv'>
							<label class='query-sub-label'>Department:</label>
						</div>	
						<select id="deptCombo">
							<option selected="selected">None Selected</option>
							<c:forEach items="${applicationScope.departments}" var="dept" >
								<option>${dept}</option>
							</c:forEach>
						</select>
					</div>
					
					<!-- Query by station name -->		
					<div id='stationDiv'>
						<div class='labelDiv'>
							<label class='query-sub-label'>Station Name:</label>
						</div>
						<input id="stationInput">
					</div>
						
					<div id='searchBtnDiv' class="ui-widget">
						<input id="searchBtn" type="submit" value="Search">
					</div>
				</div>
			</div>
			
			<!-- Incident pane - starting state -->	
			<div id='incident-pane-1' class='pane'>
				<label id='incident-label' class='paneMainLabel'>Incident Response</label>
				
				<div id='selection-div'>
					<div id='alarm-div'>
						<label id="alarm-label" class='incident-sub-label' for="alarm-count">Number of Alarms :</label>
  						<input id="alarm-count" name="alarms">
					</div>
					<div id='fire-loc-div'>
						<label id='fire-loc-label' class='incident-sub-label' for='btn-fire'>Fire Location :</label>
						<button id='btn-fire'></button>
					</div>
					<div>
						<button id='btn-response'>Simulate Response</button>
					</div>
					<p id="processing">Processing . . .</p>
				</div>
			</div>
			
			<!-- Incident pane with response table -->	
			<div id='incident-pane-2' class='pane'>
				<label id='incident-label' class='paneMainLabel'>Incident Response</label>
				<div id='resp-basic-info-div'>
					<div id='resp-counts'>
						<span class='resp-basic-info'>Number of Units : </span>
						<span id='resp-unit-count' class='resp-basic-info'></span><br>
						<span class='resp-basic-info'>Number of Stations : </span>
						<span id='resp-station-count' class='resp-basic-info'></span><br>
					</div>
					<div>
						<span class='resp-basic-info'>First Unit Arrival : </span>
						<span id='resp-first-arrival' class='resp-basic-info'></span><br>
						<span class='resp-basic-info'>Last Unit Arrival : </span>
						<span id='resp-last-arrival' class='resp-basic-info'></span><br>
					</div>
				</div>
				
				<!-- Responding units table -->	
				<div id='response-units'>
					<span id="resp-table-title">Responding Units</span>
					<table id="resp-table">
						<thead id="resp-thead">
  							<tr id="resp-table-header">
  								<th class="resp-table-cell resp-table-header-cell resp-unit">
  									<span class="resp-header-title">Unit</span>
  									<span class="ui-icon header-icon"></span>
  								</th>
  								<th class="resp-table-cell resp-table-header-cell resp-type">
  									<span class="resp-header-title">Type</span>
  									<span class="ui-icon header-icon"></span>
  								</th>
  								<th class="resp-table-cell resp-table-header-cell resp-station">
  									<span class="resp-header-title">Station</span>
  									<span class="ui-icon header-icon"></span>
  								</th>
  								<th class="resp-table-cell resp-table-header-cell resp-time">
  									<span class="resp-header-title">Time</span>
  									<span class="ui-icon header-icon"></span>
  								</th>
  								<th class="resp-table-cell resp-table-header-cell resp-dist">
  									<span class="resp-header-title">Dist</span>
  									<span class="ui-icon header-icon"></span>
  								</th>
  							</tr>
  						</thead>
  						<tbody id='resp-table-body'></tbody>
  					</table>	
				</div>
			</div>

			<!-- Control pane -->	
			<div id='controlPane' class='pane'>
				<span id='radioBtns'>
					<input type="checkbox" id="btn-query">
					<label id='btn-query-label' for="btn-query">Query</label>
					<input type="checkbox" id="btn-incident">
					<label id='btn-incident-label' for="btn-incident">Incident</label>
				</span>
				<button id="clearButton" >Clear</button>
				<button id="about-button" >About</button>
			</div>
			
			<!-- About dialog -->
			<div id="about-dialog" title="About">
				
				<p id='about-overview'><b>Overview</b></p>
				
				<p>This web application is a fire department mapping application for the 
				Northern Virginia area. The application allows users to access information 
				about fire stations/departments and simulate department responses to 
				incidents within Northern Virginia. The data for the application was gather 
				from official fire department websites and Wikipedia. All the code for this 
				project can be viewed at the author's 
				<a href="https://github.com/ldrotos1/NorthernVirginiaFireDepartMapper" target="_blank">GitHub site</a>.</p>
				
				<p class='about-section-header'><b>Capabilities</b></p>
				
				<p><b>Station Information</b> - The user can click on any of the fire station icons 
				on the map to open a window displaying information about that station. The 
				information includes the name, department, address and contact information. 
				A list of all units assigned to the station is also included.</p>
				
				<p><b>Station Query</b> - From the query pane the user can query stations based on a 
				variety of criteria. These include a particular station name, all stations that 
				belong to a particular department and all stations that have a specified type 
				of unit assigned to it. Stations that meet the query are highlighted on the map.</p>
				
				<p><b>Simulate Incident Response</b> - From the incident pane the user can simulate the 
				response to a fire. To carry out the simulation the user interactively selects a 
				location on the map and selects the alarm level of the fire, the higher the alarm 
				level the larger the fire and thus the more units that will respond. For each alarm 
				level the following types and number of units will be sent.</p>
				
				<ul>
  					<li>3 Engines/Tankers</li>
  					<li>1 Truck (Ladder) or Tower</li>
  					<li>1 Rescue or Technical Rescue</li>
  					<li>1 Battalion Chief</li>
  					<li>1 Medic</li>
				</ul>
				
				<p>Once the response has been simulated information about the response is displayed 
				on the incident pane, which includes a table of all units responding to the fire. 
				Additionally, the routes that the units will travel from the various stations to the 
				fire are displayed on the map. The units listed in the table and the routes on the map 
				are dynamically linked so that hovering on a route will cause the units taking that 
				route to be highlighted in the table and vise versa. Furthermore, by clicking on a 
				route all units taking that route will be moved to the top of the table.</p>
				
				<p class='about-section-header'><b>Technology Stack</b></p>
				
				<p>The application's frontend is coded using the <b><i>Leaflet</i></b>, <b><i>JQuery</i></b> 
				and <b><i>JQuery UI</i></b> javascript libraries. The HTML is generated by a <b><i>JSP</i></b>. 
				The base layer of the map was created using <b><i>Mapbox Studio</i></b>, which uses map data 
				provided by OpenStreetMap. <b><i>Ajax</i></b> is used throughout the application to communicate 
				with the server. The application's backend consists of a <b><i>Java servlet</i></b> and a 
				<b><i>Postgres</i></b> database running the <b><i>PostGIS</i></b> extension. The database includes 
				all the information about the stations and the units within the Northern Virginia area. The backend 
				logic uses <b><i>MapQuest's Directions API</i></b> to assist in simulating the incident responses.</p>
				
				<button id="about-close-btn">Close</button>
				
			</div>
			
			<!-- Station info dialog -->	
			<div id="station-info-dialog" title="Station Information">
  				<div>
  					<div id="station-basic-info">
  						<span class="basic-info"></span><br>
  						<span class="basic-info"></span>
  					</div>
  					<div id="station-address">
  						<span class="address"></span><br>
  						<span class="address"></span>
  					</div>
  					<div id="station-contact">
  						<span class="contact-info"></span><br>
  						<span class="contact-info"></span>
  					</div>
  				</div>
  				<div id="stat-info-table">
  					<span id="stat-info-table-title">Assigned Units</span>
  					<table id="stat-info-unit-table">
  						<tr id="stat-info-header">
  							<th class="stat-info-cell">Unit Designator</th>
  							<th class="stat-info-cell">Unit Type</th>
  						</tr>
  					</table>
  				</div>
  				<div id="station-info-btn-container">
  					<button id="station-info-close-btn">Close</button>
  				</div>
			</div>
		</div>
	</body>
</html>