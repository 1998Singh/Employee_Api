package com.evoke.employee.service;

import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evoke.employee.entity.Department;
import com.evoke.employee.entity.Employee;
import com.evoke.employee.exception.ResourceNotFoundException;
import com.evoke.employee.repository.DepartmentRepository;
import com.evoke.employee.repository.EmployeeRepository;

@Service
public class DepartmentserviceImpl implements DepartmentService {

	public Logger logger = LogManager.getLogger(DepartmentserviceImpl.class);

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Department addDepartment(Department department) {
		try {
			return departmentRepository.save(department);
		} catch (Exception e) {

			logger.error("Error While adding Department", e);
		}
		return null;
	}

	@Override
	public List<Department> getAllDepartment() {
		try {
			return departmentRepository.findAll().stream().map(e -> {
			      e.setAssignedEmployees(null);
			      return e;
			    })
			    .toList();
		} catch (Exception e) {

			logger.error("Error While Getting All Departemet ");
		}
		return null;
	}

	@Override
	public Department getDepartmentById(long dept_id) {

		logger.info("Getting the details  department of dept_id");
	return	departmentRepository.findById(dept_id)
				.orElseThrow(() -> new ResourceNotFoundException("Departmant", "dept_id", dept_id));
	
		
	}

	@Override
	public Department updateDepartment(Department department, long dept_id) {

		logger.info("Updating the Department dataild with particular dept_id");

		Department existingDepartment = departmentRepository.findById(dept_id)
				.orElseThrow(() -> new ResourceNotFoundException("Department", "dept_id", dept_id));

		existingDepartment.setName(department.getName());
		departmentRepository.save(existingDepartment);
		return existingDepartment;
	}

	@Override
	public void deleteDepartment(long dept_id) {
		departmentRepository.findById(dept_id)
				.orElseThrow(() -> new ResourceNotFoundException("Department", "dept_id", dept_id));

		departmentRepository.deleteById(dept_id);
	}

	@Override
	public Department assignEmployeeToDepartment(long dept_id, long id) {

		Set<Employee> employeeSet = null;
		Department department = departmentRepository.findById(dept_id).get();
		Employee employee = employeeRepository.findById(id).get();
		employeeSet = department.getAssignedEmployees();
		employeeSet.add(employee);
		department.setAssignedEmployees(employeeSet);
		return departmentRepository.save(department);
	}

	@Override
	public void deleteEmployeeFromDepartment(long dept_id, long id) {

		Department department = departmentRepository.findById(dept_id)
				.orElseThrow(() -> new ResourceNotFoundException("Department", "dept_id", dept_id));
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
		department.getAssignedEmployees().remove(employee);
		departmentRepository.save(department);

	}

}
