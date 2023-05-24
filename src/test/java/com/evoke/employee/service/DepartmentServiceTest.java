package com.evoke.employee.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.evoke.employee.entity.Department;
import com.evoke.employee.entity.Employee;
import com.evoke.employee.repository.DepartmentRepository;
import com.evoke.employee.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

	@Mock
	private DepartmentRepository departmentRepository;
	
	@Mock
	private EmployeeRepository employeeRepository;
	
	@InjectMocks
	private DepartmentserviceImpl departmentserviceImpl;
	

	@DisplayName("JUnit test for addDepartment method")
	
	@Test
	public void givenDepartmentObject_whenAddDepartment_thenReturnDepartmentObject() {

		Department department = new Department();
		department.setName("java");

		given(departmentRepository.save(department)).willReturn(department);

		Department savedepartment = departmentserviceImpl.addDepartment(department);

		assertThat(savedepartment).isNotNull();
	}
	
	@DisplayName("JUnit test for getAllDepartment method")
	@Test
	public void givenEmployeeList_whengetAllEmployee_thenReturnEmployeeList() {

		Department department = new Department();
		department.setName("java");

		Department department1 = new Department();
		department.setName("python");

		given(departmentRepository.findAll()).willReturn(List.of(department, department1));

		List<Department> departmentList = departmentserviceImpl.getAllDepartment();

		assertThat(departmentList).isNotNull();
		assertThat(departmentList.size()).isEqualTo(2);
	}
	
	@DisplayName("JUnit test for getDepartmentById method")
	@Test
	public void givenEmployeeId_whengetEmployeeById_thenReturnEmployeeObject() {

		Department department = new Department();
		department.setDept_id(1);
		department.setName("java");

		given(departmentRepository.findById(1L)).willReturn(Optional.of(department));

		Department saveDepartment = departmentserviceImpl.getDepartmentById(1L);

		assertThat(saveDepartment).isNotNull();
	}
	
	@DisplayName("JUnit Test for updatedepartment  Method")
	@Test
	public void giveDepartmentObject_whenUpdateDepartment_thenReturnUpdateDepartment() {

		Department department = new Department();
		department.setDept_id(2);
		department.setName("java");

		given(departmentRepository.findById(2L)).willReturn(Optional.of(department));
		given(departmentRepository.save(department)).willReturn(department);
		department.setName(".net");

		Department updatedepartment = departmentserviceImpl.updateDepartment(department, department.getDept_id());

		assertThat(updatedepartment.getName()).isEqualTo(".net");

	}
	
	@DisplayName("JUnit test for deleteDepartment method")
	@Test
	public void givendepartmentId_whenDeleteDepartment_thenReturnNothing() {

		Department department = new Department();
		department.setDept_id(3);
		department.setName("java");

		Mockito.when(departmentRepository.findById(3L)).thenReturn(Optional.of(department));
		 assertDoesNotThrow(() -> departmentserviceImpl.deleteDepartment(3L));
        Mockito.verify(departmentRepository, Mockito.times(1)).deleteById(3L);
	}
	
	@DisplayName("JUnit Test for AssignEmployeeToDepartment  Method")
	@Test
	public void giveDepartmentEmployeeObject_whenGetById_thenReturnAssignEmployeeToDepartment() {
	
		long deptId = 1L;
        long employeeId = 2L;
        
        Department department = new Department();
        department.setDept_id(deptId);
      
        Set<Employee> employeeSet = new HashSet<>();
        department.setAssignedEmployees(employeeSet);
        
        Employee employee = new Employee();
        employee.setId(employeeId);
        
        when(departmentRepository.findById(deptId)).thenReturn(Optional.of(department));
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(departmentRepository.save(department)).thenReturn(department);
        
        Department result = departmentserviceImpl.assignEmployeeToDepartment(deptId, employeeId);
        
        verify(departmentRepository, times(1)).findById(deptId);
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(departmentRepository, times(1)).save(department);
        
        Assertions.assertEquals(department, result);
        Assertions.assertTrue(department.getAssignedEmployees().contains(employee));
             
		
	}
	
	@DisplayName("JUnit Test for DeleteAssignEmployeeToDepartment  Method")
	@Test
	public void giveDepartmentEmployeeObject_whenGetById_thenReturnDeleteAssignEmployeeFromDepartment() {
		
		 long departmentId = 1L;
	     long employeeId = 2L;
	     
	     Department department = new Department();
	     department.setDept_id(departmentId);
	     Set<Employee> employeeSet = new HashSet<>();
	     Employee employee = new Employee();
	     employee.setId(employeeId);
	     employeeSet.add(employee);
	     department.setAssignedEmployees(employeeSet);
	     
	     when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
	     when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
	     when(departmentRepository.save(department)).thenReturn(department);
	     
	     departmentserviceImpl.deleteEmployeeFromDepartment(departmentId, employeeId);
	     
	     verify(departmentRepository, times(1)).findById(departmentId);
	     verify(employeeRepository, times(1)).findById(employeeId);
	     verify(departmentRepository, times(1)).save(department);
	     
	     Assertions.assertFalse(department.getAssignedEmployees().contains(employee));
	     
	
	}
	
	


}
