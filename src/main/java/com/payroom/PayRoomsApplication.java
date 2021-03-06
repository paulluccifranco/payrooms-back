package com.payroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.payroom.security.JWTAuthorizationFilter;

@SpringBootApplication
@ComponentScan({ "com.payroom.controller", "com.payroom.service", "com.payroom.dao", "com.payroom.daoImpl",
		"com.payroom.serviceImpl", "com.payroom.util", "com.payroom.security" })
@EntityScan("com.payroom.model")
@EnableJpaRepositories("com.payroom.dao")
public class PayRoomsApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PayRoomsApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(PayRoomsApplication.class);
	}

	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
					.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests()
					.antMatchers(HttpMethod.POST, "/api/v1.0/login", "/api/v1.0/users", "/api/v1.0/social-login")
					.permitAll().anyRequest().authenticated();
			http.cors();
			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		}

	}

}
