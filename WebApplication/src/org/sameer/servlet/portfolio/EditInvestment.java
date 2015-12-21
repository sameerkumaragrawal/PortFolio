package org.sameer.servlet.portfolio;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sameer.java.Constants;
import org.sameer.java.PortFolio;
import org.sameer.servlet.ServletSanity;

/**
 * Servlet implementation class EditInvestment
 */
@WebServlet("/EditInvestment")
public class EditInvestment extends HttpServlet {
	private static final long serialVersionUID = 5809526681708521379L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = ServletSanity.verifySession(request, response);
		if(session == null){
			return;
		}
		
		Timestamp cur_time = new Timestamp(new Date().getTime());
		PortFolio pf = (PortFolio)session.getAttribute(Constants.PORTFOLIO_ATTRIBUTE);
		long hour_diff = (cur_time.getTime() - pf.getTimestamp().getTime())/(1000*60*60);
		if(hour_diff < Constants.TIME_LOCK){
			String message = "Portfolio under process. Please try after " + String.valueOf(Constants.TIME_LOCK - hour_diff) + " hour"; 
			session.setAttribute(Constants.ERROR_MSG_ATTRIBUTE, message);
			ServletSanity.forwardUnaccessibleJSP(request, response, "portfolio_profile.jsp");
		}else{
			session.setAttribute(Constants.INVESTMENT_FLAG_ATTRIBUTE, Constants.EDIT_INVEST);
			ServletSanity.forwardUnaccessibleJSP(request, response, "make_portfolio.jsp");
		}
	}

}
