package com.springboot.basics.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.basics.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
