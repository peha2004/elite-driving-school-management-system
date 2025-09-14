package org.example.elite_driving_school_management_system.bo.custom.impl;

import org.example.elite_driving_school_management_system.bo.custom.InstructorBO;
import org.example.elite_driving_school_management_system.dao.DAOFactory;
import org.example.elite_driving_school_management_system.dao.custom.CourseDAO;
import org.example.elite_driving_school_management_system.dao.custom.InstructorDAO;
import org.example.elite_driving_school_management_system.dao.custom.impl.InstructorDAOImpl;
import org.example.elite_driving_school_management_system.dto.InstructorDTO;
import org.example.elite_driving_school_management_system.entity.Course;
import org.example.elite_driving_school_management_system.entity.Instructor;
import org.example.elite_driving_school_management_system.util.HibernateUtil;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class InstructorBOImpl implements InstructorBO {
    private final InstructorDAO instructorDAO = (InstructorDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.INSTRUCTOR);
    @Override
    public boolean saveInstructor(InstructorDTO dto) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Course course = session.get(Course.class, dto.getCourseId());
            Instructor instructor = new Instructor(
                    dto.getInstructorId(),
                    dto.getName(),
                    dto.getEmail(),
                    dto.getContact(),
                    dto.getAvailability(),
                    course
            );
            return instructorDAO.save(instructor);
        }
    }

    @Override
    public boolean updateInstructor(InstructorDTO dto) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Course course = session.get(Course.class, dto.getCourseId());
            Instructor instructor = new Instructor(
                    dto.getInstructorId(),
                    dto.getName(),
                    dto.getEmail(),
                    dto.getContact(),
                    dto.getAvailability(),
                    course
            );
            return instructorDAO.update(instructor);
        }
    }

    @Override
    public boolean deleteInstructor(String id) throws Exception {
        return instructorDAO.delete(id);
    }

    @Override
    public InstructorDTO searchInstructor(String id) throws Exception {
        Instructor instructor = instructorDAO.search(id);
        if (instructor != null) {
            return new InstructorDTO(
                    instructor.getInstructorId(),
                    instructor.getName(),
                    instructor.getEmail(),
                    instructor.getContact(),
                    instructor.getAvailability(),
                    instructor.getCourse().getCourseId()
            );
        }
        return null;
    }

    @Override
    public List<InstructorDTO> getAllInstructors() throws Exception {
        List<Instructor> instructors = instructorDAO.getAll();
        List<InstructorDTO> dtos = new ArrayList<>();
        for (Instructor i : instructors) {
            dtos.add(new InstructorDTO(
                    i.getInstructorId(),
                    i.getName(),
                    i.getEmail(),
                    i.getContact(),
                    i.getAvailability(),
                    i.getCourse().getCourseId()
            ));
        }
        return dtos;
    }
}
