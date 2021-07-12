package com.dailson.api.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import lombok.extern.log4j.Log4j2;

@EnableWebSecurity
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	// What are you wanto to protect in a http resquest?
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// Any request, any one, must pass for a basic http authentication
		http.csrf()
//		.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
			.and()
			.authorizeRequests()
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
