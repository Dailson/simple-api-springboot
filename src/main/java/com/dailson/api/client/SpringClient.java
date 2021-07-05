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

		// @formatter:off
		ResponseEntity<Anime> entity = new RestTemplate()
				.getForEntity("http://localhost:8080/animes/{id}",
						Anime.class, 2);
		log.info(entity); 
		// @formatter:on

		// @formatter:off
		Anime object = new RestTemplate()
				.getForObject("http://localhost:8080/animes/{id}", 
						Anime.class, 2);
		log.info(object); 
		// @formatter:on

		// @formatter:off
		ResponseEntity<List<Anime>> exchangeGet = new RestTemplate().
				exchange("http://localhost:8080/animes/all", 
						HttpMethod.GET,
						null,
						new ParameterizedTypeReference<>() {});
		log.info(exchangeGet);
		// @formatter:on

		// @formatter:off
		var animeSaved = new Anime(null, "Bito", "http://www.bito.com.br");
		ResponseEntity<Anime> exchangePost = new RestTemplate()
				.exchange("http://localhost:8080/animes/",
						HttpMethod.POST,
						new HttpEntity<>(animeSaved),
						Anime.class);
		log.info("saved anime{}", exchangePost);
		// @formatter:on

		// @formatter:off
		Anime animeToBeUpdated = exchangePost.getBody();
		animeToBeUpdated.setName("GOMES");
		animeToBeUpdated.setUrl("http://www.gomes.com.br");

		ResponseEntity<Void> exchangePut = new RestTemplate()
				.exchange("http://localhost:8080/animes/",
						HttpMethod.PUT,
						new HttpEntity<>(animeToBeUpdated),
						Void.class);
		
		log.info("updated anime{}", exchangePut);
		// @formatter:on

		
		ResponseEntity<Void> exchangeDelete = new RestTemplate()
				.exchange("http://localhost:8080/animes/{id}",
						HttpMethod.DELETE,
						null,
						Void.class,
						5);
				
		log.info(exchangeDelete);
		
	}


}
