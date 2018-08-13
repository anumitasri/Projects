import java.io.*;
import java.sql.*;
import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
public class currency extends HttpServlet {


	  public void doGet(HttpServletRequest request,
	 HttpServletResponse response)
	  throws ServletException, IOException
	  {
	    response.setContentType("text/html");
	s    PrintWriter out = response.getWriter();
	    Connection con = null;
	    out.println ("<HTML><HEAD><TITLE>Conversion Rates");
	    out.println ("</TITLE></HEAD><BODY>");
	    out.println("<H1>Conversion Rates</H1>");

	    try {
	      Class.forName("org.gjt.mm.mysql.Driver");
	      con = DriverManager.getConnection("jdbc:mysql://localhost/test","root","secret");
	      PreparedStatement pstmt = con.prepareStatement("SELECT rate FROM Currency WHERE src = ? and dst = ?");
	      pstmt.setString(1, currsymbol);
	      pstmt.setString(2, currname);
	      ResultSet results = pstmt.executeQuery();
	      if (!results.next())
	        throw new SQLException("Missing Exchange rate data row");
	      double rate = results.getDouble(1);
	      out.println("Conversion rate = "+rate+"<BR>");
	      pstmt.setString(1, currsymbol);
	      pstmt.setString(2, currname);
	      results = pstmt.executeQuery();
	      if (!results.next())
	        throw new SQLException("Missing Exchange rate data row");
	      rate = results.getDouble(1);
	      out.println("exchange rate = "+rate+"<BR>");
	    }
	    catch (Exception ex)
	    {
	      out.println("<H2>Exception Occurred</H2>");
	      out.println(ex);
	      if (ex instanceof SQLException) {
	        SQLException sqlex = (SQLException) ex;
	        out.println("SQL state: "+sqlex.getSQLState()+"<BR>");
	        out.println("Error code: "+sqlex.getErrorCode()+"<BR>");
	      }
	    }
	    finally {
	      try { con.close(); } catch (Exception ex) {}
	    }
	    out.println ("</BODY></HTML>");
	  }
	}