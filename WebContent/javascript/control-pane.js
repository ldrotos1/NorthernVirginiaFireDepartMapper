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
	
	// Code to handle the clicking of the incident button
	$( "#btn-incident" ).click(function() {
	
		var boolQueryPaneVis,
		intWaitTime;
		
		// Determine if query pane is visible
		boolQueryPaneVis = $( "#queryPane" ).is( ":visible" );
		
		if (boolQueryPaneVis === true) {
			
			// Hides the query pane
			$( "#queryPane" ).toggle({
				effect: "slide",
				easing: "linear",
				duration: 300
			});
			
			intWaitTime = 300;
			$( '#btn-query' ).prop('checked', false);
			$( '#btn-query-label' ).removeClass( 'ui-state-active' );
		}
		else {
			intWaitTime = 0;
		}
		
		setTimeout(function(){
			
			// Show the incident pane
			$( "#incident-pane" ).toggle({
				effect: "slide",
				easing: "linear",
				duration: 130
			});
		},intWaitTime);
	})
	
	// Code to handle the clicking of the query button
	$( "#btn-query" ).click(function() {
		
		var boolIncidentPaneVis,
		intWaitTime;
		
		// Determine if incident pane is visible
		var boolIncidentPaneVis = $( "#incident-pane" ).is( ":visible" );
		
		if (boolIncidentPaneVis === true) {
			
			// Hides the incident pane
			$( "#incident-pane" ).toggle({
				effect: "slide",
				easing: "linear",
				duration: 130
			});
			
			intWaitTime = 130;
			$( '#btn-incident' ).prop('checked', false);
			$( '#btn-incident-label' ).removeClass( 'ui-state-active' );
		}
		else {
			intWaitTime = 0;
		}
		
		setTimeout(function(){
			
			// Show the query pane
			$( "#queryPane" ).toggle({
				effect: "slide",
				easing: "linear",
				duration: 300
			});
		}, intWaitTime);
	});
});
	
	