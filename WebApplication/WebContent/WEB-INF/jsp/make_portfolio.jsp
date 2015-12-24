<%@page import="org.sameer.java.Strategy"%>
<%@page import="java.util.List"%>
<%@page import="org.sameer.java.Constants"%>
<%@page import="org.sameer.java.PortFolio"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<HTML>
<HEAD>
<TITLE>Make Portfolio</TITLE>
</HEAD>

<BODY>
	<div style="float: left">
		<FORM action="/WebApplication/updatePortfolio" method="post">
			<%
				Object iflagObj = session.getAttribute(Constants.INVESTMENT_FLAG_ATTRIBUTE);
				String err_msg = (String) session.getAttribute(Constants.ERROR_MSG_ATTRIBUTE);
				if (err_msg == null)
					err_msg = "";
				int iflag;
				if (iflagObj == null) {
					iflag = Constants.NEW_INVEST;
					session.setAttribute(Constants.INVESTMENT_FLAG_ATTRIBUTE, Constants.NEW_INVEST);
				} else
					iflag = (Integer) iflagObj;

				List<Strategy> strategies = null;
				String wt[] = new String[Constants.NUM_STRATEGIES];
				if (iflag == Constants.EDIT_INVEST) {
					PortFolio pf = (PortFolio) session.getAttribute(Constants.PORTFOLIO_ATTRIBUTE);
					strategies = pf.getStrategies();

					for (Strategy s : strategies) {
						wt[s.getId()] = String.valueOf(s.getWeight());
					}
				}
			%>
			<c:choose>
				<c:when test="<%=(iflag == Constants.EDIT_INVEST)%>">
        			Edit Portfolio: 
        			<br />
				</c:when>
				<c:otherwise>
        			New Portfolio: 
        			<br />
				</c:otherwise>
			</c:choose>
			<%=err_msg%><br />
			<%
				session.removeAttribute(Constants.ERROR_MSG_ATTRIBUTE);
			%>
			<%
				for (int i = 0; i < Constants.NUM_STRATEGIES; i++) {
			%>
			<br />Strategy<%=i%> <INPUT type="number" step="0.01" min="0.01"
				name="strategy" value="<%=wt[i]%>" />

			<%
				}
			%>
			<BR> <INPUT type="submit" name="Submit" value="Invest">
		</FORM>
	</div>
	<div style="margin-left:10%; float: left">
		<form action="/WebApplication/RecommendStrategy" method="post">
			<input type="submit" value="Recommend Strategies">
		</form>
		<%
			Object strategyObj = session.getAttribute(Constants.RECOMMEND_FLAG_ATTRIBUTE);
			List<Integer> strategyList = null;
			if(strategyObj != null){
				strategyList = (List<Integer>)strategyObj;
			}
			
			if(strategyList != null){
				if(strategyList.size() == 0){
					out.println("No Recommendations Available<br/>");
				}
				out.println("Most Popular Strategies are: <br/>");
				for(int strategyId: strategyList){
					out.println("Strategy"+strategyId+"<br/>");
				}
			}else{
				
			}
			session.removeAttribute(Constants.RECOMMEND_FLAG_ATTRIBUTE);
		%>
	</div>
</BODY>
</HTML>