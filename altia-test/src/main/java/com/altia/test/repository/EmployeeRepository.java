package com.altia.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altia.test.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

}
