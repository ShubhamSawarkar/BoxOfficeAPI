<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Select a Seat</title>
<style type = "text/css">
	<%@ include file="/WEB-INF/static/SimpleFormSheet.css" %>
</style>

<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script>
$('#reserveform').submit(function() { // catch the form's submit event
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
		<form action = "/book" method = "post" id = "reserveform">
			<h2 id = "title">Reserve Seat</h2>
			<table>
			<tr>
				<td><label class = "label">Row:</label></td>
			<td><select name = "row" class = "input" id = "screenno">
				<%
					Object ob = request.getAttribute("maxRow");
					 if(ob != null)
					{
						int maxRow = (int) ob;
						 for(int row=1; row<=maxRow; row++)
						{
							out.println("<option value = \"" + row + "\">" + (char)(64 + row) + "</option>");
						}
					} 
				%>
			</select> </td></tr>
			
			<tr>
				<td><label class = "label">Seat:</label></td>
			<td><select name = "seat" class = "input" id = "screenno">
				<%
					Object obj = request.getAttribute("maxSeat");
					 if(obj != null)
					{
						int maxSeat = (int) obj;
						 for(int seat=1; seat<=maxSeat; seat++)
						{
							out.println("<option value = \"" + seat + "\">" + seat + "</option>");
						}
					} 
				%>
			</select> </td></tr>
			</table>
			<input type = "hidden" name = "show" value = <%= request.getAttribute("show") %> >
			<input type = "submit" id = "button" value = "Reserve Seat">
			
		</form>
	</div>
</body>
</html>