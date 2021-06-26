package com.dailson.api.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dailson.api.domain.Anime;
import com.dailson.api.services.AnimeService;
import com.dailson.api.util.DateUtils;

import lombok.extern.log4j.Log4j2;

@RestController // Transform this class on a controller
@RequestMapping(path = "/animes")
@Log4j2
public class AnimeController {

	private DateUtils dateUtilImplemetation;
	private AnimeService animeService;

	public AnimeController(DateUtils dateUtils, AnimeService animeService) {
		this.dateUtilImplemetation = dateUtils;
		this.animeService = animeService;
	}

	@PostMapping
	public ResponseEntity<Anime> save(@RequestBody Anime anime) {
		log.info(dateUtilImplemetation.formatLocalDateTimeDatabaseStyle(LocalDateTime.now()));
		return new ResponseEntity<>(animeService.save(anime), HttpStatus.CREATED);
	}

}