package org.example.elite_driving_school_management_system.bo.custom.impl;

import org.example.elite_driving_school_management_system.bo.custom.PaymentBO;
import org.example.elite_driving_school_management_system.bo.exception.InUseException;
import org.example.elite_driving_school_management_system.bo.exception.NotFoundException;
import org.example.elite_driving_school_management_system.dao.DAOFactory;
import org.example.elite_driving_school_management_system.dao.custom.CourseDAO;
import org.example.elite_driving_school_management_system.dao.custom.PaymentDAO;
import org.example.elite_driving_school_management_system.dao.custom.StudentDAO;
import org.example.elite_driving_school_management_system.dto.CourseDTO;
import org.example.elite_driving_school_management_system.dto.PaymentDTO;
import org.example.elite_driving_school_management_system.dto.StudentDTO;
import org.example.elite_driving_school_management_system.entity.Course;
import org.example.elite_driving_school_management_system.entity.Payment;
import org.example.elite_driving_school_management_system.entity.Student;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentBOImpl implements PaymentBO {

    private final PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);
    private final StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STUDENT);
    @Override
    public boolean savePayment(PaymentDTO dto) throws Exception {
        Student student = studentDAO.search(dto.getStudentId());
        Payment payment = new Payment(
                dto.getPaymentId(),
                student,
                dto.getTotalFee(),
                dto.getPaidAmount(),
                dto.getBalance(),
                dto.getStatus(),
                Date.valueOf(dto.getPaymentDate())
        );
        return paymentDAO.save(payment);
    }


    @Override
    public boolean deletePayment(String id) throws Exception {
        Payment existing = paymentDAO.search(id);
        if (existing == null) {
            throw new NotFoundException("Payment with ID " + id + " not found!");
        }

        if ("Completed".equalsIgnoreCase(existing.getStatus())) {
            throw new InUseException("Payment with ID " + id + " is completed and cannot be deleted!");
        }
        return paymentDAO.delete(id);
    }

    @Override
    public List<PaymentDTO> getAllPayments() throws Exception {
        return paymentDAO.getAll().stream()
                .map(p -> new PaymentDTO(
                        p.getPaymentId(),
                        p.getStudent().getStudentID(),
                        p.getTotalFee(),
                        p.getPaidAmount(),
                        p.getBalance(),
                        p.getStatus(),
                        p.getPaymentDate().toString()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public String generatePaymentId() throws Exception {
        return paymentDAO.generateId();
    }

    @Override
    public List<PaymentDTO> getPaymentsByStudent(String studentId) throws Exception {
        return paymentDAO.getPaymentsByStudent(studentId).stream()
                .map(p -> new PaymentDTO(
                        p.getPaymentId(),
                        p.getStudent().getStudentID(),
                        p.getTotalFee(),
                        p.getPaidAmount(),
                        p.getBalance(),
                        p.getStatus(),
                        p.getPaymentDate().toString()
                ))
                .collect(Collectors.toList());
    }
}
