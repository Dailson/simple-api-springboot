package com.dailson.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dailson.api.domain.Anime;
import com.dailson.api.requests.AnimePostRequestBody;
import com.dailson.api.requests.AnimePutRequestBody;
import com.dailson.api.services.AnimeService;

@RestController // Transform this class on a controller
@RequestMapping(path = "/animes")
//@Log4j2
public class AnimeController {

	private AnimeService animeService;

	public AnimeController(AnimeService animeService) {
		this.animeService = animeService;
	}

	@PostMapping
	public ResponseEntity<Anime> save(@RequestBody AnimePostRequestBody anime) {
		return new ResponseEntity<>(animeService.save(anime), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<Anime>> listAll() {
		return ResponseEntity.ok().body(animeService.listAll());
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<Anime> findById(@PathVariable long id) {
		return ResponseEntity.ok().body(animeService.findByIdOrThrowBadRequestException(id));
	}

	@GetMapping(path = "/find")
	public ResponseEntity<List<Anime>> findByName(@RequestParam String name){
		return ResponseEntity.ok(animeService.findByName(name));
	}
	
	
	@PutMapping
	public ResponseEntity<Void> update(@RequestBody AnimePutRequestBody anime) {
		animeService.replace(anime);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id){
		animeService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}