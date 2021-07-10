package com.dailson.api.requests;

import javax.validation.constraints.NotEmpty;

public class AnimePutRequestBody {

	private Long id;

	@NotEmpty(message = "The Anime name cannot be empty")
	private String name;

	private String url;
	
	public AnimePutRequestBody() {
		// Do nothing
	}


	
	
	/**
	 * @param id
	 * @param name
	 * @param url
	 */
	public AnimePutRequestBody(Long id, String name, String url) {
		this.id = id;
		this.name = name;
		this.url = url;
	}




	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**	
	 * @param id the id to set	
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		AnimePutRequestBody other = (AnimePutRequestBody) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}


}
