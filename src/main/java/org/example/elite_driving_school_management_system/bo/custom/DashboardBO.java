package org.example.elite_driving_school_management_system.bo.custom;

import org.example.elite_driving_school_management_system.bo.SuperBO;

import java.util.Map;

public interface DashboardBO extends SuperBO {
    int getStudentCount() throws Exception;
    int getCourseCount() throws Exception;
    int getInstructorCount() throws Exception;
    int getUserCount() throws Exception;
    double getTotalPayments() throws Exception;
    Map<String, Double> getIncomeByDay() throws Exception;
}
