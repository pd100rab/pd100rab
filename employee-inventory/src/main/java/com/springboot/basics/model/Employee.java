package com.springboot.basics.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String fName;
	private String lName;
	private int age;

	public Employee() {
	}

	public Employee(final String fName, final String lName, final int age) {
		super();
		this.fName = fName;
		this.lName = lName;
		this.age = age;
	}

	public long getId() {
		return this.id;
	}

	public String getfName() {
		return this.fName;
	}

	public void setfName(final String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return this.lName;
	}

	public void setlName(final String lName) {
		this.lName = lName;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(final int age) {
		this.age = age;
	}

}
