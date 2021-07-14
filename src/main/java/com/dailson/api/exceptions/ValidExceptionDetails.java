package com.dailson.api.exceptions;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ValidExceptionDetails extends ExceptionDetails {

	private String fields;
	private String fieldsMessage;

	@Builder
	public ValidExceptionDetails(String title, int status, String details, String developerMessage,
			LocalDateTime timestamp, String fields, String fieldsMessage) {
		super(title, status, details, developerMessage, timestamp);
		this.fields = fields;
		this.fieldsMessage = fieldsMessage;
	}

}
