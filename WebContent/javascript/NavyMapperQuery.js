/**
 * @author Louis Drotos
 */

function queryAllShips( portList, map ) {
	
	$.ajax({ 
		url: "/NavalMapper/ShipMap",
 		type: "POST",
 		data: {
 	        requestFor: "all"
 	    },
 		dataType : "json",
 		success: function( json ) {
        	querySuccess( json, portList, map ); 
 		},
 		error: function( xhr, status, errorThrown ) {
 			queryFailed( errorThrown );
 		}
	});
}

function clearAllShips( homeports, map ) {
	$.each(homeports, function( index, port ) {
		
		clickEvent = port['circleClickEvent'];
		hoverEvent = port['circleHoverEvent'];
		outHoverEvent = port['circleOutHoverEvent'];
		
		circleOpts = {
			radius : 0
		};
		
		port['circle'].setOptions(circleOpts);
		port['shipList'].length = 0;
		
		if (clickEvent != null) {
			google.maps.event.removeListener(clickEvent);
			port['circleClickEvent'] = null;	
		}
		
		if (hoverEvent != null) {
			google.maps.event.removeListener(hoverEvent);
			port['circleHoverEvent'] = null;	
		}
		
		if (outHoverEvent != null) {
			google.maps.event.removeListener(outHoverEvent);
			port['circleOutHoverEvent'] = null;	
		}
	});
}

function querySuccess( json, portList, map ) {
	
	var shipData;
	
	clearShips( portList );
	
	shipData = json["ships"];
	addShips( portList, shipData );
	resizeCircles( portList, map );
	reorderCircles ( portList );
}

function queryFailed( errorThrown ) {
	alert( "Failed" );
}

// Helper methods
function addShips( portList, shipList ) {

	$.each( shipList, function( shipIndex, ship ) {
		homePort = ship["homeport"];
		
		$.each ( portList, function( portIndex, port ) {
			
			if (port["name"] == homePort) {
				port["shipList"].push(ship);
			}
		});
	});	
}

function resizeCircles( portList, map ) {
	
	var shipCount;
	var newRadius;
	var clickEvent;
	var hoverEvent;
	var outHoverEvent;
	var circleOptions;
	
	$.each(portList, function(index, port) {
		
		// Gets objects
		shipCount = port['shipList'].length;
		newRadius = 300000 + 20000 * shipCount;
		clickEvent = port['circleClickEvent'];
		hoverEvent = port['circleHoverEvent'];
		outHoverEvent = port['circleOutHoverEvent'];
		
		// Removes old events
		if (clickEvent != null) {
			google.maps.event.removeListener(clickEvent);
			port['circleClickEvent'] = null;
		}
		
		if (hoverEvent != null) {
			google.maps.event.removeListener(hoverEvent);
			port['circleHoverEvent'] = null;	
		}
		
		if (outHoverEvent != null) {
			google.maps.event.removeListener(outHoverEvent);
			port['circleOutHoverEvent'] = null;	
		}
		
		// Sets the new radius
		if (shipCount == 0) {
			circleOptions = {
				radius : 0
			};	
		}
		else {
			circleOptions = {
				radius : newRadius
			};
		}
		port['circle'].setOptions(circleOptions);	
		
		// Adds new events
		if (shipCount > 0) {
			clickEvent = google.maps.event.addListener(port['circle'], 'click', function() {
				alert("Event Works!");
			});
			port['circleClickEvent'] = clickEvent;
			
			hoverEvent = google.maps.event.addListener(port['circle'], 'mouseover', function() {
				
				if (port['circle'].fillColor != '#00E6E6') {
					var opt = {
						strokeColor : '#00B8B8', 
						fillColor : '#00E6E6'
					};
					port['circle'].setOptions(opt);
				}
			});
			port['circleHoverEvent'] = hoverEvent;
			
			outHoverEvent = google.maps.event.addListener(port['circle'], 'mouseout', function() {
				var opt = {
						strokeColor : '#335CD6', 
						fillColor : '#4D70DB'
					};
					port['circle'].setOptions(opt);	
			});
			port['circleOutHoverEvent'] = outHoverEvent;			
		}
	});
}

function reorderCircles ( portList ) {
	var portName;
	var shipCountObj;
	var shipCounts = new Array();
	var circleOptions = {
		zIndex : 0
	};
	
	$.each(portList, function( index, port ) {
		shipCountObj = {
			port : port['name'],
			count : port['shipList'].length
		};
		shipCounts.push(shipCountObj);
	});
	
	shipCounts.sort(function compare( a, b ) {
		var aCount = a['count'];
		var bCount = b['count'];
		
		if (aCount < bCount) {
			return -1;
		}
		if (aCount > bCount) {
			return 1;	
		}
		return 0;
	});
	
	$.each(portList, function(index, port) {
		portName = port['name'];
		for (var i = 0; i < shipCounts.length; i++) {
			if (shipCounts[i]['port'] == portName){
				circleOptions['zIndex'] = 16 - i;
				break;	
			}
		}
		
		port['circle'].setOptions(circleOptions);
	});
	
		
}

function clearShips( portList ) {
	$.each(portList, function(index, value) {
		value.shipList = [];
	});
}








