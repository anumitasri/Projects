package edu.uga.cs.recdawgs.persistence.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import edu.uga.cs.recdawgs.RDException;
import edu.uga.cs.recdawgs.entity.League;
import edu.uga.cs.recdawgs.object.ObjectLayer;
 

public class LeagueManager {
	
	//T check the commit
	
	private ResultSet rs = null;
	private ObjectLayer objLayer = null;
	private Connection conn = null;
	private boolean more = false;
	
	
	public LeagueManager(Connection conn, ObjectLayer object){
		this.conn = conn;
		this.objLayer = object;		
		
	}
	
	public Iterator<League> restoreLeague(edu.uga.cs.recdawgs.entity.League league) throws SQLException{
		long id = league.getId();
		String restoreLeagueSql = "Select l.id,l.name,l.leagueRules,l.matchRules,l.isIndoor,l.minTeams,"
									+"l.maxTeams,l.minMembers,l.maxMembers from league ";//where l.name =?";
		
		Statement statement = null;
		StringBuffer query = new StringBuffer(100);
		StringBuffer condition = new StringBuffer(100);
		condition.setLength(0);
		if(league != null){
			if(league.getId() >= 0){
				query.append(" where l.id=" + league.getId());
			}
			else if(league.getName() != null){
				query.append(" where l.name = '" +league.getName()+"'");
			}
			else {
				if(league.getLeagueRules() != null){
					condition.append("  l.leagueRules = '" +league.getLeagueRules()+"'");
				}
				if(league.getMatchRules() != null){
					if(condition.length() > 0)
						condition.append(" and ");
					condition.append(" l.matchRules = '" +league.getMatchRules()+"'");
				}
				if(league.getMinTeams() <= -1){
					if(condition.length() > 0)
						condition.append(" and ");
						condition.append("l.minTeams ="+league.getMinTeams());
					
					}
				if(league.getMaxTeams() <= -1){
					if(condition.length() > 0)
						condition.append(" and ");
					condition.append("l.maxTeams = " + league.getMaxTeams());
				}
				if(league.getMinMembers() <= -1){
					if(condition.length() >0)
						condition.append(" and ");
					condition.append("l.minMembers =" +league.getMinMembers());
				}
				if(league.getMaxMembers() <= -1){
					if(condition.length() >0)
						condition.append(" and ");
						condition.append("l.maxMembers=" + league.getMaxMembers());
				}
				if( condition.length() > 0 ) {
                    query.append(  " where " );
                    query.append( condition );
                }
				
			}		
					
			}			
			
				
		try{
			statement = conn.createStatement();
			if(statement.execute(restoreLeagueSql.toString())){
			ResultSet resultSet = statement.getResultSet();
			return new LeagueIterator(rs,objLayer);
			
			}
			}
		catch(Exception e){
			
		}
		/*finally{
			statement.close();
			conn.close();
		}*/
		return null;
		
		
	}

	public void storeLeague(edu.uga.cs.recdawgs.entity.League league) throws SQLException, RDException{
		
		String insertLeagueSql = "insert into league (name, leagueRules, matchRules,isIndoor,minTeams,maxTeams,minMembers,maxMembers)"
								+"values(?,?,?,?,?,?,?,?)";
		
		String updateLeagueSql = "update league set name=?,leagueRules = ?,matchRules =?,isIndoor=?,minTeams = ?,maxTeams = ?,"
								+"minMembers=?,maxMembers=? where id=?";
		
		
		
		PreparedStatement pStatement =null;
		long leagueId;
		int executeStatus;
		
		try{
			
			if(!league.isPersistent()){
				
				pStatement = (PreparedStatement) conn.prepareStatement(insertLeagueSql);	
			}
			else
				pStatement = (PreparedStatement) conn.prepareStatement( updateLeagueSql );
			
			if( league.getName() != null ) 
                pStatement.setString( 1, league.getName().toString() );
            else 
                throw new RDException( "League:store can't store a league: leagueName undefined" );
			
			if( league.getLeagueRules() != null ) 
                pStatement.setString( 2, league.getLeagueRules().toString() );
            else 
                throw new RDException( "League:store can't store a league: leagueRules undefined" );
			
			if( league.getMatchRules() != null ) 
                pStatement.setString( 3, league.getMatchRules().toString() );
            else 
                throw new RDException( "League:store can't store a league: matchRules undefined" );
			//System.out.println("getmatchrules" +league.getMatchRules().toString());
			pStatement.setBoolean(4, league.getIsIndoor());
			
			//System.out.println("minteams :" +league.getMinTeams());
			if( league.getMinTeams() > 0 ) 
                pStatement.setInt( 5,(int) league.getMinTeams() );
            else 
                throw new RDException( "League:store can't store a league: minTeams undefined" );
			
			if( league.getMaxTeams() > 0 ) 
                pStatement.setInt( 6, league.getMaxTeams() );
            else 
                throw new RDException( "League:store can't store a league: maxTeams undefined" );
			
			if( league.getMinMembers() > 0 ) 
                pStatement.setInt( 7, league.getMinMembers() );
            else 
                throw new RDException( "League:store can't store a league: minMembers undefined" );
			
			if( league.getMaxMembers() > 0 ) 
                pStatement.setInt( 8, league.getMaxMembers());
            else 
                throw new RDException( "League:store can't store a league: maxMembers undefined" );
			
			if(league.isPersistent()){
			pStatement.setLong(9, league.getId());	
			}
			
			
				
			executeStatus = pStatement.executeUpdate();
			
			if(executeStatus >= 1){
				String sql = "select last_insert_id()"; //select max(id) from league;
				if(pStatement.execute(sql)){
					ResultSet rs = pStatement.getResultSet();
					
					while(rs.next()){
						leagueId = rs.getLong(1);
						if(leagueId > 0){
							league.setId(leagueId);
						}
					}
				}
				
			}
			else{
                //throw new RDException( "League.storeLeague: failed to store League information" );
			}
			
		}
		catch(SQLException e){
			e.printStackTrace();
            //throw new RDException( "League.storeLeague: failed to save a League information: " + e );			
		}
		/*finally{
			pStatement.close();
			conn.close();
		}*/
		
	}

	public void deleteLeague(edu.uga.cs.recdawgs.entity.League league) throws SQLException{
		
		String deleteLeagueSql = "delete from league where id=?";
		PreparedStatement pStatement = null;
		int executeStatus ;
		
		if(!league.isPersistent())
			return;
		try{
		pStatement = (PreparedStatement)conn.prepareStatement(deleteLeagueSql);
		pStatement.setLong(1, league.getId());
		
		executeStatus = pStatement.executeUpdate();
		
		if(executeStatus == 1){
			String status = "successfull";
			return;
		}
		else{
			//throw new RDException( "League.deleteLeague: failed to delete a league" );
		}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		/*finally{
			pStatement.close();
			conn.close();
		}*/
		
		
	}
	
	


}
