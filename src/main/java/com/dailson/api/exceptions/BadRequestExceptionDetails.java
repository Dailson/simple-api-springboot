package com.dailson.api.exceptions;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor

@Data
@EqualsAndHashCode(callSuper = true)
public class BadRequestExceptionDetails extends ExceptionDetails {

	@Builder
	public BadRequestExceptionDetails(String title, int status, String details, String developerMessage,
			LocalDateTime timestamp) {
		super(title, status, details, developerMessage, timestamp);
	}


}
