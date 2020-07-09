package com.eatin.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.eatin.filters.JwtRequestFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsService  userDetailsService;
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	// autentifikacija
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	// autorizacija
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and()
				// remove crsf and state in session
				.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				// authorize requests
				.and().authorizeRequests()
				// klijent
				.antMatchers("/lokacija", "/klijent-porudzbina", "/profil").hasAuthority("Klijent")
				// dostavljac
				.antMatchers("/dostavljac-porudzbina", "/dostavljac-porudzbina-isporucena/{id}}",
						"/dostavljac-porudzbina-prihvacena/{id}}", "/dostavljac-porudzbina-gotova")
				.hasAuthority("Dostavljac")
				// zaposleni
				.antMatchers("/zaposleni-porudzbina", "/zaposleni-porudzbina-gotova/{id}").hasAuthority("Zaposleni")
				// admin
				.antMatchers("/admin/register/dostavljac", "/admin/register/admin", "/admin/register/zaposleni",
						"/porudzbina", "/porudzbina/{id}",
						"/admin/dostavljac",
						"/admin/zaposleni",
						"/admin/update/admin/{id}",
						"/admin/update/klijent/{id}",
						"/admin/update/dostavljac/{id}")
				.hasAuthority("Admin")
				// permit all
				.antMatchers("/login", "/register", "/confirm-account", "/artikl", "/artikl/{id}", "/restoran",
						"/restoran/{id}",
						"/tip-artikla",
						"/tip-restorana"
						)
				.permitAll()
				// authenticated
				.anyRequest().authenticated();

		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
		configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
				"/configuration/security", "/swagger-ui.html", "/webjars/**");

	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
