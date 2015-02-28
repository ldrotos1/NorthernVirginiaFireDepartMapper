/**
 * Louis Drotos
 * March 1, 2015
 * 
 * This file contains code that is used for creating and maintaining
 * the application's map.
 */

// A object for storing all global variables
var appGlobalVars = {
		map:{}
}

//Sets the height of the map div and creates the map once the DOM is ready
$(window).load(function() {
	setMapDivHeight();
	createMap(appGlobalVars.map);	
});

// Creates the map
function createMap(map) {
	
	// Creates the map
	var map = L.map('map', {
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
}

// Returns a bounds object that defines the panning boundaries
// of the map
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

// Sets the height of the map div based on the size of the browser 
function setMapDivHeight() {
	
	// Declares variables
	var docHeight,
	headerHeight,
	mapHeight;
	
	// Gets the header and document heights
	docHeight = $(document).height();
	headerHeight = $("header").height();
	
	// Sets the height of the map DIV
	mapHeight = docHeight - headerHeight - 2;
	$("#map").height(mapHeight);
}
