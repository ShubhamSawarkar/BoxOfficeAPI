<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View Seats</title>

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
	<%@ page import = "java.util.List, com.restapi.model.Seat, com.restapi.model.Show, java.util.Collections" %>
	<form id = "chooser" action = "/SeatsViewChange" method = "post">
				 
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
		 
		 <input type = submit class = "showselectorbtn" value = "View">
	</form>
	<div id = "hall">
		<%
			Object seatsObj = request.getAttribute("seats");
			Object statusObj = request.getAttribute("status");

			int currentRow = 1;
		
			if((seatsObj != null) && (statusObj != null))
			{
				@SuppressWarnings("unchecked")
				List<Seat> seats = (List<Seat>) seatsObj;
				@SuppressWarnings("unchecked")
				List<Boolean> status = (List<Boolean>) statusObj;
				
				out.println("<label class = \"rowlabel\">A</label>");
				for(Seat seat: seats)
				{
					if(seat.getRowNo() == currentRow+1)
					{
						out.println("<br>");
						out.println("<label class = \"rowlabel\">" + (char)(64 + currentRow + 1) + "</label>");
						currentRow++;
					}
					out.println("<input type = \"checkbox\" disabled = true class = " + ((status.get(seats.indexOf(seat)) == true) ? "available" : "reserved") +">");
					if(seat.isSpaceAfter() && (seats.indexOf(seat) != seats.size()-1) && (seats.get(seats.indexOf(seat)+1).getRowNo() != currentRow))
					{
						out.println("<br><br>");
					}
					else if((seat.isSpaceAfter()) && (seats.indexOf(seat) != seats.size()-1))
					{
						out.println("&nbsp; &nbsp; &nbsp; &nbsp;");
					}
				}
				
				out.println("<label id = \"directionlabel\">ALL EYES THIS WAY PLEASE</label>");
			}
		%>
	</div>

</body>
</html>