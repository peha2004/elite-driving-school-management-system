package org.example.elite_driving_school_management_system.dao.custom;

import org.example.elite_driving_school_management_system.dao.CrudDAO;
import org.example.elite_driving_school_management_system.entity.Student;

import java.util.List;

public interface StudentDAO extends CrudDAO<Student,String> {
    String generateNewId() throws Exception;
    List<String> getEnrolledCourseIds(String studentId) throws Exception;
    int count() throws Exception;
}
