package crud_employee.model.dao;

import java.util.List;

import crud_employee.model.entities.Department;

public interface DepartmentDao {

	void insert(Department dep);
	void update(Department dep);
	void deleteBy(Integer id);
	Department findById(Integer id);
	List<Department> findAll();
}
