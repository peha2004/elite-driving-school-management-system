package org.example.elite_driving_school_management_system.bo.custom;

import org.example.elite_driving_school_management_system.bo.SuperBO;
import org.example.elite_driving_school_management_system.dto.LessonDTO;

import java.util.List;

public interface LessonBO extends SuperBO {
    boolean saveLesson(LessonDTO dto) throws Exception;
    boolean updateLesson(LessonDTO dto) throws Exception;
    boolean deleteLesson(String id) throws Exception;
    LessonDTO searchLesson(String id) throws Exception;
    List<LessonDTO> getAllLessons() throws Exception;
    String generateNextLessonId() throws Exception;

    List<String> getAllStudentIds() throws Exception;
    List<String> getAllCourseIds() throws Exception;
    List<String> getAllInstructorIds() throws Exception;
}
