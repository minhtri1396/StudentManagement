package model.pojo;

import java.io.Serializable;

public class SubjectStudentID implements Serializable {
    
    private String subjectID;
    private String studentID;
    
    public SubjectStudentID() {}

    public SubjectStudentID(String subjectID, String studentID) {
        this.subjectID = subjectID;
        this.studentID = studentID;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
    
}
