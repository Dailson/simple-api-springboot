package com.dailson.api.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.log4j.Log4j2;

	

@EnableWebSecurity
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	/**
	 * Filters
	 * 
	 * BasicAuthenticationFilter // verify is the authentication is base64
	 * UsernamePasswordAuthenticationFilter // verity if there is a user and password on request
	 * DefaultLoginPageGeneratingFilter
	 * FilterSecurityInterceptor
	 * Authenticator -> authorization
	 */
	
	// What are you wanto to protect in a http resquest?
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// Any request, any one, must pass for a basic http authentication
		http.csrf().disable()
//		.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//			.and()
			.authorizeRequests()
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
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
