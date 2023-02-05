package crud_employee.model.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import crud_employee.db.DB;
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
					+ "INNER JOIN DEPARTMENT ON EMPLOYEE.DEPARTMENTID = DEPARTMENT.ID " + "ORDER BY NAME");
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

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("SELECT EMPLOYEE.*, DEPARTMENT.NAME AS Department FROM EMPLOYEE "
					+ "INNER JOIN DEPARTMENT ON EMPLOYEE.DEPARTMENTID = DEPARTMENT.ID " + "WHERE EMPLOYEE.ID = ?");
			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				Department dep = instantiateDepartment(rs);
				Employee emp = instantiateEmployee(rs, dep);
				return emp;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	@Override
	public List<Employee> findByDepartment(Department department) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT EMPLOYEE.*, DEPARTMENT.NAME AS Department FROM EMPLOYEE "
					+ "INNER JOIN DEPARTMENT ON EMPLOYEE.DEPARTMENTID = DEPARTMENT.ID "
					+ "WHERE DEPARTMENTID = ? ORDER BY NAME");
			st.setInt(1, department.getId());
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
	public void insert(Employee emp) {
		
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("INSERT INTO EMPLOYEE (NAME, EMAIL, BIRTHDATE, BASESALARY, DEPARTMENTID) "
									 + "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, emp.getName());
			st.setString(2, emp.getEmail());
			st.setDate(3, new java.sql.Date(emp.getBirthDate().getTime()));
			st.setDouble(4, emp.getBaseSalary());
			st.setInt(5, emp.getDepartment().getId());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				
				if (rs.next()) {
					emp.setId(rs.getInt(1));
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error! No rows affected");
			}
			
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void update(Employee emp) {
		
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("UPDATE EMPLOYEE "
									 + "SET NAME = ?, EMAIL = ?, BIRTHDATE = ?, BASESALARY = ?, DEPARTMENTID = ? " 
									 + "WHERE ID = ?");
			st.setString(1, emp.getName());
			st.setString(2, emp.getEmail());
			st.setDate(3, new java.sql.Date(emp.getBirthDate().getTime()));
			st.setDouble(4, emp.getBaseSalary());
			st.setInt(5, emp.getDepartment().getId());
			st.setInt(6, emp.getId());
			
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DbException (e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteById(Integer id) {
		
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("DELETE FROM EMPLOYEE WHERE ID = ?");
			st.setInt(1, id);
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected == 0) {
				throw new DbException ("This Id number does not exist");
			}
			
		} catch (SQLException e) {
			throw new DbException (e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

}
