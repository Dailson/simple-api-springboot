package com.dailson.api.requests;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.URL;

public class AnimePostRequestBody {

	@NotEmpty(message = "The anime name cannot be EMPTY or NULL")
	private String name;

	@URL(message = "The URL is invalid")
	private String url;
	
	
	public AnimePostRequestBody() {
		// Empty constructor
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		AnimePostRequestBody other = (AnimePostRequestBody) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AnimePostRequestBody [name=" + name + "]";
	}
	
	
	
}
