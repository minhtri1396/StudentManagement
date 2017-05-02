package controller.dao;

import app.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class LoginDAO {
    
    public enum LoginType {
        GIAO_VU,
        STUDENT
    }
    
    public static final LoginDAO BUILDER = new LoginDAO();
    
    public void addStudentAcc(String userID, String password) {
        // If this student has had an account, DB will deny this query, so don't check it here
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("call insert_student_acc(:username, :password)")
                    .setParameter("username", userID)
                    .setParameter("password", password)
                    .executeUpdate();
            transaction.commit();   
        } catch (Exception ex) {
            System.err.println("Error occur when insert new student account to DB: " + ex);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
    
    public boolean checkMapping(String userID, String password, LoginType loginType) {
        boolean result = false;
        
        String funcName = loginType == LoginType.STUDENT ? "match_student_acc" : "match_giaovu_acc";
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            result = (boolean)session.createNativeQuery(String.format(
                    "select %s(:username, :password)", funcName))
                    .setParameter("username", userID)
                    .setParameter("password", password)
                    .uniqueResult();
        } catch (Exception ex) {
            System.err.println("Error occur when check password on DB: " + ex);
        }
        
        return result;
    }
    
    public boolean updatePassword(String userID, String oldPass, String newPass, LoginType loginType) {
        boolean result = false;
        
        String funcName = loginType == LoginType.STUDENT ? "update_student_acc" : "update_giaovu_acc";
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            result = (boolean)session.createNativeQuery(String.format(
                    "select %s(:username, :oldPass, :newPass)", funcName))
                    .setParameter("username", userID)
                    .setParameter("oldPass", oldPass)
                    .setParameter("newPass", newPass)
                    .uniqueResult();
        } catch (Exception ex) {
            System.err.println("Error occur when update password on DB: " + ex);
        }
        
        return result;
    }
    
}
