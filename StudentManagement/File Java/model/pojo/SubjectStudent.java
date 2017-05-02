package model.pojo;

public class SubjectStudent {
    
    private SubjectStudentID subjectStudentID;
    private int subjectNameID;
    private int attendanceID;
    
    public SubjectStudent() {}

    public SubjectStudent(String subjectID, int subjectNameID, String studentID) {
        subjectStudentID = new SubjectStudentID();
        subjectStudentID.setSubjectID(subjectID);
        subjectStudentID.setStudentID(studentID);
        this.subjectNameID = subjectNameID;
    }

    public SubjectStudentID getSubjectStudentID() {
        return subjectStudentID;
    }

    public void setSubjectStudentID(SubjectStudentID subjectStudentID) {
        this.subjectStudentID = subjectStudentID;
    }

    public int getSubjectNameID() {
        return subjectNameID;
    }

    public void setSubjectNameID(int subjectNameID) {
        this.subjectNameID = subjectNameID;
    }

    public int getAttendanceID() {
        return attendanceID;
    }

    public void setAttendanceID(int attendanceID) {
        this.attendanceID = attendanceID;
    }
    
}
