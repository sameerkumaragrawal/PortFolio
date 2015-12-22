package org.sameer.servlet.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sameer.database.DatabaseConnection;
import org.sameer.java.Constants;
import org.sameer.java.PortFolio;
import org.sameer.java.User;
import org.sameer.servlet.ServletSanity;

/**
 * Servlet implementation class ListPortfolios
 */
@WebServlet("/ListPortfolios")
public class ListPortfolios extends HttpServlet {
	
	private static final long serialVersionUID = 8290288034659947463L;

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = ServletSanity.verifySession(request, response);
		if(session == null){
			return;
		}
		
		User cur = (User)session.getAttribute(Constants.USER_ATTRIBUTE);
		
		DatabaseConnection db = DatabaseConnection.getInstance();
		List<PortFolio> invests = db.getInvestments(cur.getId());
		
		session.setAttribute(Constants.PORTFOLIO_LIST_ATTRIBUTE, invests);
		ServletSanity.forwardUnaccessibleJSP(request, response, "user_profile.jsp");
	}

}
