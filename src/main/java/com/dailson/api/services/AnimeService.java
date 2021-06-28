package com.dailson.api.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.dailson.api.domain.Anime;
import com.dailson.api.repositories.AnimeRepository;
import com.dailson.api.requests.AnimePostRequestBody;
import com.dailson.api.requests.AnimePutRequestBody;

@Service
public class AnimeService {

	private AnimeRepository animeRepository;

	public AnimeService(AnimeRepository animeRepository) {
		this.animeRepository = animeRepository;
	}

	public List<Anime> listAll() {
		return animeRepository.findAll();
	}

	public Anime findByIdOrThrowBadRequestException(long id) {
		return animeRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found!"));
	}

	public Anime save(AnimePostRequestBody anime) {
		return animeRepository.save(new Anime(null, anime.getName()));
	}

	public void delete(long id) {
		animeRepository.delete(findByIdOrThrowBadRequestException(id));
	}

	public Anime replace(AnimePutRequestBody anime) {
		findByIdOrThrowBadRequestException(anime.getId());
		return animeRepository.save(new Anime(anime.getId(), anime.getName()));
	}
}
