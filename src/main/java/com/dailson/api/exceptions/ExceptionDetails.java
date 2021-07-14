package com.dailson.api.exceptions;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDetails {

	protected String title;
	protected int status;
	protected String details;
	protected String developerMessage;
	protected LocalDateTime timestamp;


}
