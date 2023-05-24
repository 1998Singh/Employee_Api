package com.evoke.employee.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evoke.employee.entity.Employee;
import com.evoke.employee.exception.ResourceNotFoundException;
import com.evoke.employee.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	public Logger logger = LogManager.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Employee addEmployee(Employee employee) {

		try {
			
			return employeeRepository.save(employee);
		} 
		catch (Exception e) {

			logger.error("Error While adding Employee", e);
		}
		return null;
		
	}

	@Override
	public List<Employee> getAllEmployee() {
		try {
			return employeeRepository.findAll();
		} catch (Exception e) {

			logger.error("Error While Getting Employee");
		}
		return null;
	}

	@Override
	public Employee getEmployeeById(long id) {

		logger.info("Getting the details  employee of id", +id);

		return employeeRepository.findById(id).orElseThrow(()
				-> new ResourceNotFoundException("Employee", "id", id));

	}

	@Override
	public Employee updateEmployee(Employee employee, long id) {

		logger.info("Updating the details  employee of id");
		Employee existingEmployee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", id));

		existingEmployee.setName(employee.getName());
		existingEmployee.setDesignation(employee.getDesignation());
		existingEmployee.setEmail(employee.getEmail());
		existingEmployee.setSalary(employee.getSalary());
		
		existingEmployee.setDoj(employee.getDoj());

		employeeRepository.save(existingEmployee);

		return existingEmployee;
	}

	@Override
	public void deleteEmployee(long id) {
		
		logger.info("Deleting  the details  employee of id");
		employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
		employeeRepository.deleteById(id);

	}

	@Override
	public Employee findByName(String name) {
		logger.info("Getting the details  employee of name ");
		Optional<Employee> employee = employeeRepository.findByName(name);
		if (employee.isPresent()) {

			return employee.get();
		} else {
			throw new ResourceNotFoundException("Employee", " firstName", name);
		}
	}

}
