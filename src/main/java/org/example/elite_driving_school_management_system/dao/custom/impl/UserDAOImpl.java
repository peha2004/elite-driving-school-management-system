package org.example.elite_driving_school_management_system.dao.custom.impl;


import org.example.elite_driving_school_management_system.dao.custom.UserDAO;
import org.example.elite_driving_school_management_system.entity.User;
import org.example.elite_driving_school_management_system.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public User findByUsername(String username) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<User> users = session.createQuery("FROM User WHERE username = :uname", User.class)
                .setParameter("uname", username)
                .getResultList();
        session.close();

        if (users.isEmpty()) {
            System.out.println("DEBUG: No user found with username = " + username);
            return null;
        }

        User user = users.get(0);
        System.out.println("DEBUG: Found user -> " + user.getUsername() + " | Password: " + user.getPassword());
        return user;
    }



    @Override
    public boolean save(User entity) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(entity);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(User entity) throws Exception {
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
        User user = session.get(User.class, id);
        if (user != null) {
            session.remove(user);
            tx.commit();
            session.close();
            return true;
        }
        session.close();
        return false;
    }

    @Override
    public User search(String id) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        User user = session.get(User.class, id);
        session.close();
        return user;
    }

    @Override
    public List<User> getAll() throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<User> list = session.createQuery("from User", User.class).list();
        session.close();
        return list;
    }
}
