package com.dailson.api.exceptions;

import java.time.LocalDateTime;

public class BadRequestExceptionDetails {

	private String title;
	private int status;
	private String details;
	private String developerMessage;
	private LocalDateTime timestamp;

	public BadRequestExceptionDetails() {
		// Do nothing
	}

	public BadRequestExceptionDetails(String title, int status, String details,
			String developerMessage, LocalDateTime timestamp) {

		this.title = title;
		this.status = status;
		this.details = details;
		this.developerMessage = developerMessage;
		this.timestamp = timestamp;
	}

	public String getTitle() {
		return title;
	}


	public void setTtitle(String title) {
		this.title = title;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getDetails() {
		return details;
	}


	public void setDetails(String details) {
		this.details = details;
	}


	public String getDeveloperMessage() {
		return developerMessage;
	}


	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}


	public LocalDateTime getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((details == null) ? 0 : details.hashCode());
		result = prime * result + ((developerMessage == null) ? 0 : developerMessage.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + status;
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BadRequestExceptionDetails other = (BadRequestExceptionDetails) obj;
		if (details == null) {
			if (other.details != null)
				return false;
		} else if (!details.equals(other.details))
			return false;
		if (developerMessage == null) {
			if (other.developerMessage != null)
				return false;
		} else if (!developerMessage.equals(other.developerMessage))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (status != other.status)
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}

}
