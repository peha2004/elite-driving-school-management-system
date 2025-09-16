package org.example.elite_driving_school_management_system.dao.custom;

import org.example.elite_driving_school_management_system.dao.CrudDAO;
import org.example.elite_driving_school_management_system.entity.Lesson;

public interface LessonDAO extends CrudDAO<Lesson,String> {
    String getLastLessonId() throws Exception;
}
