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
import org.sameer.java.Strategy;
import org.sameer.java.User;
import org.sameer.servlet.ServletSanity;

@WebServlet("/updatePortfolio")
public class UpdatePortfolio extends HttpServlet {

	private static final long serialVersionUID = 4575759949122855316L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = ServletSanity.verifySession(request, response);
		if(session == null){
			return;
		}
		
		String values[] = request.getParameterValues("strategy");
		
		User cur = (User)session.getAttribute(Constants.USER_ATTRIBUTE);
		int investment_flag = (Integer)session.getAttribute(Constants.INVESTMENT_FLAG_ATTRIBUTE);
		PortFolio pf = new PortFolio();
		for(int i=0;i<values.length;i++){
			try{
				double weight = Double.parseDouble(values[i]);
				if(weight > 0){
					Strategy curStrategy = new Strategy(i, weight);
					pf.addStrategy(curStrategy);
				}
			}catch(Exception e){
			}
		}
		
		if(pf.getNumberOfStrategies() == 0){
			String message = "Please add atleast one positive value"; 
			session.setAttribute(Constants.ERROR_MSG_ATTRIBUTE, message);
			ServletSanity.forwardUnaccessibleJSP(request, response, "make_portfolio.jsp");
			return;
		}
		DatabaseConnection db = DatabaseConnection.getInstance();
		if(investment_flag == Constants.NEW_INVEST){
			db.makeInvestment(cur.getId(), pf);
		}else{
			PortFolio temp = (PortFolio)session.getAttribute(Constants.PORTFOLIO_ATTRIBUTE);
			pf.setId(temp.getId());
			db.insertTransaction(pf);
		}
		session.setAttribute(Constants.PORTFOLIO_ATTRIBUTE, pf);
		ServletSanity.forwardUnaccessibleJSP(request, response, "portfolio_profile.jsp");
	}

}
