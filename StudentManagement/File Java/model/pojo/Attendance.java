package model.pojo;

import java.io.Serializable;

public class Attendance implements Serializable {
    
    private AttendanceID attendanceID;
    
    public Attendance() {}

    public AttendanceID getAttendanceID() {
        return attendanceID;
    }

    public void setAttendanceID(AttendanceID attendanceID) {
        this.attendanceID = attendanceID;
    }
    
}
