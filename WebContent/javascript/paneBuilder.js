/**
 * Louis Drotos
 * March 1, 2015
 * 
 * This file contains code that is used for initializing the application's
 * widgets, to include positioning the controls and wiring events.
 */

/**
 * @function Initializes the map pane widgets once the document
 * is ready. 
 */
$(function() {
	
	// Initializes the widgets on the control pane 
	$(" #clearButton ").button();
	$( "#radioBtns" ).buttonset();
	
	// Initializes the widgets on the query pane
	$(" #searchBtn ").button();
	$( "#unitCombo" ).selectmenu();
	$( "#deptCombo" ).selectmenu();
	$( "#stationInput" ).autocomplete({
		source: []
	});
	
	// Positions and sizes the search button
	alignSearchBtn();
	setQueryControlHeights();
	
	// Gets the list of station names and add it to the auto-complete input 
	$.getJSON( "/NorthernVirginiaFireDepartMapper/data",
		{ RequestFor: "AllStationNames" },
		function( data ){
			$( "#stationInput" ).autocomplete( "option", "source", data);
	})
	
	// Wires event handlers 
	wireParamWidgetEvents(); 
	
});

/**
 * @function Wires event controls for the select and input widgets in
 * the query pane. The events will ensure that value is selected in one
 * of the widgets then the selected values in the other two widgets are
 * cleared.  
 */
function wireParamWidgetEvents() {

	// Wires the event for the unit selection widget
	$( "#unitCombo" ).on( "selectmenuselect", function( event, ui ) {

		var objCombo;
		if (ui.item.label !== "None Selected") {
			
			// Sets the department selection widget to its default 
			objCombo = $( '#deptCombo' ); 
			objCombo.val( 'None Selected' );
			objCombo.selectmenu("refresh");
			
			// Sets the station name widget to it's default
			$( '#stationInput' ).val( '' );		
		}
	});
	
	// Wires the event for the department selection widget
	$( "#deptCombo" ).on( "selectmenuselect", function( event, ui ) {

		var objCombo;
		if (ui.item.label !== "None Selected") {
			
			// Sets the unit selection widget to its default 
			objCombo = $( '#unitCombo' ); 
			objCombo.val( 'None Selected' );
			objCombo.selectmenu("refresh");
			
			// Sets the station name widget to it's default
			$( '#stationInput' ).val( '' );		
		}
	});
	
	//Wires the event for the station name widget
	$( "#stationInput" ).on( "autocompleteselect autocompletechange", function( event, ui ) {
		
		// Declares objects
		var objUnitCombo,
		objDepartCombo;
		
		// Gets the selection widgets
		objUnitCombo = $( '#unitCombo' );
		objDepartCombo = $( '#deptCombo' );
		
		// Sets to the default values
		objUnitCombo.val( 'None Selected' );
		objDepartCombo.val( 'None Selected' );
		objUnitCombo.selectmenu("refresh");
		objDepartCombo.selectmenu("refresh");
	});
	
	
}

/**
 * @function aligns the top of the search button with the 
 * top of the select boxes.
 */
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

/**
 * @function Sets the size of the search button and the station input
 * to be the same as the height of the select boxes. 
 */
function setQueryControlHeights() {
	
	var objSearchBtn,
	objSelect,
	objStatInput,
	strSelectHeight,
	strSelectBorder,
	strBtnHeight,
	strBtnPadding,
	strBtnBorder,
	strStatInputHeight,
	strStatInputPadding,
	strStatInputBorder,
	dblSelectHeight,
	dblSelectBorder,
	dblBtnHeight,
	dblBtnPadding,
	dblBtnBorder,
	dblStatInputHeight,
	dblStatInputPadding,
	dblStatInputBorder,
	dblNewBtnPadding,
	dblNewStatInputPadding;
	
	// Gets references to controls
	objSearchBtn = $( "#searchBtn" );
	objSelect = $( "#unitCombo-button" ); 
	objStatInput = $( "#stationInput" ); 
	
	// Gets measurements
	strBtnBorder = objSearchBtn.css("border"); 
	strBtnPadding = objSearchBtn.css("padding-top");
	strBtnHeight = objSearchBtn.css("height");
	strSelectHeight = objSelect.css("height");
	strSelectBorder = objSelect.css("border");
	strStatInputHeight = objStatInput.css("height");
	strStatInputPadding = objStatInput.css("padding-top");
	strStatInputBorder = objStatInput.css("border");

	// Converts measurements to double
	dblSelectHeight = parseFloat(strSelectHeight.substring(0, strSelectHeight.length - 2));
	dblSelectBorder = parseFloat(strSelectBorder.substring(0, strSelectBorder.length - 2));
	dblBtnHeight = parseFloat(strBtnHeight.substring(0, strBtnHeight.length - 2));
	dblBtnPadding = parseFloat(strBtnPadding.substring(0, strBtnPadding.length - 2));
	dblBtnBorder = parseFloat(strBtnBorder.substring(0, strBtnBorder.length - 2));
	dblStatInputHeight = parseFloat(strStatInputHeight.substring(0, strStatInputHeight.length - 2));
	dblStatInputPadding = parseFloat(strStatInputPadding.substring(0, strStatInputPadding.length - 2));
	dblStatInputBorder = parseFloat(strStatInputBorder.substring(0, strStatInputBorder.length - 2));
	
	// Increases the button padding to adjust the height 
	dblNewBtnPadding = ((( dblSelectHeight + ( dblSelectBorder * 2 )) - dblBtnHeight ) / 2 ) + dblBtnPadding;
	objSearchBtn.css("padding-top", dblNewBtnPadding);
	objSearchBtn.css("padding-bottom", dblNewBtnPadding);
	
	// Increases the input padding to adjust the height
	dblNewStatInputPadding = (( dblSelectHeight + ( dblSelectBorder * 2 )) - (dblStatInputHeight + 2 * dblStatInputBorder)) / 2;
	objStatInput.css("padding", dblNewStatInputPadding * 0.75);
}

