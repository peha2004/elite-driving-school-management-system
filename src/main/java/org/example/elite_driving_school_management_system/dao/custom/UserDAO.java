package org.example.elite_driving_school_management_system.dao.custom;

import org.example.elite_driving_school_management_system.dao.CrudDAO;
import org.example.elite_driving_school_management_system.entity.User;

public interface UserDAO extends CrudDAO<User,String> {
    User findByUsername(String username) throws Exception;
    String generateNewUserId() throws Exception;
}
