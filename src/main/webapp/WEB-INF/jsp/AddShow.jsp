<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   	
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add New Show</title>


<style type = "text/css">
	<%@ include file="/WEB-INF/static/SimpleFormSheet.css" %>
</style>
	
<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script>
$('#newshow').submit(function() { // catch the form's submit event
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

	<%@ page import = "java.util.List, java.util.Date, com.restapi.model.Movie" %>
	
	<div id = "showform">
		<form action = "/addShow" method = "post" id = "newshow">
			<h2 id = "title">New Show</h2>
			<table>
			<tr>
				<td><label class = "label">Screen:</label></td>
			<td><select name = "screen" class = "input" id = "screenno">
				<%
					Object ob = request.getAttribute("screens");
					 if(ob != null)
					{
						@SuppressWarnings("unchecked")
						List<Integer> screens = (List<Integer>) ob;
						 for(int screen : screens)
						{
							out.println("<option value = \"" + screen + "\">" + screen + "</option>");
						}
					} 
				%>
			</select> </td></tr>
			<tr><td><label class = "label">Date:</label></td>
			<td><input type="date" class ="input" name="show-date" id = "date"></td></tr>
			<tr><td><label class = "label">Time:</label></td>
			<td><input type="time" class ="input" name="show-time" id = "time"></td></tr>
			<tr><td><label class = "label">Movie:</label></td>
			<td><select name = "movie" class = "input" id = "movie">
				<%
					Object movieOb = request.getAttribute("movies");
					if(movieOb != null)
					{
						@SuppressWarnings("unchecked")
						List<Movie> movies = (List<Movie>) movieOb;
						for(Movie movie : movies)
						{
							out.println("<option value = \"" + movie.getMovieID() + "\">"+ movie.getTitle() + "</option>");
						}
					}
				%>
			</select></td></tr>
			</table>
			<input type = "submit" id = "button" value = "Create Show">
			
		</form>
	</div>
</body>
</html>