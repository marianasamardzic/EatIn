package com.eatin.error;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<CustomErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
		List<String> errorMessages = new ArrayList<>();
		errorMessages.add(e.getMessage());
		CustomErrorResponse error = new CustomErrorResponse("BAD_REQUEST", errorMessages);

		error.setTimestamp(LocalDateTime.now());

		error.setStatus((HttpStatus.BAD_REQUEST.value()));

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

	}
    
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<CustomErrorResponse> handleDataIntegrityViolationException(
			DataIntegrityViolationException e) {
		List<String> errorMessages = new ArrayList<>();
		errorMessages.add(e.getMessage());
		CustomErrorResponse error = new CustomErrorResponse("BAD_REQUEST", errorMessages);

		error.setTimestamp(LocalDateTime.now());

		error.setStatus((HttpStatus.BAD_REQUEST.value()));

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<CustomErrorResponse> handleBadCredentialsException(BadCredentialsException e) {
		List<String> errorMessages = new ArrayList<>();
		errorMessages.add(e.getMessage());
		CustomErrorResponse error = new CustomErrorResponse("BAD_REQUEST", errorMessages);

		error.setTimestamp(LocalDateTime.now());

		error.setStatus((HttpStatus.BAD_REQUEST.value()));

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());
		CustomErrorResponse error = new CustomErrorResponse("BAD_REQUEST", errorMessages);

		error.setTimestamp(LocalDateTime.now());

		error.setStatus((HttpStatus.BAD_REQUEST.value()));

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

	}

}