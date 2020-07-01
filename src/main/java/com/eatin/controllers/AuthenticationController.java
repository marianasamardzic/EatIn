package com.eatin.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.auth.AuthenticationRequest;
import com.eatin.dto.auth.AuthenticationResponse;
import com.eatin.dto.korisnik.KorisnikDTO;
import com.eatin.error.NotActivatedException;
import com.eatin.jpa.Korisnik;
import com.eatin.jpa.Token;
import com.eatin.jpa.Uloga;
import com.eatin.mail.NotificationService;
import com.eatin.repository.KorisnikRepository;
import com.eatin.repository.TokenRepository;
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
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private TokenRepository tokenRepository;

	private org.slf4j.Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(
			@Valid @RequestBody AuthenticationRequest authenticationRequest) throws Exception {

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
				authenticationRequest.getPassword()));

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		// provera da li je aktivan
		Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(userDetails.getUsername());
		if (korisnik.getAktivan() == false) {
			throw new NotActivatedException("Korisnik nije aktivirao ovaj nalog");
		}
		final String jwt = jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@Valid @RequestBody KorisnikDTO korisnikDTO) throws Exception {

		Korisnik korisnik = ObjectMapperUtils.map(korisnikDTO, Korisnik.class);

		Optional<Uloga> uloga = ulogaRepository.findById(1);
		korisnik.setUloga(uloga.get());
		korisnik.setAktivan(false);

		Korisnik sacuvaniKorisnik = korisnikRepository.save(korisnik);
		jdbcTemplate
				.execute("insert into Dostava.Klijent(id_klijenta) values(" + sacuvaniKorisnik.getIdKorisnika() + ");");

		// kreiranje tokena
		Token token = new Token();
		token.setKorisnik(sacuvaniKorisnik);
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date());
		token.setDatumKreiranja(date);
		token.setToken(UUID.randomUUID().toString());
		this.tokenRepository.save(token);

		// slanje mejla
		try {
			this.notificationService.sendNotification(korisnik.getEmailKorisnika(), token.getToken());
		} catch (MailException e) {
			logger.info("Error sending email: " + e.getMessage());
		}

		return new ResponseEntity<String>("Verification email is send to user", HttpStatus.OK);

	}

	@GetMapping("/confirm-account")
	public void confirmUserAccount(@RequestParam("token") String confirmationToken) {

		Token token = tokenRepository.findByToken(confirmationToken);
		if (token != null) {

			Korisnik korisnik = korisnikRepository.findByEmailKorisnika(token.getKorisnik().getEmailKorisnika());
			korisnik.setAktivan(true);
			korisnikRepository.save(korisnik);

		}

	}

}
