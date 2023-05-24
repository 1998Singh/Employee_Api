package com.evoke.employee.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "department_detail")
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long dept_id;
	@Column(name = "department_name")
	private String name;	
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "Department_Employee_Table",
	joinColumns = {
	@JoinColumn(name = "department_id", referencedColumnName = "dept_id") },
	inverseJoinColumns = {
	@JoinColumn(name = "employee_id", referencedColumnName = "id") }
	)
	private Set <Employee> assignedEmployees;
}
