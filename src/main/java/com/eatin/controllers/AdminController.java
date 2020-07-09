package com.eatin.controllers;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.auth.AuthenticationRequest;
import com.eatin.dto.auth.AuthenticationResponse;
import com.eatin.dto.korisnik.DostavljacDTO;
import com.eatin.dto.korisnik.DostavljacNoIdDTO;
import com.eatin.dto.korisnik.KorisnikDTO;
import com.eatin.dto.korisnik.ZaposleniDTO;
import com.eatin.dto.korisnik.ZaposleniNoIdDTO;
import com.eatin.jpa.Dostavljac;
import com.eatin.jpa.Korisnik;
import com.eatin.jpa.Uloga;
import com.eatin.repository.DostavljacRepository;
import com.eatin.repository.KorisnikRepository;
import com.eatin.repository.RestoranRepository;
import com.eatin.repository.UlogaRepository;
import com.eatin.repository.ZaposleniRepository;
import com.eatin.security.JwtUtil;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AdminController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private KorisnikRepository korisnikRepository;
	@Autowired
	private DostavljacRepository dostavljacRepository;
	@Autowired
	private ZaposleniRepository zaposleniRepository;
	@Autowired
	private UlogaRepository ulogaRepository;
	@Autowired
	private RestoranRepository restoranRepository;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//registracija novog admina
	@PostMapping("admin/register/admin")
	public ResponseEntity<String> registerUser(@Valid @RequestBody KorisnikDTO korisnikDTO) throws Exception {

		Korisnik korisnik = ObjectMapperUtils.map(korisnikDTO, Korisnik.class);
		
		korisnik.setAktivan(true);

		Optional<Uloga> uloga = ulogaRepository.findById(4);
		korisnik.setUloga(uloga.get());
		
		Korisnik sacuvaniKorisnik = korisnikRepository.save(korisnik);
	
		return new ResponseEntity<String>("Successfully added", HttpStatus.OK);

	}
	
	//registracija novog dostavljaca
	@PostMapping("admin/register/dostavljac")
	public ResponseEntity<String> registerUser(@Valid @RequestBody DostavljacNoIdDTO dostavljacNoIdDTO) throws Exception {

		Korisnik korisnik = ObjectMapperUtils.map(dostavljacNoIdDTO.getKorisnik(), Korisnik.class);
		
		korisnik.setAktivan(true);

		Optional<Uloga> uloga = ulogaRepository.findById(3);
		korisnik.setUloga(uloga.get());
		
		Korisnik sacuvaniKorisnik = korisnikRepository.save(korisnik);
		
		//System.out.println(dostavljacNoIdDTO.getPrevoznoSredstvo());
		jdbcTemplate
				.execute("insert into Dostava.Dostavljac(id_dostavljaca, prevozno_sredstvo)"
						+ " values(" + sacuvaniKorisnik.getIdKorisnika() + ", '" + dostavljacNoIdDTO.getPrevoznoSredstvo() + "');");

		return new ResponseEntity<String>("Successfully added", HttpStatus.OK);

	}
	
	//registracija novog zaposlenog
	@PostMapping("admin/register/zaposleni")
	public ResponseEntity<String> registerUser(@Valid @RequestBody ZaposleniNoIdDTO zaposleniNoIdDTO) throws Exception {
		
		if(restoranRepository.findAllByidRestorana(zaposleniNoIdDTO.getRestoran()).isEmpty()) {	
			return new ResponseEntity<String>("Restoran doesn't exist", HttpStatus.NOT_FOUND);
		}
		else {

			Korisnik korisnik = ObjectMapperUtils.map(zaposleniNoIdDTO.getKorisnik(), Korisnik.class);
			
			korisnik.setAktivan(true);

			Optional<Uloga> uloga = ulogaRepository.findById(2);
			korisnik.setUloga(uloga.get());
			
			Korisnik sacuvaniKorisnik = korisnikRepository.save(korisnik);
			
			jdbcTemplate
			.execute("insert into Dostava.Zaposleni(id_zaposlenog, funkcija_zaposlenog, id_restorana)"
					+ " values(" + sacuvaniKorisnik.getIdKorisnika() + ", '" + zaposleniNoIdDTO.getFunkcijaZaposlenog()+ "', " + zaposleniNoIdDTO.getRestoran() + ");");

			return new ResponseEntity<String>("Successfully added", HttpStatus.OK);
		}
	}
	
	//get svih dostavljaca
	@GetMapping("admin/dostavljac")
	public Collection<DostavljacDTO> getAllDostavljac() {

		return ObjectMapperUtils.mapAll(dostavljacRepository.findAll(), DostavljacDTO.class);
	}
	
	//get svih zaposlenih
	@GetMapping("admin/zaposleni")
	public Collection<ZaposleniDTO> getAllZaposleni() {

		return ObjectMapperUtils.mapAll(zaposleniRepository.findAll(), ZaposleniDTO.class);
	}
}
