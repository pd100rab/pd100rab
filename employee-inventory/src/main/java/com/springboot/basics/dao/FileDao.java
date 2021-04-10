package com.springboot.basics.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springboot.basics.model.FileDB;

@Component
public class FileDao {

	@Autowired
	FileDbRepository fileDbRepository;

	public FileDB saveFile(final FileDB fileDB) {
		return this.fileDbRepository.save(fileDB);
	}

	public Optional<FileDB> getStatus(final String id) {
		return this.fileDbRepository.findById(id);
	}

	public void updateStatus(final String fileId, final String status, final int countOk, final int countKo) {
		this.fileDbRepository.updateStatus(fileId, status, countOk, countKo);
	}
}
