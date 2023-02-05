package crud_employee.model.dao;

import crud_employee.db.DB;
import crud_employee.model.dao.implementation.DepartmentDaoJDBC;
import crud_employee.model.dao.implementation.EmployeeDaoJDBC;

public class DaoFactory {

	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
	
	public static EmployeeDao createEmployeeDao() {
		return new EmployeeDaoJDBC(DB.getConnection());
	}
}
