package edu.uga.cs.recdwags.logic.impl;

import edu.uga.cs.recdawgs.RDException;
import edu.uga.cs.recdawgs.entity.Student;
import edu.uga.cs.recdawgs.object.ObjectLayer;

public class FindCtrl {
private ObjectLayer objectLayer = null;
Iterator<student> itr = student.iterator();
    
    public FindCtrl( ObjectLayer objectLayer )
    {
        this.objectLayer = objectLayer;
    }
    public void findStudent(Student ModelStudent){
    Student s=null;
    	try {
			s=objectLayer.findStudent(uname, studentId);
		} catch (RDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			objectLayer.storeStudent(s);
		} catch (RDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	student itr = object.layer(student);
    	while (ltr.hasNext()) {
            Student next = (Student)itr.next();
            out.println(next.getfname()+"\t"+next.getlname()+"\t" + next.getemail()+"\t"+next.getmajor()+"\t"+next.getaddress());
        }

    }

}
