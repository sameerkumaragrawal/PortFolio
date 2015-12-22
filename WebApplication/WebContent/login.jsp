<%@page import="org.sameer.java.PortFolio"%>
<%@page import="org.sameer.java.Strategy"%>
<%@page import="org.sameer.java.Constants"%>
<%@page import="java.util.List"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<HTML>
<HEAD>
<TITLE>Login</TITLE>
</HEAD>

<BODY>
	<% 
		String error_msg = "";
		if(session != null){
			error_msg = (String)session.getAttribute(Constants.ERROR_MSG_ATTRIBUTE);
			if(error_msg == null)
				error_msg = "";
		}
		%>
	<%=error_msg%><br/>
	Login:
	<form action="/WebApplication/Login" method="post">
			Email: <input type="email" name="email">
			Password: <input type="password" name="password">
			<input type="submit" value="Login">
	</form>
	<form action="/WebApplication/signup.jsp" method="post">
			<input type="submit" value="SignUp">
	</form>
	<% session.removeAttribute(Constants.ERROR_MSG_ATTRIBUTE); %>
</BODY>
</HTML>