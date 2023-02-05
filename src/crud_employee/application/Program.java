package crud_employee.application;

import java.util.ArrayList;
import java.util.Date;
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
		
		System.out.println("\n=== Test department insert =======");
		Department newDepartment = new Department(null, "Sales");
		depDao.insert(newDepartment);
		System.out.println("Inserted! New id: " + newDepartment.getId());
		System.out.println();
		
		System.out.println("\n=== Test employee insert =======");
		Employee newEmployee = new Employee(null, "Darth Vader", "darth@gmail.com", new Date(), 4000.00, newDepartment);
		empDao.insert(newEmployee);
		System.out.println("Inserted! New id: " + newEmployee.getId());

		
		sc.close();

	}

}
