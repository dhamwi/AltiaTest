package com.altia.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.altia.test.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

	public static final String SQL_AVERAGE_STATEMENT = "SELECT AVG(SALARY) FROM EMPLOYEE WHERE DPT_ID = ?1";

	@Query(value = SQL_AVERAGE_STATEMENT, nativeQuery = true)
	Double calculateAverageDepartmentSalary(Long departmentId);

}
