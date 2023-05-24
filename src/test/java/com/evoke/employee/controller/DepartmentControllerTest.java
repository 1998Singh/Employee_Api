package com.evoke.employee.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.evoke.employee.entity.Department;
import com.evoke.employee.service.DepartmentserviceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DepartmentserviceImpl departmentserviceImpl;

	@Autowired
	private ObjectMapper objectMapping;
	

	@DisplayName("JUnit test for addDepartment method")
	@Test
	public void givenDepartmentObject_whenAdddepartment_returnDepartmentObject() throws Exception {

		Department department = new Department();
		department.setName("Java");

		BDDMockito.given(departmentserviceImpl.addDepartment(ArgumentMatchers.any(Department.class)))
				.willAnswer((invocation) -> invocation.getArgument(0));

		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/departments")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapping.writeValueAsString(department)));

		response.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(department.getName())));

	}
	
	@DisplayName("jUnit Test for getAlldepartment Method")
	@Test
	public void givenListOfDepartment_whenGetAlldepartment_thenReturnDepartmentList() throws Exception {

		List<Department> listOfDepartments = new ArrayList<>();
		Department department = new Department();
		department.setName("Java");
		given(departmentserviceImpl.getAllDepartment()).willReturn(listOfDepartments);

		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/departments"));

		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(listOfDepartments.size())));
	}
	
	@DisplayName(" jUnit Test for getDepartmentById method.")
	@Test
	public void givenDepartmentId_whenGetdepartmentById_thenReturnDepartmentObject() throws Exception {

		Department department = new Department();
		department.setDept_id(1);
		department.setName("Java");

		given(departmentserviceImpl.getDepartmentById(1L)).willReturn(department);

		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/departments/{dept_id}", 1L));

		response.andExpect(status().isOk()).andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(department.getName())));
				

		
	}
	
	@DisplayName(" jUnit Test for deleteDepartment Rest API .")
	@Test
	public void givenDepartmentId_whenDeleteDepartment_thenReturn200() throws Exception {


		long departmentId = 1L;
		willDoNothing().given(departmentserviceImpl).deleteDepartment(departmentId);

		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/departments/{dept_id}", departmentId));

		response.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
	}


}
