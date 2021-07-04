package com.dailson.api.client;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.dailson.api.domain.Anime;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SpringClient {
	public static void main(String[] args) {
		ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}", Anime.class,
				2);

		log.info(entity);

		Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 2);

		log.info(object);

		// @formatter:off
		ResponseEntity<List<Anime>> exchangeGet = new RestTemplate().exchange("http://localhost:8080/animes/all",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<>() {});

		// @formatter:on

		log.info(exchangeGet);

//		var kingdom = new Anime(null, "Dailson", "http://www.dailson.com.br");
//		Anime kingdomSaved = new RestTemplate().postForObject("http://localhost:8080/animes/", kingdom, Anime.class);
//
//		log.info("Saved Anime{}", kingdomSaved);

		//@// @formatter:off
		
		var animeSaved = new Anime(null, "Bito", "http://www.bito.com.br");
		ResponseEntity<Anime> exchangePost = new RestTemplate().exchange("http://localhost:8080/animes/",
				HttpMethod.POST,
				new HttpEntity<>(animeSaved),
				Anime.class);
		
		log.info("saved anime{}", exchangePost);
		// @formatter:on

	}

}
