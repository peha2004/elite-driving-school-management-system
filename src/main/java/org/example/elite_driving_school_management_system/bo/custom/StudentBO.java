package org.example.elite_driving_school_management_system.bo.custom;

import org.example.elite_driving_school_management_system.bo.SuperBO;
import org.example.elite_driving_school_management_system.dto.StudentDTO;

import java.util.List;

    public interface StudentBO extends SuperBO {

    boolean saveStudent(StudentDTO dto) throws Exception;
    boolean updateStudent(StudentDTO dto) throws Exception;
    boolean deleteStudent(String id) throws Exception;
    List<StudentDTO> getAllStudents() throws Exception;
    String generateNewStudentId() throws Exception;

        List<String> getAllStudentIds() throws Exception;
        List<String> getEnrolledCourses(String studentId) throws Exception;
}
