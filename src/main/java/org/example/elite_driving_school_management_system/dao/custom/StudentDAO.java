package org.example.elite_driving_school_management_system.dao.custom;

import org.example.elite_driving_school_management_system.dao.CrudDAO;
import org.example.elite_driving_school_management_system.entity.Student;

public interface StudentDAO extends CrudDAO<Student,String> {
    String generateNewId() throws Exception;
}
