/**
 * Louis Drotos - 9 March 2015
 * 
 * This file provides a class that represents a fire station. 
 */

/**
 * @function This function a constructor that returns a object 
 * the represents a fire station marker on a map.
 * @param {String} id - The station ID.
 * @param {String} name - The station's name.
 * @param {String} number - The station's number.
 * @param {String} depart - The station's department.
 * @param {String} address - The station's adress.
 * @param {String} city - The station's city.
 * @param {String} state - The station's state.
 * @param {String} zip - The station's zip code.
 * @param {Double} lat - The station's latitude.
 * @param {Double} lon - The station's longitude.
 * @param {String} image - The URL of the station's image.
 */
function Station(id, name, number, depart, address, city, state, zip, lat, lon, image) {
	this.id = id
	this.name = name
	this.number = number
	this.department = depart
	this.address = address
	this.city = city
	this.state = state
	this.zip = zip
	this.image = image
	this.selected = false
	this.coord = new L.latLng(lat, lon) 
	this.marker = L.circleMarker(this.coord, {
		radius: 5,
		color:'#000000',
		weight:1,
		opacity: 1.0,
		fillColor: '#E60000',
		fillOpacity: 1.0,
		title: name
	})
	this.queryCircle = L.circle(this.coord, 0, {
		color:"#FF3333",
		weight:2,
		opacity:0,
		fillOpacity:0.0,
		clickable:false
	})
	
	// Adds the on hover label to the marker
	this.marker.bindLabel(this.name, {
		className:"stationLabel pane",
		offset:[19,-13]
	});
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
	
	toggleSelection: function(select) {
		
		this.selected = select;
		
		if(select === true) {
			this.marker.setStyle({
				fillColor: '#F7FE2E',
				radius: 6,
				weight: 2
			});
		}
		else if (select === false) {
			this.marker.setStyle({
				fillColor: '#E60000',
				radius: 5,
				weight: 1
			});
		}
	},
		
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
			});
		});
			
		// Adds the mouse off event
		this.marker.on('mouseout', function(e) {
			
			// Declare variables
			var strColor,
			objStyle;
			
			// Determines if the marker is selected and builds 
			// style object accordingly.
			strColor = e.target.options.fillColor
			if (strColor === "#F7FE2E") {
				objStyle = {
					radius: 6,
					weight: 2
				}
			}
			else if (strColor === "#E60000") {
				objStyle = {
					radius: 5,
					weight: 1
				}
			}
			
			// Sets the style
			e.target.setStyle(objStyle);
		});
			
		// Adds the station marker and query circle to the map
		this.marker.addTo(map);
		this.queryCircle.addTo(map);
	}
}


