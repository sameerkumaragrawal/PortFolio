package org.sameer.servlet.portfolio;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sameer.java.Constants;
import org.sameer.servlet.ServletSanity;

@WebServlet("/MakeAnotherInvestment")
public class MakeAnotherInvestment extends HttpServlet {
	private static final long serialVersionUID = 1649591164030992416L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = ServletSanity.verifySession(request, response);
		if(session == null){
			return;
		}
		
		session.removeAttribute(Constants.PORTFOLIO_ATTRIBUTE);
		session.setAttribute(Constants.INVESTMENT_FLAG_ATTRIBUTE, Constants.NEW_INVEST);
		ServletSanity.forwardUnaccessibleJSP(request, response, "make_portfolio.jsp");
	}

}
