package com.dailson.api.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.dailson.api.domain.Anime;
import com.dailson.api.domain.DevDojoUser;
import com.dailson.api.repositories.AnimeRepository;
import com.dailson.api.repositories.DevDojoUserRepository;
import com.dailson.api.requests.AnimePostRequestBody;
import com.dailson.api.util.AnimeUtil;
import com.dailson.api.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase

// At the beginning of each test method, the previous data ara considered dirty
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AnimeControllerIT {

	@Autowired
	@Qualifier(value = "testRestTemplateRoleUser")
	private TestRestTemplate testRestTemplateRoleUser;

	@Autowired
	@Qualifier(value = "testRestTemplateRoleAdmin")
	private TestRestTemplate testRestTemplateRoleAdmin;
	
	@Autowired
	private AnimeRepository animeRepository;
	
	@Autowired
	private DevDojoUserRepository devDojoUserRepositoy;

	private static final DevDojoUser USER = new DevDojoUser(
			null, 
			"Bito Gomes",
			"bito",
			"{bcrypt}$2a$10$TRfOi0p0dTmMbtfgZ.5FM.QPmSND0BCNWCS.zTEywzQGccS0nGWX2",
			"ROLE_USER");
	
	
	private static final DevDojoUser ADMIN = new DevDojoUser(
			null, 
			"Dailson Gomes",
			"dailson",
			"{bcrypt}$2a$10$TRfOi0p0dTmMbtfgZ.5FM.QPmSND0BCNWCS.zTEywzQGccS0nGWX2",
			"ROLE_USER,ROLE_ADMIN");
	
	@TestConfiguration
	@Lazy
	static class Config{
		@Bean(name = "testRestTemplateRoleUser")
		public TestRestTemplate testRestTemplateRoleUserCreator(@Value("${local.server.port}") int port) {
			RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
					.rootUri("http://localhost:" + port)
					.basicAuthentication("bito", "test");
			
			return new TestRestTemplate(restTemplateBuilder);
		}
		
		@Bean(name = "testRestTemplateRoleAdmin")
		public TestRestTemplate testRestTemplateRoleAdminCreator(@Value("${local.server.port}") int port) {
			RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
					.rootUri("http://localhost:" + port)
					.basicAuthentication("dailson", "test");
			
			return new TestRestTemplate(restTemplateBuilder);
		}
	}
	
	
	@Test
	@DisplayName("List - Returns list of anime inside page object when successful")
	void list_ReturnsListOfAnimeInsidePageObject_WhenSuccessful() {
		Anime animeSaved = animeRepository.save(AnimeUtil.createValidAnimeWithId());
		devDojoUserRepositoy.save(USER);
		
		String expectedName = animeSaved.getName();
		
		PageableResponse<Anime> animePage = testRestTemplateRoleUser
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
		devDojoUserRepositoy.save(USER);

		
		String expectedName = animeSaved.getName();

		List<Anime> animeList = testRestTemplateRoleUser
				.exchange("/animes/all", HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>>() {
				}).getBody();

		assertThat(animeList)
			.isNotNull()
			.isNotEmpty()
			.hasSize(1);
		assertThat(animeList.get(0).getName()).isEqualTo(expectedName);
	}

	@Test
	@DisplayName("findById - Returns a anime when successful")
	void findById_ReturnAnime_WhenSuccessful() {
		Anime animeSaved = animeRepository.save(AnimeUtil.createValidAnimeWithId());
		devDojoUserRepositoy.save(USER);

		
		Long expectedId = animeSaved.getId();

		Anime animeFound = testRestTemplateRoleUser.getForObject("/animes/{id}", Anime.class, expectedId);

		assertThat(animeFound).isNotNull();
		assertThat(animeFound.getId()).isEqualTo(expectedId);
	}

	@Test
	@DisplayName("findByName - Returns a list of anime when successful")
	void findByName_ReturnsListOfAnime_WhenSuccessfull() {
		Anime animeSaved = animeRepository.save(AnimeUtil.createValidAnimeWithId());
		devDojoUserRepositoy.save(USER);

		
		String expectedName = animeSaved.getName();

		String url = String.format("/animes/find?name=%s", expectedName);
		List<Anime> animeList = testRestTemplateRoleUser
				.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>>() {
				}).getBody();

		assertThat(animeList)
			.isNotNull()
			.isNotEmpty()
			.hasSize(1);
		assertThat(animeList.get(0).getName()).isEqualTo(expectedName);
	}

	@Test
	@DisplayName("findByName - Returns an empty list of anime when anime is not found")
	void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {
		devDojoUserRepositoy.save(USER);

		List<Anime> animeList = testRestTemplateRoleUser.exchange("/animes/find?name=bito", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Anime>>() {
				}).getBody();

		assertThat(animeList).isNotNull();
		assertThat(animeList).isEmpty();

	}

	@Test
	@DisplayName("save - Persists a anime when successful")
	void save_PersistsAnime_WhenSuccessful() {
		AnimePostRequestBody animePostRequestBody = AnimeUtil.createPostRequestBodyAnime();
		devDojoUserRepositoy.save(USER);

		ResponseEntity<Anime> animeResponseEntity = testRestTemplateRoleUser.postForEntity("/animes", animePostRequestBody,
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
		devDojoUserRepositoy.save(USER);

		animeSaved.setName("New Anime");

		ResponseEntity<Void> animeResponseEntity = testRestTemplateRoleUser.exchange("/animes", HttpMethod.PUT,
				new HttpEntity<>(animeSaved), Void.class);

		assertThat(animeResponseEntity).isNotNull();
		assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

	}

	@Test
	@DisplayName("delete - Removes anime when successful")
	void delete_RemovesAnime_WhenSuccessful() {
		Anime animeSaved = animeRepository.save(AnimeUtil.createValidAnimeWithId());
		devDojoUserRepositoy.save(ADMIN);

		ResponseEntity<Void> animeResponseEntity = testRestTemplateRoleAdmin.exchange("/animes/admin/{id}",
				HttpMethod.DELETE, null,
				Void.class, animeSaved.getId());

		assertThat(animeResponseEntity).isNotNull();
		assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}

	@Test
	@DisplayName("delete - Returns 403 when user is not admin")
	void delete_Returns403_WhenUserIsNotAdmin() {
		Anime animeSaved = animeRepository.save(AnimeUtil.createValidAnimeWithId());
		devDojoUserRepositoy.save(USER);

		ResponseEntity<Void> animeResponseEntity = testRestTemplateRoleUser.exchange("/animes/admin/{id}",
				HttpMethod.DELETE, null,
				Void.class, animeSaved.getId());

		assertThat(animeResponseEntity).isNotNull();
		assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}
}
