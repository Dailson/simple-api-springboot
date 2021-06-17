package com.dailson.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dailson.api.domain.Anime;
import com.dailson.api.repositories.AnimeRepository;

@Service
public class AnimeService {

	private AnimeRepository animeRepository;

	public AnimeService(AnimeRepository animeRepository) {
		this.animeRepository = animeRepository;
	}

	public List<Anime> listAll() {
		return animeRepository.listAnime();
	}

	public Anime findById(long id) {
		return animeRepository.findById(id);
	}

	public static Anime save(Anime anime) {
		return AnimeRepository.save(anime);
	}

	public void delete(long id) {
		animeRepository.delete(id);
	}

}
