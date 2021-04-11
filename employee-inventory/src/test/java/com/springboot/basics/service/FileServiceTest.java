package com.springboot.basics.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import com.springboot.basics.dao.EmployeeDao;
import com.springboot.basics.dao.FileDao;
import com.springboot.basics.model.FileDB;
import com.springboot.basics.utils.UploadStatus;

public class FileServiceTest {

	@InjectMocks
	FileService fileService;

	@Mock
	EmployeeDao employeeDao;

	@Mock
	FileDao fileDao;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void uploadFile() {
		ReflectionTestUtils.setField(this.fileService, "uploadDirectory", "C:\\Users\\SAURABH");
		final MockMultipartFile file = new MockMultipartFile("employee.txt", "employee.txt", null,
				"some data".getBytes());
		final FileDB fileDB = new FileDB("employee.txt", UploadStatus.RUNNING.toString(), 0, 0);
		final FileDB savedFile = new FileDB("employee.txt", UploadStatus.COMPLETED.toString(), 2, 0);
		when(this.fileDao.saveFile(ArgumentMatchers.any(FileDB.class))).thenReturn(savedFile);
		final FileDB response = this.fileService.uploadFile(file);
		assertEquals(2, response.getCountOk());
	}

	@Test
	public void readFile() {
		ReflectionTestUtils.setField(this.fileService, "uploadDirectory", "C:\\Users\\SAURABH");
		final List<String> response = this.fileService.readFile("employee.txt");
		assertEquals(1, response.size());
	}

	@Test
	public void getStatus() {
		final FileDB fileDB = new FileDB("employee.txt", UploadStatus.COMPLETED.toString(), 2, 0);
		final Optional<FileDB> file = Optional.of(fileDB);
		when(this.fileDao.getStatus("1")).thenReturn(file);
		final Optional<FileDB> response = this.fileService.getStatus("1");
		assertEquals(2, response.get().getCountOk());
	}

}
