package edu.uga.cs.recdawgs.persistence.Impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.sql.Connection;
import java.sql.PreparedStatement; 
import edu.uga.cs.recdawgs.RDException;
import edu.uga.cs.recdawgs.entity.League;
//import edu.uga.cs.recdawgs.entity.Match;
import edu.uga.cs.recdawgs.entity.Match;
import edu.uga.cs.recdawgs.entity.Team;
import edu.uga.cs.recdawgs.object.ObjectLayer;

public class MatchManager {
	Connection con=null;
	ObjectLayer ob=null;
	long mid=0;
	MatchManager(Connection con,ObjectLayer ob){
		this.con=con;
		this.ob=ob;
	}
	
	 public void deleteMatch(Match match) throws RDException, SQLException{
		 //System.out.println("in Delete"+match.getId());
		 String deleteSql = "delete from match_table where id=?";              
		 PreparedStatement  stmt = null;
		 int del;
		 if( !match.isPersistent() ) // is the Match object persistent?  If not, nothing to actually delete
	            return;
		   try {
			stmt = (PreparedStatement) con.prepareStatement( deleteSql );
			stmt.setLong(1,match.getId());
			 del=stmt.executeUpdate();
			 if( del==1 ) {
				 //System.out.println("del is"+del);
	                return;
	            }
	            else{
	                throw new RDException( "MatchManagement.delete: failed to delete a Match" );
	        }
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RDException( "MatchManagment.delete: failed to delete a Match: " + e );        
		}
	            
}//deleteMatch
	
public Iterator<Match> restoreMatch( Match modelMatch ) throws RDException, SQLException{
		 String   selectSql="select m.id,m.home_points,m.away_points,m.mdate,m.isCompleted,m.svid,sv.name,sv.address,"
				 +"sv.isIndoor,m.rid,r.number,m.hometeamid,ht.name,ht.iscaptainof,hs.username"+
"hs.email,hs.firstname,hs.lastname,hs.address,hs.password,hs.studentid,hs.major,l.id,l.name,l.leagueRules,"
				 +"l.matchRules,"
+ "l.isIndoor,l.minTeams,l.maxTeams,l.minMembers,l.maxMembers,m.awayteamid,at.name,at.iscaptainof,as.username,"
				 +"as.email,as.firstname,as.lastname,as.address,as.password,as.studentid,as.major"+ 
	" from match_table m,round r,team ht,team at,user hs,user as,sportsVenue sv,league l where m.svid=sv.id and m.rid=r.id and m.hometeamid=ht.id and m.awayteamid=at.id and at.iscaptainof=as.id and ht.iscaptainof=hs.id"+
" and at.lid=l.id and ht.lid=l.id";
		    Statement    stmt = null;
	        StringBuffer query = new StringBuffer( 100 );
	        StringBuffer condition = new StringBuffer( 100 );
	        condition.setLength(0);
            query.append(selectSql);
            condition.setLength(0);
            try {
            	if(modelMatch!=null){
            		if(modelMatch.getId()>=0){
            		  query.append(" and m.id="+modelMatch.getId());
            		}//if
            		else{
            	        if(modelMatch.getHomePoint()>=0){
            	        	query.append(" and m.home_points="+modelMatch.getHomePoint());
            	        }//if
            	        if(modelMatch.getAwayPoints()>=0){
            	        	query.append(" and m.away_points="+modelMatch.getAwayPoints());
            	        }//if
            	        if(modelMatch.getDate()!=null){
            	        	query.append(" and m.mdate="+modelMatch.getDate());
            	        }//if
            	        if(modelMatch.getSportsVenue().getId()>=0){
            	        	int svid=(int)modelMatch.getSportsVenue().getId();
            	        	query.append(" and m.svid="+svid);
            	        }//if
            	        if(modelMatch.getHomeTeam().getId()>=0){
            	        	int hid=(int) modelMatch.getHomeTeam().getId();
            	        	query.append(" and m.hometeamid="+hid);
            	        }//if
            	        if(modelMatch.getAwayTeam().getId()>=0){
            	        	int aid=(int) modelMatch.getAwayTeam().getId();
            	        	query.append(" and m.awayteamid="+aid);
            	        }//if
            		}//else
            		
            	}//if(modelMatch!=null)
            	
            	
				stmt =con.createStatement();
				//stmt.setLong(1,modelMatch.getId());
				
	                 if(stmt.execute(query.toString())){
	                	 ResultSet rs=stmt.getResultSet();
	                	 return new MatchIterator(rs,ob);
	                 }
	                //return new MatchIterator(r,ob);
	           // }//if
	            
			} catch (SQLException e) {
				e.printStackTrace();
			}
            
            return null;
		 
	}//restore	 
	
	 
public void storeMatch( Match m ) throws RDException, SQLException 
{
	String insertSql="insert into match_table(home_points,away_points,mdate,isCompleted,svid,rid,hometeamid,awayteamid)"+
      "values(?,?,?,?,?,?,?,?)";
	
			//System.out.println("sportsVenue"+m.getSportsVenue().getId());
	String updateSql="update match_table set home_points=?,away_points=?,mdate=?,isCompleted=?,svid=?,rid=?,hometeamid=?,awayteamid=? where id =?";
		
	PreparedStatement    stmt = null;
    int                  inscnt;
    long                 svId;
    
		try {
			if(!m.isPersistent()){
				
			stmt=(PreparedStatement) con.prepareStatement(insertSql);
			}
			
		
			else{

				stmt=(PreparedStatement) con.prepareStatement(updateSql);
			}//else
			if(m.getHomePoint()>=0){
				stmt.setInt(1,m.getHomePoint());
			}//if
			else{
				throw new RDException("Cannot Store Match");
			}
			
			if(m.getAwayPoints()>=0){
				stmt.setInt(2,m.getAwayPoints());
				}//if
			else{
				throw new RDException("Cannot Store Match");
			}
			
			    if(m.getDate()!=null){
            	java.util.Date d=m.getDate();
            	java.sql.Date sDate = new java.sql.Date(d.getTime());
                stmt.setDate(3,sDate);
            }//if
            else{
            	stmt.setNull(3,java.sql.Types.DATE);

            }//else
            stmt.setBoolean(4,m.getIsCompleted());
                if(m.getSportsVenue()!=null && m.getSportsVenue().isPersistent()){
                	long id1=m.getSportsVenue().getId();
                	//System.out.println("id1"+id1);
                	int id2=(int)id1;
                	stmt.setInt(5,id2);
                }//if
                else{
                	 throw new RDException( "MatchManagament.save: can't save a Match: founder is not set or not persistent" );
                     //stmt.setNull(5,java.sql.Types.INTEGER);
                }//else
                if(m.getRound()!=null && m.getRound().isPersistent()){ // getRound() method in entity layer question
                    int rid=(int)m.getRound().getId();
                	stmt.setLong(6,m.getRound().getId());
                }//if
                else{
                	throw new RDException( "MatchManagament.save: can't save a Match: founder is not set or not persistent" );
                    //stmt.setNull(6, java.sql.Types.INTEGER);
                }//else
                if(m.getHomeTeam()!=null && m.getHomeTeam().isPersistent()){
                	stmt.setLong(7, m.getHomeTeam().getId());
                }//if
                else{
                	throw new RDException( "MatchManagament.save: can't save a Match: founder is not set or not persistent" );
                    //stmt.setNull(7,java.sql.Types.INTEGER);
                }//else
                if(m.getAwayTeam()!=null && m.getAwayTeam().isPersistent()){
                	stmt.setLong(8, m.getAwayTeam().getId());
                }//if
                else{
                	throw new RDException( "MatchManagament.save: can't save a Match: founder is not set or not persistent" );
                    //stmt.setNull(8,java.sql.Types.INTEGER);
                }//else
                if(m.isPersistent()){
                	stmt.setLong(9,m.getId());   //for update
                }//if
                inscnt = stmt.executeUpdate();
               if(inscnt>=1){
                if(!m.isPersistent()){
                	String sql = "select last_insert_id()";
                    if( stmt.execute( sql ) ) { // statement returned a result

                        // retrieve the result
                        ResultSet r = stmt.getResultSet();

                        // we will use only the first row!
                        //
                        while( r.next() ) {

                            // retrieve the last insert auto_increment value
                            mid = r.getLong( 1 );
                            if( mid > 0 )
                            	m.setId(mid);
                           }//if
                    }//while
                }//if
                else
                    throw new RDException( "MatchManager.save: failed to save a Match" );
            
}//if
            else {
                if( inscnt < 1 )
                    throw new RDException( "MatchManager.save: failed to save a Match"); 
            }

            
		} catch (SQLException e) {
			e.printStackTrace();
		}
      
}//storeMatch
	
}
