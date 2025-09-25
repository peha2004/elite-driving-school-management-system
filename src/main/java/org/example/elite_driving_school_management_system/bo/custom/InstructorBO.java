package org.example.elite_driving_school_management_system.bo.custom;

import org.example.elite_driving_school_management_system.bo.SuperBO;
import org.example.elite_driving_school_management_system.dto.InstructorDTO;

import java.util.List;

public interface InstructorBO extends SuperBO {
    boolean saveInstructor(InstructorDTO dto) throws Exception;
    boolean updateInstructor(InstructorDTO dto) throws Exception;
    boolean deleteInstructor(String id) throws Exception;
    List<InstructorDTO> getAllInstructors() throws Exception;
    String generateInstructorId();
}
