<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add New Movie</title>

<style type = "text/css">
	<%@ include file="/WEB-INF/static/SimpleFormSheet.css" %>
</style>

<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script>
$('#movieform').submit(function() { // catch the form's submit event
    $.ajax({ // create an AJAX call...
        data: $(this).serialize(), // get the form data
        type: $(this).attr('method'), // GET or POST
        url: $(this).attr('action'), // the file to call
        success: function(response) { // on success..
            $('#selectedform').html(response); // update the DIV
        }
    });
    return false; // cancel original event to prevent form submitting
});
</script>
</head>
<body>
	<div id = "showform">
		<form action = "/addMovie" method = "post" id = "movieform" >
			<h2 id = "title">New Movie</h2>
			<table>
			<tr>
				<td><label class = "label">Title:</label></td>
				<td><input type = "text" class = "input" name = "title"></td></tr>
			<tr><td><label class = "label">Runtime (Minutes):</label></td>
			<td><input type="text" class ="input" name="runtime"></td></tr>
			<tr><td><label class = "label">Releasing On:</label></td>
			<td><input type="date" class ="input" name="release-date" id = "date"></td></tr>
			</table>
			<input type = "submit" id = "button" value = "Add Movie">
		</form>
	</div>
</body>
</html>