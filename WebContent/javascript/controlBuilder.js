/**
 * Louis Drotos
 * March 1, 2015
 * 
 * This file contains code that is used for initializing
 * Jquery UI controls.
 */

// This function initializes map pane controls once the
// document is ready.
$(function() {
	
	// Initializes the controls on the control pane 
	$(" #clearButton ").button();
	$( "#radioBtns" ).buttonset();
	
	// Initializes the controls on the query pane
	$(" #searchBtn ").button();
	$( "#unitCombo" ).selectmenu();
	$( "#deptCombo" ).selectmenu();
	$( "#stationCombo" ).selectmenu();
	
	// Positions and sizes the search button
	alignSearchBtn();
	setSearchBtnHeight();
});

// This function aligns the top of the search button with the
// top of the select boxes
function alignSearchBtn() {
	
	// Declares variables
	var objCmbLabel,
	objSearchBtn,
	strLabelMargin,
	strLabelHeight,
	strCmbHeight,
	dblLabelMargin,
	dblLabelHeight,
	dblCmbHeight;
	
	// Gets the label height and margin
	objCmbLabel = $( ".labelDiv" );
	strLabelMargin = objCmbLabel.css("margin-bottom");
	strLabelHeight = objCmbLabel.css("height");
	
	// Converts the label height and margin to double
	dblLabelMargin = parseFloat(strLabelMargin.substring(0, strLabelMargin.length - 2));
	dblLabelHeight = parseFloat(strLabelHeight.substring(0, strLabelHeight.length - 2));
	
	// Sets the top margin of the search button
	objSearchBtn = $( "#searchBtnDiv" );
	objSearchBtn.css("margin-top", dblLabelMargin + dblLabelHeight);
}

// This function sets the size of the search button to be the
// same as the height of the select boxes
function setSearchBtnHeight() {
	
	var objSearchBtn, 
	strSelectHeight,
	strButtonHeight,
	strButtonPadding,
	strButtonBorder,
	dblSelectHeight,
	dblButtonHeight,
	dblButtonPadding,
	dblButtonBorder,
	dblNewBtnPadding;
	
	// Gets measurements
	objSearchBtn = $( "#searchBtn" );
	strButtonBorder = objSearchBtn.css("border"); 
	strButtonPadding = objSearchBtn.css("padding-top");
	strButtonHeight = objSearchBtn.css("height");
	strSelectHeight = $( "#unitCombo-button" ).css("height"); 

	// Converts measurements to double
	dblSelectHeight = parseFloat(strSelectHeight.substring(0, strSelectHeight.length - 2));
	dblButtonHeight = parseFloat(strButtonHeight.substring(0, strButtonHeight.length - 2));
	dblButtonPadding = parseFloat(strButtonPadding.substring(0, strButtonPadding.length - 2));
	dblButtonBorder = parseFloat(strButtonBorder.substring(0, strButtonBorder.length - 2));
	
	// Increases the button padding to adjust the height 
	dblNewBtnPadding = (( dblSelectHeight - dblButtonHeight ) / 2 ) + dblButtonPadding;
	objSearchBtn.css("padding-top", dblNewBtnPadding);
	objSearchBtn.css("padding-bottom", dblNewBtnPadding);
}




