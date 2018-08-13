package edu.uga.cs.recdawgs.presentation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uga.cs.recdawgs.session.Session;
import edu.uga.cs.recdawgs.session.SessionManager;
import edu.uga.cs.recdwags.logic.LogicLayer;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Servlet implementation class CreateLeague
 */
@WebServlet("/CreateLeague")
public class CreateLeague extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	static  String         templateDir = "WEB-INF/templates";
	  static  String         resultTemplateName = "CreateLeague-Result.ftl"; // get the template file
	  private Configuration cfg;
    
	  public void init(){
		  cfg = new Configuration();
		  
		  cfg.setServletContextForTemplateLoading(getServletContext(), "WEB-INF/templates");
	  }
	  
	/**
     * @see HttpServlet#HttpServlet()
     */
    public CreateLeague() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Template resultTemplate = null;
		BufferedWriter toClient = null;
		
		String leagueName = null;
		String leagueRules = null;
		String matchRules = null;
		boolean isIndoor = false;
		int minTeams = 0;
		int maxTeams = 0;
		int minMembers = 0;
		int maxMembers = 0;
		long leagueId = 0;
		LogicLayer logicLayer = null;
		HttpSession httpSession ;
		Session session ;
		String ssid;
		
		//load templates from the WEB-INF folder
		
		try{
			resultTemplate = cfg.getTemplate(resultTemplateName); //template with name CreateLeague-result
		}
		catch (IOException e) {
	          throw new ServletException( 
	                  "Can't load template in: " + templateDir + ": " + e.toString());
	      }
			
		//Preparing the http response
		
		toClient = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), resultTemplate.getEncoding()));
		response.setContentType("text/html; charset=" +resultTemplate.getEncoding());
		
		httpSession = request.getSession();
		if(httpSession == null) { //not logged in session expired
			//ClubsError.error( cfg, toClient, "Session expired or illegal; please log in" );
			System.out.println("No session  for the user");
	          return;

		}
		
		ssid = (String) httpSession.getAttribute("ssid");
		if(ssid == null){
			//not logged in
			System.out.println("Not logged in");
			return;
		}
		session = SessionManager.getSessionById(ssid);
		if(session == null){
			//show error
			System.out.println("Session expired, not logged ");
			return;
		}
		leagueName = request.getParameter("");
		leagueRules = request.getParameter("");
		matchRules = request.getParameter("");
		minTeams = request.getParameter("");
		maxTeams = request.getParameter("");
		minMembers = request.getParameter("");
		maxMembers = request.getParameter("");
		
		if( leagueName == null){
			System.out.println( "no league name");
			return;
		}
		if( leagueRules == null){
			//System.out.println( "no league rules");
			//return;
			leagueRules = "";
		}
		if( matchRules == null){
			//System.out.println( "no match Rules");
			//return;
			matchRules = "";
		}
		if( minTeams <= 0 ){
			System.out.println( "Please specify an appropriate number");
			return;
		}
		if( maxTeams <= 0 ){
			System.out.println( "Please specify an appropriate number");
			return;
		}
		if( minMembers <= 0 ){
			System.out.println( "Please specify an appropriate number");
			return;
		}
		if( maxMembers <= 0 ){
			System.out.println( "Please specify an appropriate number");
			return;
		}
		


	      try {
	    	  //define method in logic layer
	          leagueId =  logicLayer.createLeague ( user_name, password, email, first_name, last_name, address, phone );
	      } 
	      catch ( Exception e ) {
	          ClubsError.error( cfg, toClient, e );
	          return;
	      }

		
		
		
		
	}

}
