package org.example.elite_driving_school_management_system.dao.custom.impl;

import org.example.elite_driving_school_management_system.dao.custom.LessonDAO;
import org.example.elite_driving_school_management_system.entity.Lesson;
import org.example.elite_driving_school_management_system.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class LessonDAOImpl implements LessonDAO {
    @Override
    public boolean save(Lesson entity) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(entity);
            tx.commit();
            return true;
        }
    }

    @Override
    public boolean update(Lesson entity) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.update(entity);
            tx.commit();
            return true;
        }
    }

    @Override
    public boolean delete(String id) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Lesson lesson = session.get(Lesson.class, id);
            if (lesson != null) {
                session.delete(lesson);
                tx.commit();
                return true;
            }
            return false;
        }
    }

    @Override
    public Lesson search(String id) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Lesson.class, id);
        }
    }

    @Override
    public List<Lesson> getAll() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Lesson", Lesson.class).list();
        }
    }

    @Override
    public String getLastLessonId() throws Exception {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT l.lessonId FROM Lesson l ORDER BY l.lessonId DESC";
            return session.createQuery(hql, String.class)
                    .setMaxResults(1)
                    .uniqueResult();
        }
    }
}
