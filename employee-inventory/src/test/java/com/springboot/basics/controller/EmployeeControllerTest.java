package com.springboot.basics.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.springboot.basics.model.Employee;
import com.springboot.basics.model.FileDB;
import com.springboot.basics.model.ResponseFile;
import com.springboot.basics.service.EmployeeService;
import com.springboot.basics.service.FileService;

public class EmployeeControllerTest {

	@InjectMocks
	EmployeeController employeeController;

	@Mock
	EmployeeService employeeService;

	@Mock
	FileService fileService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void fetchAllEmployees() {
		final Employee e1 = new Employee("John", "Smith", 22);
		final Employee e2 = new Employee("Maria", "Black", 24);
		final Employee e3 = new Employee("Joe", "Root", 26);
		final List<Employee> empList = new ArrayList<>();
		empList.add(e1);
		empList.add(e2);
		empList.add(e3);
		when(this.employeeService.fetchAllEmployees()).thenReturn(empList);
		final ResponseEntity<List<Employee>> response = this.employeeController.fetchAllEmployees();
		assertEquals(3, response.getBody().size());
	}

	@Test
	public void getStatus() {
		final FileDB fileDB = new FileDB("employee.txt", "Completed", 2, 0);
		final Optional<FileDB> file = Optional.of(fileDB);
		when(this.fileService.getStatus("1")).thenReturn(file);
		final ResponseEntity<Optional<FileDB>> response = (ResponseEntity<Optional<FileDB>>) this.employeeController
				.getStatus("1");
		assertEquals("Completed", response.getBody().get().getStatus());
	}

	@Test
	public void uploadEmployeeInfo() {
		final MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		final ResponseFile responseFile = new ResponseFile("employee.txt", "/api/status", "Completed", 2, 0);
		final FileDB fileDB = new FileDB("employee.txt", "Completed", 2, 0);
		final List<String> lines = new ArrayList<>();
		final MockMultipartFile file = new MockMultipartFile("employee.txt", "some data".getBytes());
		when(this.fileService.uploadFile(file)).thenReturn(fileDB);
		when(this.fileService.readFile(fileDB.getName())).thenReturn(lines);
		when(this.employeeService.processEmployeeData(fileDB.getId(), lines)).thenReturn(responseFile);
		final ResponseEntity<ResponseFile> response = this.employeeController.uploadEmployeeInfo(file);
		assertEquals("http://localhost/api/status/", response.getBody().getUrl());

	}
}
