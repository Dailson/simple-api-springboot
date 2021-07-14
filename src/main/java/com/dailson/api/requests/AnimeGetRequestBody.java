package com.dailson.api.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnimeGetRequestBody {

	private String name;

	private String url;
	
}
