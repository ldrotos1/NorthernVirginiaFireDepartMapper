/**
 * Louis Drotos
 * April 13, 2015
 * 
 * This file contains code that is used to initialize the controls
 * on the pane pane and handle their functionality.
 */

/**
 * @function Initializes the control pane widgets once the document
 * is ready.
 */
$(function() { 
	
	// Initializes the widgets on the control pane 
	$( "#clearButton" ).button();
	$( "#radioBtns" ).buttonset();
	
	// Wires the clear button click event
	$( "#clearButton" ).click(function() {
		clearMap(objGlobalVars.arrStations)
	});
});


/**
 * @function This function clears any station selection in the map.
 * @param arrStations {Array} - An array of all station in the map
 */
function clearMap(arrStations) {
	
	// Unselects each station
	$.each(arrStations, function( index, value ) {
		  value.toggleSelection(false);
	});
}