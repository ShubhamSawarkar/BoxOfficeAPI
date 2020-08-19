<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Control Panel - BoxOffice</title>

<style type = "text/css">
	<%@ include file="/WEB-INF/static/CpanelSheet.css" %>
</style>

</head>

<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script>
$(document).ready(function() {
	$( "#addshow" ).click(function() {
		$( "#selectedform" ).load( "AddShow" );
		$( "#addshow" ).css("background-color", "DodgerBlue");
		$( "#addshow" ).css("color", "white");
		$( "#addmovie" ).css("background-color", "white");
		$( "#addmovie" ).css("color", "DodgerBlue");
		$( "#adduser" ).css("background-color", "white");
		$( "#adduser" ).css("color", "DodgerBlue");
		$( "#viewstatus" ).css("background-color", "white");
		$( "#viewstatus" ).css("color", "DodgerBlue");
		$( "#disableseat" ).css("background-color", "white");
		$( "#disableseat" ).css("color", "DodgerBlue");
	});
});

$(document).ready(function() {
	$( "#addmovie" ).click(function() {
		$( "#selectedform" ).load( "AddMovie" );
		$( "#addmovie" ).css("background-color", "DodgerBlue");
		$( "#addmovie" ).css("color", "white");
		$( "#adduser" ).css("background-color", "white");
		$( "#adduser" ).css("color", "DodgerBlue");
		$( "#viewstatus" ).css("background-color", "white");
		$( "#viewstatus" ).css("color", "DodgerBlue");
		$( "#disableseat" ).css("background-color", "white");
		$( "#disableseat" ).css("color", "DodgerBlue");
		$( "#addshow" ).css("background-color", "white");
		$( "#addshow" ).css("color", "DodgerBlue");
	});
});

$(document).ready(function() {
	$( "#adduser" ).click(function() {
		$( "#selectedform" ).load( "AddUser" );
		$( "#adduser" ).css("background-color", "DodgerBlue");
		$( "#adduser" ).css("color", "white");
		$( "#addmovie" ).css("background-color", "white");
		$( "#addmovie" ).css("color", "DodgerBlue");
		$( "#viewstatus" ).css("background-color", "white");
		$( "#viewstatus" ).css("color", "DodgerBlue");
		$( "#disableseat" ).css("background-color", "white");
		$( "#disableseat" ).css("color", "DodgerBlue");
		$( "#addshow" ).css("background-color", "white");
		$( "#addshow" ).css("color", "DodgerBlue");
	});
});

$(document).ready(function() {
	$( "#viewstatus" ).click(function() {
		$( "#selectedform" ).load( "SeatsView" );
		$( "#viewstatus" ).css("background-color", "DodgerBlue");
		$( "#viewstatus" ).css("color", "white");
		$( "#addmovie" ).css("background-color", "white");
		$( "#addmovie" ).css("color", "DodgerBlue");
		$( "#adduser" ).css("background-color", "white");
		$( "#adduser" ).css("color", "DodgerBlue");
		$( "#disableseat" ).css("background-color", "white");
		$( "#disableseat" ).css("color", "DodgerBlue");
		$( "#addshow" ).css("background-color", "white");
		$( "#addshow" ).css("color", "DodgerBlue");
	});
});

$(document).ready(function() {
	$( "#disableseat" ).click(function() {
		$( "#selectedform" ).load( "ShowSelect" );
		$( "#disableseat" ).css("background-color", "DodgerBlue");
		$( "#disableseat" ).css("color", "white");
		$( "#addmovie" ).css("background-color", "white");
		$( "#addmovie" ).css("color", "DodgerBlue");
		$( "#adduser" ).css("background-color", "white");
		$( "#adduser" ).css("color", "DodgerBlue");
		$( "#viewstatus" ).css("background-color", "white");
		$( "#viewstatus" ).css("color", "DodgerBlue");
		$( "#addshow" ).css("background-color", "white");
		$( "#addshow" ).css("color", "DodgerBlue");
	});
});

</script>

<body>
	<div id = "header">
		<h1 id = "logo">BoxOffice</h1>
		<form action="/logout" class = "logoutform">
					<input class = "button" id = "logoutbutton" type = "submit" value = "Log Out">
		</form>
	</div>
	
	<div id = "panelbody">
	
		<div id = "selector" class = "container">
			<button class = "sidebutton" type = "button" id = "viewstatus">View Status</button>
			<button class = "sidebutton" type = "button" id = "addshow">Add Show</button>
			<button class = "sidebutton" type = "button" id = "addmovie">Add Movie</button>
			<button class = "sidebutton" type = "button" id = "adduser">Add User</button>
			<button class = "sidebutton" type = "button" id = "disableseat">Reserve Seat</button> 
			<!--<button class = "sidebutton" type = "button" id = "addscreen">Add Screen</button>
			<button class = "sidebutton" type = "button" id = "addseat">Add Seat</button>  -->
			<!-- <button class = "sidebutton" type = "button" id = "changeprice">Change Pricing</button>
			<button class = "sidebutton" type = "button" id = "changespace">Change Spacing</button> -->
		</div>
		
		<div id = "selectedform">
		</div>
	</div>
	
</body>
</html>