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

		// Un-selects each station
		$.each(objGlobalVars.arrStations, function( index, value ) {
			  value.toggleSelection(false);
		});
	});
});