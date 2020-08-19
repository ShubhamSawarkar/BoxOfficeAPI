<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create New User</title>

<style type = "text/css">
	<%@ include file="/WEB-INF/static/SimpleFormSheet.css" %>
</style>

<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script>
$('#userform').submit(function() { // catch the form's submit event
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
		<form action = "/addUser" method = "post" id = "userform">
			<h2 id = "title">New User</h2>
			<table>
			<tr>
				<td><label class = "label">Username:</label></td>
				<td><input type = "text" class = "input" name = "username"></td></tr>
			<tr><td><label class = "label">Password:</label></td>
			<td><input type="password" class ="input" name="password"></td></tr>
			<tr><td><label class = "label">Confirm Password:</label></td>
			<td><input type="password" class ="input" name="confirm"></td></tr>
			<tr><td><label class = "label">Role:</label></td>
				<td><select name = "admin" class = "input" id = "screenno">
				<option value = "false">User</option>
				<option value = "true">Administrator</option>
				</select>
			</tr>
			</table>
			<input type = "submit" id = "button" value = "Sign Up">
		</form>
	</div>
</body>
</html>