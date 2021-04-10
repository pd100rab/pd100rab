package com.springboot.basics.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springboot.basics.model.Employee;
import com.springboot.basics.model.FileDB;
import com.springboot.basics.model.ResponseFile;
import com.springboot.basics.service.EmployeeService;
import com.springboot.basics.service.FileService;

@RestController
@RequestMapping(value = "/api")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private FileService fileService;

	@GetMapping("/status/{id}")
	public ResponseEntity<?> getStatus(@PathVariable final String id) {
		final Optional<FileDB> fileDB = this.fileService.getStatus(id);
		if (fileDB.isPresent()) {
			return ResponseEntity.ok().body(fileDB);
		} else {
			return ResponseEntity.noContent().build();
		}

	}

	@GetMapping("/fetchAllEmployees")
	public ResponseEntity<List<Employee>> fetchAllEmployees() {
		final List<Employee> employeeList = this.employeeService.fetchAllEmployees();
		return ResponseEntity.ok().body(employeeList);

	}

	@PostMapping("/employee")
	public ResponseEntity<ResponseFile> uploadEmployeeInfo(@RequestParam("file") final MultipartFile file) {

		final FileDB fileDB = this.fileService.uploadFile(file);

		final List<String> lines = this.fileService.readFile(fileDB.getName());

		final ResponseFile response = this.employeeService.processEmployeeData(fileDB.getId(), lines);

		final String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/status/")
				.path(fileDB.getId()).toUriString();

		response.setName(fileDB.getName());
		response.setUrl(fileDownloadUri);

		return ResponseEntity.ok().body(response);

	}

}
