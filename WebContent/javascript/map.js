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
	addStations(objGlobalVars.objMap, objGlobalVars.arrStations);
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
 * @function Adds the stations to the map. Modifies the stations parameter by
 * storing the station objects within the array starting from the 0 index. 
 * @param map {Object} The map.
 * @param stations {Array} An array that the station objects will be added to.
 */
function addStations(map, stations) { 
	
	// Declare variables
	var dblLat,
	dblLon,
	strId,
	strName,
	strNumber,
	strDepart,
	strAddress,
	strCity,
	strState,
	strZip,
	strPhone,
	strFax,
	strImage,
	objStation;
	
	// Gets a list of all stations from the server
	$.getJSON( "/NorthernVirginiaFireDepartMapper/data",
		{ RequestFor: "AllStations" },
		function( data ){
			$.each(data, function(i, value) {
				
				// Extracts the station info from the JSON
				dblLat = value.location.y;
				dblLon = value.location.x;
				strId = value.stationId;
				strNumber = value.stationNumber;
				strName = value.stationName;
				strDepart = value.department;
				strAddress = value.address;
				strCity = value.city;
				strState = value.state;
				strZip = value.zipCode;
				strPhone = value.phoneNumber;
				strFax = value.faxNumber;
				strImage = value.imageUrl;
				
				// Creates the station object
				objStation = new Station(
						strId, 
						strName, 
						strNumber, 
						strDepart, 
						strAddress, 
						strCity, 
						strState, 
						strZip,
						strPhone,
						strFax,
						dblLat, 
						dblLon, 
						strImage);
				objStation.addStation(map);
				stations[i] = objStation;
			});
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
