package org.example.elite_driving_school_management_system.bo.custom.impl;

import org.example.elite_driving_school_management_system.bo.custom.InstructorBO;
import org.example.elite_driving_school_management_system.bo.exception.DuplicateException;
import org.example.elite_driving_school_management_system.bo.exception.InUseException;
import org.example.elite_driving_school_management_system.bo.exception.NotFoundException;
import org.example.elite_driving_school_management_system.dao.DAOFactory;
import org.example.elite_driving_school_management_system.dao.custom.CourseDAO;
import org.example.elite_driving_school_management_system.dao.custom.InstructorDAO;
import org.example.elite_driving_school_management_system.dao.custom.impl.InstructorDAOImpl;
import org.example.elite_driving_school_management_system.dto.InstructorDTO;
import org.example.elite_driving_school_management_system.entity.Course;
import org.example.elite_driving_school_management_system.entity.Instructor;
import org.example.elite_driving_school_management_system.config.FactoryConfiguration;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class InstructorBOImpl implements InstructorBO {
    private final InstructorDAO instructorDAO = (InstructorDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.INSTRUCTOR);

    public String generateInstructorId() {
        try {
            List<Instructor> allInstructors = instructorDAO.getAll();
            if (allInstructors.isEmpty()) return "I001";
            int maxId = allInstructors.stream()
                    .map(i -> i.getInstructorId().substring(1))
                    .mapToInt(Integer::parseInt)
                    .max()
                    .orElse(0);
            return String.format("I%03d", maxId + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return "I001";
        }
    }
    @Override
    public boolean saveInstructor(InstructorDTO dto) throws Exception {
        if (instructorDAO.search(dto.getInstructorId()) != null) {
            throw new DuplicateException("Instructor with ID " + dto.getInstructorId() + " already exists!");
        }
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
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
        Instructor existing = instructorDAO.search(dto.getInstructorId());
        if (existing == null) {
            throw new NotFoundException("Instructor with ID " + dto.getInstructorId() + " not found!");
        }
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Course course = session.get(Course.class, dto.getCourseId());
            existing.setName(dto.getName());
            existing.setEmail(dto.getEmail());
            existing.setContact(dto.getContact());
            existing.setAvailability(dto.getAvailability());
            existing.setCourse(course);
            return instructorDAO.update(existing);
        }
    }

    @Override
    public boolean deleteInstructor(String id) throws Exception {
        Instructor existing = instructorDAO.search(id);
        if (existing == null) {
            throw new NotFoundException("Instructor with ID " + id + " not found!");
        }
        try {
            return instructorDAO.delete(id);
        } catch (Exception e) {
            throw new InUseException("Instructor with ID " + id + " is in use and cannot be deleted!");
        }
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
