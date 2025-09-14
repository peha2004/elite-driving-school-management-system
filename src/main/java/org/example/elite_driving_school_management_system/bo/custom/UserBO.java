package org.example.elite_driving_school_management_system.bo.custom;

import org.example.elite_driving_school_management_system.bo.SuperBO;
import org.example.elite_driving_school_management_system.dto.UserDTO;

import java.util.List;

public interface UserBO extends SuperBO {
    boolean saveUser(UserDTO dto) throws Exception;
    boolean updateUser(UserDTO dto) throws Exception;
    boolean deleteUser(String id) throws Exception;
    UserDTO searchUser(String id) throws Exception;
    List<UserDTO> getAllUsers() throws Exception;
    UserDTO findByUsername(String username) throws Exception;

}
