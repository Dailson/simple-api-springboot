package com.dailson.api.util;

import com.dailson.api.domain.Anime;
import com.dailson.api.requests.AnimePostRequestBody;
import com.dailson.api.requests.AnimePutRequestBody;

public class AnimeUtil {

	public static Anime createEmptyAnimeWithId() {
		return new Anime();
	}

	public static Anime createValidAnimeWithoutId() {
		String name = "Dailson";
		String url = "http://www.dailson.com.br";
		return new Anime(null, name, url);
	}

	public static Anime createValidAnimeWithId() {
		String name = "Dailson";
		String url = "http://www.dailson.com.br";
		Long id = 1L;
		return new Anime(id, name, url);
	}

	public static AnimePostRequestBody createPostRequestBodyAnime() {
		String name = "Dailson";
		String url = "http://www.dailson.com.br";
		return new AnimePostRequestBody(name, url);
	}

	public static AnimePutRequestBody createPutRequestBody() {
		String name = "Dailson";
		String url = "http://www.dailson.com.br";
		Long id = 1L;
		return new AnimePutRequestBody(id, name, url);
	}

}
