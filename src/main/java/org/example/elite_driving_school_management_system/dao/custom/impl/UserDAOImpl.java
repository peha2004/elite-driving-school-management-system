package org.example.elite_driving_school_management_system.dao.custom.impl;


import org.example.elite_driving_school_management_system.config.FactoryConfiguration;
import org.example.elite_driving_school_management_system.dao.custom.UserDAO;
import org.example.elite_driving_school_management_system.entity.User;
import org.example.elite_driving_school_management_system.config.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();
    @Override
    public User findByUsername(String username) {
        Session session = factoryConfiguration.getSession();
        List<User> users = session.createQuery("FROM User WHERE LOWER(username) = :uname", User.class)
                .setParameter("uname", username.toLowerCase())
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
        Session session = factoryConfiguration.getSession();
        Transaction tx = session.beginTransaction();
        session.persist(entity);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(User entity) throws Exception {
        Session session = factoryConfiguration.getSession();
        Transaction tx = session.beginTransaction();
        session.merge(entity);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) throws Exception {
        Session session = factoryConfiguration.getSession();
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
        Session session = factoryConfiguration.getSession();
        User user = session.get(User.class, id);
        session.close();
        return user;
    }

    @Override
    public List<User> getAll() throws Exception {
        Session session = factoryConfiguration.getSession();
        List<User> list = session.createQuery("from User", User.class).list();
        session.close();
        return list;
    }

    public String generateNewUserId() throws Exception {
        Session session = factoryConfiguration.getSession();
        String lastId = (String) session.createQuery("SELECT u.userid FROM User u WHERE u.userid LIKE 'U%' ORDER BY cast(substring(u.userid, 2) as int) DESC")
                .setMaxResults(1)
                .uniqueResult();
        session.close();

        if (lastId != null && !lastId.trim().isEmpty()) {
            int newId = Integer.parseInt(lastId.substring(1)) + 1;
            return String.format("U%03d", newId);
        } else {
            return "U001";
        }
    }

    @Override
    public int count() throws Exception {
        Session session = factoryConfiguration.getSession();
        Long count = session.createQuery("SELECT COUNT(u) FROM User u", Long.class).uniqueResult();
        session.close();
        return count != null ? count.intValue() : 0;
    }
}
