/**
 * Louis Drotos
 * May 15, 2015
 * 
 * This file contains code that is used to initialize the incident
 * pane controls and its functionality.
 */

/**
 * @function Initializes the incident pane widgets once the document
 * is ready. 
 */
$(function() {
	
	// Inits the alarm count spinner
	$( "#alarm-count" ).spinner({
		  min: 1,
		  max: 5
	}).spinner("value", 1).width(20);
	
	// Inits the buttons
	$( "#btn-fire" ).button({
		  text: false
	});
	$( "#btn-response" ).button();
	
	// Aligns the controls
	alignControls();
	$( "#processing" ).toggle();
	
	// Sets the pane to be hidden.
	$( "#incident-pane-1" ).hide();
	$( "#incident-pane-2" ).hide();
	objGlobalVars.objActiveIncidentPane = $( "#incident-pane-1" );
	
	// Wires the simulate response button click event
	$( "#btn-response" ).click(function() {

		var strAlarms,
		objLoc,
		objInterval,
		strLat,
		strLon,
		objReq;
		
		// Ensures that a fire location has been selected
		objLoc = objGlobalVars.objIncidentLoc;
		if (objLoc instanceof L.Marker == false) {
			alert("You must select a incident location.")
		}
		else {
			
			// Displays the processing message
			objInterval = startProcessing();
			
			// Gets request parameters
			strAlarms = $( "#alarm-count" ).spinner( "value" ).toString(); 
			strLat = objLoc.getLatLng().lat.toString();
			strLon = objLoc.getLatLng().lng.toString();
			
			// Sends request to server
			objReq = $.getJSON( "/NorthernVirginiaFireDepartMapper/data", { 
				RequestFor: "IncidentResponse",
				alarms: strAlarms,
				latitude: strLat,
				longitude: strLon
				},
				function( data ){
					
					// Process response
					if (data.hasOwnProperty('error')) {
						alert(data.error);
					}
					else {
						
						// Adds the response information to the user interface
						addResponseToPane(data);
						addResponseToMap(data, objGlobalVars.objMap, objGlobalVars.arrRoutes);
						
						// Toggles the incident panes
						$('#incident-pane-1,#incident-pane-2').fadeToggle({
							duration: 400
						});
						objGlobalVars.objActiveIncidentPane = $( '#incident-pane-2' );
					}
					endProcessing(objInterval);
			})
			.fail(function(){
				alert("Unable to simulate incident response at this time.")
				endProcessing(objInterval);
			});
			setTimeout(function(){ objReq.abort(); }, 90000);
		}
	});
	
	// Wires fire button click event
	$( "#btn-fire" ).click(function() {
		  
		var objMap = objGlobalVars.objMap;
		
		$( "#map" ).css( 'cursor', 'crosshair' );
		
		map.once('click', function(e) {        
	        
			var loc,
	        newMarker,
	        oldMarker;
	        
			// Creates the incident marker
	        loc = e.latlng;
	        newMarker = L.marker(loc, {
	        	icon: L.icon({
	    		    iconUrl: 'css/images/incident.png',
	    		    iconSize: [20, 20],
	    		    iconAnchor: [10, 10]
	    		})	
	        });
	        objMap.addLayer(newMarker);
	        
	        // Removes the old incident marker
	        oldMarker = objGlobalVars.objIncidentLoc;
	        if(oldMarker instanceof L.Marker) {
	        	objMap.removeLayer(oldMarker);
	        }
	        
	        objGlobalVars.objIncidentLoc = newMarker;
	        $( "#map" ).css( 'cursor', '' );     
	    });  
	});
	
	/**
	 * @function Creates the incident icon and stores it as 
	 * a global variable.
	 */
	function createIncidentIcon(){
		var icon = L.icon({
		    iconUrl: 'css/images/incident.png',
		    iconSize: [20, 20],
		    iconAnchor: [10, 10]
		});
		
		// Adds the icon to the global object
		objGlobalVars.objIncidentIcon = icon;
	}
	
	/**
	 * @function Vertically aligns the labels for the alarm
	 * number and fire location controls 
	 */
	function alignControls() {
		
		var strAlarmDivHeight,
		strFireLocDivHeight;
		
		// Gets the containing heights of the DIVs
		strFireLocDivHeight = $( "#fire-loc-div" ).css( "height" );
		strAlarmDivHeight = $( "#alarm-div" ).css( "height" );

		// Sets line height equal to containing DIVs
		$( "#alarm-label" ).css( "line-height", strAlarmDivHeight );
		$( "#fire-loc-label" ).css( "line-height", strFireLocDivHeight );		
	}
	
	/**
	 * @function Adjusts the state of the incident pane to indicate
	 * that the application is computing the incident response  
	 */
	function startProcessing() {
		
		var objInterval,
		objProcessingTag,
		arrValues = ["Processing .", "Processing . .", "Processing . . ."],
		intArrIndex = 0;
		
		// Disables the incident pane controls
		$( '#alarm-count' ).spinner( "disable" );
		$( '#btn-fire' ).button( "disable" );
		$( '#btn-response' ).button( "disable" );
		
		// Disables the control pane controls
		$( '#clearButton' ).button( 'disable' );
		$( '#radioBtns' ).buttonset( 'disable' );
		
		// Displays the processing message
		objProcessingTag = $( "#processing" );
		objProcessingTag.toggle( "slow" );
		
		// Animates the processing message
		objInterval = setInterval(function(){
			
			objProcessingTag.text( arrValues[intArrIndex] );
			if ( intArrIndex == arrValues.length - 1 ) {
				intArrIndex = 0;
			}
			else {
				intArrIndex++;
			}
		}, 700);
		
		return objInterval;
	}
	
	/**
	 * @function Adjusts the state of the incident pane so that
	 * it is not in a processing state.
	 */
	function endProcessing(objInterval) {
		
		// Enables the incident pane controls
		$( '#alarm-count' ).spinner( "enable" );
		$( '#btn-fire' ).button( "enable" );
		$( '#btn-response' ).button( "enable" );
		
		// Enables the control pane controls
		$( '#clearButton' ).button( "enable" );
		$( '#radioBtns' ).buttonset( 'enable' );
		
		// Hides the processing message
		$( "#processing" ).toggle( "slow" );
		clearInterval(objInterval);
	}
	
	/**
	 * @function This function updates the incident response pane with
	 * the information pertaining to the units responding to the incident. 
	 */
	function addResponseToPane(objData) {
		
		var numFirstArrival,
		numLastArrival,
		strTableDom = '',
		boolEvenRow = false,
		numTravelTime,
		numTravelDist,
		arrCellClasses;
		
		// Adds the basic information about the response
		numFirstArrival = (Math.round( objData.firstArrivalSec / 60 * 10) / 10).toFixed(1);
		numLastArrival = (Math.round( objData.lastArrivalSec / 60 * 10) / 10).toFixed(1);
		
		$( '#resp-unit-count' ).text( objData.unitCount );
		$( '#resp-station-count' ).text( objData.stationCount );
		$( '#resp-first-arrival' ).text( numFirstArrival + ' mins' );
		$( '#resp-last-arrival' ).text( numLastArrival + ' mins' );
		
		// Constructs the unit table
		$.each(objData.units, function(i, value) {
			
			// Creates the DOM for the current row
			if (boolEvenRow === true) {
				strTableDom += "<tr class=\"resp-table-row resp-even\">";
				boolEvenRow = false;
			}
			else {
				strTableDom += "<tr class=\"resp-table-row\">";
				boolEvenRow = true;
			}
			
			// Abbreviates certain unit types
			
			// Determines the travel time and distance
			numTravelTime = (Math.round( value.travelTime / 60 * 10) / 10).toFixed(1);
			numTravelDist = (Math.round( value.travelDistance * 10) / 10).toFixed(1);
			
			// Creates the DOM for the cells within the current row
			strTableDom += "<td class=\"resp-table-cell resp-str-cell resp-unit\">" + value.unitDesignator + "</td>";
			strTableDom += "<td class=\"resp-table-cell resp-str-cell resp-type\">" + value.unitType + "</td>";
			strTableDom += "<td class=\"resp-table-cell resp-str-cell resp-station\">" + value.stationName + "</td>";
			strTableDom += "<td class=\"resp-table-cell resp-num-cell resp-time\">" + numTravelTime + " min" + "</td>";
			strTableDom += "<td class=\"resp-table-cell resp-num-cell resp-dist\">" + numTravelDist + " mi" + "</td>";
			
			// Creates the DOM for closing the row
			strTableDom += "</tr>";
		});
		
		// Updates the table with the constructed DOM
		$( ".resp-table-row" ).remove();
		$( "#resp-table-body" ).append( strTableDom );
	}
	
	/**
	 * @function This function adds the response routes to the map
	 * @param objData {Object} The incident response data from the server
	 * @param objMap {Object} A reference to the map
	 * @param arrRoutes {Array} A reference to the array where the routes are stored
	 */
	function addResponseToMap(objData, objMap, arrRoutes) {
		
		var objRoutes,
		objRoute,
		arrWayPoints,
		objIncidentLoc,
		arrStations,
		objstation;
		
		objIncidentLoc = objGlobalVars.objIncidentLoc.getLatLng();
		arrStations = objGlobalVars.arrStations;
		objRoutes = objData.travelRoutes;
		
		// Iterates through the routes
		$.each( objRoutes, function( stationId, points ) {
			
			// Gets the station location
			objstation = $.grep(arrStations, function(e){ 
				return e.id == stationId; 
			});
			
			// Builds the list of LatLng objects for the route
			arrWayPoints = [];
			arrWayPoints.push(objstation[0].coord)
			
			$.each( points, function( key, value ) {
				arrWayPoints.push(L.latLng(value.y, value.x));
			} )
			
			arrWayPoints.push(objIncidentLoc);
			
			// Creates the route and adds it to the map 
			objRoute = new Route(stationId, null, arrWayPoints);
			objRoute.addRoute(objMap);
			arrRoutes.push(objRoute);
		});
	}
});




