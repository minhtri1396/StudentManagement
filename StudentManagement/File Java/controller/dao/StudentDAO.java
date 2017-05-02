package controller.dao;

import app.HibernateUtil;
import model.pojo.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class StudentDAO {
    
    public static final StudentDAO BUILDER = new StudentDAO();
    
    public Student get(String studentCode) {
        Student student = null;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            student = (Student) session.createQuery(
                    "from Student s where s.code = :code")
                    .setParameter("code", studentCode)
                    .uniqueResult();
        } catch (Exception ex) {
            System.err.println("Error occur when get student from DB: " + ex);
        }

        return student;
    }
    
    public void add(Student student) {
        if (get(student.getCode()) == null) {
            LoginDAO.BUILDER.addStudentAcc(student.getCode(), student.getCode());
            Transaction transaction = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                session.save(student);
                transaction.commit();
            } catch (Exception ex) {
                System.err.println("Error occur when insert new student to DB: " + ex);
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }
    
}
