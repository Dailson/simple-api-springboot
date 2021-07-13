package com.dailson.api.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dailson.api.repositories.DevDojoUserRepository;


@Service
public class DevDojoUserDetailsService implements UserDetailsService{

	private DevDojoUserRepository devDojoUserRepository;
	
	public DevDojoUserDetailsService(DevDojoUserRepository devDojoUserRepository) {
		this.devDojoUserRepository = devDojoUserRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return Optional.ofNullable(devDojoUserRepository.findByUsername(username))
				.orElseThrow(() -> new UsernameNotFoundException("DevDojo User not foud"));
	}



}
