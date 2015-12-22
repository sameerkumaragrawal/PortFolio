package org.sameer.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sameer.java.Constants;

public class ServletSanity {
	public static HttpSession verifySession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession(false);
		if(session == null){
			String message = "Please Login first";
			HttpSession new_session = request.getSession(true);
			new_session.setAttribute(Constants.ERROR_MSG_ATTRIBUTE, message);
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
		return session;
	}
	
	public static void forwardUnaccessibleJSP(HttpServletRequest request, HttpServletResponse response, String url) throws ServletException, IOException{
		String jsp = "WEB-INF/jsp/"+url;
		request.getRequestDispatcher(jsp).forward(request, response);
	}
}
