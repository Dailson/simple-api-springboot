package com.dailson.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dailson.api.domain.Anime;
import com.dailson.api.repositories.AnimeRepository;

@Service
public class AnimeService {

	private AnimeRepository animeRepository;

	public AnimeService(AnimeRepository animeRepository) {
		this.animeRepository = animeRepository;
	}

	public Anime save(Anime anime) {
		return animeRepository.save(anime);
	}

	public List<Anime> listAll(){
		return animeRepository.findAll();
	}

	public Optional<Anime> findById(long id) {
		return animeRepository.findById(id);
	}
}
