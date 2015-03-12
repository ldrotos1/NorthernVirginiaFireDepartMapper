/**
 * Louis Drotos - 9 March 2015
 * 
 * This file provides a class that represents a fire station. 
 */

/**
 * @function This function a constructor that returns a object 
 * the represents a fire station marker on a map.
 * @param {String} id - The station ID.
 * @param {Double} lat - The station's latitude.
 * @param {Double} lon - The station's longitude.
 */
function Station(id, lat, lon) {
	this.id = id
	this.marker = L.circleMarker(new L.latLng(lat, lon), {
		radius: 5,
		color:'#000000',
		weight:1,
		opacity: 1.0,
		fillColor: '#E60000',
		fillOpacity: 1.0,
		title: name
	})
}

/**
 * Declares class methods
 */
Station.prototype = {
		
	/**
	 * Constructor.
	 * @returns A new station instance.
	 */
	constructor: Station,
		
	/**
	 * This method adds this station to a map.
	 * @param map {Object} - The Leaflet map that will contain the station.
	 */
	addStation: function(map) { 
			
		// Adds the mouse over event
		this.marker.on('mouseover', function(e) {
			e.target.setStyle({
				radius: 8,
				weight: 3
			})
		});
			
		// Adds the mouse off event
		this.marker.on('mouseout', function(e) {
			e.target.setStyle({
				radius: 5,
				weight: 1
			})
		});
			
		// Adds the marker to the map
		this.marker.addTo(map);
	}
}





