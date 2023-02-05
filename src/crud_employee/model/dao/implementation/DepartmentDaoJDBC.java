package crud_employee.model.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import crud_employee.db.DB;
import crud_employee.db.DbException;
import crud_employee.model.dao.DepartmentDao;
import crud_employee.model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		
		Department dep = new Department();
		dep.setId(rs.getInt("Id"));
		dep.setName(rs.getString("Name"));
		
		return dep;
	}

	@Override
	public List<Department> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT * FROM DEPARTMENT");
			rs = st.executeQuery();

			List<Department> list = new ArrayList<>();

			while (rs.next()) {
				list.add(instantiateDepartment(rs));
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	@Override
	public Department findById(Integer id) {
			
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT * FROM DEPARTMENT WHERE ID = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if (rs.next()) {
				return instantiateDepartment(rs);
			}
			return null;
		} catch (SQLException e) {
			throw new DbException (e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public void insert(Department dep) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Department dep) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteBy(Integer id) {
		// TODO Auto-generated method stub

	}

}
