package org.example.elite_driving_school_management_system.dao.custom.impl;

import org.example.elite_driving_school_management_system.config.FactoryConfiguration;
import org.example.elite_driving_school_management_system.dao.custom.LessonDAO;
import org.example.elite_driving_school_management_system.entity.Lesson;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class LessonDAOImpl implements LessonDAO {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();
    @Override
    public boolean save(Lesson entity) throws Exception {
        try (Session session =  factoryConfiguration.getSession()) {
            Transaction tx = session.beginTransaction();
            session.save(entity);
            tx.commit();
            return true;
        }
    }

    @Override
    public boolean update(Lesson entity) throws Exception {
        try (Session session = factoryConfiguration.getSession()) {
            Transaction tx = session.beginTransaction();
            session.update(entity);
            tx.commit();
            return true;
        }
    }

    @Override
    public boolean delete(String id) throws Exception {
        try (Session session = factoryConfiguration.getSession()) {
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
        try (Session session =factoryConfiguration.getSession()) {
            return session.get(Lesson.class, id);
        }
    }

    @Override
    public List<Lesson> getAll() throws Exception {
        try (Session session = factoryConfiguration.getSession()) {
            return session.createQuery("from Lesson", Lesson.class).list();
        }
    }

    @Override
    public String getLastLessonId() throws Exception {
        try (var session = factoryConfiguration.getSession()) {
            String hql = "SELECT l.lessonId FROM Lesson l ORDER BY l.lessonId DESC";
            return session.createQuery(hql, String.class)
                    .setMaxResults(1)
                    .uniqueResult();
        }
    }
}
