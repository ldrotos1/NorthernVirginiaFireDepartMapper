/**
 * Louis Drotos
 * March 16, 2015
 * 
 * This file contains code that handles the query pane search.
 */

$(function(){
	
	// Wires the click event
	$(" #searchBtn ").click(function() {
		runQuery(objGlobalVars.arrStations);
	});
})

function runQuery( arrStations ) {

	// Declares variables
	var strUnit,
	strDepart,
	strStation;
	
	// Gets query parameter values
	strUnit = $( "#unitCombo" ).val();
	strDepart = $( "#deptCombo" ).val();
	strStation = $( "#stationInput" ).val(); 
	
	// Determines the type of query based on query parameter
	if( strUnit !== 'None Selected' ) {	
		
		// Query by unit type
		queryUnits( arrStations, strUnit );
	}
	else if ( strDepart !== 'None Selected' ) {
		
		// Query by department
		queryDepartment( arrStations, strDepart );
	}
	else if ( strStation !== '' ) {
		
		// Query by station name
		queryStation( arrStations, strStation );
	}
	else {
		
		// No query parameter provided
		alert( "Please select a query parameter." );
	}
}

function queryUnits( arrStations, targetUnit ) {
	
}

function queryDepartment( arrStations, targetDept ) {
	
	// Iterates through station array
	$.each(arrStations, function( index, value ) {
		
		// Determines if current station should be selected
		if( value.department === targetDept ) {
			value.toggleSelection( true );
		}
		else {
			value.toggleSelection( false )
		}
	})
}

function queryStation( arrStations, targetStation ) {
	
	var found = false;
	
	// Iterates through station array
	$.each(arrStations, function( index, value ) {
		
		// Determines if current station should be selected
		if( value.name === targetStation ) {
			value.toggleSelection( true );
			found = true;
		}
		else {
			value.toggleSelection( false )
		}
	})
	
	// Informs user if a matching station was not found
	if ( found === false ) {
		alert( "There is no station by that name." );
	}
}



