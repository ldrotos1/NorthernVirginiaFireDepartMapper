/**
 * Louis Drotos
 * April 18, 2015
 * 
 * This file contains code that handles the station info modal window.
 */

$(function() {

	$( "#stationInfo" ).dialog({
		autoOpen: false,
		modal: true,
		width: 750
	});
	
});

function showStationInfo(stationId) {
	
	$( "#stationInfo" ).dialog( "open" );
}