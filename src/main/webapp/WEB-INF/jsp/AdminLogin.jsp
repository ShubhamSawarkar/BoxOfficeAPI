<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>BoxOffice - Login</title>

<style>
	.login-form
		{
			border: 3px solid DodgerBlue;
			border-radius: 25px;
			padding: 8px;
			width: 40%;
			margin: auto;
			height: 420px;
			background-color: white;
			margin-top: 163px;
		}
		
	.logo
		{
			text-align: center;
			font-size: 40px;
			font-family: "Times New Roman", Times, Serif;
			color: DodgerBlue; 
			margin: 8px;
		}
		
	.label
		{
			text-align: left;
			color: DodgerBlue;
			font-family: Roboto, sans-serif;
			font-size: 14px;
			margin: 35px 0px;
			height: 20px;
		}
		
	.input
		{
			border: 3px solid DodgerBlue;
			border-top-style: none;
			border-left-style: none;
			border-right-style: none;
			width: 100%;
			margin: auto;
			height: 30px;
			color: purple;
			font-size: 23px;
			font-style: italic;
		}
		
	.button
		{
			border: 3px solid DodgerBlue;
			border-radius: 10px;
			text-align: center;
			font-family: Roboto, sans-serif;
			font-size: 14px;
			color: DodgerBlue;
			margin-top: 35px;
			width: 30%;
			margin-left: 35%;
			margin-right: auto;
			height: 40px;
		}
		
	#submit:hover
		{
			background-color: DodgerBlue;
			color: white;
		}
		
	form
		{
			width: 75%;
			margin: auto;
			height: 90%;
		}
	
	body
  		{
	  		background-color: rgba(30, 144, 255, 0.1);
  		}
  		
  	#loginFailed
  		{
  			color: red;
  			font-size: 14px;
  		}
</style>
</head>

<body>
	<div class = "login-form">
	
	<form action = "/login" method = "post">
			<h1 class = "logo">BoxOffice</h1>
			
				<%
					String errorParam = request.getParameter("error");
					if(errorParam != null && errorParam.equals("true"))
					{
						out.print("<h2 id = \"loginFailed\">Invalid Credentials!</h2>");
					}
				%>
			
				<h2 class = "label">Username</h2>
			
				<input type = "text" name = "Username" class = "input">
		
				<h2 class = "label">Password</h2>
			
				<input type = "password" name = "Password" class = "input">
			
				<input type = "submit" value = "Log In" class = "button" id = "submit">
		
	</form>
	
	
	</div>
</body>

</html>