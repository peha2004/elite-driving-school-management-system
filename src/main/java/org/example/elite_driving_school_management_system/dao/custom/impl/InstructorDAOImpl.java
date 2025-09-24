package org.example.elite_driving_school_management_system.dao.custom.impl;

import org.example.elite_driving_school_management_system.dao.custom.InstructorDAO;
import org.example.elite_driving_school_management_system.entity.Instructor;
import org.example.elite_driving_school_management_system.config.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class InstructorDAOImpl implements InstructorDAO {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();
    @Override
    public boolean save(Instructor entity) throws Exception {
        try (Session session =factoryConfiguration.getSession()){
            Transaction tx = session.beginTransaction();
            session.save(entity);
            tx.commit();
            return true;
        }
    }

    @Override
    public boolean update(Instructor entity) throws Exception {
        try (Session session = factoryConfiguration.getSession()) {
            Transaction tx = session.beginTransaction();
            session.update(entity);
            tx.commit();
            return true;
        }
    }

    @Override
    public boolean delete(String id) throws Exception {
        try (Session session =  factoryConfiguration.getSession()) {
            Transaction tx = session.beginTransaction();
            Instructor instructor = session.get(Instructor.class, id);
            if (instructor != null) {
                session.delete(instructor);
                tx.commit();
                return true;
            }
            return false;
        }
    }

    @Override
    public Instructor search(String id) throws Exception {
        try (Session session =  factoryConfiguration.getSession()) {
            return session.get(Instructor.class, id);
        }
    }

    @Override
    public List<Instructor> getAll() throws Exception {
        try (Session session = factoryConfiguration.getSession()) {
            return session.createQuery("FROM Instructor", Instructor.class).list();
        }
    }

    @Override
    public int count() throws Exception {
        Session session = factoryConfiguration.getSession();
        Long count = session.createQuery("SELECT COUNT(i) FROM Instructor i", Long.class).uniqueResult();
        session.close();
        return count != null ? count.intValue() : 0;
    }
}
