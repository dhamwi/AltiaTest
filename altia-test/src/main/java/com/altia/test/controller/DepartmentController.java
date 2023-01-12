package com.altia.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.altia.test.service.DepartmentService;

@RestController
@RequestMapping("/department")
public class DepartmentController {

	@Autowired
	DepartmentService departmentService;

	@GetMapping("/departmentAverage")
	public ResponseEntity<Double> calculateAverageDepartmentSalary(@RequestParam("dptId") Long id) {
		return new ResponseEntity<>(departmentService.calculateAverageDepartmentSalary(id), HttpStatus.OK);
	}
}
