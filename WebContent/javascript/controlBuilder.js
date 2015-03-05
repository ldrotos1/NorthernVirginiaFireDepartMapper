/**
 * Louis Drotos
 * March 1, 2015
 * 
 * This file contains code that is used for initializing
 * Jquery UI controls.
 */

$(function() {
	$(" #clearButton ").button();
	$( "#radioBtns" ).buttonset();
	
	$(" #searchBtn ").button();
	$( "#unitCombo" ).selectmenu();
	$( "#deptCombo" ).selectmenu();
	$( "#stationCombo" ).selectmenu();
});