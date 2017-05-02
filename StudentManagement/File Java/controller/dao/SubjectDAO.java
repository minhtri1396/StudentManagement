package controller.dao;

import app.HibernateUtil;
import java.util.List;
import model.pojo.Subject;
import model.pojo.SubjectName;
import org.hibernate.Session;
import org.hibernate.Transaction;
public class SubjectDAO {
    
    public static final SubjectDAO BUILDER = new SubjectDAO();
    
    private SubjectDAO() {}
    
    public Subject get(String subjectCode, int nameId) {
        Subject subject = null;
        SubjectName subjectName = SubjectNameDAO.BUILDER.get(nameId);
        if (subjectName != null) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                subject = (Subject) session.createQuery(
                        "from Subject s where s.subjectID.code = :code and s.subjectID.nameId = :id")
                        .setParameter("code", subjectCode)
                        .setParameter("id", nameId)
                        .uniqueResult();
                subject.setName(subjectName.getName());
            } catch (Exception ex) {
                System.err.println("Error occur when get subject from DB: " + ex);
            }
        }
        
        return subject;
    }
    
    public Subject[] getAll() {
        List<Subject> subjectsList = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            subjectsList = session.createQuery("from Subject").list();
        } catch (Exception ex) {
            System.err.println("Error occur when get all subjects from DB: " + ex);
        }
        
                
        if (subjectsList != null) {
            SubjectName subjectName;
            for (Subject subject : subjectsList) {
                subjectName = SubjectNameDAO.BUILDER.get(subject.getNameId());
                subject.setName(subjectName.getName());
            }
        }
        
        if (subjectsList == null) {
            return new Subject[0];
        }
        
        return subjectsList.toArray(new Subject[0]);
    }
    
    public void add(Subject subject) {
        SubjectName subjectName = SubjectNameDAO.BUILDER.add(subject.getName());
        subject.getSubjectID().setNameId(subjectName.getId());
        subject.setName(subjectName.getName());
        
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(subject);
            transaction.commit();   
        } catch (Exception ex) {
            System.err.println("Error occur when insert new subject to DB: " + ex);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
    
    public void update(Subject subject) {
        
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(subject);
            transaction.commit();   
        } catch (Exception ex) {
            System.err.println("Error occur when update subject name on DB: " + ex);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        
        SubjectNameDAO.BUILDER.update(
                subject.getSubjectID().getNameId(),
                subject.getName()
        );
        
    }
    
    private long countSubject(String subjectCode) {
        long nSubjects = 0;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            nSubjects = (long)session.createQuery(
                    "select count(*) from Subject s where s.subjectID.code = :code")
                    .setParameter("code", subjectCode)
                    .uniqueResult();
        } catch (Exception ex) {
            System.err.println("Error occur when count subject in DB: " + ex);
        }
        
        return nSubjects;
    }
    
}
