package com.dailson.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.dailson.api.domain.Anime;
import com.dailson.api.util.AnimeUtil;

@DataJpaTest
@DisplayName("Test for Anime repository")
class AnimeRepositoryTest {

	@Autowired
	private AnimeRepository animeRepository;
	
	
	@Test
	@DisplayName("Save - Creates anime when successful")
	void save_PersistAnime_WhenSuccessful() {
		Anime animeToBeSaved = AnimeUtil.createOneAnime();
		
		Anime savedAnime = animeRepository.save(animeToBeSaved);
		
		assertThat(savedAnime).isNotNull();
		assertThat(savedAnime.getId()).isNotNull();
		assertThat(savedAnime.getName()).isEqualTo(animeToBeSaved.getName());
				
		
	}

}
