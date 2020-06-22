package com.eatin.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.auth.AuthenticationRequest;
import com.eatin.dto.auth.AuthenticationResponse;
import com.eatin.dto.korisnik.KorisnikDTO;
import com.eatin.jpa.Korisnik;
import com.eatin.jpa.Uloga;
import com.eatin.repository.KorisnikRepository;
import com.eatin.repository.UlogaRepository;
import com.eatin.security.JwtUtil;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private KorisnikRepository korisnikRepository;
	@Autowired
	private UlogaRepository ulogaRepository;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(
			@Valid @RequestBody AuthenticationRequest authenticationRequest) {

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
				authenticationRequest.getPassword()));

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@Valid @RequestBody KorisnikDTO korisnikDTO) throws Exception {

		Korisnik korisnik = ObjectMapperUtils.map(korisnikDTO, Korisnik.class);

		Optional<Uloga> uloga = ulogaRepository.findById(1);
		korisnik.setUloga(uloga.get());

		Korisnik sacuvaniKorisnik = korisnikRepository.save(korisnik);
		jdbcTemplate
				.execute("insert into Dostava.Klijent(id_klijenta) values(" + sacuvaniKorisnik.getIdKorisnika() + ");");

		return new ResponseEntity<String>("Successfully added", HttpStatus.OK);

	}

}
