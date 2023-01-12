package com.altia.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altia.test.entity.Department;
import com.altia.test.exception.DepartmentNotFoundException;
import com.altia.test.repository.DepartmentRepository;

@Service
public class DepartmentService {

	@Autowired
	DepartmentRepository departmentRepository;

	public Department getDepartmentById(Long id) {
		return findDepartmentOrThrowException(id);
	}

	public Double calculateAverageDepartmentSalary(Long id) {
		findDepartmentOrThrowException(id);
		return departmentRepository.calculateAverageDepartmentSalary(id);
	}

	private Department findDepartmentOrThrowException(Long id) {
		return departmentRepository.findById(id).orElseThrow(() -> new DepartmentNotFoundException(id));
	}

}
