package com.altia.test.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AltiaTestExceptionHandler {

	private static final String EXCEPTION_TEXT = "[Exception Response] - Exception: ";

	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleEmployeeNotFoundException(EmployeeNotFoundException e) {
		return createExceptionResponse(e, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DepartmentNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleDepartmentNotFoundException(DepartmentNotFoundException e) {
		return createExceptionResponse(e, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleInternalServerException(Exception e) {
		return createExceptionResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private <T> ResponseEntity<ExceptionResponse> createExceptionResponse(Exception e, HttpStatus status) {
		ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), EXCEPTION_TEXT + e.getMessage(),
				status.value(), status.getReasonPhrase());
		return new ResponseEntity<>(response, status);
	}
}