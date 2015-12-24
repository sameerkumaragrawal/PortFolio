package org.sameer.servlet.portfolio;

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
import org.sameer.servlet.ServletSanity;

/**
 * Servlet implementation class RecommendStrategy
 */
@WebServlet("/RecommendStrategy")
public class RecommendStrategy extends HttpServlet {
	
	private static final long serialVersionUID = -1329608316034108695L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = ServletSanity.verifySession(request, response);
		if(session == null){
			return;
		}
		DatabaseConnection db = DatabaseConnection.getInstance();
		List<Integer> s = db.getTopStrategies();
		session.setAttribute(Constants.RECOMMEND_FLAG_ATTRIBUTE, s);
		ServletSanity.forwardUnaccessibleJSP(request, response, "make_portfolio.jsp");
	}

}
