package model;

import controller.dao.AttendanceDAO;
import controller.dao.SubjectStudentDAO;
import java.util.Calendar;
import java.util.Date;
import model.pojo.Student;
import model.pojo.Subject;
import model.pojo.SubjectStudent;
import util.DateProcess;

public class AttendanceTable {
    
    public enum MarkType {
        CHECKED,
        EXPIRED,
        NORMAL,
        NOPE
    }
    
    private Date[] dates;
    
    public static AttendanceTable getFromDAO(String subjectID, int subjectNameID, String studentID) {
        AttendanceTable attendanceTable = null;
        
        SubjectStudent subjectStudent = SubjectStudentDAO.BUILDER.get(studentID, subjectID, subjectNameID);
        if (subjectStudent != null) {
            attendanceTable = new AttendanceTable();
            attendanceTable.dates =
                    AttendanceDAO.BUILDER.get(subjectStudent.getAttendanceID());
        }
        
        return attendanceTable;
    }
    
    public static AttendanceTable createNew(Student student, String subjectID, int subjectNameID) {
        // Add student to Database
        SubjectStudentDAO.BUILDER.add(
                student,
                subjectID,
                subjectNameID
        );
        
        AttendanceTable attendanceTable = new AttendanceTable();
        attendanceTable.dates = new Date[0];
            
        return attendanceTable;
    }
    
    public boolean markFor(Subject subject, String studentID) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(subject.getStartTime());
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        
        boolean result = AttendanceDAO.BUILDER.isCanMarkAt(calendar.getTime());
        
        if (result) {
            int attendanceID = SubjectStudentDAO.BUILDER.getAttendanceID(
                subject.getCode(),
                subject.getNameId(),
                studentID
            );
            sureMarkAt(calendar.getTime(), subject, studentID, attendanceID);
//            int currentWeek = (int)((calendar.getTime().getTime() - subject.getStartTime().getTime()) /
//                (1000 * 60 * 60 * 24 * 7)) + 1;
//            SubjectStudentDAO.BUILDER.updateAttendancePercent(
//                    currentWeek,
//                    subject.getCode(),
//                    subject.getNameId()
//            );
        }
            
        return result;
    }
    
    public void sureMarkAt(Date date, Subject subject, String studentID, int attendanceID) {
        AttendanceDAO.BUILDER.add(attendanceID, date);
    }
    
    // Now, just input start date of subject
    public MarkType getMarkTypeAt(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        
        long time = DateProcess.getStartOfDay(date).getTime();
        if(dates != null) {
            for(int i = 0; i < dates.length; ++i) {
                if (time == dates[i].getTime()) {
                    return MarkType.CHECKED;
                }
            }
        }
        // Allow 30 minutes late
        long timeAfter30Minutes = DateProcess.addMinute(date, 30).getTime();
        long currentTime = new Date().getTime();
        if(currentTime > timeAfter30Minutes) {
            return MarkType.EXPIRED;
        }
        
        return MarkType.NOPE;
    }
    
    // Now, just input start date of subject
    public MarkType getMarkTypeForStudentAt(Date date) {
        MarkType markType = getMarkTypeAt(date);
        
        if (markType == MarkType.NOPE) {
            long time = date.getTime();
            long currentTime = new Date().getTime();
            if(currentTime >= time) {
                markType = MarkType.NORMAL;
            }
        }
        
        return markType;
    }
    
}
