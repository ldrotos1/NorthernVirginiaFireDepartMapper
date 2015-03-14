/**
 * Louis Drotos
 * March 1, 2015
 * 
 * This file contains code that is used for creating and maintaining
 * the application's map.
 */

/**
 * @function Creates the map and adds the map when the document has been loaded.
 */
$( window ).load(function() {
	setMapDivHeight();
	objGlobalVars.objMap = createMap();
	addStations(objGlobalVars.objMap);
});

/**
 * @function Creates the map
 * @returns {Object} The Leaflet map object
 */
function createMap() {
	
	// Creates the map
	map = L.map('map', {
		center: [38.841534, -77.271913],
		zoom: 11,
		minZoom: 11,
		zoomControl: false,
		maxBounds: getMapBounds(),
		bounceAtZoomLimits: false
	});
	
	// Adds the base layer to the map
	L.tileLayer('http://api.tiles.mapbox.com/v4/ldrotos.13eccffb/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibGRyb3RvcyIsImEiOiJwQXgwZ2ZVIn0.pPrIMXZdwniJcp79DNpg9g', {
	    attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>',
	}).addTo(map);
	
	// Wires event to ensure map is resized when browser size changes
	$( window ).resize(function() {
		setMapDivHeight();
	});
	
	// Returns the map
	return map;
}

/**
 * @function Adds the stations to the map. Sets the arrStations property on the
 * global variable object to an array of stations added to the map. 
 * @param map {Object} The map.
 */
function addStations(map) { 
	
	// Declare variables
	var dblLat,
	dblLon,
	strId,
	strName,
	objStation,
	arrStations = [];
	
	// Gets a list of all stations from the server
	$.getJSON( "/NorthernVirginiaFireDepartMapper/data",
		{ RequestFor: "AllStations" },
		function( data ){
			$.each(data, function(i, value) {
				
				// Creates the station object and adds it to the map
				dblLat = value.location.y;
				dblLon = value.location.x;
				strId = value.stationId;
				strName = value.stationName;
				objStation = new Station(strId, strName, dblLat, dblLon);
				objStation.addStation(map);
				arrStations[i] = objStation;	
			});
			
			// Add the station array to the global
			objGlobalVars.arrStations = arrStations;
	})
	.fail(function(){
		alert("Unable to add stations to the map.");
	});
}

/**
 * @function Creates a bounds object that defines the panning boundaries of the map.
 * @returns {Object} The Leaflet bounds object
 */
function getMapBounds() {
	
	// Declare variables
	var southWest,
    northEast,
    bounds;
	
	// Builds bounds object
	southWest = L.latLng(38.574669, -77.755987);
    northEast = L.latLng(39.104280, -76.704703);
    bounds = L.latLngBounds(southWest, northEast);
	
	// Returns bounds
	return bounds;
}

/** 
 *@function Sets the height of the map div based on the size of the browser.  
 */ 
function setMapDivHeight() {
	
	// Declares variables
	var docHeight,
	headerHeight,
	mapHeight;
	
	// Gets the header and document heights
	docHeight = $( window ).height();
	headerHeight = $("header").height();
	
	// Sets the height of the map DIV
	mapHeight = docHeight - headerHeight - 2;
	$("#map").height(mapHeight);
}
