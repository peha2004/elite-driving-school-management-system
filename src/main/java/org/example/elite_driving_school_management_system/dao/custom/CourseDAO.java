package org.example.elite_driving_school_management_system.dao.custom;

import org.example.elite_driving_school_management_system.dao.CrudDAO;
import org.example.elite_driving_school_management_system.entity.Course;

import java.util.List;

public interface CourseDAO extends CrudDAO<Course,String> {
    String generateNewId() throws Exception;
    Course searchByName(String name) throws Exception;
    List<Object[]> getAllWithStudentCount() throws Exception;

    Course getWithInstructors(String id) throws Exception;
    boolean saveWithInstructors(Course course, List<String> instructorIds) throws Exception;

    int count() throws Exception;
}
