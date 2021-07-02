package com.dailson.api.exceptions;

import java.time.LocalDateTime;

public class BadRequestExceptionDetails extends ExceptionDetails {

	
	public BadRequestExceptionDetails() {
		super();
		// Do nothing
	}

	public BadRequestExceptionDetails(String title, int status, String details, String developerMessage,
			LocalDateTime timestamp) {
		super(title, status, details, developerMessage, timestamp);
	}


}
