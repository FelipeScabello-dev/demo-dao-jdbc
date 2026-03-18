package model.dao;

import java.util.List;

import model.Department;

public interface DepartmentDao {
    
        void insert(Department obj);
        void update(Department obj);
        void deleteById(Department obj);
        Department findByid (Integer id);
        List<Department> findAll();
        
    
}
