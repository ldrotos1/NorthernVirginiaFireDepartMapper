/**
 * Louis Drotos
 * March 1, 2015
 * 
 * This file contains code that is used for initializing
 * Jquery UI controls.
 */


$(function() {
	
	// Initializes the controls on the control pane 
	$(" #clearButton ").button();
	$( "#radioBtns" ).buttonset();
	
	// Initializes the controls on the query pane
	$(" #searchBtn ").button();
	$( "#unitCombo" ).selectmenu();
	$( "#deptCombo" ).selectmenu();
	$( "#stationCombo" ).selectmenu();
	
	alignSearchBtn();
});

function alignSearchBtn() {
	
	var objCmbLabel,
	objSearchBtn,
	strLabelMargin,
	strLabelHeight,
	strCmbHeight,
	dblLabelMargin,
	dblLabelHeight,
	dblCmbHeight;
	
	
	
	objCmbLabel = $( ".labelDiv" );
	strLabelMargin = objCmbLabel.css("margin-bottom")
	strLabelHeight = objCmbLabel.css("height");
	
	dblLabelMargin = parseFloat(strLabelMargin.substring(0, strLabelMargin.length - 2));
	dblLabelHeight = parseFloat(strLabelHeight.substring(0, strLabelHeight.length - 2));
	
	strCmbHeight = $( ".ui-selectmenu-button" ).css("height"); 
	dblCmbHeight = parseFloat(strCmbHeight.substring(0, strCmbHeight.length - 2));
	
	objSearchBtn = $( "#searchBtnDiv" );
	objSearchBtn.css("margin-top", dblLabelMargin + dblLabelHeight);
	objSearchBtn.css("")
	
	//$( "#searchBtnDiv" ).css("margin-top", dblLabelMargin + dblLabelHeight);
	//position = $( "#stationDiv" ).offset().top;
	//$( "#searchBtn" ).css("position", "fixed");
	//$( "#searchBtn" ).css("top", position);
}




