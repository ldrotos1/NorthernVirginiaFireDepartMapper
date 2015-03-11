/**
 * Louis Drotos - 9 March 2015
 * 
 * This file provides a class that represents a fire station on the map 
 */

/**
@function This function a constructor that returns a object 
the represents a fire station marker on a map.
@param {Obj L.map} map - The Leaflet map that will contain the station.
@param {String} name - The name of the station.
@param {Double} lat - The station's latitude.
@param {Double} lon - The station's longitude.
*/
function Station(map, name, lat, lon) {
	this.name = name
	this.marker = L.circleMarker(new L.latLng(lat, lon), {
		radius: 5,
		color:'#000000',
		weight:1,
		opacity: 1.0,
		fillColor: '#E60000',
		fillOpacity: 1.0,
		title: name
	})
	.addTo(map)
	.on('mouseover', function(e) {
		e.target.setStyle({
			radius: 8,
			weight: 3
		})
	})
	.on('mouseout', function(e) {
		e.target.setStyle({
			radius: 5,
			weight: 1
		})
	});
};
