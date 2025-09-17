package org.example.elite_driving_school_management_system.bo.custom.impl;

import org.example.elite_driving_school_management_system.bo.custom.StudentBO;
import org.example.elite_driving_school_management_system.dao.DAOFactory;
import org.example.elite_driving_school_management_system.dao.custom.StudentDAO;
import org.example.elite_driving_school_management_system.dto.StudentDTO;
import org.example.elite_driving_school_management_system.entity.Student;

import java.util.List;
import java.util.stream.Collectors;

public class StudentBOImpl implements StudentBO {

    private StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STUDENT);
    @Override
    public boolean saveStudent(StudentDTO dto) throws Exception {
        Student student = new Student(dto.getStudentID(), dto.getName(), dto.getEmail(), dto.getContact(), dto.getRegistrationDate(), null);
        return studentDAO.save(student);
    }

    @Override
    public boolean updateStudent(StudentDTO dto) throws Exception {
        Student student = new Student(dto.getStudentID(), dto.getName(), dto.getEmail(), dto.getContact(), dto.getRegistrationDate(), null);
        return studentDAO.update(student);
    }

    @Override
    public boolean deleteStudent(String id) throws Exception {
        return studentDAO.delete(id);
    }

    @Override
    public StudentDTO searchStudent(String id) throws Exception {
        Student student = studentDAO.search(id);
        if (student == null) return null;

        List<String> courseNames = student.getEnrolledCourses() != null
                ? student.getEnrolledCourses().stream()
                .map(c -> c.getCourseName())
                .collect(Collectors.toList())
                : null;

        return new StudentDTO(student.getStudentID(), student.getName(), student.getEmail(),
                student.getContact(), student.getRegistrationDate(), courseNames);
    }

    @Override
    public List<StudentDTO> getAllStudents() throws Exception {
        return studentDAO.getAll().stream()
                .map(s -> new StudentDTO(
                        s.getStudentID(),
                        s.getName(),
                        s.getEmail(),
                        s.getContact(),
                        s.getRegistrationDate(),
                        s.getEnrolledCourses() != null
                                ? s.getEnrolledCourses().stream()
                                .map(c -> c.getCourseName())
                                .collect(Collectors.toList())
                                : null
                ))
                .collect(Collectors.toList());
    }

    @Override
    public String generateNewStudentId() throws Exception {
        return studentDAO.generateNewId();
    }
}
