package org.example.elite_driving_school_management_system.dao;

import org.example.elite_driving_school_management_system.dao.custom.impl.*;

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
        INSTRUCTOR,
        LESSON,
        PAYMENT
    }

    public SuperDAO getDAO(DAOType type) {
        return switch (type) {
            case USER -> new UserDAOImpl();
            case STUDENT -> new StudentDAOImpl();
            case COURSE -> new CourseDAOImpl();
            case INSTRUCTOR -> new InstructorDAOImpl();
            case LESSON -> new LessonDAOImpl();
            case PAYMENT -> new PaymentDAOImpl();
        };
    }
}