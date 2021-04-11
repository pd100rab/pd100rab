package com.springboot.basics.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.basics.dao.FileDao;
import com.springboot.basics.model.FileDB;
import com.springboot.basics.utils.FileStorageException;
import com.springboot.basics.utils.UploadStatus;

@Service
public class FileService {

	@Value("${app.upload.dir:${user.home}}")
	public String uploadDirectory;

	@Autowired
	private FileDao fileDao;

	@Autowired
	EmployeeService employeeService;

	public FileDB uploadFile(final MultipartFile file) {

		try {
			final String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			final Path copyLocation = Paths.get(this.uploadDirectory + File.separator + fileName);
			Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

			final FileDB fileDB = new FileDB(fileName, UploadStatus.RUNNING.toString(), 0, 0);
			return this.fileDao.saveFile(fileDB);

		} catch (final Exception e) {
			e.printStackTrace();
			throw new FileStorageException(
					"Unable to store file " + file.getOriginalFilename() + ". Please try again!");
		}
	}

	public List<String> readFile(final String fileName) {

		final Path path = Paths.get(this.uploadDirectory + File.separator + fileName);
		Stream<String> lines = null;
		try {
			lines = Files.lines(path);
			return lines.collect(Collectors.toList());
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			lines.close();
		}
		return null;

	}

	public Optional<FileDB> getStatus(final String id) {
		return this.fileDao.getStatus(id);
	}

}
