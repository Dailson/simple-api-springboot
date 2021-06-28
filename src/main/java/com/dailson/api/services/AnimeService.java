package com.dailson.api.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.dailson.api.domain.Anime;
import com.dailson.api.mappers.AnimeMapper;
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
		return animeRepository.save(AnimeMapper.INSTANCE.toAnime(anime));
	}

	public void delete(long id) {
		animeRepository.delete(findByIdOrThrowBadRequestException(id));
	}

	public Anime replace(AnimePutRequestBody anime) {
		var savedAnime = findByIdOrThrowBadRequestException(anime.getId());
		var mappedAnime = AnimeMapper.INSTANCE.toAnime(anime);

		anime.setId(savedAnime.getId());

		return animeRepository.save(mappedAnime);
	}
}
