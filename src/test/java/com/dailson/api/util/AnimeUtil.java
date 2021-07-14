package com.dailson.api.util;

import com.dailson.api.domain.Anime;
import com.dailson.api.requests.AnimePostRequestBody;
import com.dailson.api.requests.AnimePutRequestBody;

public class AnimeUtil {

	public static Anime createEmptyAnimeWithId() {
		return Anime.builder().build();
	}

	public static Anime createValidAnimeWithoutId() {
		return Anime.builder()
				.name("Dailson")
				.url("http://www.dailson.com.br")
				.build();
	}
	public static Anime createValidAnimeWithId() {
		return Anime.builder()
				.id(1L)
				.name("Dailson")
				.url("http://www.dailson.com.br")
				.build();
	}

	public static AnimePostRequestBody createPostRequestBodyAnime() {
		return AnimePostRequestBody.builder()
				.name("Dailson")
				.url("http://www.dailson.com.br")
				.build();
	}

	public static AnimePutRequestBody createPutRequestBody() {
		return AnimePutRequestBody.builder()
				.id(1L)
				.name("Dailson")
				.url("http://www.dailson.com.br")
				.build();
	}

}
