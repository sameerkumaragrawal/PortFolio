<%@page import="org.sameer.java.PortFolio"%>
<%@page import="org.sameer.java.Strategy"%>
<%@page import="org.sameer.java.Constants"%>
<%@page import="java.util.List"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<HTML>
<HEAD>
<TITLE>Portfolio Profile</TITLE>
</HEAD>

<BODY>
	<% 
		PortFolio pf = (PortFolio) session.getAttribute(Constants.PORTFOLIO_ATTRIBUTE);
		String error_msg = (String)session.getAttribute(Constants.ERROR_MSG_ATTRIBUTE);
		if(error_msg == null)
			error_msg = "";
	 %>
	Your PortFolio: <%=pf.getId()%> created at <%=pf.getTimestamp().toString() %>
	<TABLE border="1">
		<TBODY>
			<%
				
				List<Strategy> strategies = pf.getStrategies();
				for (Strategy s: strategies) {
			%>
			<TR>
				<TH>Strategy <%=s.getId()%>, with Weight: <%=s.getWeight()%></TH>
			</TR>
			<%
				}
			%>
		</TBODY>
	</TABLE>
	<form action="/WebApplication/EditInvestment" method="post">
    	<input type="submit" value="Edit Portfolio">
	</form>
	<form action="/WebApplication/ListPortfolios" method="post">
    	<%=error_msg%><br/>
    	<% session.removeAttribute(Constants.ERROR_MSG_ATTRIBUTE); %>
    	<input type="submit" value="List All Portfolios">
	</form>
</BODY>
</HTML>