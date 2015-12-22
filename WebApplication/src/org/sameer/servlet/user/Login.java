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
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {

	private static final long serialVersionUID = -4485515350356166136L;

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
		User cur = db.loginCheck(email, password);
		if(cur == null){
			String message = "Error: Invalid email/password";
			session.setAttribute(Constants.ERROR_MSG_ATTRIBUTE, message);
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}
		
		session.setAttribute(Constants.USER_ATTRIBUTE, cur);
		request.getRequestDispatcher("/ListPortfolios").forward(request, response);
	}

}
