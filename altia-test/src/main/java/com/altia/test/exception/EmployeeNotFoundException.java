package com.altia.test.exception;

public class EmployeeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3128403331768159827L;

	private String employeeId;

	public EmployeeNotFoundException(String employeeId) {
		this.employeeId = employeeId;
	}

	@Override
	public String getMessage() {
		return String.format("Employee with ID '%s' NOT FOUND", this.employeeId);
	}
}
