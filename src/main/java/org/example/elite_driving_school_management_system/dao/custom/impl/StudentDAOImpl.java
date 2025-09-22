package org.example.elite_driving_school_management_system.dao.custom.impl;

import org.example.elite_driving_school_management_system.dao.custom.StudentDAO;
import org.example.elite_driving_school_management_system.entity.Student;
import org.example.elite_driving_school_management_system.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public boolean save(Student entity) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(entity);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Student entity) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.merge(entity);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Student student = session.get(Student.class, id);
        if (student != null) {
            session.remove(student);
            tx.commit();
            session.close();
            return true;
        }
        tx.rollback();
        session.close();
        return false;
    }

    @Override
    public Student search(String id) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Student student = session.get(Student.class, id);
        session.close();
        return student;
    }

    @Override
    public List<Student> getAll() throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Student> list = session.createQuery("from Student", Student.class).list();
        session.close();
        return list;
    }
    public String generateNewId() throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String newId = "S001";
        try {
            String hql = "SELECT s.studentID FROM Student s ORDER BY s.studentID DESC";
            String lastId = (String) session.createQuery(hql).setMaxResults(1).uniqueResult();

            if (lastId != null) {
                int num = Integer.parseInt(lastId.substring(1)) + 1;
                newId = String.format("S%03d", num);
            }
        } finally {
            session.close();
        }
        return newId;
    }

    public List<String> getEnrolledCourseIds(String studentId) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT c.courseId FROM Student s JOIN s.enrolledCourses c WHERE s.studentID = :id",
                            String.class
                    )
                    .setParameter("id", studentId)
                    .list();
        }
    }
}
