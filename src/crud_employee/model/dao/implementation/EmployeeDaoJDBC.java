package crud_employee.model.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import crud_employee.db.DbException;
import crud_employee.model.dao.EmployeeDao;
import crud_employee.model.entities.Department;
import crud_employee.model.entities.Employee;

public class EmployeeDaoJDBC implements EmployeeDao {

	private Connection conn;
	
	public EmployeeDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	private Employee instantiateEmployee(ResultSet rs, Department dep) throws SQLException {
		Employee emp = new Employee();
		emp.setId(rs.getInt("Id"));
		emp.setName(rs.getString("Name"));
		emp.setEmail(rs.getString("Email"));
		emp.setBirthDate(rs.getDate("BirthDate"));
		emp.setBaseSalary(rs.getDouble("BaseSalary"));
		emp.setDepartment(dep);
		
		return emp;
	}
	
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("Department"));
		
		return dep;
	}
	
	@Override
	public List<Employee> findAll() {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT EMPLOYEE.*, DEPARTMENT.NAME AS Department FROM EMPLOYEE "
									 + "INNER JOIN DEPARTMENT ON EMPLOYEE.DEPARTMENTID = DEPARTMENT.ID "
									 + "ORDER BY NAME");
			rs = st.executeQuery();
			List<Employee> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			while (rs.next()) {
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Employee emp = instantiateEmployee(rs, dep);
				list.add(emp);
			}
			
			return list;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	@Override
	public Employee findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employee> findByDepartment(Department dep) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void insert(Employee emp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Employee emp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}
	
}
