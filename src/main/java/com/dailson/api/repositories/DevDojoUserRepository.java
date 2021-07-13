package com.dailson.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dailson.api.domain.DevDojoUser;

@Repository
public interface DevDojoUserRepository extends JpaRepository<DevDojoUser, Long>{
	
	DevDojoUser findByUsername(String username);
}
