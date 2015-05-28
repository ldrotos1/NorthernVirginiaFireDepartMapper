/**
 * Louis Drotos
 * May 15, 2015
 * 
 * This file contains code that is used to initialize the incident
 * pane controls and its functionality.
 */

/**
 * @function Initializes the incident pane widgets once the document
 * is ready. 
 */
$(function() {
	
	// Inits the alarm count spinner
	$( "#alarm-count" ).spinner({
		  min: 1,
		  max: 6
	}).spinner("value", 1).width(20);
	
	// Inits the buttons
	$( "#btn-fire" ).button({
		  text: false
	});
	$( "#btn-response" ).button();
	
	// Aligns the controls
	alignControls()
	
	// Sets the pane to be hidden.
	$( "#incident-pane" ).hide();
	
	/**
	 * @function Vertically aligns the labels for the alarm
	 * number and fire location controls 
	 */
	function alignControls() {
		
		var intAlarmDivHeight,
		intFireLocDivHeight;
		
		// Gets the containing heights of the DIVs
		intFireLocDivHeight = $( "#fire-loc-div" ).css( "height" );
		intAlarmDivHeight = $( "#alarm-div" ).css( "height" );

		// Sets line height equal to containing DIVs
		$( "#alarm-label" ).css( "line-height", intAlarmDivHeight );
		$( "#fire-loc-label" ).css( "line-height", intFireLocDivHeight );		
	}
});




