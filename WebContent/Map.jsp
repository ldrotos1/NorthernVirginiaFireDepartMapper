<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="http://cdn.leafletjs.com/leaflet-0.7.3/leaflet.css" />
		<link rel="stylesheet" type="text/css" href="css/layout.css">
		<link rel="stylesheet" type="text/css" href="css/jquery-ui.min.css">
		<link rel="stylesheet" type="text/css" href="css/jquery-ui.structure.css">
		<link rel="stylesheet" type="text/css" href="css/jquery-ui.theme.css">
		<link rel="stylesheet" type="text/css" href="css/query.css">
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
		<script src="http://cdn.leafletjs.com/leaflet-0.7.3/leaflet.js"></script>
		<script src="javascript/jquery-ui.js" type="text/javascript"></script>
		<script src="javascript/map.js" type="text/javascript"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Northern Virginia Fire Department Mapper</title>
	</head>
	<body>
		<header class="ui-widget-header">
			<h1 id="title">Northern Virginia Fire Department Mapper</h1>
		</header>
		
		<div id='wrapper'>
			<div id='map'></div>
			
			<div id='queryPane'>
				<form action="">
					<label id='queryLabel' class='paneLabel'>Query</label>
					
					<div id='queryControls'>
						
						<div id='unitDiv'>
							<label class='queryLabel'>Unit Type:</label><br>
							<select id="unitCombo">
								<option>Mass Casualty Support</option>
							</select>
						</div>
						
						<div id='deptDiv'>
							<label class='queryLabel'>Department:</label><br>
							<select id="deptCombo">
								<option>Arlington County Fire Department</option>
							</select>
						</div>
						
						<div id='stationDiv'>
							<label class='queryLabel'>Station:</label><br>
							<select id="stationCombo">
								<option>Bailey's Crossroads</option>
							</select>
						</div>
						
						<div id='searchBtnDiv'>
							<input id="searchBtn" type="submit" value="Search">
						</div>
						
					</div>
				</form>
			</div>
		</div>
		
	</body>
</html>