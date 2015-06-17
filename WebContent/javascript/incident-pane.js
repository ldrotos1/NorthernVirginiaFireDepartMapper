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
	
	// Wires the click events on the response table's header row
	$(".resp-table-header-cell").click(function(event) {
		
		var objElement,
		strColumn,
		intIndex;
		
		// Determines the index of the column to be sorted by
		strColumn = $( this ).children( '' ).text();
		switch(strColumn) {
			
			case 'Unit':
				intIndex = 0;
				break;
				
			case 'Type':
				intIndex = 1;
				break;
				
			case 'Station':
				intIndex = 2;
				break;
				
			case 'Time':
				intIndex = 3;
				break;
				
			case 'Dist':
				intIndex = 4;
				break;
		}
		
		if ( $( this.childNodes[3] ).hasClass( 'ui-icon-carat-1-n' ) === true) {
			
			if (strColumn === 'Unit' || strColumn === 'Type' || strColumn === 'Station') {
				
				// Sorts in descending order by string
				sortUnitTblByStr(intIndex, false);
			}
			else {
				
				// Sorts in descending order by float
				sortUnitTblByFlt(intIndex, false);
			}
		}
		else {
			
			if (strColumn === 'Unit' || strColumn === 'Type' || strColumn === 'Station') {
				
				// Sorts in ascending order by string
				sortUnitTblByStr(intIndex, true);
			}
			else {
				
				// Sorts in ascending order by float
				sortUnitTblByFlt(intIndex, true);
			}
		}
    });
	
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
						addResponseToMap(data, objGlobalVars.objMap, objGlobalVars.arrRoutes);
						addResponseToPane(data);
						
						// Toggles the incident panes
						$( '#incident-pane-1' ).fadeToggle({
							duration: 400,
							complete: function() {
								
								$('#incident-pane-2').fadeToggle({
									duration: 400,
									complete: function() {
										setHeaderCellWidths();
									}
								})
							}
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

			// Determines the travel time and distance
			numTravelTime = (Math.round( value.travelTime / 60 * 10) / 10).toFixed(1);
			numTravelDist = (Math.round( value.travelDistance * 10) / 10).toFixed(1);
			
			// Creates the DOM for the current row
			strTableDom += "<tr class=\"resp-table-row\">";
			strTableDom += "<td class=\"resp-table-cell resp-unit\">" + value.unitDesignator + "</td>";
			strTableDom += "<td class=\"resp-table-cell resp-type\">" + value.unitType + "</td>";
			strTableDom += "<td class=\"resp-table-cell resp-station\">" + value.stationName + "</td>";
			strTableDom += "<td class=\"resp-table-cell resp-time\">" + numTravelTime + " min" + "</td>";
			strTableDom += "<td class=\"resp-table-cell resp-dist\">" + numTravelDist + " mi" + "</td>";
			
			// Creates the DOM for closing the row
			strTableDom += "</tr>";
		});
		
		// Updates the table with the constructed DOM
		$( ".resp-table-row" ).remove();
		$( "#resp-table-body" ).append( strTableDom );
		
		// Resets the sorting icons in the table header row
		$( '.header-icon' ).removeClass( 'ui-icon-carat-1-n' );
		$( '.header-icon' ).removeClass( 'ui-icon-carat-1-s' );
		$( '.header-icon' ).addClass( 'ui-icon-carat-2-n-s' );
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
	
	/**
	 * @function Sorts the table of responding units based on a specified 
	 * column containing strings.
	 * @param intIndex The index of the column that the table will be sorted by
	 * @param boolAscn If true the table will be sorted in ascending
	 * order, or descending order if false.  
	 */
	function sortUnitTblByStr(intIndex, boolAscn) {
		
		var arrRows,
		newDom,
		aText,
		bText,
		result;
		
		arrRows = $( '.resp-table-row' ).toArray();
		
		if (boolAscn === true) {
			
			// Sorts as a string in ascending order
			arrRows.sort(function(a,b) {

				// Gets the cell values
				aText = a.childNodes[intIndex].textContent;
				bText = b.childNodes[intIndex].textContent;
				
				// Determines sort order
				if (aText < bText) {
					result = -1
				}
				else if (aText > bText) {
					result = 1
				}
				else {
					result = 0;
				}
				
				return result;
			});
		}
		else {
			
			// Sorts as a string descending order
			arrRows.sort(function(a,b) {
				
				// Gets the cell values
				aText = a.childNodes[intIndex].textContent;
				bText = b.childNodes[intIndex].textContent;
				
				// Determines sort order
				if (aText < bText) {
					result = 1
				}
				else if (aText > bText) {
					result = -1
				}
				else {
					result = 0;
				}
				
				return result;
			});	
		}
		
		// Builds the new sorted DOM
		newDom = '<tbody id=\'resp-table-body\'>'
		$.each(arrRows, function(i, value) { 
			newDom += value.outerHTML;
		});
	    newDom += '</tbody>'
	    	
	    // Swaps out the table body DOM
		$( '#resp-table-body' ).replaceWith( newDom );
	    setTblHeaderIcon(intIndex, boolAscn);
	    
	}
	
	/**
	 * @function Sorts the table of responding units based on a specified 
	 * column containing floats.
	 * @param intIndex The index of the column that the table will be sorted by
	 * @param boolAscn If true the table will be sorted in ascending
	 * order, or descending order if false.  
	 */
	function sortUnitTblByFlt(intIndex, boolAscn) {
		
		var arrRows,
		newDom,
		aText,
		bText,
		aNum,
		bNum,
		result;
		
		arrRows = $( '.resp-table-row' ).toArray();
		
		if (boolAscn === true) {
			
			// Sorts as a string in ascending order
			arrRows.sort(function(a,b) {

				// Gets the cell values
				aText = a.childNodes[intIndex].textContent;
				bText = b.childNodes[intIndex].textContent;
				
				// Extracts the numbers from the text
				aNum = parseFloat( aText.match(/\d+.\d|\d+/)[0] );
				bNum = parseFloat( bText.match(/\d+.\d|\d+/)[0] );
				
				// Determines sort order
				if (aNum < bNum) {
					result = -1
				}
				else if (aNum > bNum) {
					result = 1
				}
				else {
					result = 0;
				}
				
				return result;
			});
		}
		else {
			
			// Sorts as a string descending order
			arrRows.sort(function(a,b) {
				
				// Gets the cell values
				aText = a.childNodes[intIndex].textContent;
				bText = b.childNodes[intIndex].textContent;
				
				// Extracts the numbers from the text
				aNum = parseFloat( aText.match(/\d+.\d|\d+/)[0] );
				bNum = parseFloat( bText.match(/\d+.\d|\d+/)[0] );
				
				// Determines sort order
				if (aNum < bNum) {
					result = 1
				}
				else if (aNum > bNum) {
					result = -1
				}
				else {
					result = 0;
				}
				
				return result;
			});	
		}
		
		// Builds the new sorted DOM
		newDom = '<tbody id=\'resp-table-body\'>'
		$.each(arrRows, function(i, value) { 
			newDom += value.outerHTML;
		});
	    newDom += '</tbody>'
	    	
	    // Swaps out the table body DOM
		$( '#resp-table-body' ).replaceWith( newDom );
	    setTblHeaderIcon(intIndex, boolAscn);
	}
	
	/**
	 * @function Sets the the sorting icon in the responding table header row
	 * @param intIndex The index of the column that is being sorted
	 * @param boolAscn Indicates if the table is being sorted in ascending or
	 * descending order.
	 */
	function setTblHeaderIcon(intIndex, boolAscn) {
		
		// Gets the header row's icon span tags
		$( '.header-icon' ).each( function( index ) {
			
			if ( index === intIndex ) {
				
				if (boolAscn === true) {
				
					// Adds ascending arrow icon
					$( this ).removeClass( 'ui-icon-carat-2-n-s ui-icon-carat-1-s' );
					$( this ).addClass( 'ui-icon-carat-1-n' );
				}
				else {
					
					// Adds descending arrow icon
					$( this ).removeClass( 'ui-icon-carat-2-n-s ui-icon-carat-1-n' );
					$( this ).addClass( 'ui-icon-carat-1-s' );
				}
			}
			else {
				
				// Adds double arrow icon
				$( this ).removeClass( 'ui-icon-carat-1-n ui-icon-carat-1-s' );
				$( this ).addClass( 'ui-icon-carat-2-n-s' );
			}
		});
	}
	
	function setHeaderCellWidths() {
		
		var objTbl;
		
		objTbl = $( '#resp-table-body' )

		if( objTbl[0].offsetHeight < objTbl[0].scrollHeight ) {
			
			
			$( '.resp-unit' ).width( '4em' );
			$( '.resp-type, .resp-station' ).width( '9.5em' );
			$( '.resp-time' ).width( '4.5em' );
			$( 'th.resp-dist' ).width( '5.5em' );
			$( 'td.resp-dist' ).width( '3.70em' );
		}
		else {

			$( '.resp-unit, .resp-dist' ).width( '4em' );
			$( '.resp-type, .resp-station' ).width( '9.5em' );
			$( '.resp-time' ).width( '4.5em' );
		}
	}
});




