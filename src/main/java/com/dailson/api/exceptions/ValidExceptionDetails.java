package com.dailson.api.exceptions;

import java.time.LocalDateTime;

public class ValidExceptionDetails extends ExceptionDetails{

	private String fields;
	private String fieldsMessage;
	
	
	public ValidExceptionDetails() {
		super();
		// Do nothin
	}

	public ValidExceptionDetails(String title, int status, String details, String developerMessage,
			LocalDateTime timestamp, String fields, String fieldsMessage) {
		super(title, status, details, developerMessage, timestamp);
		this.fields = fields;
		this.fieldsMessage = fieldsMessage;
	}
	
	
	public String getFields() {
		return this.fields;
	}
	
	public String getFieldsMessage() {
		return this.fieldsMessage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
		result = prime * result + ((fieldsMessage == null) ? 0 : fieldsMessage.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ValidExceptionDetails other = (ValidExceptionDetails) obj;
		if (fields == null) {
			if (other.fields != null)
				return false;
		} else if (!fields.equals(other.fields))
			return false;
		if (fieldsMessage == null) {
			if (other.fieldsMessage != null)
				return false;
		} else if (!fieldsMessage.equals(other.fieldsMessage))
			return false;
		return true;
	}
	
	
}
