package com.evoke.employee.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.evoke.employee.entity.Department;
import com.evoke.employee.entity.Employee;

@DataJpaTest
public class EmployeeRepositoryTest {

	@Autowired
	EmployeeRepository employeeRepository;
	
	

	@DisplayName("Junit test for save employee operation")
	@Test
	public void givenEmployeeObject_whenSave_thenReturnSaveEmployee() {

		Employee employee = new Employee();
		employee.setId(1L);
		employee.setName("Satyam");
		employee.setDoj("23/12/2022");
		employee.setDesignation("Techanical Associate");
		employee.setEmail("satyam@gmail.com");

		Employee saveEmployee = employeeRepository.save(employee);

		assertThat(saveEmployee).isNotNull();
		assertThat(saveEmployee.getId()).isGreaterThan(0);

	}

	@DisplayName("Junit test for get all employee operation")
	@Test
	public void givenEmployeeList_whenFindAll_thenEmployeesList() {

		Employee employee = new Employee();
		employee.setId(12L);
		employee.setName("Satyam");
		employee.setDoj("23/12/2022");
		employee.setDesignation("Techanical Associate");
		employee.setEmail("satyam@gmail.com");

		employeeRepository.save(employee);
		List<Employee> employeelist = employeeRepository.findAll();

		assertThat(employeelist).isNotNull();
		assertThat(employeelist.size()).isEqualTo(1);

	}

	@DisplayName("Junit test for get employee by id ")
	@Test
	public void givenEmployeeObject_whenFindById_thenEmployeesObject() {

		Employee employee = new Employee();
		employee.setId(5L);
		employee.setName("Satyam");
		employee.setDoj("23/12/2022");
		employee.setDesignation("Techanical Associate");
		employee.setEmail("satyam@gmail.com");

		employeeRepository.save(employee);

		Employee e1 = employeeRepository.findById(5L).get();

		assertEquals(employee.getId(), e1.getId());
		assertThat(e1).isNotNull();

	}

	@DisplayName("Junit test for get employee by name ")
	@Test
	public void givenEmployeeName_whenFindByName_thenEmployeesObject() {

		Employee employees = new Employee();
		employees.setName("Ram");
		employees.setDoj("23/12/2022");
		employees.setDesignation("Techanical Associate");
		employees.setEmail("ram@gmail.com");

		employeeRepository.save(employees);

		Employee e1 = employeeRepository.findByName(employees.getName()).get();

		assertEquals(employees.getEmail(), e1.getEmail());
		assertThat(employees).isNotNull();

	}

	@DisplayName("Junit test for Update Employee")
	@Test
	public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {

		Employee emp = new Employee();
		emp.setName("Satyam");
		emp.setDoj("23/12/2022");
		emp.setDesignation("Techanical Associate");
		emp.setEmail("satyam@gmail.com");
		employeeRepository.save(emp);

		Employee saveEmployee =employeeRepository.getById(emp.getId());
		saveEmployee.setName("Ram");
		saveEmployee.setEmail("ram@gmail.com");
		Employee updateEmployee = employeeRepository.save(saveEmployee);

		assertThat(updateEmployee.getName()).isEqualTo("Ram");
		assertThat(updateEmployee.getEmail()).isEqualTo("ram@gmail.com");

	}
	
	@DisplayName("Junit test for delete Employee")
	@Test
	public void givenEmployeeObject_whenDelete_thenRemoveEmployee() {

		Employee employee1 = new Employee();
		employee1.setId(11L);
		employee1.setName("Satyam");
		employee1.setDoj("23/12/2022");
		employee1.setDesignation("Techanical Associate");
		employee1.setEmail("satyam@gmail.com");

		
		employeeRepository.save(employee1);
		
		employeeRepository.delete(employee1);

		Optional<Employee> e1 = employeeRepository.findById(11L);

		assertThat(e1).isEmpty();

	}


}
