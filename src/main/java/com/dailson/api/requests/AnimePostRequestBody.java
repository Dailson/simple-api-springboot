package com.dailson.api.requests;

import javax.validation.constraints.NotEmpty;

public class AnimePostRequestBody {

	@NotEmpty(message = "The anime name cannot be empty ")
	private String name;
		
	public AnimePostRequestBody() {
		// Empty constructor
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
