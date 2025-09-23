package org.example.elite_driving_school_management_system.bo.custom.impl;

import org.example.elite_driving_school_management_system.bo.custom.UserBO;
import org.example.elite_driving_school_management_system.dao.DAOFactory;
import org.example.elite_driving_school_management_system.dao.custom.UserDAO;
import org.example.elite_driving_school_management_system.dto.UserDTO;
import org.example.elite_driving_school_management_system.entity.User;
import org.example.elite_driving_school_management_system.util.PasswordUtils;

import java.util.List;
import java.util.stream.Collectors;

public class UserBOImpl implements UserBO {

    private final UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER);
    @Override
    public boolean saveUser(UserDTO dto) throws Exception {
        String hashed = PasswordUtils.hashPassword(dto.getPassword());
        User user = new User(dto.getUserid(), dto.getUsername(), hashed, dto.getRole());
        return userDAO.save(user);
    }

    @Override
    public boolean updateUser(UserDTO dto) throws Exception {
        User existing = userDAO.search(dto.getUserid());
        if (existing == null) return false;
        String hashed = dto.getPassword() == null || dto.getPassword().isBlank() ? existing.getPassword() : PasswordUtils.hashPassword(dto.getPassword());
        User user = new User(dto.getUserid(), dto.getUsername(), hashed, dto.getRole());
        return userDAO.update(user);
    }

    @Override
    public boolean deleteUser(String id) throws Exception {
        return userDAO.delete(id);
    }

    @Override
    public UserDTO searchUser(String id) throws Exception {
        User user = userDAO.search(id);
        return (user != null) ? new UserDTO(user.getUserid(), user.getUsername(), user.getPassword(), user.getRole()) : null;
    }

    @Override
    public List<UserDTO> getAllUsers() throws Exception {
        return userDAO.getAll().stream()
                .map(u -> new UserDTO(u.getUserid(), u.getUsername(),null, u.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUsername(String username) throws Exception {
        User user = userDAO.findByUsername(username);
        return (user != null) ? new UserDTO(user.getUserid(), user.getUsername(), user.getPassword(), user.getRole()) : null;
    }

    @Override
    public String generateNewUserId() throws Exception {
        return userDAO.generateNewUserId();
    }


}
