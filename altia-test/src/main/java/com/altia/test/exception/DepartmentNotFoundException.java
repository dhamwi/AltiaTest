package com.altia.test.exception;

public class DepartmentNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3240739666825123740L;

	private Long departmentId;

	public DepartmentNotFoundException(Long departmentId) {
		this.departmentId = departmentId;
	}

	@Override
	public String getMessage() {
		return String.format("Department with ID '%s' NOT FOUND", this.departmentId);
	}

}
