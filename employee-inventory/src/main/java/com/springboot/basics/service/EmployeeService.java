package com.springboot.basics.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.basics.dao.EmployeeDao;
import com.springboot.basics.dao.FileDao;
import com.springboot.basics.model.Employee;
import com.springboot.basics.model.ResponseFile;
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
		int age;

		for (final String employee : employeeList) {
			try {
				fName = "";
				lName = "";
				final String[] employeeData = employee.split("  "); // SACHIN
																	// TEND
																	// 44
				final int length = employeeData.length;
				if (length == 3) {
					final String[] names = employeeData[0].split(" ");
					fName = names[0].trim();
					lName = names[1].trim();
					age = Integer.parseInt(employeeData[2].trim());

				} else {
					fName = employeeData[0].trim();
					age = Integer.parseInt(employeeData[1].trim());
				}

				final Employee empl = new Employee(fName, lName, age);
				this.employeeDao.saveEmployee(empl);
				countOk++;

			} catch (final RuntimeException e) {
				countKo++;
			}
		}
		if (countKo == 0) {
			this.fileDao.updateStatus(fileId, UploadStatus.COMPLETED.toString(), countOk, countKo);
			response.setStatus(UploadStatus.COMPLETED.toString());
		} else {
			this.fileDao.updateStatus(fileId, UploadStatus.COMPLETED_WITH_ERRORS.toString(), countOk, countKo);
			response.setStatus(UploadStatus.COMPLETED_WITH_ERRORS.toString());
		}

		response.setCountKo(countKo);
		response.setCountOk(countOk);
		return response;
	}

}
