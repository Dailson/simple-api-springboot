package com.dailson.api.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dailson.api.domain.Anime;
import com.dailson.api.exceptions.BadRequestException;
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
				.orElseThrow(() -> new BadRequestException("Anime not found!"));
	}

	public List<Anime> findByName(String name) {
		return animeRepository.findByName(name);
	}
	
	@Transactional
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
