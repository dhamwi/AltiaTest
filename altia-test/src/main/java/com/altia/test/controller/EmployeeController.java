package com.altia.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.altia.test.dto.EmployeeDTO;
import com.altia.test.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@GetMapping
	public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
		return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable String id) {
		return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody @Valid EmployeeDTO employeeDto) {
		return new ResponseEntity<>(employeeService.createEmployee(employeeDto), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable String id, @RequestBody @Valid EmployeeDTO employeeDto) {
		return new ResponseEntity<>(employeeService.updateEmployee(id, employeeDto), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable String id) {
		employeeService.deleteEmployee(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/departmentId")
	public ResponseEntity<List<EmployeeDTO>> getEmployeesByDepartmentId(@RequestParam("dptId") Long id) {
		return new ResponseEntity<>(employeeService.getAllEmployeesByDepartmentId(id), HttpStatus.OK);
	}
}
