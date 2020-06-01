package com.eatin.error;

import java.time.LocalDateTime;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<CustomErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
		CustomErrorResponse error = new CustomErrorResponse("BAD_REQUEST", e.getMessage());

		error.setTimestamp(LocalDateTime.now());

		error.setStatus((HttpStatus.BAD_REQUEST.value()));

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

	}
}