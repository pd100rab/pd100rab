package com.springboot.basics.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springboot.basics.model.Employee;

@Component
public class EmployeeDao {

	@Autowired
	EmployeeRepository employeeRepository;

	public Employee createBook(final Employee employee) {
		return this.employeeRepository.save(employee);
	}

	public void saveEmployee(final Employee empl) {
		this.employeeRepository.save(empl);
	}

	public List<Employee> fetchAllEmployees() {
		return this.employeeRepository.findAll();
	}

}
