package org.sameer.servlet.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sameer.database.DatabaseConnection;
import org.sameer.java.Constants;
import org.sameer.java.User;
import org.sameer.servlet.ServletSanity;

/**
 * Servlet implementation class SignUp
 */
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	
	private static final long serialVersionUID = -6899105029347694087L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = ServletSanity.verifySession(request, response);
		if(session == null){
			return;
		}
		
		session.invalidate();
		session = request.getSession(true);
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		DatabaseConnection db = DatabaseConnection.getInstance();
		int user_id = db.signupUser(email, password);
		if(user_id < 0){
			String message = "Error: Email ID already exists";
			session.setAttribute(Constants.ERROR_MSG_ATTRIBUTE, message);
			request.getRequestDispatcher("/signup.jsp").forward(request, response);
			return;
		}
		User cur = new User(user_id, email);
		session.setAttribute(Constants.USER_ATTRIBUTE, cur);
		request.getRequestDispatcher("/ListPortfolios").forward(request, response);
	}

}
