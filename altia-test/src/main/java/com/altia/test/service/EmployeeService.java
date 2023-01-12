package com.altia.test.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.altia.test.dto.EmployeeDTO;
import com.altia.test.entity.Employee;
import com.altia.test.exception.EmployeeNotFoundException;
import com.altia.test.repository.EmployeeRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class EmployeeService {

	private static final String URL_UUIDTOOLS = "https://www.uuidtools.com/api/generate/v1";

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	DepartmentService departmentService;

	@Autowired
	EntityManager entityManager;

	private RestTemplate restTemplate;
	private ModelMapper modelMapper;

	public EmployeeService() {
		restTemplate = new RestTemplate();
		modelMapper = new ModelMapper();
	}

	public List<EmployeeDTO> getAllEmployees() {
		List<Employee> employeeList = employeeRepository.findAll();
		return employeeList.stream().map(this::convertToEmployeeDTO).collect(Collectors.toList());
	}

	public EmployeeDTO getEmployeeById(String id) {
		return convertToEmployeeDTO(findEmployeeOrThrowException(id));
	}

	public EmployeeDTO createEmployee(EmployeeDTO employeeDto) {
		departmentService.getDepartmentById(employeeDto.getDepartment().getId());
		employeeDto.setId(generateUUID());
		return convertToEmployeeDTO(employeeRepository.save(convertToEmployeeEntity(employeeDto)));
	}

	public EmployeeDTO updateEmployee(String id, EmployeeDTO updatedEmployeeDto) {
		findEmployeeOrThrowException(id);
		departmentService.getDepartmentById(updatedEmployeeDto.getDepartment().getId());
		updatedEmployeeDto.setId(id);
		return convertToEmployeeDTO(employeeRepository.save(convertToEmployeeEntity(updatedEmployeeDto)));
	}

	public void deleteEmployee(String id) {
		findEmployeeOrThrowException(id);
		employeeRepository.deleteById(id);
	}

	public List<EmployeeDTO> getAllEmployeesByDepartmentId(Long id) {
		List<Employee> lista = findEmployeesByDepartmentId(id);
		departmentService.getDepartmentById(id);
		return lista.stream().map(this::convertToEmployeeDTO).collect(Collectors.toList());
	}

	private List<Employee> findEmployeesByDepartmentId(Long departmentId) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

		Root<Employee> employee = criteriaQuery.from(Employee.class);
		Predicate authorNamePredicate = criteriaBuilder.equal(employee.get("department").get("id"), departmentId);
		criteriaQuery.where(authorNamePredicate);

		TypedQuery<Employee> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}

	private Employee findEmployeeOrThrowException(String id) {
		return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
	}

	private EmployeeDTO convertToEmployeeDTO(Employee employee) {
		return modelMapper.map(employee, EmployeeDTO.class);
	}

	private Employee convertToEmployeeEntity(EmployeeDTO employeeDto) {
		return modelMapper.map(employeeDto, Employee.class);
	}

	private String generateUUID() {
		ResponseEntity<String[]> response = restTemplate.getForEntity(URL_UUIDTOOLS, String[].class);
		return response.getBody()[0];
	}
}
