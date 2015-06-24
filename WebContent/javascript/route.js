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
		weight: 5,
		opacity: 0.3
	})
}

Route.prototype = {
		
		/**
		 * @function Constructor
		 */
		constructor: Route,
		
		/**
		 * @function Adds this route to a map
		 * @param objMap The map
		 */
		addRoute: function(objMap) {
			
			// Declares objects.
			var self = this,
			strStationId = this.stationId,
			strUnit,
			strId;
			
			// Adds the mouse over event
			this.path.on('mouseover', function(e) {
				e.target.setStyle({
					weight: 7,
					opacity: 1.0
				});
				
				// Gets the rows from the response table
				$( '.resp-table-row' ).each(function() {
					
					// Extracts the station ID from the unit designator
					strUnit = this.childNodes[0].textContent;
					strId = strUnit.match(/\d{3}/g)[0];
					
					// Applies the new background color if station IDs match
					if (strId === strStationId) {
						$( this ).addClass( 'resp-row-highlighed' );
					}
				});
			});
			
			// Adds the mouse off event
			this.path.on('mouseout', function(e) {
				e.target.setStyle({
					weight: 5,
					opacity: 0.3
				});
				
				// Gets the rows from the response table
				$( '.resp-table-row' ).each(function() {
					
					// Extracts the station ID from the unit designator
					strUnit = this.childNodes[0].textContent;
					strId = strUnit.match(/\d{3}/g)[0];
					
					// Applies the new background color if station IDs match
					if (strId === strStationId) {
						$( this ).removeClass( 'resp-row-highlighed' );
					}
				});
			});
			
			// Sorts the unit table by the shifting the units traveling this
			// route to the top
			this.path.on('click', function(e) {
				
				var arrRows,
				strUnitA,
				strUnitB,
				strIdA,
				strIdB;
				
				// Gets and sorts the rows
				arrRows = $( '.resp-table-row' ).toArray();
				arrRows.sort(function(a,b) {
					
					// Extracts the station ID from the unit designators
					strUnitA = a.childNodes[0].textContent;
					strUnitB = b.childNodes[0].textContent;
					strIdA = strUnitA.match(/\d{3}/g)[0];
					strIdB = strUnitB.match(/\d{3}/g)[0];
					
					if (strIdA === strStationId) {
						return -1;
					}
					else if (strIdB === strStationId) {
						return 1;
					}
					else {
						return 0;
					}
				});
				
				// Builds the new sorted DOM
				newDom = '<tbody id=\'resp-table-body\'>'
				$.each(arrRows, function(i, value) { 
					newDom += value.outerHTML;
				});
			    newDom += '</tbody>'
			    	
			    // Swaps out the table body DOM and resets scroll bar
				$( '#resp-table-body' ).replaceWith( newDom );
			    $( '#resp-table-body' ).scrollTop();
			    nsIncident.wireRowHoverEvent();
			    
			    // Resets the table header icons
			    $( '.header-icon' ).each( function( index ) {
			    	
			    	$( this ).removeClass( 'ui-icon-carat-1-n ui-icon-carat-1-s' );
					$( this ).addClass( 'ui-icon-carat-2-n-s' );
			    });
			    
			    // Causes the route to flash yellow
			    e.target.setStyle({
					color: '#F7FE2E'
				});
			    
			    setTimeout(function() { 
			    	e.target.setStyle({
						color: '#FF0000'
					});
			    }, 100);
			});
			
			// Adds this route to the bottom layer of the map
			this.path.addTo(objMap);
			this.path.bringToBack();
		},

		/**
		 * @function Changes this route's style to indicate that
		 * it is selected
		 */
		selectRoute: function() {
			this.path.setStyle({
				weight: 7,
				opacity: 1.0
			});
		},
		
		/**
		 * @function Changes this route's style to indicate that
		 * it is not selected
		 */
		unselectRoute: function() {
			this.path.setStyle({
				weight: 5,
				opacity: 0.3
			});
		}
}

