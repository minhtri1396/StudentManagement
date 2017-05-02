package model.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import util.DateFormatter;

public class Subject implements Serializable {
    
    public static final double PERCENT_MINIMUM = 50;
    
    private SubjectID subjectID;
    private String classroom;
    private int numberStudents;
    private boolean expired;
    private double percentAttendance;

    private String name;
    private Date startTime;
    private Date endTime;
    
    public Subject() {}
    
    public Subject(String code, String name, String classroom, Date startTime, Date endTime) {
        this.subjectID = new SubjectID();
        this.subjectID.setCode(code.toUpperCase());
        this.name = name.toUpperCase();
        this.classroom = classroom.toUpperCase();
        this.startTime = startTime;
        this.endTime = endTime;
        this.numberStudents = 0;
        this.expired = false;
        this.percentAttendance = 0;
    }

    public SubjectID getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(SubjectID subjectID) {
        this.subjectID = subjectID;
    }
    
    public boolean isExpired() {
        return expired;
    }
    
    public void setExpired(boolean isExpired) {
        this.expired = isExpired;
    }

    public String getCode() {
        return subjectID.getCode();
    }

    public void setCode(String code) {
        subjectID.setCode(code);
    }
    
    public int getNameId() {
        return subjectID.getNameId();
    }
    
    public void setNameId(int nameId) {
        subjectID.setNameId(nameId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }
    
    // Used for Hibernate
    public String getStartTimeText() {
        return DateFormatter.fullTimeAsStringOf(startTime);
    }
    
    // Used for Hibernate
    public void setStartTimeText(String startTimeText) {
        this.startTime = DateFormatter.fullTimeOf(startTimeText);
    }
    
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
    // Used for Hibernate
    public String getEndTimeText() {
        return DateFormatter.fullTimeAsStringOf(endTime);
    }
    
    // Used for Hibernate
    public void setEndTimeText(String endTimeText) {
        this.endTime = DateFormatter.fullTimeOf(endTimeText);
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    public void setNumberStudents(int numberStudents) {
        this.numberStudents = numberStudents;
    }
    
    public int getNumberStudents() {
        return numberStudents;
    }

    public double getPercentAttendance() {
        return percentAttendance;
    }

    public void setPercentAttendance(double percentAttendance) {
        this.percentAttendance = percentAttendance;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Subject) {
            Subject other = (Subject) obj;
            return this.subjectID.getCode().equalsIgnoreCase(other.subjectID.getCode());
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.subjectID);
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.classroom);
        hash = 23 * hash + Objects.hashCode(this.startTime);
        hash = 23 * hash + Objects.hashCode(this.endTime);
        hash = 23 * hash + Objects.hashCode(this.numberStudents);
        hash = 23 * hash + (this.expired ? 1 : 0);
        return hash;
    }
    
}
