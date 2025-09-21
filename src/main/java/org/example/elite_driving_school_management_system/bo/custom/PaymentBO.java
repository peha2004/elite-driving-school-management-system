package org.example.elite_driving_school_management_system.bo.custom;

import org.example.elite_driving_school_management_system.bo.SuperBO;
import org.example.elite_driving_school_management_system.dto.CourseDTO;
import org.example.elite_driving_school_management_system.dto.PaymentDTO;
import org.example.elite_driving_school_management_system.dto.StudentDTO;

import java.util.List;

public interface PaymentBO extends SuperBO {
    boolean savePayment(PaymentDTO dto) throws Exception;
    boolean updatePayment(PaymentDTO dto) throws Exception;
    boolean deletePayment(String id) throws Exception;
    PaymentDTO searchPayment(String id) throws Exception;
    List<PaymentDTO> getAllPayments() throws Exception;
    String generatePaymentId() throws Exception;

    List<PaymentDTO> getPaymentsByStudent(String studentId) throws Exception;
}
