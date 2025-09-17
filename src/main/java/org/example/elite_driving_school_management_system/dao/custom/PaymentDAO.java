package org.example.elite_driving_school_management_system.dao.custom;

import org.example.elite_driving_school_management_system.dao.CrudDAO;
import org.example.elite_driving_school_management_system.entity.Payment;

public interface PaymentDAO  extends CrudDAO<Payment,String> {
    String generateId() throws Exception;

}
