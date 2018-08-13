package edu.uga.cs.recdawgs.presentation;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uga.cs.recdawgs.session.Session;
import edu.uga.cs.recdawgs.session.SessionManager;
import edu.uga.cs.recdawgs.logic.LogicLayer; 

/**
 * Servlet implementation class Register
 */
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Register() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String firstName = null; 
		String lastName = null; 
		String username = null; 
		String password = null; 
		String email = null;
		String studentId = null; 
		String major = null; 
		String address = null; 

		HttpSession httpSession = null; 
		Session session = null; 
		LogicLayer logicLayer = null; 
		
		String submit = request.getParameter("submit"); 
		String cancel = request.getParameter("cancel); 
		//redirect to login page
		if (cancel != null) {
			response.sendRedirect("Login_Page.html"):
		}
		else if(submit != null) {
		
			httpSession = request.getSession(); 
			String ssid = (String) httpSession.getAttribute("ssid"); 
			if(ssid != null) {
				session = SessionManager.getSessionById(ssid); 
			}
			//if session is not already started, try to start session  
			if(session == null) {
				try {
					session = SessionManager.createSession(); 
				}
				catch(Exception e){
					System.out.println("Unable to Create Session");
	            	e.printStackTrace();
	                return;
				}
			}//if session == null 
		
			logicLayer = session.getLogicLayer(); 
			firstName = request.getParameter("firstName");
			lastName = request.getParameter("lastName"); 
			username = request.getParameter("username");
			password = request.getParameter("password");		
			email = request.getParameter("email"); 
			studentId = request.getParameter("studentId"); 
			major = request.getParameter("major"); 
			address = request.getParameter("address"); 
		
		
		} //else if submit != null
		
	} //doPost

} //Register
