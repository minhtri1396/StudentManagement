package controller.dao;

import app.HibernateUtil;
import model.pojo.SubjectName;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SubjectNameDAO {
    
    public static final SubjectNameDAO BUILDER = new SubjectNameDAO();
    
    public SubjectName get(int id) {
        SubjectName subjectName = null;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            subjectName = (SubjectName)session.get(SubjectName.class, id);
        } catch (Exception ex) {
            System.err.println("Error occur when get subject name from DB: " + ex);
        }
        
        return subjectName;
    }
    
    public SubjectName add(String name) {
        SubjectName subjectName = null;
        
        Transaction transaction = null;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            subjectName = new SubjectName();
            subjectName.setName(name);
            session.save(subjectName);
            session.flush();
            transaction.commit();
        } catch (Exception ex) {
            System.err.println("Error occur when insert subject name to DB: " + ex);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        
        return subjectName;
    }
    
    public void update(int id, String name) {
        SubjectName subjectName = new SubjectName(id, name);
        
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(subjectName);
            transaction.commit();   
        } catch (Exception ex) {
            System.err.println("Error occur when update subject name on DB: " + ex);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        
    }
    
}
