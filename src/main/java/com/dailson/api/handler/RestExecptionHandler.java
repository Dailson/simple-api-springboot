package com.dailson.api.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dailson.api.exceptions.BadRequestException;
import com.dailson.api.exceptions.BadRequestExceptionDetails;

@ControllerAdvice
public class RestExecptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<BadRequestExceptionDetails> handleExceptions(BadRequestException badRequestException){
		return new ResponseEntity<>(
				new BadRequestExceptionDetails(
					"Bad Request Exception, check the Documentation",
					HttpStatus.BAD_REQUEST.value(),
					badRequestException.getMessage(),
					badRequestException.getClass().getName(),
					LocalDateTime.now()), HttpStatus.BAD_REQUEST);					
	}
}