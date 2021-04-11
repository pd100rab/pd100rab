package com.springboot.basics.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.springboot.basics.dao.EmployeeDao;
import com.springboot.basics.dao.FileDao;
import com.springboot.basics.model.Employee;
import com.springboot.basics.model.ResponseFile;
import com.springboot.basics.utils.UploadStatus;

public class EmployeeServiceTest {

	@InjectMocks
	EmployeeService employeeService;

	@Mock
	EmployeeDao employeeDao;

	@Mock
	FileDao fileDao;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void processEmployeeData() {
		final String s1 = "John  Smith  22";
		final String s2 = "Joe  Root  23";
		final String s3 = "Dhoni  26";
		final List<String> empList = new ArrayList<>();
		empList.add(s1);
		empList.add(s2);
		empList.add(s3);
		final String fileId = "1";
		doNothing().when(this.fileDao).updateStatus(fileId, UploadStatus.COMPLETED.toString(), 3, 0);
		final ResponseFile response = this.employeeService.processEmployeeData(fileId, empList);
		assertEquals(3, response.getCountOk());
	}

	@Test
	public void processEmployeeDataCompletedWithErrors() {
		final String s1 = "John  Smith  22e";
		final String s2 = "Joe  Root  23";
		final String s3 = "Dhoni  26";
		final List<String> empList = new ArrayList<>();
		empList.add(s1);
		empList.add(s2);
		empList.add(s3);
		final String fileId = "1";
		doNothing().when(this.fileDao).updateStatus(fileId, UploadStatus.COMPLETED_WITH_ERRORS.toString(), 2, 1);
		final ResponseFile response = this.employeeService.processEmployeeData(fileId, empList);
		assertEquals(2, response.getCountOk());
		assertEquals(1, response.getCountKo());
	}

	@Test
	public void processEmployeeDataFailed() {
		final String s1 = "John  Smith  22e";
		final String s2 = "Joe1  Root  23";
		final String s3 = "Dhoni  26  23";
		final List<String> empList = new ArrayList<>();
		empList.add(s1);
		empList.add(s2);
		empList.add(s3);
		final String fileId = "1";
		doNothing().when(this.fileDao).updateStatus(fileId, UploadStatus.FAILED.toString(), 0, 3);
		final ResponseFile response = this.employeeService.processEmployeeData(fileId, empList);
		assertEquals(3, response.getCountKo());
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
		when(this.employeeDao.fetchAllEmployees()).thenReturn(empList);
		final List<Employee> response = this.employeeService.fetchAllEmployees();
		assertEquals(3, response.size());
	}

	@Test
	public void updateEmployee() {
		final Employee oldEmpData = new Employee("Maria", "Smith", 22);
		final Employee newEmpData = new Employee("Maria", "Black", 24);
		when(this.employeeDao.updateEmployee(newEmpData)).thenReturn(newEmpData);
		final Employee response = this.employeeService.updateEmployee(oldEmpData, newEmpData);
		assertEquals("Black", response.getlName());
	}

	@Test
	public void deleteById() {
		doNothing().when(this.employeeDao).deleteById(2);
		this.employeeService.deleteById(2);
		verify(this.employeeDao, Mockito.times(1)).deleteById(2);
	}

	@Test
	public void deleteAll() {
		doNothing().when(this.employeeDao).deleteAll();
		this.employeeService.deleteAll();
		verify(this.employeeDao, Mockito.times(1)).deleteAll();
	}
}
