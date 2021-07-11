package com.dailson.api.integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import com.dailson.api.domain.Anime;
import com.dailson.api.repositories.AnimeRepository;
import com.dailson.api.util.AnimeUtil;
import com.dailson.api.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class AnimeControllerIT {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@LocalServerPort
	private int port;
	
	@Autowired
	private AnimeRepository animeRepository;

	@Test
	@DisplayName("List - Returns list of anime inside page object when successful")
	void list_ReturnsListOfAnimeInsidePageObject_WhenSuccessful() {
		
		Anime animeSaved = animeRepository.save(AnimeUtil.createValidAnimeWithoutId());
		
		
		String expectedName = animeSaved.getName();

		PageableResponse<Anime> animePage = testRestTemplate
				.exchange("/animes", HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<Anime>>() {
				}).getBody();

		assertThat(animePage).isNotNull();
		assertThat(animePage.toList()).isNotEmpty().hasSize(1);
		assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
	}

}
