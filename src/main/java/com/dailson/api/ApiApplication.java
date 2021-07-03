package com.dailson.api;

import java.util.Locale;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.dailson.api.domain.Anime;
import com.dailson.api.repositories.AnimeRepository; import com.github.javafaker.Faker; import lombok.extern.log4j.Log4j2; @Log4j2 @SpringBootApplication public class ApiApplication implements CommandLineRunner { 
	private AnimeRepository animeRepository;

	public ApiApplication(AnimeRepository animeRepository) {
		this.animeRepository = animeRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Initializing animes...");

		var fake = new Faker(new Locale("pt-BR"));

		for (var i = 0; i < 40; i++) {
			animeRepository.save(new Anime(null, fake.name().firstName(), fake.company().url()));
		}
		log.info("Finished the initialization");
	}

}