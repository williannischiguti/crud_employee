package crud_employee.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import crud_employee.model.dao.DaoFactory;
import crud_employee.model.dao.DepartmentDao;
import crud_employee.model.dao.EmployeeDao;
import crud_employee.model.entities.Department;
import crud_employee.model.entities.Employee;

public class Program {

	public static void main(String[] args) {
			
		Scanner sc = new Scanner(System.in);
		Locale.setDefault(Locale.US);
		
		EmployeeDao empDao = DaoFactory.createEmployeeDao();
		DepartmentDao depDao = DaoFactory.createDepartmentDao();
		
		List<Employee> empList = new ArrayList<>();
		List<Department> depList = new ArrayList<>();
		
		Department dep = new Department(7, null);
		empList = empDao.findByDepartment(dep);
		
		for (Employee emp : empList) {
			System.out.println(emp);
		}
		


		
		sc.close();

	}

}
