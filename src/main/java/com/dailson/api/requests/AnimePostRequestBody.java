package com.dailson.api.requests;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.URL;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnimePostRequestBody {

	@NotEmpty(message = "The anime name cannot be EMPTY or NULL")
	@Schema(description = "This is the Anime's name", example = "Boruto", required = true)
	private String name;

	@URL(message = "The URL is invalid")
	@Schema(description = "This is the Anime's url", example = "http://www.boruto.com", required = true)
	private String url;
		
}
