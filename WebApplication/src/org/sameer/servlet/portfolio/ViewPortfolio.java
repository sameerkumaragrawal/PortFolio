package org.sameer.servlet.portfolio;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sameer.database.DatabaseConnection;
import org.sameer.java.Constants;
import org.sameer.java.PortFolio;
import org.sameer.servlet.ServletSanity;

/**
 * Servlet implementation class ViewPortfolio
 */
@WebServlet("/ViewPortfolio")
public class ViewPortfolio extends HttpServlet {

	private static final long serialVersionUID = 5899635533565236636L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = ServletSanity.verifySession(request, response);
		if(session == null){
			return;
		}
		
		int invest_id = Integer.parseInt(request.getParameter("ViewPortfolio"));
		
		DatabaseConnection db = DatabaseConnection.getInstance();
		PortFolio pf = db.getPortfolio(invest_id);
		
		session.setAttribute(Constants.PORTFOLIO_ATTRIBUTE, pf);
		ServletSanity.forwardUnaccessibleJSP(request, response, "portfolio_profile.jsp");
	}

}
