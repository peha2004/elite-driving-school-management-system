package org.example.elite_driving_school_management_system.dao.custom;

import org.example.elite_driving_school_management_system.dao.CrudDAO;
import org.example.elite_driving_school_management_system.entity.Course;

public interface CourseDAO extends CrudDAO<Course,String> {
    String generateNewId() throws Exception;

}
