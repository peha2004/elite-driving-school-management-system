package org.example.elite_driving_school_management_system.dao;

import java.util.List;

public interface CrudDAO<T,ID> extends SuperDAO{
    boolean save(T entity) throws Exception;
    boolean update(T entity) throws Exception;
    boolean delete(ID id) throws Exception;
    T search(ID id) throws Exception;
    List<T> getAll() throws Exception;
}
