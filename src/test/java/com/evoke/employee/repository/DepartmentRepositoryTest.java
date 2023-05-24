package com.evoke.employee.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.evoke.employee.entity.Department;
import com.evoke.employee.entity.Employee;

@DataJpaTest
public class DepartmentRepositoryTest {

	@Autowired
	DepartmentRepository departmentRepository;

	@DisplayName("Junit test for save Department operation")
	@Test
	public void givenDepartmentObject_whenSave_thenReturnSaveDepartment() {

		Department department = new Department();
		department.setName("Java");

		Department savedepartment = departmentRepository.save(department);

		assertThat(savedepartment).isNotNull();
		assertThat(savedepartment.getDept_id()).isGreaterThan(0);

	}

	@DisplayName("Junit test for get all department operation")
	@Test
	public void givenDepartmentList_whenFindAll_thenDepartmentList() {

		Department department = new Department();
		department.setName("Java");

		departmentRepository.save(department);
		List<Department> departmentlist = departmentRepository.findAll();

		assertThat(departmentlist).isNotNull();
		assertThat(departmentlist.size()).isEqualTo(1);

	}

	@DisplayName("Junit test for get Department by id ")
	@Test
	public void givenDepartmentObject_whenFindById_thenDepartmentObject() {

		Department department = new Department();
		department.setName("Java");

		departmentRepository.save(department);

		Department dep = departmentRepository.findById(department.getDept_id()).get();

		assertEquals(department.getDept_id(), dep.getDept_id());
		assertThat(dep).isNotNull();

	}

	@DisplayName("Junit test for Update Department")
	@Test
	public void givenDepartmentObject_whenUpdateDepartment_thenReturnUpdatedDepartment() {

		Department department = new Department();
		department.setName("Java");
		departmentRepository.save(department);

		Department saveDepartment = departmentRepository.getById(department.getDept_id());
		saveDepartment.setName("Python");
		Department updateDepartment = departmentRepository.save(saveDepartment);

		assertThat(updateDepartment.getName()).isEqualTo("Python");

	}

	@DisplayName("Junit test for delete department")
	@Test
	public void givenDepartmentObject_whenDelete_thenRemoveDepartment() {

		Department department = new Department();
		department.setName("Java");
		departmentRepository.save(department);

		departmentRepository.delete(department);
		
		Optional<Department> dept = departmentRepository.findById(department.getDept_id());

		assertThat(dept).isEmpty();

	}

}
