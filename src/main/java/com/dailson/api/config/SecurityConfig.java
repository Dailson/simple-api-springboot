package com.dailson.api.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dailson.api.services.DevDojoUserDetailsService;

import lombok.extern.log4j.Log4j2;

	

@EnableWebSecurity
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private DevDojoUserDetailsService devDojoUserDetailsService;

	
	public SecurityConfig(DevDojoUserDetailsService devDojoUserDetailsService) {
		this.devDojoUserDetailsService = devDojoUserDetailsService;
	}	
	
	
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
			.antMatchers("/animes/admin/**").hasRole("ADMIN")
			.antMatchers("/animes/**").hasRole("USER")
			.antMatchers("/actuator/**").permitAll() // This is jus for test
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
		log.info("Password encoded {}", passwordEncoder.encode("test"));
		
		// creates user in memory
		auth.inMemoryAuthentication()
			.withUser("dailson")
			.password(passwordEncoder.encode("test"))
			.roles("USER", "ADMIN")
			.and()
			.withUser("bito")
			.password(passwordEncoder.encode("test"))
			.roles("USER");
			
			auth.userDetailsService(devDojoUserDetailsService)
					.passwordEncoder(passwordEncoder);
	}

	
}
