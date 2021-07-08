package com.dailson.api.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.dailson.api.domain.Anime;
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

		// Creates a list of animes
		PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeUtil.createOneStaticAnimeWithId()));
		
		// For any request to listAll() method, with any params, then return animePage list
		BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
			.thenReturn(animePage);
		
	}
	

	@Test
	@DisplayName("List - Returns list of anime inside page object when successful")
	void list_ReturnsListOfAnimeInsidePageObject_WhenSuccessful() {
		
		String expectedName = AnimeUtil.createOneStaticAnimeWithId().getName();
		
		Page<Anime> animePage = animeController.list(null).getBody();
	
		
		assertThat(animePage).isNotNull();
		
		assertThat(animePage.toList())
			.isNotEmpty()
			.hasSize(1);
		
		assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
	}
	
}
