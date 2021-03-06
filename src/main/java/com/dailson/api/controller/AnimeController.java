package com.dailson.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.log4j.Log4j2;

@RestController // Transform this class on a controller
@RequestMapping(path = "/animes")
@Log4j2
public class AnimeController {

	private AnimeService animeService;

	public AnimeController(AnimeService animeService) {
		this.animeService = animeService;
	}


	@GetMapping
	@Operation(summary = "List all animes paginated", 
			   description = "The default size is 20, use the parameter size  to change de default value",
			   tags = {"animes"})
	public ResponseEntity<Page<Anime>> list(@ParameterObject Pageable pageable) {
		return ResponseEntity.ok().body(animeService.listAll(pageable));
	}

	// Method used for testing. Need to be removed at the end of the project
	@GetMapping(path = "/all")
	public ResponseEntity <List<Anime>> listAllNoPageable() {
		return ResponseEntity.ok().body(animeService.listAllNoPageable());
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<Anime> findById(@PathVariable long id) {
		return ResponseEntity.ok().body(animeService.findByIdOrThrowBadRequestException(id));
	}

	@GetMapping(path = "/find")
	public ResponseEntity<List<Anime>> findByName(@RequestParam String name){
		return ResponseEntity.ok(animeService.findByName(name));
	}

	@GetMapping(path = "/by-id/{id}")
	public ResponseEntity<Anime> findByIdAuthenticationPrincipal(@PathVariable long id, 
			@AuthenticationPrincipal UserDetails userDetails){
		
		
		log.info(userDetails);
		return ResponseEntity.ok(animeService.findByIdOrThrowBadRequestException(id));
		
	}
	
	@PostMapping
	public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequestBody anime) {
		return new ResponseEntity<>(animeService.save(anime), HttpStatus.CREATED);
	}
		
	@PutMapping
	public ResponseEntity<Void> replace(@RequestBody @Valid AnimePutRequestBody anime) {
		animeService.replace(anime);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping(path = "/admin/{id}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Successful Operation"),
			@ApiResponse(responseCode = "400", description = "When Animes Does No Exists In The Database")
	})
	public ResponseEntity<Void> delete(@PathVariable long id){
		animeService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}