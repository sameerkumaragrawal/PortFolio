<%@page import="org.sameer.java.User"%>
<%@page import="org.sameer.java.PortFolio"%>
<%@page import="org.sameer.java.Strategy"%>
<%@page import="org.sameer.java.Constants"%>
<%@page import="java.util.List"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<HTML>
<HEAD>
<TITLE>User Profile</TITLE>
</HEAD>

<BODY>
<%
	User cur = (User)session.getAttribute(Constants.USER_ATTRIBUTE);
%>
<h1>Welcome <%=cur.getEmail()%></h1>	
	Your PortFolios:
	<form action="/WebApplication/ViewPortfolio" method="post">
			<%
				List<PortFolio> invests = (List<PortFolio>) session.getAttribute(Constants.PORTFOLIO_LIST_ATTRIBUTE);
				for (PortFolio i: invests) {
			%>
				Portfolio <%=i.getId()%><button type="submit" value=<%=i.getId()%> name="ViewPortfolio">View</button><BR>
			<%
				}
			%>
	</form>
	<form action="/WebApplication/MakeAnotherInvestment" method="post">
    	<input type="submit" value="Make Another Investment">
	</form>
	<form action="/WebApplication/Logout" method="post">
    	<input type="submit" value="Logout">
	</form>
</BODY>
</HTML>