/**
 * Louis Drotos
 * March 16, 2015
 * 
 * This file contains code that handles the query pane functionality
 */

$(function(){
	
	// Initializes the search by unit type select menu
	$( "#unitCombo" ).selectmenu().on( "selectmenuselect", function( event, ui ) {

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
	
	
	// Initializes the search by department select menu
	$( "#deptCombo" ).selectmenu().on( "selectmenuselect", function( event, ui ) {

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
	
	// Initializes the search by name auto-complete input
	$( "#stationInput" ).autocomplete().on( "autocompleteselect autocompletechange", function( event, ui ) {
		
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

	// Gets the list of station names and adds them to the auto-complete input 
	$.getJSON( "/NovaFireMapper/data",
		{ RequestFor: "AllStationNames" },
		function( data ){
			$( "#stationInput" ).autocomplete( "option", "source", data);
	})
	
	// Initializes the search button
	$(" #searchBtn ").button().click(function() {
		nsStationQuery.runQuery(objGlobalVars.arrStations);
	});
	
	// Hides the query pane
	$( "#queryPane" ).hide();
})

/**
 * Station query namespace. 
 */
nsStationQuery = function() {
	
	/**
	 * Selects all stations that have at least one apparatus of a target type
	 * @param arrStations The list of stations within the map
	 * @param targetDept The target unit type
	 */
	function queryUnits( arrStations, targetUnit ) {
		
		$.getJSON( "/NovaFireMapper/data", { 
			RequestFor: "QueryUnitType",
			type: targetUnit
			},
			function( data ){
					
				$.each(arrStations, function(i, value) {
					
					if ($.inArray(value.id, data) !== -1) {
						
						// Selects the station
						value.toggleSelection(true);
					}
					else {
						
						// Unselects the station
						value.toggleSelection(false);
					}
				});
		})
			.fail(function(){
				alert("Unable to complete unit query.");
			});
	}

	/**
	 * Selects all stations within the map that belong to a target department.
	 * @param arrStations The list of stations within the map
	 * @param targetDept The target department
	 */
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

	/**
	 * Queries the a station based on it's name and selects that station
	 * in the map. 
	 * @param arrStations The list of stations within the map.
	 * @param targetStation The name of the target station.
	 */
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
	
	/**
	 * Public functions
	 */
	return {
		
		/**
		 * This method is used to query the server for stations that match
		 * the query parameters. Stations that match the query will be selected
		 * on the map
		 * @param arrStations The list on stations depicted on the map.
		 */
		runQuery: function( arrStations ) {
			
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
	}
	
}();






