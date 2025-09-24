package org.example.elite_driving_school_management_system.bo.custom.impl;

import org.example.elite_driving_school_management_system.bo.custom.DashboardBO;
import org.example.elite_driving_school_management_system.dao.custom.*;
import org.example.elite_driving_school_management_system.dao.custom.impl.*;

import java.util.Map;

public class DashboardBOImpl implements DashboardBO {

    private final StudentDAO studentDAO = new StudentDAOImpl();
    private final CourseDAO courseDAO = new CourseDAOImpl();
    private final InstructorDAO instructorDAO = new InstructorDAOImpl();
    private final UserDAO userDAO = new UserDAOImpl();
    private final PaymentDAO paymentDAO = new PaymentDAOImpl();

    @Override
    public int getStudentCount() throws Exception {
        return studentDAO.count();
    }

    @Override
    public int getCourseCount() throws Exception {
        return courseDAO.count();
    }

    @Override
    public int getInstructorCount() throws Exception {
        return instructorDAO.count();
    }

    @Override
    public int getUserCount() throws Exception {
        return userDAO.count();
    }

    @Override
    public double getTotalPayments() throws Exception {
        return paymentDAO.getTotalPayments();
    }

    @Override
    public Map<String, Double> getIncomeByDay() throws Exception {
        return paymentDAO.getIncomeByDay();
    }


}
