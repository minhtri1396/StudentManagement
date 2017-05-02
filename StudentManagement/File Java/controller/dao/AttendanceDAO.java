package controller.dao;

import app.HibernateUtil;
import java.util.Date;
import java.util.List;
import model.pojo.Attendance;
import model.pojo.AttendanceID;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.DateProcess;

public class AttendanceDAO {
    
    public static AttendanceDAO BUILDER = new AttendanceDAO();
    
    private AttendanceDAO() {}
    
    public Date[] get(int attendanceID) {
        List<Date> dates = null;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            dates = session.createQuery(
                    "select a.attendanceID.checkedDate from Attendance a where a.attendanceID.id = :attendanceID")
                    .setParameter("attendanceID", attendanceID)
                    .list();
        } catch (Exception ex) {
            System.err.println("Error occur when get attendance from DB: " + ex);
        }
        
        if (dates == null) {
            return null;
        }
        
        return dates.toArray(new Date[0]);
    }
    
    public void add(int attendanceID, Date date) {
        
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            Attendance attendace = new Attendance();
            
            AttendanceID attendanceIDObj = new AttendanceID();
            attendanceIDObj.setId(attendanceID);
            attendanceIDObj.setCheckedDate(date);
            
            attendace.setAttendanceID(attendanceIDObj);
            
            session.save(attendace);
            transaction.commit();
        } catch (Exception ex) {
            System.err.println("Error occur when insert new attendance to DB: " + ex);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
    
    public boolean isCanMarkAt(Date date) {
        boolean result = false;
        
        long timeAfter30Minutes = DateProcess.addMinute(date, 30).getTime();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            result = (boolean)session.createNativeQuery(
                    "select is_can_insert_attendance(:startTime, :timeAfter30Minutes)")
                    .setParameter("startTime", date.getTime())
                    .setParameter("timeAfter30Minutes", timeAfter30Minutes)
                    .uniqueResult();
        } catch (Exception ex) {
            System.err.println("Error occur when check marking attendance on DB: " + ex);
        }
        
        return result;
    }
    
    public void delete(int attendanceID, Date date) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Attendance attendance = new Attendance();
            AttendanceID attendanceIDObj = new AttendanceID();
            attendanceIDObj.setId(attendanceID);
            attendanceIDObj.setCheckedDate(date);
            attendance.setAttendanceID(attendanceIDObj);
            session.delete(attendance);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error occur when delete attendance in DB: " + ex);
        }
    }
    
}
