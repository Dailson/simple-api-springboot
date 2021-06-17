package com.dailson.api.repositories;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import com.dailson.api.domain.Anime;

@Repository
public class AnimeRepository {

	private List<Anime> animes = List.of(
			new Anime(1L, "Anime 01"),
			new Anime(2L, "Anime 03"),
			new Anime(3L, "Anime 03"));
	
	
	public List<Anime> listAnime(){
		return animes;
	}
	
	public Anime findById(long id) {
		return animes.stream()
				.filter(anime -> anime.getId().equals(id))
				.findFirst()
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found"));
	}
}
