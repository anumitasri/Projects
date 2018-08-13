package edu.uga.cs.recdwags.logic.impl;

import java.sql.Connection;

import edu.uga.cs.recdawgs.RDException;
import edu.uga.cs.recdawgs.object.ObjectLayer;
import edu.uga.cs.recdawgs.object.impl.ObjectLayerImplementor;
import edu.uga.cs.recdawgs.persistence.PersistenceLayer;
import edu.uga.cs.recdawgs.persistence.Impl.Persistance_Layer_Impl;
import edu.uga.cs.recdawgs.session.Session;
import edu.uga.cs.recdawgs.session.SessionManager;
import edu.uga.cs.recdwags.logic.LogicLayer;
import edu.uga.cs.recdwags.logic.impl.LoginCtrl;

public class LogicLayerImpl implements LogicLayer{
	
	ObjectLayer ob=null;
	public LogicLayerImpl(ObjectLayer ob){
		this.ob=ob;
	}
	public LogicLayerImpl( Connection conn )
    {
        ob = new ObjectLayerImplementor();
        PersistenceLayer persistenceLayer = new Persistance_Layer_Impl( conn,ob);
        ob.setPersistence( persistenceLayer );
        System.out.println( "LogicLayerImpl.LogicLayerImpl(conn): initialized" );
    }
    
	public void logout(String ssid) throws RDException{
		SessionManager.logout(ssid);
	}//logout
	public String login( Session session, String userName, String password ) 
            throws RDException
    {
        LoginCtrl ctrlVerifyPerson = new LoginCtrl(ob);
        return ctrlVerifyPerson.login( session, userName, password );
    }
}



