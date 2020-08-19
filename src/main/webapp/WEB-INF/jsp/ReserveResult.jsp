<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Reserve Seats</title>
<style type = "text/css">
	<%@ include file="/WEB-INF/static/SeatsViewSheet.css" %>
</style>

<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script>
$('#chooser').submit(function() { // catch the form's submit event
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
	<%@ page import = "com.restapi.service.SeatService, com.restapi.model.Show, com.restapi.model.AdminControl, java.util.List" %>
	
	<form id = "chooser" action = "/SeatSelect" method = "post">
	<select name = "show" class = "input">
		 	<%
		 		Object obj = request.getAttribute("shows");
		 		if(obj != null)
				{
					@SuppressWarnings("unchecked")
					List<Show> shows = (List<Show>) obj;
			 		for(Show show : shows)
					{
						out.println("<option value = \"" + show.getID() + "\">" + show.getShowDate() + "	" + show.getShowTime() + "	Screen-"+ show.getScreenNo() + "</option>");
					}
				} 
		 	%>
		 </select>
		 
		 <input type = submit class = "showselectorbtn" value = "Next">
		</form>
</body>
</html>