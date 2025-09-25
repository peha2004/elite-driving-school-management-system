package org.example.elite_driving_school_management_system.bo.custom;

import org.example.elite_driving_school_management_system.bo.SuperBO;
import org.example.elite_driving_school_management_system.dto.CourseDTO;

import java.util.List;

public interface CourseBO extends SuperBO {

    boolean saveCourse(CourseDTO dto) throws Exception;
    boolean updateCourse(CourseDTO dto) throws Exception;
    boolean deleteCourse(String id) throws Exception;
    List<CourseDTO> getAllCourses() throws Exception;
    String generateNewId() throws Exception;
    List<String> getAllCourseIds() throws Exception;
    CourseDTO search(String id) throws Exception;
    CourseDTO searchWithInstructors(String id) throws Exception;
    CourseDTO searchWithoutInstructors(String id) throws Exception;
}
