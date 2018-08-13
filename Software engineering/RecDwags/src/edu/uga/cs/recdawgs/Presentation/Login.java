package edu.uga.cs.recdawgs.presentation;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uga.cs.recdawgs.session.Session;
import edu.uga.cs.recdawgs.session.SessionManager;
import edu.uga.cs.recdwags.logic.LogicLayer;

/**
 * Servlet implementation class Login
 */
//@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username=null;
		String password=null;
		HttpSession httpsession=null;
		Session session=null;
		LogicLayer  logicLayer = null;
        System.out.println("In Login");
        String login = request.getParameter("Login");
        String register = request.getParameter("Register");
        if(login != null){
        	System.out.println("For login");
        }
        else
        	System.out.println("not login");
        if( register != null){
        	System.out.println("for register");
        }
        httpsession=request.getSession();
		String ssid=(String)httpsession.getAttribute("ssid");
		   if( ssid != null ) {
	            System.out.println( "Already have ssid: " + ssid );
	            session = SessionManager.getSessionById( ssid );
	            System.out.println( "Connection: " + session.getConnection());
	        }
	        else
	            System.out.println( "ssid is null" );
		   
		   if( session == null ) {
	            try {
	                session = SessionManager.createSession();
	            }
	            catch ( Exception e ) {
	                //ClubsError.error( cfg, toClient, e );
	            	System.out.println("Not Created Session");
	            	e.printStackTrace();
	                return;
	            }
	        }
	        
		   logicLayer = session.getLogicLayer();
		   username=request.getParameter("uname");
			password=request.getParameter("password");
			System.out.println("username:"+username);
			System.out.println("password"+password);
			if( username == null || password == null ) {
	           // ClubsError.error( cfg, toClient, "Missing user name or password" );
	            System.out.println("User and pwd null");
				return;
	        }//if
			try {          
	            ssid = logicLayer.login( session, username, password );
	            System.out.println( "Obtained ssid: " + ssid );
	            httpsession.setAttribute( "ssid", ssid );
	            System.out.println( "Connection: " + session.getConnection() );
	        } 
	        catch ( Exception e ) {
	           // ClubsError.error( cfg, toClient, e );
	            return;
	        }
           if(ssid!=null){
        	   System.out.println("Login Successfull"+ssid);
           }//if
           else{
        	   System.out.println("NOT Successfull"+ssid);
                  
           }
		
	}//dopost

}
