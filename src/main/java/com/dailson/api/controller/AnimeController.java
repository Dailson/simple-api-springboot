package com.dailson.api.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dailson.api.domain.Anime;
import com.dailson.api.util.DateUtils;

import lombok.extern.log4j.Log4j2;

@RestController // Transform this class on a controller
@RequestMapping(path = "/animes")
@Log4j2
public class AnimeController {

	private DateUtils dateUtilImplemetation;

	public AnimeController(DateUtils dateUtilImplemetation) {
		this.dateUtilImplemetation = dateUtilImplemetation;
	}

	@GetMapping(path = "list")
	public ResponseEntity<List<Anime>> listAll() {
		log.info(dateUtilImplemetation.formatLocalDateTimeDatabaseStyle(LocalDateTime.now()));
		return ResponseEntity.ok().body(List.of(new Anime("Anime 1"), new Anime("Anime 2")));
	}
	
}