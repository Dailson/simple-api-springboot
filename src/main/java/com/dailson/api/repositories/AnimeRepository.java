package com.dailson.api.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import com.dailson.api.domain.Anime;

@Repository
public class AnimeRepository {

	private static List<Anime> animes;

	static {
		animes = new ArrayList<>(
				List.of(new Anime(1L, "Anime 01"), new Anime(2L, "Anime 02")));
	}
	
	public List<Anime> listAnime(){
		return animes;
	}
	
	public Anime findById(long id) {
		return animes.stream()
				.filter(anime -> anime.getId().equals(id))
				.findFirst()
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found"));
	}

	public static Anime save(Anime anime) {
		anime.setId(ThreadLocalRandom.current().nextLong(4, 100));
		animes.add(anime);
		return anime;
	}
}
