package com.dailson.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.dailson.api.domain.Anime;
import com.dailson.api.requests.AnimePostRequestBody;
import com.dailson.api.requests.AnimePutRequestBody;
import com.dailson.api.services.AnimeService;
import com.dailson.api.util.AnimeUtil;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {

	// This is used to creates and inject the mock object.
	// It is used when you want test the class itself
	@InjectMocks
	private AnimeController animeController;

	// This is used to creates the mock object to be injected
	// It is used for every class that is used inside MathApplication class
	// Here you can defines de behavior of the mock object
	@Mock
	private AnimeService animeServiceMock;

	@BeforeEach
	void setUp() {

		
		/* listAll(Pegeable)*/
		//Creates a dummy list of animes 
		PageImpl<Anime> animePageableList = new PageImpl<>(List.of(AnimeUtil.createValidAnimeWithId()));
		
		// For any request to list() method, with any params, then return animePageableList
		BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any())).thenReturn(animePageableList);

		
		/* listAllNoPageable()*/
		// Create a dummy list of animes
		List<Anime> animeList = List.of(AnimeUtil.createValidAnimeWithId());
		
		// For any request to listAllNoPegeable() method, then return animeList
		BDDMockito.when(animeServiceMock.listAllNoPageable()).thenReturn(animeList);

		
		/* findById*/
		// Creates a dummy anime object
		Anime animeToFindById = AnimeUtil.createValidAnimeWithId();
		
		BDDMockito.when(animeServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
			.thenReturn(animeToFindById);
		
		
		/* findByName*/
		// Creates a dummy anime object
		List<Anime> animeToFindByName = List.of(AnimeUtil.createValidAnimeWithId());
		
		BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
			.thenReturn(animeToFindByName);
		
		
		/* save*/
		Anime animeToBeReturnedAsSaved = AnimeUtil.createValidAnimeWithId();
		BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
			.thenReturn(animeToBeReturnedAsSaved);
		
		
		/* replace*/
		// For any request to replace(), do nothing
		BDDMockito.doNothing().when(animeServiceMock).replace(ArgumentMatchers.any(AnimePutRequestBody.class));
		
		
		/* delete*/
		// For any request to delete(), do nothing
		BDDMockito.doNothing().when(animeServiceMock).delete(ArgumentMatchers.anyLong());
	}

	@Test
	@DisplayName("List - Returns list of anime inside page object when successful")
	void list_ReturnsListOfAnimeInsidePageObject_WhenSuccessful() {

		String expectedName = AnimeUtil.createValidAnimeWithId().getName();

		Page<Anime> listAnime = animeController.list(null).getBody();

		assertThat(listAnime).isNotNull();
		assertThat(listAnime.toList()).isNotEmpty().hasSize(1);
		assertThat(listAnime.toList().get(0).getName()).isEqualTo(expectedName);
	}

	@Test
	@DisplayName("ListAllNoPageable - Returns list of all anime when successful")
	void listAll_ReturnListOfAllAnime_WhenSuccessful() {
		String expectedName = AnimeUtil.createValidAnimeWithId().getName();

		List<Anime> listAnime = animeController.listAllNoPageable().getBody();

		assertThat(listAnime).isNotNull();
		assertThat(listAnime).isNotEmpty();
		assertThat(listAnime).hasSize(1);
		assertThat(listAnime.get(0).getName()).isEqualTo(expectedName);

	}

	@Test
	@DisplayName("findById - Returns a anime when successful")
	void findById_ReturnAnime_WhenSuccessful() {
		Long expectedId = AnimeUtil.createValidAnimeWithId().getId();

		Anime animeFound = animeController.findById(1L).getBody();

		assertThat(animeFound).isNotNull();
		assertThat(animeFound.getId()).isNotNull();
		assertThat(animeFound.getId()).isEqualTo(expectedId);
	}

	@Test
	@DisplayName("findByName - Returns a list of anime when successful")
	void findByName_ReturnsListOfAnime_WhenSuccessfull() {
		String expectedName = AnimeUtil.createValidAnimeWithId().getName();

		List<Anime> listAnime = animeController.findByName("anime").getBody();

		assertThat(listAnime).isNotNull();
		assertThat(listAnime).isNotEmpty();
		assertThat(listAnime).hasSize(1);
		assertThat(listAnime.get(0).getName()).isEqualTo(expectedName);

	}

	@Test
	@DisplayName("findByName - Returns an empty list of anime when anime is not found")
	void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {

		// unsucessful behavior, put it inside the method
		BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString())).thenReturn(Collections.emptyList());

		List<Anime> listAnime = animeController.findByName("anime").getBody();

		assertThat(listAnime).isNotNull();
		assertThat(listAnime).isEmpty();

	}

	@Test
	@DisplayName("save - Persists a anime when successful")
	void save_PersistsAnime_WhenSuccessful() {
		Long expectedId = AnimeUtil.createValidAnimeWithId().getId();

		Anime animeSaved = animeController.save(AnimeUtil.createPostRequestBodyAnime()).getBody();

		assertThat(animeSaved).isNotNull();
		assertThat(animeSaved.getId()).isEqualTo(expectedId);
		assertThat(animeSaved.getName()).isEqualTo(animeSaved.getName());
	}
	
	@Test
	@DisplayName("replace - Updates anime when successful")
	void replace_UpdatesAnime_WhenSuccessful() {
			
		// "Fist way
		assertThatCode(() -> animeController.replace(AnimeUtil.createPutRequestBody()))
			.doesNotThrowAnyException();
		
		// Second way
		ResponseEntity<Void> entity = animeController.replace(AnimeUtil.createPutRequestBody());
		
		assertThat(entity).isNotNull();
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		
	}
	
	@Test
	@DisplayName("delete - Removes anime when successful")
	void delete_RemovesAnime_WhenSuccessful() {
			
		// "Fist way
		assertThatCode(() -> animeController.delete(1L)).doesNotThrowAnyException();
		
		// Second way
		ResponseEntity<Void> entity = animeController.delete(1L);
		
		assertThat(entity).isNotNull();
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		
	}
	
	
}








