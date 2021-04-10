package com.springboot.basics.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "file_db")
public class FileDB {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String name;
	private String status;
	private int countOk;
	private int countKo;

	public FileDB() {
	}

	public FileDB(final String name, final String status, final int countOk, final int countKo) {
		super();
		this.name = name;
		this.status = status;
		this.countOk = countOk;
		this.countKo = countKo;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public int getCountOk() {
		return this.countOk;
	}

	public void setCountOk(final int countOk) {
		this.countOk = countOk;
	}

	public int getCountKo() {
		return this.countKo;
	}

	public void setCountKo(final int countKo) {
		this.countKo = countKo;
	}

}
