package org.sameer.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sameer.java.Constants;
import org.sameer.java.User;

public class ServletSanity {
	public static HttpSession verifySession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession(false);
		try{
			User cur = (User)session.getAttribute(Constants.USER_ATTRIBUTE);
			if(cur == null){
				redirectLoginPage(request, response);
			}
		}catch(Exception e){
			redirectLoginPage(request, response);
		}
		
		return session;
	}
	
	public static HttpSession verifyAccesiblePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession(false);
		if(session == null){
			redirectLoginPage(request, response);
		}
		return session;
	}
	
	public static void forwardUnaccessibleJSP(HttpServletRequest request, HttpServletResponse response, String url) throws ServletException, IOException{
		String jsp = "WEB-INF/jsp/"+url;
		request.getRequestDispatcher(jsp).forward(request, response);
	}
	
	private static void redirectLoginPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String message = "Please Login first";
		HttpSession new_session = request.getSession(true);
		new_session.setAttribute(Constants.ERROR_MSG_ATTRIBUTE, message);
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}
}
