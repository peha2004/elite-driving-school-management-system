package org.example.elite_driving_school_management_system.dao;

import org.example.elite_driving_school_management_system.dao.custom.impl.CourseDAOImpl;
import org.example.elite_driving_school_management_system.dao.custom.impl.InstructorDAOImpl;
import org.example.elite_driving_school_management_system.dao.custom.impl.StudentDAOImpl;
import org.example.elite_driving_school_management_system.dao.custom.impl.UserDAOImpl;

public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory() {}

    public static DAOFactory getInstance() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOType {
        USER,
        STUDENT,
        COURSE,
        INSTRUCTOR
    }

    public SuperDAO getDAO(DAOType type) {
        return switch (type) {
            case USER -> new UserDAOImpl();
            case STUDENT -> new StudentDAOImpl();
            case COURSE -> new CourseDAOImpl();
            case INSTRUCTOR -> new InstructorDAOImpl();
        };
    }
}