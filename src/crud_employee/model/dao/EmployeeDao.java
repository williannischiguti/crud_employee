package crud_employee.model.dao;

import java.util.List;

import crud_employee.model.entities.Department;
import crud_employee.model.entities.Employee;

public interface EmployeeDao {
	
	void insert(Employee emp);
	void update(Employee emp);
	void deleteById(Integer id);
	Employee findById(Integer id);
	List<Employee> findAll();
	List<Employee> findByDepartment(Department dep);
	
}
