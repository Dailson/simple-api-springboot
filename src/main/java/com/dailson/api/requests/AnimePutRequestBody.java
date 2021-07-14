package com.dailson.api.requests;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnimePutRequestBody {

	private Long id;

	@NotEmpty(message = "The Anime name cannot be empty")
	private String name;

	private String url;

}
