package com.dailson.api.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.dailson.api.domain.Anime;
import com.dailson.api.repositories.AnimeRepository;
import com.dailson.api.requests.AnimePostRequestBody;
import com.dailson.api.util.AnimeUtil;
import com.dailson.api.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase

// At the beginning of each test method, the previous data ara considered dirty
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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

		Anime animeSaved = animeRepository.save(AnimeUtil.createValidAnimeWithId());

		String expectedName = animeSaved.getName();

		PageableResponse<Anime> animePage = testRestTemplate
				.exchange("/animes", HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<Anime>>() {
				}).getBody();

		assertThat(animePage).isNotNull();
		assertThat(animePage.toList()).isNotEmpty().hasSize(1);
		assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
	}

	@Test
	@DisplayName("ListAllNoPageable - Returns list of all anime when successful")
	void listAll_ReturnListOfAllAnime_WhenSuccessful() {
		Anime animeSaved = animeRepository.save(AnimeUtil.createValidAnimeWithId());

		String expectedName = animeSaved.getName();

		List<Anime> animeList = testRestTemplate
				.exchange("/animes/all", HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>>() {
				}).getBody();

		assertThat(animeList).isNotNull();
		assertThat(animeList).isNotEmpty();
		assertThat(animeList).hasSize(1);
		assertThat(animeList.get(0).getName()).isEqualTo(expectedName);
	}

	@Test
	@DisplayName("findById - Returns a anime when successful")
	void findById_ReturnAnime_WhenSuccessful() {
		Anime animeSaved = animeRepository.save(AnimeUtil.createValidAnimeWithId());

		Long expectedId = animeSaved.getId();

		Anime animeFound = testRestTemplate.getForObject("/animes/{id}", Anime.class, expectedId);

		assertThat(animeFound).isNotNull();
		assertThat(animeFound.getId()).isEqualTo(expectedId);
	}

	@Test
	@DisplayName("findByName - Returns a list of anime when successful")
	void findByName_ReturnsListOfAnime_WhenSuccessfull() {
		Anime animeSaved = animeRepository.save(AnimeUtil.createValidAnimeWithId());

		String expectedName = animeSaved.getName();

		String url = String.format("/animes/find?name=%s", expectedName);
		List<Anime> animeList = testRestTemplate
				.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>>() {
				}).getBody();

		assertThat(animeList).isNotNull();
		assertThat(animeList).isNotEmpty();
		assertThat(animeList).hasSize(1);
		assertThat(animeList.get(0).getName()).isEqualTo(expectedName);
	}

	@Test
	@DisplayName("findByName - Returns an empty list of anime when anime is not found")
	void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {

		List<Anime> animeList = testRestTemplate.exchange("/animes/find?name=bito", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Anime>>() {
				}).getBody();

		assertThat(animeList).isNotNull();
		assertThat(animeList).isEmpty();

	}

	@Test
	@DisplayName("save - Persists a anime when successful")
	void save_PersistsAnime_WhenSuccessful() {

		AnimePostRequestBody animePostRequestBody = AnimeUtil.createPostRequestBodyAnime();

		ResponseEntity<Anime> animeResponseEntity = testRestTemplate.postForEntity("/animes", animePostRequestBody,
				Anime.class);

		assertThat(animeResponseEntity).isNotNull();
		assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(animeResponseEntity.getBody()).isNotNull();
		assertThat(animeResponseEntity.getBody().getId()).isNotNull();
	}

	@Test
	@DisplayName("replace - Updates anime when successful")
	void replace_UpdatesAnime_WhenSuccessful() {

		Anime animeSaved = animeRepository.save(AnimeUtil.createValidAnimeWithId());

		animeSaved.setName("New Anime");

		ResponseEntity<Void> animeResponseEntity = testRestTemplate.exchange("/animes", HttpMethod.PUT,
				new HttpEntity<>(animeSaved), Void.class);

		assertThat(animeResponseEntity).isNotNull();
		assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

	}

	@Test
	@DisplayName("delete - Removes anime when successful")
	void delete_RemovesAnime_WhenSuccessful() {

		Anime animeSaved = animeRepository.save(AnimeUtil.createValidAnimeWithId());

		ResponseEntity<Void> animeResponseEntity = testRestTemplate.exchange("/animes/{id}", HttpMethod.DELETE, null,
				Void.class, animeSaved.getId());

		assertThat(animeResponseEntity).isNotNull();
		assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}

}
