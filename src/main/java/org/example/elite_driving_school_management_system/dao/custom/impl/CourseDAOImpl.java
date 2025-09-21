package org.example.elite_driving_school_management_system.dao.custom.impl;

import org.example.elite_driving_school_management_system.dao.custom.CourseDAO;
import org.example.elite_driving_school_management_system.entity.Course;
import org.example.elite_driving_school_management_system.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CourseDAOImpl implements CourseDAO {

    @Override
    public String generateNewId() throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String newId = "C1001";
        try {
            String hql = "SELECT c.courseId FROM Course c ORDER BY c.courseId DESC";
            String lastId = (String) session.createQuery(hql).setMaxResults(1).uniqueResult();

            if (lastId != null) {
                int num = Integer.parseInt(lastId.substring(1)) + 1;
                newId = String.format("C%04d", num);
            }
        } finally {
            session.close();
        }
        return newId;
    }

    @Override
    public Course searchByName(String name) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Course course = session.createQuery(
                        "FROM Course c WHERE c.courseName = :name", Course.class)
                .setParameter("name", name)
                .uniqueResult();
        session.close();
        return course;
    }

    @Override
    public List<Object[]> getAllWithStudentCount() throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Object[]> list = session.createQuery(
                "SELECT c, COUNT(s) " +
                        "FROM Course c " +
                        "LEFT JOIN c.students s " +
                        "GROUP BY c", Object[].class
        ).list();
        session.close();
        return list;
    }

    @Override
    public boolean save(Course entity) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(entity);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Course entity) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Course existing = session.get(Course.class, entity.getCourseId());
        if (existing != null) {
            session.merge(entity);
            tx.commit();
            session.close();
            return true;
        }
        tx.rollback();
        session.close();
        return false;
    }

    @Override
    public boolean delete(String id) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Course course = session.get(Course.class, id);
        if (course != null) {
            session.remove(course);
            tx.commit();
            session.close();
            return true;
        }
        tx.rollback();
        session.close();
        return false;
    }

    @Override
    public Course search(String id) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Course course = session.get(Course.class, id);
        session.close();
        return course;
    }

    @Override
    public List<Course> getAll() throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Course> list = session.createQuery(
                "SELECT DISTINCT c FROM Course c LEFT JOIN FETCH c.instructors", Course.class
        ).list();
        session.close();
        return list;


    }
}
