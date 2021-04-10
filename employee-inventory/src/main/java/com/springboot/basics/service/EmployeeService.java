package com.springboot.basics.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.basics.dao.EmployeeDao;
import com.springboot.basics.dao.FileDao;
import com.springboot.basics.model.Employee;
import com.springboot.basics.model.ResponseFile;
import com.springboot.basics.utils.InvalidDataException;
import com.springboot.basics.utils.UploadStatus;

@Service
public class EmployeeService {

	@Autowired
	EmployeeDao employeeDao;

	@Autowired
	FileDao fileDao;

	public List<Employee> getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public ResponseFile processEmployeeData(final String fileId, final List<String> employeeList) {
		final ResponseFile response = new ResponseFile();
		int countOk = 0;
		int countKo = 0;
		String fName = "", lName = "";
		int age = 0;

		for (final String employee : employeeList) {
			try {
				fName = "";
				lName = "";
				final String[] employeeData = employee.split("\\s+");
				final int length = employeeData.length;
				if (length == 3) {
					if (employeeData[0].matches("^[a-zA-Z]*$") && employeeData[1].matches("^[a-zA-Z]*$")) {
						fName = employeeData[0].trim();
						lName = employeeData[1].trim();
					} else {
						throw new InvalidDataException("Invalid Data provided");
					}

					if (employeeData[2].matches("[0-9]+")) {
						age = Integer.parseInt(employeeData[2].trim());
					} else {
						throw new InvalidDataException("Invalid Data provided");
					}

				} else if (length == 2) {
					if (employeeData[0].matches("^[a-zA-Z]*$")) {
						fName = employeeData[0].trim();
					} else {
						throw new InvalidDataException("Invalid Data provided");
					}

					if (employeeData[1].matches("[0-9]+")) {
						age = Integer.parseInt(employeeData[1].trim());
					} else {
						throw new InvalidDataException("Invalid Data provided");
					}
				} else {
					throw new InvalidDataException("Invalid Data provided");
				}

				final Employee empl = new Employee(fName, lName, age);
				this.employeeDao.saveEmployee(empl);
				countOk++;

			} catch (final InvalidDataException e) {
				countKo++;
			}
		}
		if ((countKo == 0) && (countOk > 0)) {
			this.fileDao.updateStatus(fileId, UploadStatus.COMPLETED.toString(), countOk, countKo);
			response.setStatus(UploadStatus.COMPLETED.toString());
		} else if ((countKo > 0) && (countOk > 0)) {
			this.fileDao.updateStatus(fileId, UploadStatus.COMPLETED_WITH_ERRORS.toString(), countOk, countKo);
			response.setStatus(UploadStatus.COMPLETED_WITH_ERRORS.toString());
		} else if ((countKo > 0) && (countOk == 0)) {
			this.fileDao.updateStatus(fileId, UploadStatus.FAILED.toString(), countOk, countKo);
			response.setStatus(UploadStatus.FAILED.toString());
		}

		response.setCountKo(countKo);
		response.setCountOk(countOk);
		return response;
	}

	public List<Employee> fetchAllEmployees() {
		return this.employeeDao.fetchAllEmployees();
	}

}
