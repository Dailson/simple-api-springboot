package com.dailson.api.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.dailson.api.domain.Anime;
import com.dailson.api.exceptions.BadRequestException;
import com.dailson.api.repositories.AnimeRepository;
import com.dailson.api.util.AnimeUtil;


@ExtendWith(SpringExtension.class)
class AnimeServiceTest {

	// This is used to creates and inject the mock object.
	// It is used when you want test the class itself
	@InjectMocks
	private AnimeService animeService;

	// This is used to creates the mock object to be injected
	// It is used for every class that is used inside MathApplication class
	// Here you can defines de behavior of the mock object
	@Mock
	private AnimeRepository animeRepositoryMock;

	@BeforeEach
	void setUp() {

		
		/* listAll(Pegeable)*/
		//Creates a dummy list of animes 
		PageImpl<Anime> animePageableList = new PageImpl<>(List.of(AnimeUtil.createValidAnimeWithId()));
		
		// For any request to list() method, with any params, then return animePageableList
		BDDMockito.when(animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(animePageableList);

		
		/* listAllNoPageable()*/
		// Create a dummy list of animes
		List<Anime> animeList = List.of(AnimeUtil.createValidAnimeWithId());
		
		// For any request to listAllNoPegeable() method, then return animeList
		BDDMockito.when(animeRepositoryMock.findAll()).thenReturn(animeList);

		
		/* findById*/
		// Creates a dummy anime object
		Anime animeToFindById = AnimeUtil.createValidAnimeWithId();
		
		BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
			.thenReturn(Optional.of(animeToFindById));
		
		
		/* findByName*/
		// Creates a dummy anime object
		List<Anime> animeToFindByName = List.of(AnimeUtil.createValidAnimeWithId());
		
		BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
			.thenReturn(animeToFindByName);
		
		
		/* save*/
		Anime animeToBeReturnedAsSaved = AnimeUtil.createValidAnimeWithId();
		BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)))
			.thenReturn(animeToBeReturnedAsSaved);
		
		
		/* delete*/
		// For any request to delete(), do nothing
		BDDMockito.doNothing().when(animeRepositoryMock).delete(ArgumentMatchers.any(Anime.class));
	}

	@Test
	@DisplayName("listAll - Returns list of anime inside page object when successful")
	void listAll_ReturnsListOfAnimeInsidePageObject_WhenSuccessful() {

		String expectedName = AnimeUtil.createValidAnimeWithId().getName();

		Page<Anime> listAnime = animeService.listAll(PageRequest.of(1, 1));

		assertThat(listAnime).isNotNull();
		assertThat(listAnime.toList()).isNotEmpty().hasSize(1);
		assertThat(listAnime.toList().get(0).getName()).isEqualTo(expectedName);
	}


	@Test
	@DisplayName("listAllNoPageable - Returns list of all anime when successful")
	void listAllNoPageable_ReturnListOfAllAnime_WhenSuccessful() {
		String expectedName = AnimeUtil.createValidAnimeWithId().getName();

		List<Anime> listAnime = animeService.listAllNoPageable();

		assertThat(listAnime).isNotNull();
		assertThat(listAnime).isNotEmpty();
		assertThat(listAnime).hasSize(1);
		assertThat(listAnime.get(0).getName()).isEqualTo(expectedName);

	}

	@Test
	@DisplayName("findByIdOrThrowBadRequestException - Returns a anime when successful")
	void findByIdOrThrowBadRequestException_ReturnAnime_WhenSuccessful() {
		Long expectedId = AnimeUtil.createValidAnimeWithId().getId();

		Anime animeFound = animeService.findByIdOrThrowBadRequestException(1L);

		assertThat(animeFound).isNotNull();
		assertThat(animeFound.getId()).isNotNull();
		assertThat(animeFound.getId()).isEqualTo(expectedId);
	}

	@Test
	@DisplayName("findByIdOrThrowBadRequestException - throws BadRequestException when anime is not found")
	void findByIdOrThrowBadRequestException_throusBadRequestException_WhenAnimeIsNotFound() {
		
		BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
			.thenReturn(Optional.empty());
		
		assertThatExceptionOfType(BadRequestException.class)
			.isThrownBy(() -> animeService.findByIdOrThrowBadRequestException(1L));
		
	}
	
	
	@Test
	@DisplayName("findByName - Returns a list of anime when successful")
	void findByName_ReturnsListOfAnime_WhenSuccessfull() {
		String expectedName = AnimeUtil.createValidAnimeWithId().getName();

		List<Anime> listAnime = animeService.findByName("anime");

		assertThat(listAnime).isNotNull();
		assertThat(listAnime).isNotEmpty();
		assertThat(listAnime).hasSize(1);
		assertThat(listAnime.get(0).getName()).isEqualTo(expectedName);

	}

	@Test
	@DisplayName("findByName - Returns an empty list of anime when anime is not found")
	void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {

		// unsucessful behavior, put it inside the method
		BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString())).thenReturn(Collections.emptyList());

		List<Anime> listAnime = animeService.findByName("anime");

		assertThat(listAnime).isNotNull();
		assertThat(listAnime).isEmpty();

	}

	@Test
	@DisplayName("save - Persists a anime when successful")
	void save_PersistsAnime_WhenSuccessful() {
		Long expectedId = AnimeUtil.createValidAnimeWithId().getId();

		Anime animeSaved = animeService.save(AnimeUtil.createPostRequestBodyAnime());

		assertThat(animeSaved).isNotNull();
		assertThat(animeSaved.getId()).isEqualTo(expectedId);
		assertThat(animeSaved.getName()).isEqualTo(animeSaved.getName());
	}
	
	@Test
	@DisplayName("replace - Updates anime when successful")
	void replace_UpdatesAnime_WhenSuccessful() {
			
		// "Fist way
		assertThatCode(() -> animeService.replace(AnimeUtil.createPutRequestBody()))
			.doesNotThrowAnyException();

	}
	
	@Test
	@DisplayName("delete - Removes anime when successful")
	void delete_RemovesAnime_WhenSuccessful() {
			
		// "Fist way
		assertThatCode(() -> animeService.delete(1L)).doesNotThrowAnyException();
		
	}
	
}
