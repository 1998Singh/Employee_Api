package com.evoke.employee.service;

import java.util.List;

import com.evoke.employee.entity.Employee;

public interface EmployeeService {
	
	Employee addEmployee(Employee employee);
	
	List<Employee> getAllEmployee();
	
	Employee findByName(String name);
	
	Employee getEmployeeById(long id);
	
	Employee updateEmployee(Employee employee, long id);
	
	void deleteEmployee(long id);	
}
