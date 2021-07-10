package com.dailson.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.dailson.api.domain.Anime;
import com.dailson.api.util.AnimeUtil;

import lombok.extern.log4j.Log4j2;

@DataJpaTest
@DisplayName("Test for Anime repository")
@Log4j2
class AnimeRepositoryTest {

	@Autowired
	private AnimeRepository animeRepository;

	// ################# SUCCESSFUL CASE ##############
	// ################################################

	@Test
	@DisplayName("Save - Persists anime when successful")
	void save_PersistAnime_WhenSuccessful() {

		// Scenery
		Anime animeToBeSaved = AnimeUtil.createValidAnimeWithoutId();

		// Action
		Anime animeSaved = animeRepository.save(animeToBeSaved);

		// Verification
		assertThat(animeSaved).isNotNull();
		assertThat(animeSaved.getId()).isNotNull();
		assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());

	}

	@Test
	@DisplayName("Update - Update anime when successfull")
	void update_UpdaterAnime_WhenSuccessful() {

		// Scenery
		Anime animeToBeSaved = AnimeUtil.createValidAnimeWithoutId();
		Anime animeSaved = animeRepository.save(animeToBeSaved);

		// Action
		animeSaved.setName("Dailson Update");
		Anime animeUpdated = animeRepository.save(animeSaved);
		log.info(animeUpdated.getName());
		// Verification
		assertThat(animeUpdated).isNotNull();
		assertThat(animeUpdated.getId()).isNotNull();
		assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());
	}

	@Test
	@DisplayName("Delete - Remove anieme when successfull")
	void delete_RemoveAnime_WhenSuccessful() {

		// Scenery
		Anime animeToBeSaved = AnimeUtil.createValidAnimeWithoutId();
		Anime animeSaved = animeRepository.save(animeToBeSaved);

		// Action
		animeRepository.delete(animeSaved);
		Optional<Anime> animeFoundOptional = animeRepository.findById(animeSaved.getId());

		assertThat(animeFoundOptional).isNotPresent();
		log.info("Anime removed!");

	}

	@Test
	@DisplayName("FindById - Find Anime by id")
	void findById_FindAnimeById_WhenSuccessful() {

		// Scenery
		Anime animeToBeSaved = AnimeUtil.createValidAnimeWithoutId();
		Anime animeSaved = animeRepository.save(animeToBeSaved);

		// Action
		Optional<Anime> animeFoundOptional = animeRepository.findById(animeSaved.getId());

		// Verification
		assertThat(animeFoundOptional).isPresent();
		assertThat(animeFoundOptional.get().getId()).isNotNull();
		assertThat(animeFoundOptional.get().getId()).isEqualTo(animeSaved.getId());
	}

	@Test
	@DisplayName("FindByName - Return list of anime")
	void findByName_ReturnsListOfAnime_WhenSuccessful() {
		// Scenery
		Anime animeToBeSaved = AnimeUtil.createValidAnimeWithoutId();
		Anime animeSaved = animeRepository.save(animeToBeSaved);

		String animeName = animeSaved.getName();

		// Action
		List<Anime> animeList = animeRepository.findByName(animeName);

		// Verification
		assertThat(animeList).isNotEmpty();
		assertThat(animeList).contains(animeSaved);
	}

	@Test
	@DisplayName("List - Returns pageable list of anime")
	void list_ReturnsPageableList_WhenSuccessful() {
		// Scenery
		List<Anime> AnimeListToBeSaved = List.of(AnimeUtil.createValidAnimeWithoutId());
		List<Anime> AnimeListSaved = new ArrayList<>();

		for (Anime anime : AnimeListToBeSaved) {
			Anime animeSaved = animeRepository.save(anime);
			AnimeListSaved.add(animeSaved);
		}

		// Action
		List<Anime> animeFoundList = animeRepository.findAll();

		// Verification
		assertThat(animeFoundList).isNotEmpty();
		assertThat(animeFoundList.get(0)).isNotNull();

	}

	// ################## EDGE CASE ###################
	// ################################################

	@Test
	@DisplayName("FindByName - Return empty list when no anime is found")
	void findByName_ReturnsEmptyLis_WhenAnimeIsNotFound() {
		// Scenery
		List<Anime> animeList = animeRepository.findByName("xaxa");

		// Verification
		assertThat(animeList).isEmpty();
	}

	// ################ EXCEPTIONS CASE ###############
	// ################################################

	@Test
	@DisplayName("Save - ThrowsConstrainViolationException_WhenNameIsEmpty")
	void save_ThrowsConstrainViolationException_WhenNameIsEmpty() {

		// Scenary
		Anime emptyAnime = AnimeUtil.createEmptyAnimeWithId();

		// Action and Validation

		// Method one
		//assertThatThrownBy(() -> this.animeRepository.save(emptyAnime))
		//  .isInstanceOf(ConstraintViolationException.class);
		
		
		// Method two
		assertThatExceptionOfType(ConstraintViolationException.class)
			.isThrownBy(() -> this.animeRepository.save(emptyAnime))
			.withMessageContaining("The Anime name cannot be EMPTY OR NULL");
	}

}
