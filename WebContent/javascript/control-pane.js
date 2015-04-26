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
	
	$( "#btn-query" ).click(function() {
		
		// Determine if incident pane is visible
		var boolIncidentPaneVis = $( "#incidentPane" ).is( ":visible" );
		
		if (boolIncidentPaneVis === true) {
			
			// Hides the incident pane
			$( "#queryPane" ).toggle({
				effect: "slide",
				easing: "linear",
				duration: 300
			});
		}
		
		// Show the query pane
		$( "#queryPane" ).toggle({
			effect: "slide",
			easing: "linear",
			duration: 300
		});
	});
	
	
});
	
	