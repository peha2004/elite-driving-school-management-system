package org.example.elite_driving_school_management_system.dao.custom.impl;

import org.example.elite_driving_school_management_system.dao.custom.PaymentDAO;
import org.example.elite_driving_school_management_system.entity.Payment;
import org.example.elite_driving_school_management_system.config.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PaymentDAOImpl implements PaymentDAO {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();
    @Override
    public boolean save(Payment entity) throws Exception {
        Session session = factoryConfiguration.getSession();
        Transaction tx = session.beginTransaction();
        session.persist(entity);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Payment entity) throws Exception {
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
        Payment payment = session.get(Payment.class, id);
        if (payment != null) {
            session.remove(payment);
        }
        tx.commit();
        session.close();
        return payment != null;
    }

    @Override
    public Payment search(String s) throws Exception {
        return null;
    }

    @Override
    public List<Payment> getAll() throws Exception {
        Session session = factoryConfiguration.getSession();
        List<Payment> list = session.createQuery("FROM Payment", Payment.class).list();
        session.close();
        return list;
    }

    @Override
    public String generateId() throws Exception {
        Session session =factoryConfiguration.getSession();
        String lastId = (String) session.createQuery("SELECT p.paymentId FROM Payment p ORDER BY p.paymentId DESC")
                .setMaxResults(1)
                .uniqueResult();
        session.close();

        if (lastId == null) {
            return "PAY001";
        } else {
            int num = Integer.parseInt(lastId.replace("PAY", "")) + 1;
            return String.format("PAY%03d", num);
        }
    }

    @Override
    public List<Payment> getPaymentsByStudent(String studentId) throws Exception {
        try (Session session = factoryConfiguration.getSession()) {
            String hql = "FROM Payment p WHERE p.student.studentID = :studentId ORDER BY p.paymentDate DESC";
            return session.createQuery(hql, Payment.class)
                    .setParameter("studentId", studentId)
                    .list();
        }
    }

    @Override
    public double getTotalPayments() throws Exception {
        try (Session session =  factoryConfiguration.getSession()) {
            Query<Double> query = session.createQuery("SELECT SUM(p.paidAmount) FROM Payment p", Double.class);
            Double total = query.uniqueResult();
            return total != null ? total : 0.0;
        }
    }

    @Override
    public Map<String, Double> getIncomeByDay() throws Exception {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            String hql = "SELECT p.paymentDate, SUM(p.paidAmount) " +
                    "FROM Payment p " +
                    "GROUP BY p.paymentDate " +
                    "ORDER BY p.paymentDate";

            List<Object[]> results = session.createQuery(hql, Object[].class).getResultList();

            Map<String, Double> incomeByDay = new LinkedHashMap<>();
            for (Object[] row : results) {

                Date day = (Date) row[0];
                Double total = (Double) row[1];

                String formattedDay = new java.text.SimpleDateFormat("yyyy-MM-dd").format(day);
                incomeByDay.put(formattedDay, total != null ? total : 0.0);
            }
            return incomeByDay;
        }
    }


}
