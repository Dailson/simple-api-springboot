package com.dailson.api.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.log4j.Log4j2;

@EnableWebSecurity
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	// O que estamos querendo proteger em uma requisição http?
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// Qualquer requisição, qualquer uma, deve passar por autênticação http básica.
		http.authorizeRequests()
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic();
		 
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// EncodePassword
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		// creates user in memory
		auth.inMemoryAuthentication()
			.withUser("root")
			.password(passwordEncoder.encode("test"))
			.roles("USER", "ADMIN")
			.and()
			.withUser("dailson")
			.password(passwordEncoder.encode("test"))
			.roles("USER");
			
			
	}

	
}
