package com.springboot.basics.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.basics.model.FileDB;

@Repository
public interface FileDbRepository extends JpaRepository<FileDB, String> {

	@Modifying
	@Query("update FileDB u set u.status = :status, u.countOk = :countOk, u.countKo = :countKo where u.id = :id")
	void updateStatus(@Param(value = "id") String id, @Param(value = "status") String status,
			@Param(value = "countOk") int countOk, @Param(value = "countKo") int countKo);
}
