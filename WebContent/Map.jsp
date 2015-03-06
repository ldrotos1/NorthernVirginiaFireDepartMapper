<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="http://cdn.leafletjs.com/leaflet-0.7.3/leaflet.css" />
		<link rel="stylesheet" type="text/css" href="css/layout.css">
		<link rel="stylesheet" type="text/css" href="css/jquery-ui.min.css">
		<link rel="stylesheet" type="text/css" href="css/queryPane.css">
		<link rel="stylesheet" type="text/css" href="css/controlPane.css">
		<link rel="stylesheet" type="text/css" href="css/paneDesign.css">
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
		<script type="text/javascript" src="javascript/jquery-ui.min.js"></script>
		<script src="http://cdn.leafletjs.com/leaflet-0.7.3/leaflet.js"></script>
		<!--<script src="javascript/jquery-ui.js" type="text/javascript"></script>-->
		<script src="javascript/map.js" type="text/javascript"></script>
		<script src="javascript/controlBuilder.js" type="text/javascript"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Northern Virginia Fire Department Mapper</title>
	</head>

	<body>
		<header class="ui-widget-header">
			<h1 id="title">Northern Virginia Fire Department Mapper</h1>
		</header>
		
		<div id='wrapper'>
			<div id='map'></div>
			<div id='queryPane' class='pane'>
				<label id='queryLabel' class='paneMainLabel'>Query</label>
					
				<div id='queryControls'>
						
					<div id='unitDiv'>
						<div class='labelDiv'>
							<label class='paneSubLabel'>Unit Type:</label><br>
						</div>	
						<select id="unitCombo" class='querySelect'>
							<option>Mass Casualty Support</option>
						</select>
					</div>
						
					<div id='deptDiv'>
						<div class='labelDiv'>
							<label class='paneSubLabel'>Department:</label>
						</div>	
						<select id="deptCombo" class='querySelect'>
							<option>Arlington County Fire Department</option>
						</select>
					</div>
						
					<div id='stationDiv'>
						<div class='labelDiv'>
							<label class='paneSubLabel'>Station:</label>
						</div>
						<select id="stationCombo" class='querySelect'>
							<option>Bailey's Crossroads</option>
						</select>
					</div>
						
					<div id='searchBtnDiv'>
						<input id="searchBtn" type="submit" value="Search">
					</div>
				</div>
			</div>
		
			<div id='controlPane' class='pane'>
				<span id='radioBtns'>
					<input type="checkbox" id="check1"><label for="check1">Query</label>
					<input type="checkbox" id="check2"><label for="check2">Incident</label>
				</span>
				<button id="clearButton">Clear</button>
			</div>
			
		</div>
		
	</body>
</html>