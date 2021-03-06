package com.dailson.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dailson.api.domain.Anime;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long>{
	
	List<Anime> findByName(String name);
}
