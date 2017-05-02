package controller.dao;

import app.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import model.pojo.Student;
import model.pojo.Subject;
import model.pojo.SubjectStudent;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SubjectStudentDAO {
    
    public static final SubjectStudentDAO BUILDER = new SubjectStudentDAO();
    
    private SubjectStudentDAO() {}
    
    public SubjectStudent get(String studentID, String subjectID, int subjectNameID) {
        SubjectStudent subjectStudent = null;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            subjectStudent = (SubjectStudent) session.createQuery(
                    "from SubjectStudent o "
                            + "where o.subjectStudentID.subjectID = :subjectID "
                            + "and o.subjectStudentID.studentID = :studentID "
                            + "and o.subjectNameID = :subjectNameID")
                    .setParameter("subjectID", subjectID)
                    .setParameter("studentID", studentID)
                    .setParameter("subjectNameID", subjectNameID)
                    .uniqueResult();
        } catch (Exception ex) {
            System.err.println("Error occur when get SubjectStudent from DB: " + ex);
        }
        
        return subjectStudent;
    }
    
    public void add(Student student, String subjectID, int subjectNameID) {
        if (!isExisted(subjectID, student.getCode())) {
            StudentDAO.BUILDER.add(student); // add student (if can)
            
            Transaction transaction = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                session.createNativeQuery("call insert_subject_student(:subjectID, :subjectNameID, :studentID)")
                        .setParameter("subjectID", subjectID)
                        .setParameter("subjectNameID", subjectNameID)
                        .setParameter("studentID", student.getCode())
                        .executeUpdate();
                transaction.commit();   
            } catch (Exception ex) {
                System.err.println("Error occur when insert new SubjectStudent to DB: " + ex);
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }
    
    public int getAttendanceID(String subjectID, int subjectNameID, String studentCode) {
        int attendanceID = -1;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            attendanceID = (int) session.createQuery(
                    "select o.attendanceID from SubjectStudent o "
                            + "where o.subjectStudentID.subjectID = :subjectID "
                            + "and o.subjectStudentID.studentID = :studentID "
                            + "and o.subjectNameID = :subjectNameID")
                    .setParameter("subjectID", subjectID)
                    .setParameter("studentID", studentCode)
                    .setParameter("subjectNameID", subjectNameID)
                    .uniqueResult();
        } catch (Exception ex) {
            System.err.println("Error occur when get attendance id from DB: " + ex);
        }
        
        return attendanceID;
    }
    
    public Subject[] getSubjectsOf(String studentCode) {
        List<Subject> students = new ArrayList<>();
        
        List<SubjectStudent> subjectStudents;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            subjectStudents = session.createQuery(
                    "from SubjectStudent o where o.subjectStudentID.studentID = :studentID ")
                    .setParameter("studentID", studentCode)
                    .list();
            if (subjectStudents != null) {
                Subject subject;
                for (SubjectStudent subjectStudent : subjectStudents) {
                    subject = SubjectDAO.BUILDER.get(
                            subjectStudent.getSubjectStudentID().getSubjectID(),
                            subjectStudent.getSubjectNameID()
                    );
                    if (subject != null) {
                        students.add(subject);
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println("Error occur when get subjects of student from DB: " + ex);
        }
        
        return students.toArray(new Subject[0]);
    }
    
    public Student[] getStudentsStudy(String subjectID, int subjectNameID) {
        List<Student> students = new ArrayList<>();
        
        List<String> studentIDs;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            studentIDs = session.createQuery(
                    "select o.subjectStudentID.studentID from SubjectStudent o "
                            + "where o.subjectStudentID.subjectID = :subjectID "
                            + "and o.subjectNameID = :subjectNameID")
                    .setParameter("subjectID", subjectID)
                    .setParameter("subjectNameID", subjectNameID)
                    .list();
            if (studentIDs != null) {
                Student student;
                for (String studentID : studentIDs) {
                    student = StudentDAO.BUILDER.get(studentID);
                    if (student != null) {
                        students.add(student);
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println("Error occur when getStudentsStudy from DB: " + ex);
        }
        
        return students.toArray(new Student[0]);
    }
    
    public Student[] getStudentsNotStudy(String subjectID, int schoolYear) {
        ArrayList<Student> students = new ArrayList<>();
        
        List<String> studentIDs;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            studentIDs = session.createQuery(
                    "select o.subjectStudentID.studentID from SubjectStudent o where "
                            + " o.subjectStudentID.subjectID != :subjectID")
                    .setParameter("subjectID", subjectID)
                    .list();
            if (studentIDs != null) {
                Student student;
                for (String studentID : studentIDs) {
                    if (!isExisted(subjectID, studentID)) {
                        student = StudentDAO.BUILDER.get(studentID);
                        if (student.getSchoolYear() == schoolYear) {
                            students.add(student);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println("Error occur when getStudentsNotStudy from DB: " + ex);
        }
        
        return students.toArray(new Student[0]);
    }
    
    public boolean isExisted(String subjectID, String studentID) {
        SubjectStudent subjectStudent = null;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            subjectStudent = (SubjectStudent) session.createQuery(
                    "from SubjectStudent o "
                            + "where o.subjectStudentID.subjectID = :subjectID "
                            + "and o.subjectStudentID.studentID = :studentID ")
                    .setParameter("subjectID", subjectID)
                    .setParameter("studentID", studentID)
                    .uniqueResult();
        } catch (Exception ex) {
            System.err.println("Error occur when check if SubjectStudent exists from DB: " + ex);
        }
        
        return subjectStudent != null;
    }

    public Student[] filterStudentNotStudy(String subjectID, Student[] students) {
        ArrayList<Student> filterStudents = new ArrayList<>();
        
        for (Student student : students) {
            if (!isExisted(subjectID, student.getCode())) {
                filterStudents.add(student);
            }
        }
        
        return filterStudents.toArray(new Student[0]);
    }
    
    // Just invoke this function when a student has mark on attendance table
//    public void updateAttendancePercent(int currentWeek, String subjectID, int subjectNameID) {
//        Transaction transaction = null;
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//            session.createNativeQuery(
//                    "call update_attendance_percent(:currentWeek, :subjectID, :subjectNameID)")
//                    .setParameter("currentWeek", currentWeek)
//                    .setParameter("subjectID", subjectID)
//                    .setParameter("subjectNameID", subjectNameID)
//                    .executeUpdate();
//            transaction.commit();   
//        } catch (Exception ex) {
//            System.err.println("Error occur when update attendance percent on DB: " + ex);
//            if (transaction != null) {
//                transaction.rollback();
//            }
//        }
//    }
    
}
