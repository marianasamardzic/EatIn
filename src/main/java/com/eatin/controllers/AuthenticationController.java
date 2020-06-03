package com.eatin.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.AuthenticationRequest;
import com.eatin.dto.AuthenticationResponse;
import com.eatin.dto.KorisnikDTO;
import com.eatin.dto.KorisnikJWTDTO;
import com.eatin.jpa.Korisnik;
import com.eatin.jpa.Uloga;
import com.eatin.repository.KorisnikRepository;
import com.eatin.repository.UlogaRepository;
import com.eatin.security.JwtUtil;

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

	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Invalid username or password");
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

	@PostMapping("/register")
	public ResponseEntity<KorisnikJWTDTO> registerUser(@Valid @RequestBody KorisnikDTO korisnikDTO) throws Exception {
		Korisnik korisnik = ObjectMapperUtils.map(korisnikDTO, Korisnik.class);
		Optional<Uloga> uloga = ulogaRepository.findById(korisnikDTO.getUlogaId());
		if (uloga == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		korisnik.setUloga(uloga.get());
		korisnikRepository.save(korisnik);
		final UserDetails userDetails = userDetailsService.loadUserByUsername(korisnik.getEmailKorisnika());
		final String jwt = jwtUtil.generateToken(userDetails);
		KorisnikJWTDTO res = ObjectMapperUtils.map(korisnik, KorisnikJWTDTO.class);
		res.setJwt(jwt);
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}

}
