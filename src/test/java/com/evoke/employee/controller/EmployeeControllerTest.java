package com.evoke.employee.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.evoke.employee.entity.Department;
import com.evoke.employee.entity.Employee;
import com.evoke.employee.repository.EmployeeRepository;
import com.evoke.employee.service.EmployeeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeServiceImpl employeeServiceImpl;

	@MockBean
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private EmployeeController employeeController;

	@Autowired
	private ObjectMapper objectMapping;

	@DisplayName("JUnit test for addEmployee method")
	@Test
	public void givenEmployeeObject_whenAddEmployee_returnEmployeeObject() throws Exception {

		Employee employee = new Employee();
		employee.setName("Ram");
		employee.setDesignation("Techanical Associate");
		employee.setEmail("ram@gmail.com");
		employee.setDoj("04-05-2022");
		employee.setSalary(70000);

		BDDMockito.given(employeeServiceImpl.addEmployee(ArgumentMatchers.any(Employee.class)))
				.willAnswer((invocation) -> invocation.getArgument(0));

		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/employees")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapping.writeValueAsString(employee)));

		response.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(employee.getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.salary", CoreMatchers.is(employee.getSalary())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())));

	}

	@DisplayName("jUnit Test for getAllEmployee Method")
	@Test
	public void givenListOfEmployee_whenGetAllEmployee_thenReturnEmployeeList() throws Exception {

		List<Employee> listOfEmployee = new ArrayList<>();
		Employee employee = new Employee();
		employee.setName("Ram");
		employee.setDesignation("Techanical Associate");
		employee.setEmail("ram@gmail.com");
		employee.setDoj("04-05-2022");
		employee.setSalary(70000);
		given(employeeServiceImpl.getAllEmployee()).willReturn(listOfEmployee);

		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees"));

		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(listOfEmployee.size())));
	}

	@DisplayName(" jUnit Test for getEmployeeById  for postive scenario.")
	@Test
	public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {

		Employee employee = new Employee();
		employee.setId(1L);
		employee.setName("Ram");
		employee.setDesignation("Techanical Associate");
		employee.setEmail("ram@gmail.com");
		employee.setDoj("04-05-2022");
		employee.setSalary(70000);

		given(employeeServiceImpl.getEmployeeById(employee.getId())).willReturn(employee);

		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}", 1L));

		response.andExpect(status().isOk()).andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(employee.getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.salary", CoreMatchers.is(employee.getSalary())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())));

	}

	@DisplayName(" jUnit Test for deleteEmployee Rest API .")
	@Test
	public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception {

		long employeeId = 1L;
		willDoNothing().given(employeeServiceImpl).deleteEmployee(employeeId);

		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/employees/{id}", employeeId));

		response.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
	}



}
