/**
 * Louis Drotos
 * April 18, 2015
 * 
 * This file contains code that handles the station info modal window.
 */

/**
 * @function Initializes the station info dialog once the document
 * is ready.
 */
$(function() {
	
	$( "#stationInfo" ).dialog({
		autoOpen: false,
		modal: true,
		resizable: false
	});
});

/**
 * Station info namespace. 
 */
nsStationInfo = function() {
	
	/**
	 * @function Populates the station info dialog with information
	 * belonging to a specified station to include its assigned units
	 * and displays the dialog to the user. 
	 * @param objStation The station object belonging to the station
	 * that will have its info displayed.
	 * @param arrUnits An array of unit objects assigned to the station. 
	 */
	function constructInfoDialog(objStation, arrUnits) {
		
		// Declares variables
		var intCellWidth,
		objBasicInfo,
		objAddressInfo,
		objContactInfo,
		arrUnits,
		evenRow = false,
		strUnitListDom = '';
		
		// Gets the DOM elements
		objBasicInfo = $( ".basicInfo" );
		objAddressInfo = $( ".address" );
		objContactInfo = $( ".contactInfo" );
		
		// Sets the values for the basic station information.
		objBasicInfo[0].textContent = "Station " + objStation.number + " - " + objStation.name;
		objBasicInfo[1].textContent = objStation.department;
		
		objAddressInfo[0].textContent = objStation.address;
		objAddressInfo[1].textContent = objStation.city + ", " + objStation.state + " " + objStation.zip;
		
		objContactInfo[0].textContent = "Phone: " + objStation.phone;
		objContactInfo[1].textContent = "Fax: " + objStation.fax;
		
		// Constructs the unit table
		$.each(arrUnits, function(i, value) {
			
			// Creates the DOM for the current row
			if (evenRow === true) {
				strUnitListDom += "<tr class=\"tableRow even\">";
				evenRow = false;
			}
			else {
				strUnitListDom += "<tr class=\"tableRow\">";
				evenRow = true;
			}
			
			// Creates the DOM for the cells within the current row
			strUnitListDom += "<td>" + value.unitDesignator + "</td>";
			strUnitListDom += "<td>" + value.unitType + "</td>";
			
			// Creates the DOM for closing the row
			strUnitListDom += "</tr>";
		});
		
		// Updates the table with the constructed DOM
		$( ".tableRow" ).remove();
		$( strUnitListDom ).insertAfter( "#unitTableHeader" );
		
		// Shows the dialog
		$( "#stationInfo" ).dialog( "open" );
		
		// Adjust table width
		intCellWidth = ( $( "#tableContainer" ).width() ) / 2;
		$( "th" ).width(intCellWidth);
		$( "td" ).width(intCellWidth);
	}
	
	/**
	 * Public functions
	 */
	return {
		
		/**
		 * @function Populates the station info dialog with information
		 * belonging to the specified station to include it's assigned units.
		 * Information on the assigned units are queried from the database.
		 * Displays the dialog to the user.
		 * @param objStation The station object belonging to the station
		 * that will have its info displayed.
		 */
		showStationInfo: function(objStation) {
			
			$.getJSON( "/NorthernVirginiaFireDepartMapper/data", { 
				RequestFor: "AssignedUnits",
				stationId: objStation.id,
				},
				function( data ){
					
					// Creates the dialog
					constructInfoDialog(objStation, data);
			})
			.fail(function(){
				alert("Unable to access station information.")
			});
		}	
	}
}();







