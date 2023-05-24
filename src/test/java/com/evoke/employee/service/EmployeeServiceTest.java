package com.evoke.employee.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mock.*;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.evoke.employee.entity.Employee;
import com.evoke.employee.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

	@Mock
	
	private EmployeeRepository employeeRepository;
	@InjectMocks
	private EmployeeServiceImpl employeeServiceImpl;

	@DisplayName("JUnit test for addEmployee method")
	@Test
	public void givenEmployeeObject_whenAddEmployee_thenReturnEmployeeObject() {
		Employee employee = new Employee();
		employee.setName("Ram");
		employee.setDesignation("Techanical Associate");
		employee.setEmail("ram@gmail.com");
		employee.setDoj("04-05-2022");
		employee.setSalary(70000);

		given(employeeRepository.save(employee)).willReturn(employee);

		Employee saveEmployee = employeeServiceImpl.addEmployee(employee);

		assertThat(saveEmployee).isNotNull();
	}

	@DisplayName("JUnit test for getAllEmployee method")
	@Test
	public void givenEmployeeList_whengetAllEmployee_thenReturnEmployeeList() {

		Employee employee1 = new Employee();
		employee1.setName("Rajesh");
		employee1.setDesignation("Techanical Associate");
		employee1.setEmail("rajesh@gmail.com");
		employee1.setDoj("04-02-2022");
		employee1.setSalary(70000);

		Employee employee2 = new Employee();
		employee2.setName("Ram");
		employee2.setDesignation("Techanical Associate");
		employee2.setEmail("ram@gmail.com");
		employee2.setDoj("04-02-2023");
		employee2.setSalary(70000);

		given(employeeRepository.findAll()).willReturn(List.of(employee1, employee2));

		List<Employee> employeeList = employeeServiceImpl.getAllEmployee();

		assertThat(employeeList).isNotNull();
		assertThat(employeeList.size()).isEqualTo(2);
	}

	@DisplayName("JUnit test for getAllEmployee method Negative Scenario")
	@Test
	public void givenEmptyEmployeeList_whengetAllEmployee_thenReturnEmptyEmployeeList() {

		given(employeeRepository.findAll()).willReturn(Collections.emptyList());

		List<Employee> employeeList = employeeServiceImpl.getAllEmployee();

		assertThat(employeeList).isEmpty();

		assertThat(employeeList.size()).isEqualTo(0);
	}

	@DisplayName("JUnit test for getEmployeeById method")
	@Test
	public void givenEmployeeId_whengetEmployeeById_thenReturnEmployeeObject() {

		Employee employee = new Employee();
		employee.setId(1L);
		employee.setName("Rajan");
		employee.setDesignation("Techanical Associate");
		employee.setEmail("rajan@gmail.com");
		employee.setDoj("04-05-2022");
		employee.setSalary(50000);

		given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

		Employee saveEmployee = employeeServiceImpl.getEmployeeById(1L);

		assertThat(saveEmployee).isNotNull();
	}

	@DisplayName("JUnit Test for updateEmployee Method")
	@Test
	public void giveEmployeeObject_whenUpdateEmployee_thenReturnUpdateEmployee() {

		Employee employee = new Employee();
		employee.setId(1L);
		employee.setName("Rajan");
		employee.setDesignation("Techanical Associate");
		employee.setEmail("rajan@gmail.com");
		employee.setDoj("04-05-2022");
		employee.setSalary(50000);

		given(employeeRepository.findById(employee.getId())).willReturn(Optional.of(employee));
		given(employeeRepository.save(employee)).willReturn(employee);
		employee.setEmail("rishabhsingh@gmail.com");
		employee.setName("Rishabh Kumar");

		Employee updateEmployee = employeeServiceImpl.updateEmployee(employee, employee.getId());

		assertThat(updateEmployee.getEmail()).isEqualTo("rishabhsingh@gmail.com");
		assertThat(updateEmployee.getName()).isEqualTo("Rishabh Kumar");

	}


	@DisplayName("JUnit test for deleteEmployee method")
	@Test
	public void givenEmployeeId_whenDeleteEmployee_thenReturnNothing() {

		Employee employee = new Employee();
		employee.setId(1L);
		employee.setName("Rajan");
		employee.setDesignation("Techanical Associate");
		employee.setEmail("rajan@gmail.com");
		employee.setDoj("04-05-2022");
		employee.setSalary(50000);

		Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
		 assertDoesNotThrow(() -> employeeServiceImpl.deleteEmployee(1L));
        Mockito.verify(employeeRepository, Mockito.times(1)).deleteById(1L);
	}
	
	@DisplayName("JUnit test for getEmployeeByNmae method")
	@Test
	public void givenEmployeeName_whengetEmployeeByName_thenReturnEmployeeObject() {

		Employee employee = new Employee();
		employee.setId(1L);
		employee.setName("Rajan");
		employee.setDesignation("Techanical Associate");
		employee.setEmail("rajan@gmail.com");
		employee.setDoj("04-05-2022");
		employee.setSalary(50000);

		given(employeeRepository.findByName(employee.getName())).willReturn(Optional.of(employee));

		Employee saveEmployee = employeeServiceImpl.findByName("Rajan");

		assertThat(saveEmployee).isNotNull();
	}

}
