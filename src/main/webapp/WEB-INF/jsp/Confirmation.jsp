<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Result</title>
<style>
	h1
	{
		font-family: Roboto, sans-serif;
		font-size: 18px;
		text-align: center;
		width: 90%;
		margin: auto;
		margin-top: 150px;
	}
</style>
</head>
<body>
	<%
	if(request.getAttribute("status") == "success")
	{
		out.println("<h1> Congratulations! Operation is Successful! </h1>");
	}
	else
	{
		out.println("<h1> Oops! Operation Failed! Please check the information provided! </h1>");
	}
	%>
</body>
</html>