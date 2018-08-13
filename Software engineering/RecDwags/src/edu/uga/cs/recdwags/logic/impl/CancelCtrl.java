package edu.uga.cs.recdwags.logic.impl;

import edu.uga.cs.recdawgs.RDException;
import edu.uga.cs.recdawgs.entity.Student;
import edu.uga.cs.recdawgs.object.ObjectLayer;

public class CancelCtrl {
private ObjectLayer objectLayer = null;
    
    public CancelCtrl( ObjectLayer objectLayer )
    {
        this.objectLayer = objectLayer;
    }
    public void deleteStudent(Student student){
    Student s=null;
    	try {
			s=objectLayer.deleteStudent(studentId);
		} catch (RDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			objectLayer.deleteStudent(s);
		} catch (RDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
