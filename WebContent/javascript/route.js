/**
 * Louis Drotos - 4 June 2015
 * 
 * This file provides a class that represents a response route 
 */

/**
 * @function This function is a constructor that returns an object 
 * the represents a incident response route from a station to an
 * incident
 * @param {String} station_id - The station ID.
 * @param {Array} units - An array of units that will take this route
 * @param {Array} waypoints - An array of LatLng objects that define the route.
 */
function Route(station_id, units, waypoints) {
	
	this.stationId = station_id
	this.units = units
	this.path = L.polyline(waypoints, {
		color: '#FF0000',
		weight: 4,
		opacity: 0.3
	})
}

Route.prototype = {
		
		constructor: Route,
		
		addRoute: function(objMap) {
			this.path.addTo(objMap);
			this.path.bringToBack();
		}
}