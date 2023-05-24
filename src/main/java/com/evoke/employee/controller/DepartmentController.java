package com.evoke.employee.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evoke.employee.entity.Department;
import com.evoke.employee.service.DepartmentserviceImpl;

@RestController
@RequestMapping("/api")
public class DepartmentController {

	public Logger logger = LogManager.getLogger(DepartmentController.class);

	@Autowired
	private DepartmentserviceImpl departmentserviceImpl;

	@PostMapping("/departments")
	public ResponseEntity<?> addDepartment(@RequestBody Department department) {

		logger.info("addDepartment: start adding department information to database ");
		Department result = departmentserviceImpl.addDepartment(department);
		if (ObjectUtils.isEmpty(result)) {
			return new ResponseEntity<String>("Error While Adding department", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Department>(result, HttpStatus.CREATED);
	}

	@GetMapping("/departments")
	public List<Department> getAllDepartment() {
		logger.info("Getting All Departments Details");
		return departmentserviceImpl.getAllDepartment();
	}
	
	@GetMapping("/departments/{dept_id}")
	public ResponseEntity<Department> getDepartmentById(@PathVariable("dept_id") long dept_id ) {

		return new ResponseEntity<Department>(departmentserviceImpl.getDepartmentById(dept_id ), HttpStatus.OK);
	}

	@PutMapping("/departments/{dept_id}")
	public ResponseEntity<Department> updateDepartment(@PathVariable("dept_id") long dept_id, @RequestBody Department department ) {

		return new ResponseEntity<Department>(departmentserviceImpl.updateDepartment(department, dept_id), HttpStatus.OK);
	}

	@DeleteMapping("/departments/{dept_id}")
	public ResponseEntity<String> deleteDepartment(@PathVariable("dept_id") long dept_id) {
		departmentserviceImpl.deleteDepartment(dept_id);
		return new ResponseEntity<String>("Department deleted successfully", HttpStatus.OK);
	}
	
    
	@PutMapping("/department/{dept_id}/employee/{id}")
	public Department assignEmployeeToDepartment(@PathVariable("dept_id") long dept_id,
			@PathVariable("id") long id) {
				return departmentserviceImpl.assignEmployeeToDepartment(dept_id,id);
		
	}
	
	@DeleteMapping("/department/{dept_id}/employee/{id}")
	public ResponseEntity<String> deleteEmployeeFromDepartment(@PathVariable("dept_id") long dept_id,
			@PathVariable("id") long id) {
		
		departmentserviceImpl.deleteEmployeeFromDepartment(dept_id,id);
		return  new ResponseEntity<String>("Employee deleted successfully", HttpStatus.OK);
			
	}
	
	
}
