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
		
		System.out.println("\n=== Test employee delete =======");
		empDao.deleteById(21);
		System.out.println("Delete completed");
		
		System.out.println("\n=== Test department delete =======");

		depDao.deleteById(8);
		System.out.println("Delete completed");
		System.out.println();
		


		
		sc.close();

	}

}
