package com.dailson.api.handler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.dailson.api.exceptions.BadRequestException;
import com.dailson.api.exceptions.BadRequestExceptionDetails;
import com.dailson.api.exceptions.ExceptionDetails;
import com.dailson.api.exceptions.ValidExceptionDetails;

@ControllerAdvice

public class RestExecptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<BadRequestExceptionDetails> handleBadRequestExceptions(BadRequestException exception){
		return new ResponseEntity<>(
				new BadRequestExceptionDetails(
					"Bad Request Exception, check the Documentation",
					HttpStatus.BAD_REQUEST.value(),
					exception.getMessage(),
					exception.getClass().getName(),
					LocalDateTime.now()), HttpStatus.BAD_REQUEST);					
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request){
		
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

		String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
		String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));
			
		return new ResponseEntity<>(
				new ValidExceptionDetails(
					"Bad Request Exception, Invalid Fields",
					HttpStatus.BAD_REQUEST.value(),
					"Check the field(s) error",
					exception.getClass().getName(),
					LocalDateTime.now(),
					fields,
					fieldsMessage),HttpStatus.BAD_REQUEST);
		
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(
			Exception exception, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

				var exceptionDetails = new ExceptionDetails(
					exception.getCause().getMessage(),
					status.value(),
					exception.getMessage(),
					exception.getClass().getName(),
					LocalDateTime.now());		
		
		return new ResponseEntity<>(exceptionDetails, headers, status);
	}

}