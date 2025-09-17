package org.example.elite_driving_school_management_system.bo.custom.impl;

import org.example.elite_driving_school_management_system.bo.custom.PaymentBO;
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
    private final CourseDAO courseDAO = (CourseDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.COURSE);

    @Override
    public boolean savePayment(PaymentDTO dto) throws Exception {
        Student student = studentDAO.search(dto.getStudentId());
        Course course = courseDAO.search(dto.getCourseId());

        Payment payment = new Payment(
                dto.getPaymentId(),
                course,
                dto.getAmount(),
                Date.valueOf(dto.getPaymentDate()),
                student,
                dto.getStatus()
        );
        return paymentDAO.save(payment);
    }

    @Override
    public boolean updatePayment(PaymentDTO dto) throws Exception {
        Student student = studentDAO.search(dto.getStudentId());
        Course course = courseDAO.search(dto.getCourseId());

        Payment payment = new Payment(
                dto.getPaymentId(),
                course,
                dto.getAmount(),
                Date.valueOf(dto.getPaymentDate()),
                student,
                dto.getStatus()
        );
        return paymentDAO.update(payment);
    }

    @Override
    public boolean deletePayment(String id) throws Exception {
        return paymentDAO.delete(id);
    }

    @Override
    public PaymentDTO searchPayment(String id) throws Exception {
        Payment p = paymentDAO.search(id);
        if (p == null) return null;

        return new PaymentDTO(
                p.getPaymentId(),
                p.getCourse().getCourseId(),
                p.getAmount(),
                p.getPaymentDate().toString(),
                p.getStudent().getStudentID(),
                p.getStatus()
        );
    }

    @Override
    public List<PaymentDTO> getAllPayments() throws Exception {
        return paymentDAO.getAll().stream()
                .map(p -> new PaymentDTO(
                        p.getPaymentId(),
                        p.getCourse().getCourseId(),
                        p.getAmount(),
                        p.getPaymentDate().toString(),
                        p.getStudent().getStudentID(),
                        p.getStatus()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public String generatePaymentId() throws Exception {
        return "";
    }

    @Override
    public List<StudentDTO> getAllStudents() throws Exception {
        return studentDAO.getAll().stream()
                .map(s -> new StudentDTO(s.getStudentID()))
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> getAllCourses() throws Exception {
        return courseDAO.getAll().stream()
                .map(c -> new CourseDTO(c.getCourseId()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateStatus(String paymentId, String newStatus) throws Exception {
        Payment payment = paymentDAO.search(paymentId);
        if (payment == null) return false;
        payment.setStatus(newStatus);
        return paymentDAO.update(payment);
    }
}
