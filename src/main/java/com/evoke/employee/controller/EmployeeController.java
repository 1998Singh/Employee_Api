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

import com.evoke.employee.entity.Employee;

import com.evoke.employee.service.EmployeeServiceImpl;


@RestController
@RequestMapping("/api")
public class EmployeeController {

	public Logger logger = LogManager.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeServiceImpl employeeServiceimpl;

	
	@PostMapping("/employees")
	public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {

		logger.debug("addEmployee start adding employee information to database ");
		Employee result = employeeServiceimpl.addEmployee(employee);
		if (ObjectUtils.isEmpty(result)) {
			return new ResponseEntity<String>("Error While Adding employee", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Employee>(result, HttpStatus.CREATED);
	}

	@GetMapping("/employees")
	public List<Employee> getAllEmployee() {

		logger.info("Getting All Employee Details");
		return employeeServiceimpl.getAllEmployee();
	}

	@GetMapping("/employee/{name}")
	public ResponseEntity<Employee> findByName(@PathVariable("name") String name) {
		logger.info(name);
		Employee employee = employeeServiceimpl.findByName(name);
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long employeeId) {

		return new ResponseEntity<Employee>(employeeServiceimpl.getEmployeeById(employeeId), HttpStatus.OK);
	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee employee) {

		return new ResponseEntity<Employee>(employeeServiceimpl.updateEmployee(employee, id), HttpStatus.OK);
	}

	@DeleteMapping("/employees/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") long id) {
		employeeServiceimpl.deleteEmployee(id);
		return new ResponseEntity<String>("Employee deleted successfully", HttpStatus.OK);
	}
}
