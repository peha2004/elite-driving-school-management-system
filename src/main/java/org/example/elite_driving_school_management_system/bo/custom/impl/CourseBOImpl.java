package org.example.elite_driving_school_management_system.bo.custom.impl;

import org.example.elite_driving_school_management_system.bo.custom.CourseBO;
import org.example.elite_driving_school_management_system.bo.exception.DuplicateException;
import org.example.elite_driving_school_management_system.bo.exception.NotFoundException;
import org.example.elite_driving_school_management_system.dao.DAOFactory;
import org.example.elite_driving_school_management_system.dao.custom.CourseDAO;
import org.example.elite_driving_school_management_system.dto.CourseDTO;
import org.example.elite_driving_school_management_system.dto.StudentDTO;
import org.example.elite_driving_school_management_system.entity.Course;
import org.example.elite_driving_school_management_system.entity.Instructor;
import org.example.elite_driving_school_management_system.config.FactoryConfiguration;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CourseBOImpl implements CourseBO {

    private final CourseDAO courseDAO = (CourseDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.COURSE);
    @Override
    public boolean saveCourse(CourseDTO dto) throws Exception {
        if (courseDAO.search(dto.getCourseId()) != null) {
            throw new DuplicateException("Course with ID " + dto.getCourseId() + " already exists!");
        }

        Course course = new Course(dto.getCourseId(), dto.getCourseName(),
                dto.getDuration(), dto.getFee(), dto.getDescription());

        return courseDAO.saveWithInstructors(course, dto.getInstructorIds());
        }


    @Override
    public boolean updateCourse(CourseDTO dto) throws Exception {
        Course existing = courseDAO.search(dto.getCourseId());
        if (existing == null) {
            throw new NotFoundException("Course with ID " + dto.getCourseId() + " does not exist!");
        }

        Course course = new Course(dto.getCourseId(), dto.getCourseName(),
                dto.getDuration(), dto.getFee(), dto.getDescription());

        return courseDAO.saveWithInstructors(course, dto.getInstructorIds());
    }

    @Override
    public boolean deleteCourse(String id) throws Exception {
        Course existing = courseDAO.search(id);
        if (existing == null) {
            throw new NotFoundException("Course with ID " + id + " does not exist!");
        }
        return courseDAO.delete(id);

    }

    @Override
    public List<CourseDTO> getAllCourses() throws Exception {
        List<Course> courses = courseDAO.getAll();
        List<CourseDTO> dtos = new ArrayList<>();


        for (Course c : courses) {
            List<String> instructorIds = null;
            if (c.getInstructors() != null && !c.getInstructors().isEmpty()) {
                instructorIds = c.getInstructors().stream()
                        .map(Instructor::getInstructorId)
                        .collect(Collectors.toList());
            }
            int enrollmentCount = 0;
            Session session = FactoryConfiguration.getInstance().getSession();
            enrollmentCount = ((Long) session.createQuery(
                            "SELECT COUNT(s) FROM Course c JOIN c.students s WHERE c.courseId = :id")
                    .setParameter("id", c.getCourseId())
                    .uniqueResult()).intValue();
            session.close();
            dtos.add(new CourseDTO(
                    c.getCourseId(),
                    c.getCourseName(),
                    c.getDuration(),
                    c.getFee(),
                    c.getDescription(),
                    instructorIds,
                    enrollmentCount
            ));

        }
        return dtos;
    }

    @Override
    public String generateNewId() throws Exception {
        return courseDAO.generateNewId();
    }

    @Override
    public List<String> getAllCourseIds() throws Exception {
        List<Course> courses = courseDAO.getAll();
        List<String> ids = new ArrayList<>();
        for (Course c : courses) {
            ids.add(c.getCourseId());
        }
        return ids;
    }

    @Override
    public CourseDTO search(String id) throws Exception {
        Course c = courseDAO.search(id);
        if (c == null) return null;

        List<String> instructorIds = null;
        if (c.getInstructors() != null) {
            instructorIds = c.getInstructors().stream()
                    .map(Instructor::getInstructorId)
                    .collect(Collectors.toList());
        }

        return new CourseDTO(
                c.getCourseId(),
                c.getCourseName(),
                c.getDuration(),
                c.getFee(),
                c.getDescription(),
                instructorIds,
                0
        );
    }


    @Override
    public CourseDTO searchWithoutInstructors(String id) throws Exception {
        Course c = courseDAO.search(id);
        if (c == null) {
            throw new NotFoundException("Course with ID " + id + " not found!");
        }
        return new CourseDTO(
                c.getCourseId(),
                c.getCourseName(),
                c.getDuration(),
                c.getFee(),
                c.getDescription(),
                null,
                0
        );
    }


}
