package com.evoke.employee.service;

import java.util.List;

import com.evoke.employee.entity.Department;


public interface DepartmentService {
	
	Department addDepartment (Department department);
	
	List<Department> getAllDepartment();
	
	Department getDepartmentById(long dept_id);
	
	Department updateDepartment(Department department, long dept_id);
	
	void deleteDepartment(long dept_id);
	
    Department assignEmployeeToDepartment(long dept_id, long id);
    
    void deleteEmployeeFromDepartment(long dept_id, long id);
	

}
