package org.example.elite_driving_school_management_system.dao.custom;

import org.example.elite_driving_school_management_system.dao.CrudDAO;
import org.example.elite_driving_school_management_system.entity.Payment;

import java.util.List;
import java.util.Map;

public interface PaymentDAO  extends CrudDAO<Payment,String> {
    String generateId() throws Exception;
    List<Payment> getPaymentsByStudent(String studentId) throws Exception;
    double getTotalPayments() throws Exception;
    Map<String, Double> getIncomeByDay() throws Exception;
}
