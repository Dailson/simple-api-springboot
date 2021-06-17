package com.dailson.api.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	public AnimeController(DateUtils dateUtilImplemetation, AnimeService animeService) {
		this.dateUtilImplemetation = dateUtilImplemetation;
		this.animeService = animeService;
	}

	@GetMapping()
	public ResponseEntity<List<Anime>> listAll() {
		log.info(dateUtilImplemetation.formatLocalDateTimeDatabaseStyle(LocalDateTime.now()));
		return ResponseEntity.ok().body(animeService.listAll());
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Anime> findById(@PathVariable long id){
		return ResponseEntity.ok().body(animeService.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<Anime> save(@RequestBody Anime anime){
		return new ResponseEntity<>(AnimeService.save(anime), HttpStatus.CREATED);
	}
	
}