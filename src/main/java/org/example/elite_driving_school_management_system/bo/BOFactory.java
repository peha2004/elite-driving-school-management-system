package org.example.elite_driving_school_management_system.bo;

import org.example.elite_driving_school_management_system.bo.custom.impl.CourseBOImpl;
import org.example.elite_driving_school_management_system.bo.custom.impl.InstructorBOImpl;
import org.example.elite_driving_school_management_system.bo.custom.impl.StudentBOImpl;
import org.example.elite_driving_school_management_system.bo.custom.impl.UserBOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {}

    public static BOFactory getInstance() {
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOType {
        USER,
        STUDENT,
        COURSE,
        INSTRUCTOR
    }

    public SuperBO getBO(BOType type) {
        return switch (type) {
            case USER -> new UserBOImpl();
            case STUDENT -> new StudentBOImpl();
            case COURSE -> new CourseBOImpl();
            case INSTRUCTOR -> new InstructorBOImpl();
        };
    }
}
